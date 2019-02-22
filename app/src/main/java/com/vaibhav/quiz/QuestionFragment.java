package com.vaibhav.quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    TextView question;
    RadioButton btn1;
    RadioButton btn2;
    RadioButton btn3;
    RadioButton btn4;
    RadioGroup radioGroup;
    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question, container, false);

        question = (TextView) view.findViewById(R.id.question);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        btn1 = (RadioButton) view.findViewById(R.id.btn1);
        btn2 = (RadioButton) view.findViewById(R.id.btn2);
        btn3 = (RadioButton) view.findViewById(R.id.btn3);
        btn4 = (RadioButton) view.findViewById(R.id.btn4);
        next = (Button) view.findViewById(R.id.next);

        Bundle bundle = getArguments();
        question.setText(bundle.getString("question", ""));
        btn1.setText(bundle.getString("option1", ""));
        btn2.setText(bundle.getString("option2", ""));
        btn3.setText(bundle.getString("option3", ""));
        btn4.setText(bundle.getString("option4", ""));
        next.setText(bundle.getString("btnText", ""));
        next.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        ListToDetails listToDetails = (ListToDetails) getActivity();
        int selectedAnswer = radioGroup.indexOfChild(getActivity().findViewById(radioGroup.getCheckedRadioButtonId()));
        int currentQuestion = getArguments().getInt("questionId");
        if (currentQuestion <= 3) {
            listToDetails.showQuestionOnFragment(currentQuestion, selectedAnswer);
        } else {
            listToDetails.updateScoreBoard(currentQuestion, selectedAnswer);
        }
    }
}
