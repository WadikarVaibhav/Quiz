package com.vaibhav.quiz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import java.text.SimpleDateFormat;
import com.vaibhav.quiz.quizBoard.QuizBoardConstants;
import com.vaibhav.quiz.model.Question;
import com.vaibhav.quiz.model.Summary;
import com.vaibhav.quiz.model.User;

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
        values.put(DatabaseConstants.USER_ID, summary.getUserId());
        values.put(DatabaseConstants.SCORE, summary.getUserScore());
        values.put(DatabaseConstants.START_DATE,
                new SimpleDateFormat(QuizBoardConstants.DATE_FORMAT).format(summary.getStartTime()));
        values.put(DatabaseConstants.END_DATE,
                new SimpleDateFormat(QuizBoardConstants.DATE_FORMAT).format(summary.getEndTime()));
        db.insert(DatabaseConstants.SCORE_TABLE, null, values);
    }

    public String getUserName(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DatabaseConstants.USER_TABLE);
        sqLiteQueryBuilder.appendWhere(DatabaseConstants.ID + userId);
        Cursor cursor = sqLiteQueryBuilder.query(db, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                return cursor.getString(1).concat(" ").concat(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return "";
    }
}