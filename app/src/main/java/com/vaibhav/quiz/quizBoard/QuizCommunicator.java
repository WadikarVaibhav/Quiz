package com.vaibhav.quiz.quizBoard;

public interface QuizCommunicator {

    void nextQuestion(int questionId, int selectedAnswer);
    void onNextQuestion(int questionId, int selectedAnswer);

}
