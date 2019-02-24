package com.vaibhav.quiz.model;

import java.util.Date;

public class Summary {

    private int user;
    private int score;
    private Date start;
    private Date end;

    public void setUser(int user) {
        this.user = user;
    }

    public int getUser() {
        return user;
    }

    public void setStartDate(Date date) {
        this.start = date;
    }

    public Date getStartDate() {
        return start;
    }

    public void setEndDate(Date end) {
        this.end = end;
    }

    public Date getEndDate() {
        return end;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }


}
