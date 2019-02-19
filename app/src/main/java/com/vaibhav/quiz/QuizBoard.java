package com.vaibhav.quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class QuizBoard extends AppCompatActivity implements ListToDetails {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_board);
        getQuizList();
    }

    private void getQuizList() {
        Quizzes quizzes = new Quizzes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, quizzes, "quizzes");
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
