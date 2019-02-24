package com.vaibhav.quiz.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.vaibhav.quiz.communication.UserdataCommunicator;
import com.vaibhav.quiz.constants.ActivityConstants;
import com.vaibhav.quiz.db.DatabaseHelper;
import com.vaibhav.quiz.R;
import com.vaibhav.quiz.model.User;
import com.vaibhav.quiz.fragment.FormFragment;
import com.vaibhav.quiz.fragment.ScoreCardFragment;

public class MainActivity extends AppCompatActivity implements UserdataCommunicator {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createUserRegistrationForm();
    }

    @Override
    public void submitUser(User user) {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        int userId = db.insert(user);
        Intent intent = new Intent(MainActivity.this, QuizBoard.class);
        intent.putExtra(ActivityConstants.USER_ID, userId);
        startActivityForResult(intent, ActivityConstants.REQUEST_CODE);
    }

    private void createUserRegistrationForm() {
        FormFragment form = new FormFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, form, ActivityConstants.FORM_LABEL);
        transaction.commit();
    }

    private void createScoreCard(Intent intent) {
        Bundle bundle = getScoreCardData(intent);
        ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
        scoreCardFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, scoreCardFragment, ActivityConstants.SCORE_CARD_LABEL);
        transaction.commitAllowingStateLoss();
    }

    private Bundle getScoreCardData(Intent data) {
        Bundle bundle = new Bundle();
        bundle.putInt(ActivityConstants.SCORE, data.getIntExtra(ActivityConstants.SCORE, 0));
        bundle.putString(ActivityConstants.START_TIME, data.getStringExtra(ActivityConstants.START_TIME));
        bundle.putString(ActivityConstants.END_TIME, data.getStringExtra(ActivityConstants.END_TIME));
        bundle.putInt(ActivityConstants.USER_NAME, data.getIntExtra(ActivityConstants.USER_NAME, 0));
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ActivityConstants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                createScoreCard(data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(ActivityConstants.QUIT_MESSAGE_TITLE).setMessage(ActivityConstants.QUIT_MESSAGE)
            .setPositiveButton(ActivityConstants.QUIT_MESSAGE_YES, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton(ActivityConstants.QUIT_MESSAGE_NO, null)
            .show();
    }

}
