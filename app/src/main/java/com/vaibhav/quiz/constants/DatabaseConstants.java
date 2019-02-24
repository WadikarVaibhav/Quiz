package com.vaibhav.quiz.constants;

public class DatabaseConstants {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Quiz.db";

    public static final String USER_TABLE = "user";
    public static final String SCORE_TABLE = "score";
    public static final String QUESTIONS_TABLE = "questions";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, FIRST_NAME TEXT, LAST_NAME TEXT, NICK_NAME TEXT, AGE INTEGER)";
    public static final String CREATE_TABLE_SCORE = "CREATE TABLE " + SCORE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, USER_ID INTEGER, SCORE INTEGER, START_DATE DATETIME, END_DATE DATETIME)";
    public static final String CREATE_TABLE_QUESTION = "CREATE TABLE " + QUESTIONS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, QUESTION TEXT, ANSWER INTEGER, OPTION1 TEXT, OPTION2 TEXT, OPTION3 TEXT, OPTION4 TEXT)";

    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String NICK_NAME = "NICK_NAME";
    public static final String AGE = "AGE";

    public static final String START_DATE = "START_DATE";
    public static final String END_DATE = "END_DATE";
    public static final String SCORE = "SCORE";
    public static final String USER_ID = "USER_ID";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    public static final String ID = "ID = ";


}
