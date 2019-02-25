package com.vaibhav.quiz.mainActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.vaibhav.quiz.R;

/**
 * Press back button to exit Quiz
 * This fragment shows user score card
 */

public class ScoreReport extends Fragment implements View.OnClickListener {

    TextView name;
    TextView score;
    TextView start;
    TextView end;
    Button quit;
    Button startNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_report, container, false);
        name = view.findViewById(R.id.name);
        score = view.findViewById(R.id.score);
        start = view.findViewById(R.id.start);
        end = view.findViewById(R.id.end);
        quit = view.findViewById(R.id.quit);
        startNew = view.findViewById(R.id.startNew);

        quit.setOnClickListener(this);
        startNew.setOnClickListener(this);

        Bundle bundle = getArguments();
        name.setText(bundle.getString(MainActivityConstants.USER_NAME, ""));
        score.setText(bundle.getInt(MainActivityConstants.SCORE, 0) + "");
        start.setText(bundle.getString(MainActivityConstants.START_TIME, ""));
        end.setText(bundle.getString(MainActivityConstants.END_TIME, ""));

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.quit) {
            new AlertDialog.Builder(getContext()).setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(MainActivityConstants.QUIT_MESSAGE_TITLE).setMessage(MainActivityConstants.QUIT_MESSAGE)
                    .setPositiveButton(MainActivityConstants.QUIT_MESSAGE_YES, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().moveTaskToBack(true);
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton(MainActivityConstants.QUIT_MESSAGE_NO, null)
                    .show();
        } else {
            UserdataCommunicator userdataCommunicator = (UserdataCommunicator) getActivity();
            userdataCommunicator.startNewQuiz();
        }
    }
}
