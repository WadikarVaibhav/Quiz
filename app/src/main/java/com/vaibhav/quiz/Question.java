package com.vaibhav.quiz;

public class Question {

    private int questionId;
    private String question;
    private int answer;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String alias;
    private boolean selectedAnswer;

    public boolean getSelectedAnswer() { return selectedAnswer; }

    public void setSelectedAnswer(boolean selectedAnswer) { this.selectedAnswer = selectedAnswer; }

    public int getQuestionId() { return questionId; }

    public void setQuestionId(int questionId) {this.questionId = questionId; }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

}
