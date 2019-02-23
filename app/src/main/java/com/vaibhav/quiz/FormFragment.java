package com.vaibhav.quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class FormFragment extends Fragment implements View.OnClickListener {

    EditText firstname;
    EditText nickname;
    EditText lastname;
    EditText age;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
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
        User user = new User(firstname.getText().toString(), lastname.getText().toString(), nickname.getText().toString(), Integer.parseInt(age.getText().toString()));
        ListToDetails listToDetails = (ListToDetails) getActivity();
        listToDetails.submitUser(user);
    }
}
