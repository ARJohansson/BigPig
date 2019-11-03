package com.example.bigpig;


import android.os.Bundle;

// Imports the widgets
import android.view.View;
import android.widget.EditText;
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

import androidx.appcompat.app.AppCompatActivity;

import static com.example.bigpig.SecondFragment.playerScore;
import static com.example.bigpig.SecondFragment.score1;
import static com.example.bigpig.SecondFragment.score2;

public class MainActivity extends AppCompatActivity {

    // Global variables for the Widgets
    private static final String SCORE_1 = "player_1_score";
    private static final String SCORE_2 = "player_2_score";
    private static final String CURRENT_SCORE = "current_player_score";
    private static final String NUM_TURNS = "number_of_turns";
    private static final String PLAYER_1 = "player_1_name";
    private static final String Player_2 = "player_2_name";

    // Number Constants for die, score, and evil die
    private static final int NUM_ONE = 1;
    private static final int NUM_EIGHT = 8;
    private static final int SCORE_ONE = 75;
    private static final int SCORE_TWO = 100;
    private static final int SCORE_THREE = 150;
    private static final int SCORE_FOUR = 200;

    // Set up preferences
    private SharedPreferences prefs;
    private static int defaultDieNumber = NUM_ONE;
    public static int defaultEvilDie = NUM_EIGHT;
    private static int defaultHighScore = SCORE_TWO;
    private static final String NUM_DIE = "defaultDieNumber";

    // Starts the Pig Game
    public static void StartGame(PigGame newGame) {
        //Local variables
        String p1, p2;

        // gets the players' names
        p1 = newGame.getPlayer1Name();
        p2 = newGame.getPlayer2Name();

        // displays the initial scores
        DisplayScores(newGame);

        // Displays whose turn it is
        DisplayPlayerName(FirstFragment.game);

    }

    // Sets the die Image using the rolled die number
    public static void DieImage(int n) {
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
        SecondFragment.dieNumber.setImageResource(id);
    }

    // displays the scores based on integers
    protected static void DisplayScores(PigGame newGame) {
        // local variables
        int p1Score, p2Score, playerTotal;
        String winner;

        // gets the scores
        p1Score = newGame.getPlayer1Score();
        p2Score = newGame.getPlayer2Score();
        playerTotal = newGame.getTurnPoints();

        if (defaultHighScore == SCORE_ONE)
            winner = newGame.checkForWinner(SCORE_ONE);
        else if (defaultHighScore == SCORE_TWO)
            winner = newGame.checkForWinner(SCORE_TWO);
        else if (defaultHighScore == SCORE_THREE)
            winner = newGame.checkForWinner(SCORE_THREE);
        else
            winner = newGame.checkForWinner(SCORE_FOUR);

        if (winner != "") {
            // sets the scores using formatting
            NumberFormat integer = NumberFormat.getIntegerInstance();
            score1.setText(integer.format(p1Score));
            score2.setText(integer.format(p2Score));
            winner += " wins!";
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

    protected static void DisplayPlayerName(PigGame newGame) {
        String pTurn;
        pTurn = newGame.getCurrentPlayer()+"'s Turn";  // we'll get who's playing now
        SecondFragment.playerTurn.setText(pTurn);  // and set the new text
    }

    protected static void DieNumber(PigGame newGame) {
        int n = 0;

        // Rolls the die the number of times equal to defaultDieNumber
        n = newGame.rollDie(defaultEvilDie, defaultDieNumber);
        DieImage(n);            // and assign an image based on that var
        DisplayScores(newGame);        // display scores
        DisplayPlayerName(newGame);    // display player names

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int p1 = 0, p2 = 0, s = 0, t = 0, dieNum = 0;
        String p1Name ="", p2Name = "";
        if(savedInstanceState != null) {
            p1 = savedInstanceState.getInt(SCORE_1);
            p2 = savedInstanceState.getInt(SCORE_2);
            s = savedInstanceState.getInt(CURRENT_SCORE);
            t = savedInstanceState.getInt(NUM_TURNS);
            p1Name = savedInstanceState.getString(PLAYER_1);
            p2Name = savedInstanceState.getString(Player_2);
            dieNum = savedInstanceState.getInt(NUM_DIE);

            NumberFormat integer = NumberFormat.getIntegerInstance();
            SecondFragment.numDie.setText(integer.format(dieNum));
            }
        else {// starts the game
            FirstFragment.game = new PigGame();
            StartGame(FirstFragment.game);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bigpig_game, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SCORE_1, FirstFragment.game.getPlayer1Score());
        outState.putInt(SCORE_2, FirstFragment.game.getPlayer2Score());
        outState.putInt(CURRENT_SCORE, FirstFragment.game.getTurnPoints());
        outState.putInt(NUM_TURNS, FirstFragment.game.getTurn());
        outState.putInt(NUM_DIE, defaultDieNumber);
        outState.putString(PLAYER_1, FirstFragment.game.getPlayer1Name());
        outState.putString(Player_2, FirstFragment.game.getPlayer2Name());
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
        SecondFragment.numDie.setText(integer.format(defaultDieNumber));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Toast.makeText(this, "About not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
