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
import com.vaibhav.quiz.UserDataCommunicator;
import com.vaibhav.quiz.db.DatabaseHelper;
import com.vaibhav.quiz.QuizCommunicator;
import com.vaibhav.quiz.R;
import com.vaibhav.quiz.model.User;
import com.vaibhav.quiz.fragment.FormFragment;
import com.vaibhav.quiz.fragment.ScoreCardFragment;

public class MainActivity extends AppCompatActivity implements UserDataCommunicator {

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userRegistrationForm();
    }

    @Override
    public void submitUser(User user) {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        int userId = db.insert(user);
        Intent intent = new Intent(MainActivity.this, QuizBoard.class);
        intent.putExtra("userId", userId);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void userRegistrationForm() {
        FormFragment form = new FormFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, form, "form");
        transaction.commit();
    }

    private void scoreCard(Intent intent) {
        Bundle bundle = scoreCardBundle(intent);
        ScoreCardFragment scoreCardFragment = new ScoreCardFragment();
        scoreCardFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, scoreCardFragment, "scoreCardFragment");
        transaction.commitAllowingStateLoss();
    }

    private Bundle scoreCardBundle(Intent data) {
        Bundle bundle = new Bundle();
        bundle.putInt("score", data.getIntExtra("score", 0));
        bundle.putString("start", data.getStringExtra("start"));
        bundle.putString("end", data.getStringExtra("end"));
        bundle.putInt("name", data.getIntExtra("user", 0));
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                scoreCard(data);
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Quit Quiz").setMessage("Do you want to quit this quiz?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton("No", null)
            .show();
    }

}
