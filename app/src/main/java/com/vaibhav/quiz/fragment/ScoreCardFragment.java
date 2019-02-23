package com.vaibhav.quiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.vaibhav.quiz.R;
import com.vaibhav.quiz.constants.ActivityConstants;

public class ScoreCardFragment extends Fragment implements View.OnClickListener {

    TextView name;
    TextView score;
    Button quit;
    TextView start;
    TextView end;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_card_fragment, container, false);

        name = (TextView) view.findViewById(R.id.name);
        score = (TextView) view.findViewById(R.id.score);
        start = (TextView) view.findViewById(R.id.start);
        end = (TextView) view.findViewById(R.id.end);
        quit = (Button) view.findViewById(R.id.quit);
        quit.setOnClickListener(this);

        Bundle bundle = getArguments();
        name.setText(bundle.getInt(ActivityConstants.USER_NAME, 0)+"");
        score.setText(bundle.getInt(ActivityConstants.SCORE, 0)+"");
        start.setText(bundle.getString(ActivityConstants.START_TIME, ""));
        end.setText(bundle.getString(ActivityConstants.END_TIME, ""));

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
