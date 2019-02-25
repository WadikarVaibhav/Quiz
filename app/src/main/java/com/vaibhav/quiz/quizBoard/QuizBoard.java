package com.vaibhav.quiz.quizBoard;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.vaibhav.quiz.*;
import com.vaibhav.quiz.mainActivity.MainActivityConstants;
import com.vaibhav.quiz.db.DBCommunicator;
import com.vaibhav.quiz.db.DatabaseHelper;
import com.vaibhav.quiz.model.Option;
import com.vaibhav.quiz.model.Question;
import com.vaibhav.quiz.model.Summary;
import com.vaibhav.quiz.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This is second activity which manages question list fragment and question details fragment
 */

public class QuizBoard extends AppCompatActivity implements QuizCommunicator, DBCommunicator {


    private List<Question> questions;
    private Summary summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_board);
        summary = new Summary(getIntent().getExtras().getInt(MainActivityConstants.USER_ID));
        questions = getQuestions();
        createQuestionsList();
    }

    /**
     * Load questions file in Json format in a string
     * @return
     */
    private String load() {
        String json = null;
        try {
            final AssetManager assets = getApplicationContext().getAssets();
            final String[] names = assets.list( "" );
            InputStream is = getApplicationContext().getAssets().open(QuizBoardConstants.QUESTIONS_FILE);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private List<Option> getOptions(JSONArray optionInJSONFormat) {
        List<Option> options = new ArrayList<Option>();
        try {
            for (int i = 0; i < optionInJSONFormat.length(); i++){
                JSONObject jsonObject = optionInJSONFormat.getJSONObject(i);
                String option = jsonObject.getString(getOptionLabel(i+1));
                options.add(new Option(option));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return options;
    }

    /**
     * Fetch questions one by one from Json array
     * @return
     */
    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(load());
            JSONArray questionFromJSON = obj.getJSONArray(QuizBoardConstants.QUESTIONS);
            for (int i = 0; i < questionFromJSON.length(); i++){
                JSONObject jsonObject = questionFromJSON.getJSONObject(i);
                int id = jsonObject.getInt(QuizBoardConstants.ID);
                String question = jsonObject.getString(QuizBoardConstants.QUESTION);
                int answer = jsonObject.getInt(QuizBoardConstants.ANSWER);
                JSONArray optionInJSONFormat = jsonObject.getJSONArray(QuizBoardConstants.OPTIONS);
                questions.add(new Question(id, question, getOptions(optionInJSONFormat), answer));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }


    private void createQuestionsList() {
        QuestionsList questionList = new QuestionsList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, questionList);
        transaction.commit();
    }

    @Override
    public void nextQuestion(int nextQuestion, int selectedAnswer) {
        QuestionDetails questionDetails = new QuestionDetails();
        questionDetails.setArguments(getQuestionDetails(nextQuestion));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, questionDetails);
        transaction.addToBackStack(QuizBoardConstants.BACKSTACK_MESSAGE_TO_REPLACE_WITH_NEXT_QUESTION);
        transaction.commit();
    }

    private String getOptionLabel(int number) {
        switch(number) {
            case 1:
                return QuizBoardConstants.OPTION_1;
            case 2:
                return QuizBoardConstants.OPTION_2;
            case 3:
                return QuizBoardConstants.OPTION_3;
            case 4:
                return QuizBoardConstants.OPTION_4;
            default:
                return null;
        }
    }

    private Bundle getQuestionDetails(int questionId) {
        Bundle bundle = new Bundle();
        for (Question question : questions) {
            if (question.getQuestionId() == questionId) {
                bundle.putInt(QuizBoardConstants.QUESTION_ID, question.getQuestionId());
                bundle.putString(QuizBoardConstants.QUESTION_TEXT, question.getQuestion());
                bundle.putInt(QuizBoardConstants.ANSWER, question.getAnswer());
                List<Option> options = question.getOptions();
                for (int index=0; index<options.size(); index++) {
                    bundle.putString(getOptionLabel(index+1), options.get(index).getOptionText());
                }
                bundle.putString(QuizBoardConstants.BUTTON_TEXT, buttonText(question.getQuestionId()));
            }
        }
        return bundle;
    }


    private void generateQuizSummary() {
        summary.setEndTime(Calendar.getInstance().getTime());
        summary.setUserScore(getFinalUserScore());
        insert(summary);
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
            return QuizBoardConstants.BUTTON_TEXT_NEXT;
        }
        return QuizBoardConstants.BUTTON_TEXT_FINISH;
    }

    private int getFinalUserScore() {
        int userScore = 0;
        for (Question question : questions) {
            if (question.getSelectedAnswer() == true) {
                userScore++;
            }
        }
        return userScore;
    }

    private String formatDate(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat(QuizBoardConstants.DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    private Intent getFinalScoreReportData() {
        Intent returnToMainActivity = new Intent();
        returnToMainActivity.putExtra(MainActivityConstants.SCORE, summary.getUserScore());
        returnToMainActivity.putExtra(MainActivityConstants.START_TIME, formatDate(summary.getStartTime()));
        returnToMainActivity.putExtra(MainActivityConstants.END_TIME, formatDate(summary.getEndTime()));
        returnToMainActivity.putExtra(MainActivityConstants.USER_NAME, fetchUserName(summary.getUserId()));
        return returnToMainActivity;
    }

    private String fetchUserName(int userId) {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        String username = db.getUserName(userId);
        return username;
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public void insert(Summary summary) {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.insert(summary);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
