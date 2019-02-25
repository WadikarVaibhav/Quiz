package com.vaibhav.quiz.mainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vaibhav.quiz.R;

/**
 * Press back button to exit Quiz
 * This fragment shows user score card
 */

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
        name.setText(bundle.getString(MainActivityConstants.USER_NAME, ""));
        score.setText(bundle.getInt(MainActivityConstants.SCORE, 0) + "");
        start.setText(bundle.getString(MainActivityConstants.START_TIME, ""));
        end.setText(bundle.getString(MainActivityConstants.END_TIME, ""));

        return view;
    }

}
