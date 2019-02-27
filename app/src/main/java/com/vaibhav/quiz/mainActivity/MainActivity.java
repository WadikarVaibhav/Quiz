package com.vaibhav.quiz.mainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.vaibhav.quiz.db.DatabaseConstants;
import com.vaibhav.quiz.db.DatabaseHelper;
import com.vaibhav.quiz.model.User;
import com.vaibhav.quiz.quizBoard.QuizBoard;
import com.vaibhav.quiz.R;

public class MainActivity extends AppCompatActivity implements UserdataCommunicator {

    User previousUser = null;
    int prevUserId = 0;
    int previousUserScore = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPreviousUserData();
        createUserRegistrationForm();
    }

    private void getPreviousUserData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        previousUser = databaseHelper.getPreviousUserData();
    }

    @Override
    public void sendUserIdToQuiz(int userId) {
        Intent intent = new Intent(MainActivity.this, QuizBoard.class);
        intent.putExtra(MainActivityConstants.USER_ID, userId);
        startActivityForResult(intent, MainActivityConstants.REQUEST_CODE);
    }

    private Bundle getPreviousUserDataInBundle() {
        Bundle bundle = new Bundle();
        if (previousUser != null) {
            bundle.putString(DatabaseConstants.FIRST_NAME, previousUser.getFirstname());
            bundle.putString(DatabaseConstants.LAST_NAME, previousUser.getLastname());
            bundle.putString(DatabaseConstants.NICK_NAME, previousUser.getNickname());
            bundle.putInt(DatabaseConstants.AGE, previousUser.getAge());
            bundle.putInt(DatabaseConstants.SCORE, previousUserScore);
        }
        return bundle;
    }

    @Override
    public void startNewQuiz() {
        getPreviousUserData();
        UserRegistration form = new UserRegistration();
        form.setArguments(getPreviousUserDataInBundle());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, form, MainActivityConstants.FORM_LABEL);
        transaction.commit();
    }

    private void createUserRegistrationForm() {
        UserRegistration form = new UserRegistration();
        form.setArguments(getPreviousUserDataInBundle());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, form, MainActivityConstants.FORM_LABEL);
        transaction.commit();
    }


    private void createScoreCard(Intent intent) {
        Bundle bundle = getScoreCardData(intent);
        ScoreReport scoreCardFragment = new ScoreReport();
        scoreCardFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, scoreCardFragment, MainActivityConstants.SCORE_CARD_LABEL);
        transaction.commitAllowingStateLoss();
    }

    private Bundle getScoreCardData(Intent data) {
        Bundle bundle = new Bundle();
        bundle.putInt(MainActivityConstants.SCORE, data.getIntExtra(MainActivityConstants.SCORE, 0));
        bundle.putString(MainActivityConstants.START_TIME, data.getStringExtra(MainActivityConstants.START_TIME));
        bundle.putString(MainActivityConstants.END_TIME, data.getStringExtra(MainActivityConstants.END_TIME));
        bundle.putString(MainActivityConstants.USER_NAME, data.getStringExtra(MainActivityConstants.USER_NAME));
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MainActivityConstants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                createScoreCard(data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(MainActivityConstants.QUIT_MESSAGE_TITLE).setMessage(MainActivityConstants.QUIT_MESSAGE)
            .setPositiveButton(MainActivityConstants.QUIT_MESSAGE_YES, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton(MainActivityConstants.QUIT_MESSAGE_NO, null)
            .show();
    }

}
