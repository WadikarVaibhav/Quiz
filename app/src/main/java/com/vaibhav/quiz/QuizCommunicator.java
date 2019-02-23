package com.vaibhav.quiz;

import com.vaibhav.quiz.model.User;

public interface QuizCommunicator {

    public void nextQuestion(int questionId, int selectedAnswer);
    public void onNextQuestion(int questionId, int selectedAnswer);

}
