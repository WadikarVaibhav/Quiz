package com.vaibhav.quiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var context = this

        submit.setOnClickListener {
            var user = User(firstname.text.toString(), lastname.text.toString(), nickname.text.toString(), age.text.toString().toInt())
            var db = DatabaseOpenHelper(context)
            db.insert(user)
        }

    }
}
