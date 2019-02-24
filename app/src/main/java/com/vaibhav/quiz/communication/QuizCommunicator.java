package com.vaibhav.quiz.communication;

public interface QuizCommunicator {

    void nextQuestion(int questionId, int selectedAnswer);
    void onNextQuestion(int questionId, int selectedAnswer);

}
