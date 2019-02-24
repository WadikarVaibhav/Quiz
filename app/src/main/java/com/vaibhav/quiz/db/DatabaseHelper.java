package com.vaibhav.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import com.vaibhav.quiz.constants.ActivityConstants;

import java.text.SimpleDateFormat;

import com.vaibhav.quiz.constants.DatabaseConstants;
import com.vaibhav.quiz.model.Question;
import com.vaibhav.quiz.model.Summary;
import com.vaibhav.quiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db = null;

    public DatabaseHelper(Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseConstants.CREATE_TABLE_USER);
        db.execSQL(DatabaseConstants.CREATE_TABLE_QUESTION);
        db.execSQL(DatabaseConstants.CREATE_TABLE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseConstants.DROP_TABLE + DatabaseConstants.USER_TABLE);
        db.execSQL(DatabaseConstants.DROP_TABLE + DatabaseConstants.QUESTIONS_TABLE);
        db.execSQL(DatabaseConstants.DROP_TABLE + DatabaseConstants.SCORE_TABLE);
        onCreate(db);
    }

    private Question getQuestionFromCursor(Cursor cursor) {
        Question question = new Question();
        question.setQuestionId(Integer.parseInt(cursor.getString(0)));
        question.setQuestion(cursor.getString(1));
        question.setAnswer(cursor.getInt(2));
        question.setOption1(cursor.getString(3));
        question.setOption2(cursor.getString(4));
        question.setOption3(cursor.getString(5));
        question.setOption4(cursor.getString(6));
        question.setSelectedAnswer(false);
        return question;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DatabaseConstants.QUESTIONS_TABLE);
        Cursor cursor = sqLiteQueryBuilder.query(db, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                questions.add(getQuestionFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return questions;
    }

    public int insert(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.FIRST_NAME, user.getFirstname());
        values.put(DatabaseConstants.LAST_NAME, user.getLastname());
        values.put(DatabaseConstants.NICK_NAME, user.getNickname());
        values.put(DatabaseConstants.AGE, user.getAge());
        int userId = (int) db.insert(DatabaseConstants.USER_TABLE, null, values);
        return userId;
    }

    public void insert(Summary summary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.USER_ID, summary.getUser());
        values.put(DatabaseConstants.SCORE, summary.getScore());
        values.put(DatabaseConstants.START_DATE, new SimpleDateFormat(ActivityConstants.DATE_FORMAT).format(summary.getStartDate()));
        values.put(DatabaseConstants.END_DATE, new SimpleDateFormat(ActivityConstants.DATE_FORMAT).format(summary.getEndDate()));
        db.insert(DatabaseConstants.SCORE_TABLE, null, values);
    }
}