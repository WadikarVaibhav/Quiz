package com.vaibhav.quiz;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
        QuestionListFragment questions = new QuestionListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, questions, "questions");
        transaction.commit();
    }

    private String getButtonText(int index) {
        if (index < 4) {
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
    public void showQuestionOnFragment(int selectedQuestionId, int selectedAnswer) {
        if (selectedAnswer != -1) {
            updateScoreBoard(selectedQuestionId, selectedAnswer);
        }
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(getQuestionDetailsInBundle(++selectedQuestionId));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, questionFragment, "questionFragment");
        transaction.commit();
    }

    @Override
    public void updateScoreBoard(int questionId, int selectedAnswer) {
        for (Question question: questions) {
            if (question.getQuestionId() == questionId) {
                if (selectedAnswer == question.getAnswer()) {
                    question.setSelectedAnswer(true);
                }
            }
        }
    }
}
