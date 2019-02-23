package com.vaibhav.quiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.vaibhav.quiz.*;
import com.vaibhav.quiz.db.DatabaseHelper;
import com.vaibhav.quiz.fragment.QuestionFragment;
import com.vaibhav.quiz.fragment.QuestionListFragment;
import com.vaibhav.quiz.model.Question;
import com.vaibhav.quiz.model.ScoreBoard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuizBoard extends AppCompatActivity implements QuizCommunicator {

    private List<Question> questions;
    private ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_board);
        scoreBoard = new ScoreBoard();
        fetchQuestionsFromDB();
        initQuizSummary();
        questionList();
    }

    private void initQuizSummary() {
        scoreBoard.setUser(getIntent().getExtras().getInt("userId"));
        scoreBoard.setStartDate(Calendar.getInstance().getTime());
        scoreBoard.setEndDate(Calendar.getInstance().getTime());
        scoreBoard.setScore(0);
    }

    private void fetchQuestionsFromDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        questions = dbHelper.getAllQuestion();
    }

    private void questionList() {
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

    private Bundle questionDetailsBundle(int questionId) {
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
    public void nextQuestion(int nextQuestion, int selectedAnswer) {
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(questionDetailsBundle(nextQuestion));
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

    private void scoreCard() {
        scoreBoard.setEndDate(Calendar.getInstance().getTime());
        scoreBoard.setScore(finalScore());
        setResult(Activity.RESULT_OK, getReturnIntent());
        finish();
    }

    @Override
    public void onNextQuestion(int nextQuestionId, int selectedAnswer) {
        updateScore(nextQuestionId-1, selectedAnswer);
        if (nextQuestionId > questions.size()) {
            scoreCard();
        } else {
            nextQuestion(nextQuestionId, selectedAnswer);
        }
    }

    private int finalScore() {
        int score = 0;
        for (Question question: questions) {
            if (question.getSelectedAnswer() == true) {
                score++;
            }
        }
        return score;
    }

    private String formatDate(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yy h.mm.a");
        return simpleDateFormat.format(date);
    }

    private Intent getReturnIntent() {
        Intent returnToMainActivity = new Intent();
        returnToMainActivity.putExtra("score", scoreBoard.getScore());

        returnToMainActivity.putExtra("start", formatDate(scoreBoard.getStartDate()));
        returnToMainActivity.putExtra("end", formatDate(scoreBoard.getEndDate()));
        returnToMainActivity.putExtra("user", scoreBoard.getUser());

        return returnToMainActivity;
    }

}
