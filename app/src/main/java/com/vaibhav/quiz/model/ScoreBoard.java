package com.vaibhav.quiz.model;

import java.util.Date;

public class ScoreBoard {

    private int user;
    private int score;
    private Date start;
    private Date end;

    public int getUser() {
        return user;
    }

    public void setUser(int user) { this.user = user; }

    public Date getEndDate() { return end; }

    public void setEndDate(Date end) { this.end = end; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public Date getStartDate() { return start; }

    public void setStartDate(Date date) { this.start = date; }
}
