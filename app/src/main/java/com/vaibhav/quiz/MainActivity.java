package com.vaibhav.quiz;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

class MainActivity extends AppCompatActivity {

    EditText firstname;
    EditText nickname;
    EditText lastname;
    EditText age;
    Button submit;
    private static DatabaseHelper dbHelper = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(getApplicationContext());
        firstname = (EditText) findViewById(R.id.firstname);
        nickname = (EditText) findViewById(R.id.nickname);
        lastname = (EditText) findViewById(R.id.lastname);
        age = (EditText) findViewById(R.id.age);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(firstname.getText().toString(), lastname.getText().toString(), nickname.getText().toString(), Integer.parseInt(age.getText().toString()));
                int userId = dbHelper.insert(user);
                Log.i("id: ", userId+"");
                Intent intent = new Intent(MainActivity.this, QuizBoard.class);
                startActivity(intent);
            }
        });
    }
}
