package com.vaibhav.quiz.mainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.vaibhav.quiz.R;
import com.vaibhav.quiz.db.DBCommunicator;
import com.vaibhav.quiz.db.DatabaseConstants;
import com.vaibhav.quiz.db.DatabaseHelper;
import com.vaibhav.quiz.model.Summary;
import com.vaibhav.quiz.model.User;

public class UserRegistration extends Fragment implements View.OnClickListener, DBCommunicator {

    EditText firstname;
    EditText nickname;
    EditText lastname;
    EditText age;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_registration, container, false);
        firstname = view.findViewById(R.id.firstname);
        nickname = view.findViewById(R.id.nickname);
        lastname = view.findViewById(R.id.lastname);
        age = view.findViewById(R.id.age);
        submit = view.findViewById(R.id.submit);

        Bundle bundle = getArguments();
        firstname.setText(bundle.getString(DatabaseConstants.FIRST_NAME, ""));
        lastname.setText(bundle.getString(DatabaseConstants.LAST_NAME, ""));
        nickname.setText(bundle.getString(DatabaseConstants.NICK_NAME, ""));
        age.setText(bundle.getInt(DatabaseConstants.AGE, 0)+"");

        submit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (isValid()) {
            User user = new User(firstname.getText().toString(), lastname.getText().toString(), nickname.getText().toString(), Integer.parseInt(age.getText().toString()));
            int userId = insert(user);
            UserdataCommunicator userDataCommunicator = (UserdataCommunicator) getActivity();
            userDataCommunicator.sendUserIdToQuiz(userId);
        } else {
            Toast toast = Toast.makeText(getContext(), MainActivityConstants.VALIDATION_MESSAGE, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean isValid() {
        return !TextUtils.isEmpty(firstname.getText()) && !TextUtils.isEmpty(lastname.getText()) && !TextUtils.isEmpty(nickname.getText()) && !TextUtils.isEmpty(age.getText());
    }

    @Override
    public int insert(User user) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        int userId = db.insert(user);
        return userId;
    }

    @Override
    public void insert(Summary summary) {

    }
}
