package com.vaibhav.quiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.vaibhav.quiz.R;
import com.vaibhav.quiz.communication.UserdataCommunicator;
import com.vaibhav.quiz.constants.ActivityConstants;
import com.vaibhav.quiz.model.User;

public class FormFragment extends Fragment implements View.OnClickListener {


    EditText firstname;
    EditText nickname;
    EditText lastname;
    EditText age;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_fragment, container, false);
        firstname = (EditText) view.findViewById(R.id.firstname);
        nickname = (EditText) view.findViewById(R.id.nickname);
        lastname = (EditText) view.findViewById(R.id.lastname);
        age = (EditText) view.findViewById(R.id.age);
        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (validate()) {
            User user = new User(firstname.getText().toString(), lastname.getText().toString(), nickname.getText().toString(), Integer.parseInt(age.getText().toString()));
            UserdataCommunicator userDataCommunicator = (UserdataCommunicator) getActivity();
            userDataCommunicator.submitUser(user);
        } else {
            Toast toast = Toast.makeText(getContext(), ActivityConstants.VALIDATION_MESSAGE, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(firstname.getText()) || TextUtils.isEmpty(lastname.getText())) {
            return false;
        }
        return true;
    }
}
