package com.vaibhav.quiz.model;

import java.util.Calendar;
import java.util.Date;

public class Summary {

    private final int userId;
    private int userScore;
    private final Date startTime;
    private Date endTime;

    public Summary(int userId) {
        this.userId = userId;
        this.userScore = 0;
        this.startTime = Calendar.getInstance().getTime();
        this.endTime = Calendar.getInstance().getTime();
    }

    public int getUserId() {
        return this.userId;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserScore() {
        return this.userScore;
    }
}
