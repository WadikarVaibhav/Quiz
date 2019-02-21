package com.vaibhav.quiz;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class QuizBoard extends AppCompatActivity implements ListToDetails {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_board);
        getQuestions();
    }

    private void getQuestions() {
        Questions questions = new Questions();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, questions, "questions");
        transaction.commit();
    }

    @Override
    public void showQuestions(int quiz) {
        Question question = new Question();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, question, "question");
        transaction.commit();
    }
}
