package com.vaibhav.quiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vaibhav.quiz.R;
import com.vaibhav.quiz.constants.ActivityConstants;

public class ScoreReport extends Fragment {

    TextView name;
    TextView score;
    TextView start;
    TextView end;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_report, container, false);
        name = view.findViewById(R.id.name);
        score = view.findViewById(R.id.score);
        start = view.findViewById(R.id.start);
        end = view.findViewById(R.id.end);

        Bundle bundle = getArguments();
        name.setText(bundle.getString(ActivityConstants.USER_NAME, ""));
        score.setText(bundle.getInt(ActivityConstants.SCORE, 0) + "");
        start.setText(bundle.getString(ActivityConstants.START_TIME, ""));
        end.setText(bundle.getString(ActivityConstants.END_TIME, ""));

        return view;
    }

}
