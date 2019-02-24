package com.vaibhav.quiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.vaibhav.quiz.*;
import com.vaibhav.quiz.communication.QuizCommunicator;
import com.vaibhav.quiz.constants.ActivityConstants;
import com.vaibhav.quiz.db.DatabaseHelper;
import com.vaibhav.quiz.fragment.QuestionDetails;
import com.vaibhav.quiz.fragment.QuestionsList;
import com.vaibhav.quiz.model.Question;
import com.vaibhav.quiz.model.Summary;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuizBoard extends AppCompatActivity implements QuizCommunicator {

    private List<Question> questions;
    private Summary summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_board);
        summary = new Summary();
        fetchQuestionsFromDB();
        initQuizSummary();
        createQuestionsList();
    }

    private void fetchQuestionsFromDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        questions = dbHelper.getAllQuestions();
    }

    private void initQuizSummary() {
        summary.setUser(getIntent().getExtras().getInt(ActivityConstants.USER_ID));
        summary.setStartDate(Calendar.getInstance().getTime());
        summary.setEndDate(Calendar.getInstance().getTime());
        summary.setScore(0);
    }

    private void createQuestionsList() {
        QuestionsList questionList = new QuestionsList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, questionList, ActivityConstants.QUESTIONS_LIST_LABEL);
        transaction.commit();
    }

    @Override
    public void nextQuestion(int nextQuestion, int selectedAnswer) {
        QuestionDetails questionFragment = new QuestionDetails();
        questionFragment.setArguments(getQuestionDetails(nextQuestion));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, questionFragment, ActivityConstants.QUESTION_LABEL);
        transaction.addToBackStack(ActivityConstants.BACKSTACK_MESSAGE_TO_REPLACE_WITH_NEXT_QUESTION);
        transaction.commit();
    }

    private Bundle getQuestionDetails(int questionId) {
        Bundle bundle = new Bundle();
        for (Question question : questions) {
            if (question.getQuestionId() == questionId) {
                bundle.putInt(ActivityConstants.QUESTION_ID, question.getQuestionId());
                bundle.putString(ActivityConstants.QUESTION_TEXT, question.getQuestion());
                bundle.putInt(ActivityConstants.ANSWER, question.getAnswer());
                bundle.putString(ActivityConstants.OPTION_1, question.getOption1());
                bundle.putString(ActivityConstants.OPTION_2, question.getOption2());
                bundle.putString(ActivityConstants.OPTION_3, question.getOption3());
                bundle.putString(ActivityConstants.OPTION_4, question.getOption4());
                bundle.putString(ActivityConstants.BUTTON_TEXT, buttonText(question.getQuestionId()));
            }
        }
        return bundle;
    }

    private void saveQuizSummary() {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.insert(summary);
    }

    private void generateQuizSummary() {
        summary.setEndDate(Calendar.getInstance().getTime());
        summary.setScore(getFinalScore());
        saveQuizSummary();
        setResult(Activity.RESULT_OK, getFinalScoreReportData());
        finish();
    }

    @Override
    public void onNextQuestion(int nextQuestionId, int selectedAnswer) {
        updateScore(nextQuestionId - 1, selectedAnswer);
        if (nextQuestionId > questions.size()) {
            generateQuizSummary();
        } else {
            nextQuestion(nextQuestionId, selectedAnswer);
        }
    }

    private void updateScore(int questionId, int selectedAnswer) {
        for (Question question : questions) {
            if (question.getQuestionId() == questionId && selectedAnswer == question.getAnswer()) {
                question.setSelectedAnswer(true);
            }
        }
    }

    private String buttonText(int questionIndex) {
        if (questionIndex < questions.size()) {
            return ActivityConstants.BUTTON_TEXT_NEXT;
        }
        return ActivityConstants.BUTTON_TEXT_FINISH;
    }

    private int getFinalScore() {
        int score = 0;
        for (Question question : questions) {
            if (question.getSelectedAnswer() == true) {
                score++;
            }
        }
        return score;
    }

    private String formatDate(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat(ActivityConstants.DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    private Intent getFinalScoreReportData() {
        Intent returnToMainActivity = new Intent();
        returnToMainActivity.putExtra(ActivityConstants.SCORE, summary.getScore());
        returnToMainActivity.putExtra(ActivityConstants.START_TIME, formatDate(summary.getStartDate()));
        returnToMainActivity.putExtra(ActivityConstants.END_TIME, formatDate(summary.getEndDate()));
        returnToMainActivity.putExtra(ActivityConstants.USER_NAME, summary.getUser());
        return returnToMainActivity;
    }

}
