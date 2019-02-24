package com.vaibhav.quiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.vaibhav.quiz.communication.QuizCommunicator;
import com.vaibhav.quiz.R;
import com.vaibhav.quiz.constants.ActivityConstants;

public class QuestionDetails extends Fragment implements View.OnClickListener {

    TextView question;
    RadioButton btn1;
    RadioButton btn2;
    RadioButton btn3;
    RadioButton btn4;
    RadioGroup radioGroup;
    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_details, container, false);

        question = view.findViewById(R.id.question);
        radioGroup = view.findViewById(R.id.radiogroup);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        next = view.findViewById(R.id.next);

        Bundle bundle = getArguments();
        question.setText(bundle.getString(ActivityConstants.QUESTION_TEXT, "").toUpperCase());
        btn1.setText(bundle.getString(ActivityConstants.OPTION_1, ""));
        btn2.setText(bundle.getString(ActivityConstants.OPTION_2, ""));
        btn3.setText(bundle.getString(ActivityConstants.OPTION_3, ""));
        btn4.setText(bundle.getString(ActivityConstants.OPTION_4, ""));
        next.setText(bundle.getString(ActivityConstants.BUTTON_TEXT, ""));
        next.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int selectedAnswer = radioGroup.indexOfChild(getActivity().findViewById(radioGroup.getCheckedRadioButtonId())) + 1;
        int nextQuestion = getArguments().getInt(ActivityConstants.QUESTION_ID) + 1;
        QuizCommunicator quizCommunicator = (QuizCommunicator) getActivity();
        quizCommunicator.onNextQuestion(nextQuestion, selectedAnswer);
    }
}
