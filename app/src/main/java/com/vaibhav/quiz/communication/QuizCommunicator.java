package com.vaibhav.quiz.communication;

public interface QuizCommunicator {

    public void nextQuestion(int questionId, int selectedAnswer);
    public void onNextQuestion(int questionId, int selectedAnswer);

}
