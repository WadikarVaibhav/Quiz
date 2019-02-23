package com.vaibhav.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.vaibhav.quiz.constants.ActivityConstants;
import java.text.SimpleDateFormat;
import com.vaibhav.quiz.model.Question;
import com.vaibhav.quiz.model.Summary;
import com.vaibhav.quiz.model.User;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Quiz.db";

        private static final String USER_TABLE = "user";
        private static final String SCORE_TABLE = "score";
        private static final String QUESTIONS_TABLE = "questions";

        SQLiteDatabase db;

        private static final String CREATE_TABLE_USER = "CREATE TABLE "+ USER_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, FIRST_NAME TEXT, LAST_NAME TEXT, NICK_NAME TEXT, AGE INTEGER)";
        private static final String CREATE_TABLE_SCORE = "CREATE TABLE "+ SCORE_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, USER_ID INTEGER, SCORE INTEGER, START_DATE DATETIME, END_DATE DATETIME)";
        private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "+ QUESTIONS_TABLE +"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, QUESTION TEXT, ANSWER INTEGER, OPTION1 TEXT, OPTION2 TEXT, OPTION3 TEXT, OPTION4 TEXT)";

        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String NICK_NAME = "NICK_NAME";
        public static final String AGE = "AGE";

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

        public List<Question> getAllQuestion() {
            List<Question> questions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + QUESTIONS_TABLE +";";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.setQuestionId(Integer.parseInt(cursor.getString(0)));
                    question.setQuestion(cursor.getString(1));
                    question.setAnswer(cursor.getInt(2));
                    question.setOption1(cursor.getString(3));
                    question.setOption2(cursor.getString(4));
                    question.setOption3(cursor.getString(5));
                    question.setOption4(cursor.getString(6));
                    question.setSelectedAnswer(false);
                    questions.add(question);
                } while (cursor.moveToNext());
            }
            return questions;
        }

        public int insert(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FIRST_NAME, user.getFirstname());
            values.put(LAST_NAME, user.getLastname());
            values.put(NICK_NAME, user.getNickname());
            values.put(AGE, user.getAge());
            int userId = (int)db.insert(USER_TABLE, null, values);
            return userId;
        }

        public void insert(Summary summary) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("USER_ID", summary.getUser());
            values.put("SCORE", summary.getScore());
            values.put("START_DATE", new SimpleDateFormat(ActivityConstants.DATE_FORMAT).format(summary.getStartDate()));
            values.put("END_DATE", new SimpleDateFormat(ActivityConstants.DATE_FORMAT).format(summary.getEndDate()));
            db.insert(SCORE_TABLE, null, values);
        }
}