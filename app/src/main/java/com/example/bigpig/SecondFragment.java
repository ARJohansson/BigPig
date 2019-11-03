package com.example.bigpig;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;


// Imports the widgets
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

// Imports the listeners
import android.view.View.OnClickListener;

// imports the number format tool
import java.text.NumberFormat;

// imports everything for preferences and menu not already in app
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import static com.example.bigpig.MainActivity.DisplayScores;

public class SecondFragment extends Fragment
implements OnClickListener {

    protected static TextView score1;
    protected static TextView score2;
    protected static TextView playerTurn;
    protected static TextView playerScore;
    protected static TextView numDie;
    protected static ImageView dieNumber;

    // local variable for button widgets as they're not used elsewhere
    Button rollDie, turnEnd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set default values for preferences
        PreferenceManager.setDefaultValues((getActivity()), R.xml.preferences, false);

        // get default SharedPreferences object
        //prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // tun on options menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout for fragment
        View view = inflater.inflate(R.layout.second_fragment, container, false);

        // Creating references to the data displaying widgets
        score1 = (TextView) view.findViewById(R.id.p1ScoreTextView);
        score2 = (TextView) view.findViewById(R.id.p2ScoreTextView);
        playerTurn = (TextView) view.findViewById(R.id.turnLabel);
        playerScore = (TextView) view.findViewById(R.id.pointTotalTextView);
        numDie = (TextView) view.findViewById(R.id.numDieTextView);
        dieNumber = (ImageView) view.findViewById(R.id.diceRollImageView);
        rollDie = (Button) view.findViewById(R.id.dieRollButton);
        turnEnd = (Button) view.findViewById(R.id.endTurnButton);


        // Sets the listeners to the button widgets
        rollDie.setOnClickListener(this);
        turnEnd.setOnClickListener(this);
        // return view for layout
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dieRollButton:    // If the dieRoll button is pressed:
                MainActivity.DieNumber(FirstFragment.game);
                break;
            case R.id.endTurnButton:    // If the endTurn button is pressed:
                FirstFragment.game.changeTurn();      // we'll change who's playing
                DisplayScores(FirstFragment.game);        // display scores
                MainActivity.DisplayPlayerName(FirstFragment.game);    // displays the next player's name
                break;
        }
    }
}
