package com.vaibhav.quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScoreCardFragment extends Fragment implements View.OnClickListener {

    TextView name;
    TextView score;
    TextView correct;
    TextView wrong;
    Button quit;
    TextView start;
    TextView end;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_card, container, false);

        name = (TextView) view.findViewById(R.id.name);
        score = (TextView) view.findViewById(R.id.score);
        correct = (TextView) view.findViewById(R.id.correct);
        wrong = (TextView) view.findViewById(R.id.wrong);
        start = (TextView) view.findViewById(R.id.start);
        end = (TextView) view.findViewById(R.id.end);
        quit = (Button) view.findViewById(R.id.quit);
        quit.setOnClickListener(this);

        Bundle bundle = getArguments();
        name.setText(bundle.getInt("name", 0)+"");
        score.setText(bundle.getInt("score", 0)+"");
        correct.setText(bundle.getString("correct", ""));
        wrong.setText(bundle.getString("wrong", ""));
        start.setText(bundle.getString("start", ""));
        end.setText(bundle.getString("end", ""));

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
