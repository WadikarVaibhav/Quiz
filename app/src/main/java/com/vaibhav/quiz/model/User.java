package com.vaibhav.quiz.model;

public class User {

    private String firstname;
    private String lastname;
    private String nickname;
    private int age;

    public User(String firstname, String lastname, String nickname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }
}