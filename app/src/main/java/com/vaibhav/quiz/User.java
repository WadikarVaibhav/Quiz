package com.vaibhav.quiz;

class User {

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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}