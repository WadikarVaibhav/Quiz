package com.vaibhav.quiz.model;

import java.util.List;

public class Question {

    private final int questionId;
    private final String question;
    private final List<Option> options;
    private final int answer;
    private boolean selectedAnswer;

    public Question(int questionId, String question, List<Option> options, int answer) {
        this.questionId = questionId;
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.selectedAnswer = false;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public List<Option> getOptions() {
        return  this.options;
    }

    public int getAnswer() {
        return answer;
    }

    public boolean getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(boolean selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
