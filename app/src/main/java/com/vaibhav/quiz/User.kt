package com.vaibhav.quiz

class User {

    var id: Int = 0
    var firstname: String = ""
    var lastname: String = ""
    var nickname: String = ""
    var age: Int = 0

    constructor(firstname: String, lastname: String, nickname: String, age: Int) {
        this.firstname = firstname
        this.lastname = lastname
        this.nickname = nickname
        this.age = age
    }

}