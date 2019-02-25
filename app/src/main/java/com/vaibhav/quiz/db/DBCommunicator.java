package com.vaibhav.quiz.db;

import com.vaibhav.quiz.model.Summary;
import com.vaibhav.quiz.model.User;

public interface DBCommunicator {

    public int insert(User user);
    public void insert(Summary summary);

}
