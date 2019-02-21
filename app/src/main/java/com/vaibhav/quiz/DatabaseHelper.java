package com.vaibhav.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        SQLiteDatabase db;
        public static final String DATABASE_NAME = "Quiz.db";

        private static final String USER_TABLE = "user";
        private static final String SCORE_TABLE = "score";
        private static final String QUESTIONS_TABLE = "questions";

        private static final String CREATE_TABLE_USER = "CREATE TABLE "+ USER_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, FIRST_NAME TEXT, LAST_NAME TEXT, NICK_NAME TEXT, AGE INTEGER)";
        private static final String CREATE_TABLE_SCORE = "CREATE TABLE "+ SCORE_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, USER INTEGER, SCORE INTEGER, DATE DATETIME)";
        private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "+ QUESTIONS_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, QUESTION TEXT, ANSWER TEXT, QPTION1 TEXT, QPTION2 TEXT, QPTION3 TEXT, QPTION4 TEXT, ALIAS TEXT)";

        public DatabaseHelper(Context context) {
             super(context, DATABASE_NAME, null, DATABASE_VERSION);
             db = getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_QUESTION);
            db.execSQL(CREATE_TABLE_SCORE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
                db.execSQL("DROP TABLE IF EXISTS " + QUESTIONS_TABLE);
                db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE);
                onCreate(db);
        }

        public int insert(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("FIRST_NAME", user.getFirstname());
            values.put("LAST_NAME", user.getLastname());
            values.put("NICK_NAME", user.getNickname());
            values.put("AGE", user.getAge());

            int userId = (int)db.insert(USER_TABLE, null, values);
            return userId;
        }
}