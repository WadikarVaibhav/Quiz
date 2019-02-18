package com.vaibhav.quiz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "QuizDB", null, 1) {

    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("user", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "firstname" to TEXT,
            "lastname" to TEXT,
            "nickname" to TEXT,
            "age" to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("user", true)
    }

    fun insert(user: User) {
        var db = this.writableDatabase
        var contentValue = ContentValues()
        contentValue.put("firstname", user.firstname)
        contentValue.put("lastname", user.lastname)
        contentValue.put("nickname", user.nickname)
        contentValue.put("age", user.age)
        var record = db.insert("user", null, contentValue)
        if (record == -1.toLong()) {
            Log.i("Failed to add record ", user.firstname)
        } else {
            Log.i("Success ", user.firstname)
        }
    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(getApplicationContext())