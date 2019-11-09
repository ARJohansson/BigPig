package com.example.bigpig;

import android.app.Activity;
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

import android.app.Fragment;

import org.w3c.dom.Text;

public class SecondFragment extends Fragment
implements OnClickListener {

    // Global variables for the Widgets
    public static final String SCORE_1 = "player_1_score";
    public static final String SCORE_2 = "player_2_score";
    public static final String CURRENT_SCORE = "current_player_score";
    public static final String NUM_TURNS = "number_of_turns";
    public static final String PLAYER_1 = "player_1_name";
    public static final String Player_2 = "player_2_name";

    // Number Constants for die, score, and evil die
    public final int NUM_ONE = 1;
    public static final int NUM_EIGHT = 8;
    public final int SCORE_ONE = 75;
    public static final int SCORE_TWO = 100;
    public final int SCORE_THREE = 150;
    public final int SCORE_FOUR = 200;

    // Set up preferences
    private SharedPreferences prefs;
    private int defaultDieNumber = NUM_ONE;
    public static int defaultEvilDie = NUM_EIGHT;
    public static int defaultHighScore = SCORE_TWO;
    public static final String NUM_DIE = "defaultDieNumber";

    public PigGame game;
    public TextView score1;
    public TextView score2;
    public TextView playerTurn;
    public TextView player1Name;
    public TextView player2Name;
    public TextView playerScore;
    public static TextView numDie;
    public ImageView dieNumber;

    // local variable for button widgets as they're not used elsewhere
    Button rollDie, turnEnd;
/*
    @Override
    public void  onCreate(Bundle savedInstanceState) {

    } */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout for fragment
        View view = inflater.inflate(R.layout.second_fragment, container, false);

        // Creating references to the data displaying widgets
        score1 = (TextView) view.findViewById(R.id.p1ScoreTextView);
        score2 = (TextView) view.findViewById(R.id.p2ScoreTextView);
        playerTurn = (TextView) view.findViewById(R.id.turnLabel);
        playerScore = (TextView) view.findViewById(R.id.pointTotalTextView);
        player1Name = (TextView) view.findViewById(R.id.p1ScoreLabel);
        player2Name = (TextView) view.findViewById(R.id.p2ScoreLabel);
        numDie = (TextView) view.findViewById(R.id.numDieTextView);
        dieNumber = (ImageView) view.findViewById(R.id.diceRollImageView);
        rollDie = (Button) view.findViewById(R.id.dieRollButton);
        turnEnd = (Button) view.findViewById(R.id.endTurnButton);

        // Sets the listeners to the button widgets
        rollDie.setOnClickListener(this);
        turnEnd.setOnClickListener(this);

        // set default values for preferences
        PreferenceManager.setDefaultValues((getActivity()), R.xml.preferences, false);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // tun on options menu
        setHasOptionsMenu(true);
        // return view for layout
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SCORE_1, game.getPlayer1Score());
        outState.putInt(SCORE_2, game.getPlayer2Score());
        outState.putInt(CURRENT_SCORE, game.getTurnPoints());
        outState.putInt(NUM_TURNS, game.getTurn());
        outState.putInt(NUM_DIE, defaultDieNumber);
        outState.putString(PLAYER_1, game.getPlayer1Name());
        outState.putString(Player_2, game.getPlayer2Name());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        Bundle bundle = new Bundle();
        Editor editor = prefs.edit();
        editor.commit();

        onSaveInstanceState(bundle);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // get preferences
        defaultDieNumber = Integer.parseInt(prefs.getString("pref_number_of_die", "1"));
        defaultEvilDie = Integer.parseInt(prefs.getString("pref_evil_die", "8"));
        defaultHighScore = Integer.parseInt(prefs.getString("pref_high_score", "100"));
        NumberFormat integer = NumberFormat.getIntegerInstance();
        numDie.setText(integer.format(defaultDieNumber));

    }

    // Starts the Pig Game
    public void StartGame(PigGame g) {
        Activity activity = getActivity();

        game = g;

        //Local variables
        String p1, p2;

        // gets the players' names
        p1 = game.getPlayer1Name();
        p2 = game.getPlayer2Name();

        // displays the initial scores
        DisplayScores();

        // Displays whose turn it is
        DisplayPlayerName();

    }

    // Sets the die Image using the rolled die number
    public void DieImage(int n) {
        int id = 0;
        // A switch case statement that will determine the corresponding image
        switch (n)
        {
            case 1:
                id = R.drawable.die8side1;
                break;
            case 2:
                id = R.drawable.die8side2;
                break;
            case 3:
                id = R.drawable.die8side3;
                break;
            case 4:
                id = R.drawable.die8side4;
                break;
            case 5:
                id = R.drawable.die8side5;
                break;
            case 6:
                id = R.drawable.die8side6;
                break;
            case 7:
                id = R.drawable.die8side7;
                break;
            case 8:
                id = R.drawable.die8side8;
                break;
        }
        // sets the image
        dieNumber.setImageResource(id);
    }

    // displays the scores based on integers
    public void DisplayScores() {
        // local variables
        int p1Score, p2Score, playerTotal;
        String winner;

        // gets the scores
        p1Score = game.getPlayer1Score();
        p2Score = game.getPlayer2Score();
        playerTotal = game.getTurnPoints();

        if (defaultHighScore == SCORE_ONE)
            winner = game.checkForWinner(SCORE_ONE);
        else if (defaultHighScore == SCORE_TWO)
            winner = game.checkForWinner(SCORE_TWO);
        else if (defaultHighScore == SCORE_THREE)
            winner = game.checkForWinner(SCORE_THREE);
        else
            winner = game.checkForWinner(SCORE_FOUR);

        if (winner != "") {
            // sets the scores using formatting
            NumberFormat integer = NumberFormat.getIntegerInstance();
            score1.setText(integer.format(p1Score));
            score2.setText(integer.format(p2Score));
            playerScore.setText(winner);
        }
        else {
            // sets the scores using formatting
            NumberFormat integer = NumberFormat.getIntegerInstance();
            playerScore.setText(integer.format(playerTotal));
            score1.setText(integer.format(p1Score));
            score2.setText(integer.format(p2Score));
        }
    }

    public void DisplayPlayerName() {
        String pTurn;
        player1Name.setText(game.getPlayer1Name());
        player2Name.setText(game.getPlayer2Name());
        pTurn = game.getCurrentPlayer()+"'s Turn";  // we'll get who's playing now
        playerTurn.setText(pTurn);  // and set the new text
    }

    public void DieNumber() {
        int n = 0;

        // Rolls the die the number of times equal to defaultDieNumber
        n = game.rollDie(defaultEvilDie, defaultDieNumber);
        DieImage(n);            // and assign an image based on that var
        DisplayScores();        // display scores
        DisplayPlayerName();    // display player names

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dieRollButton:    // If the dieRoll button is pressed:
                DieNumber();
                break;
            case R.id.endTurnButton:    // If the endTurn button is pressed:
                game.changeTurn();      // we'll change who's playing
                DisplayScores();        // display scores
                DisplayPlayerName();    // displays the next player's name
                break;
        }
    }

}
