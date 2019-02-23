package com.vaibhav.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class QuizBoard extends AppCompatActivity implements ListToDetails {

    private List<Question> questions;
    private ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_board);
        scoreBoard = new ScoreBoard();
        fetchQuestionsFromDB();
        setQuizSummary();
        showQuestionListInFragment();
    }

    private void setQuizSummary() {
        scoreBoard.setUser(getIntent().getExtras().getInt("userId"));
        scoreBoard.setStartDate(Calendar.getInstance().getTime());
        scoreBoard.setEndDate(Calendar.getInstance().getTime());
        scoreBoard.setScore(0);
    }

    private void fetchQuestionsFromDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        questions = dbHelper.getAllQuestion();
    }

    private void showQuestionListInFragment() {
        QuestionListFragment questionList = new QuestionListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, questionList, "questions");
        transaction.commit();
    }

    private String getButtonText(int questionIndex) {
        if (questionIndex < questions.size()) {
            return "Next";
        }
        return "Finish";
    }

    private Bundle getQuestionDetailsInBundle(int questionId) {
        Bundle bundle = new Bundle();
        for (Question question: questions) {
            if (question.getQuestionId() == questionId) {
                bundle.putInt("questionId", question.getQuestionId());
                bundle.putString("question", question.getQuestion());
                bundle.putInt("answer", question.getAnswer());
                bundle.putString("option1", question.getOption1());
                bundle.putString("option2", question.getOption2());
                bundle.putString("option3", question.getOption3());
                bundle.putString("option4", question.getOption4());
                bundle.putString("btnText", getButtonText(question.getQuestionId()));
            }
        }
        return bundle;
    }

    @Override
    public void showQuestionOnFragment(int nextQuestion, int selectedAnswer) {
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(getQuestionDetailsInBundle(nextQuestion));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, questionFragment, "questionFragment");
        transaction.commit();
    }

    private void updateScore(int questionId, int selectedAnswer) {
        for (Question question: questions) {
            if (question.getQuestionId() == questionId && selectedAnswer == question.getAnswer()) {
                question.setSelectedAnswer(true);
            }
        }
    }

    private void showFinalScoreCard() {
        scoreBoard.setEndDate(Calendar.getInstance().getTime());
        scoreBoard.setScore(getFinalScore());
        setResult(Activity.RESULT_OK, getReturnIntent());
        finish();
    }

    @Override
    public void updateScoreBoard(int nextQuestion, int selectedAnswer) {
        updateScore(nextQuestion-1, selectedAnswer);
        if (nextQuestion >= questions.size()) {
            showFinalScoreCard();
        } else {
            showQuestionOnFragment(nextQuestion, selectedAnswer);
        }
    }

    private int getFinalScore() {
        int score = 0;
        for (Question question: questions) {
            if (question.getSelectedAnswer() == true) {
                score++;
            }
        }
        return score;
    }

    private Intent getReturnIntent() {
        Intent returnToMainActivity = new Intent();
        returnToMainActivity.putExtra("score", scoreBoard.getScore());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        returnToMainActivity.putExtra("start", simpleDateFormat.format(scoreBoard.getStartDate()));
        returnToMainActivity.putExtra("end", simpleDateFormat.format(scoreBoard.getEndDate()));
        returnToMainActivity.putExtra("user", scoreBoard.getUser());

        return returnToMainActivity;
    }

    @Override
    public void submitUser(User user) {

    }
}
