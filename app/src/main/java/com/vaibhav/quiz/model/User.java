package com.vaibhav.quiz.model;

import java.util.Date;

public class User {

    private final String firstname;
    private final String lastname;
    private final String nickname;
    private final int age;

    public User(String firstname, String lastname, String nickname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.age = age;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getAge() {
        return this.age;
    }
}