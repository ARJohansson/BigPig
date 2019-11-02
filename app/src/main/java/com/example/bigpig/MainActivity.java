package com.example.bigpig;


import android.os.Bundle;

// Imports the widgets
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

// Imports the listeners
import android.widget.TextView.OnEditorActionListener;
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
import android.app.Activity;

public class MainActivity extends Activity
implements OnEditorActionListener, OnClickListener {

    // Global variables for the Widgets
    private PigGame game;
    private EditText player1, player2;
    private TextView score1, score2, playerTurn, playerScore, numDie;
    private ImageView dieNumber;
    private static final String SCORE_1 = "player_1_score";
    private static final String SCORE_2 = "player_2_score";
    private static final String CURRENT_SCORE = "current_player_score";
    private static final String NUM_TURNS = "number_of_turns";
    private static final String PLAYER_1 = "player_1_name";
    private static final String Player_2 = "player_2_name";
    // local variable for button widgets as they're not used elsewhere
    Button rollDie, turnEnd, playAgain;

    // Number Constants for die, score, and evil die
    private static final int NUM_ONE = 1;
    private static final int NUM_EIGHT = 8;
    private static final int SCORE_ONE = 75;
    private static final int SCORE_TWO = 100;
    private static final int SCORE_THREE = 150;
    private static final int SCORE_FOUR = 200;

    // Set up preferences
    private SharedPreferences prefs;
    private int defaultDieNumber = NUM_ONE;
    private int defaultEvilDie = NUM_EIGHT;
    private int defaultHighScore = SCORE_TWO;
    private static final String NUM_DIE = "defaultDieNumber";

    // Starts the Pig Game
    public void StartGame() {
        //Local variables
        String p1, p2;

        // gets the players' names
        p1 = game.getPlayer1Name();
        p2 = game.getPlayer2Name();
        // Sets the players' names
        player1.setText(p1);
        player2.setText(p2);

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
    private void DisplayScores() {
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

    private void DisplayPlayerName() {
        String pTurn;
        pTurn = game.getCurrentPlayer()+"'s Turn";  // we'll get who's playing now
        playerTurn.setText(pTurn);  // and set the new text
    }

    private void DieNumber() {
        int n = 0;

        // Rolls the die the number of times equal to defaultDieNumber
        n = game.rollDie(defaultEvilDie, defaultDieNumber);
        DieImage(n);            // and assign an image based on that var
        DisplayScores();        // display scores
        DisplayPlayerName();    // display player names

    }

    // if the enter key on the hard keyboard or the done key on the soft
    // keyboard are entered this event listener will activate and set the
    // players' names
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
            actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            String p1, p2;
            // gets and sets Player One's name
            p1 = player1.getText().toString();
            game.setPlayer1Name(p1);
            // gets and sets Player Two's name
            p2 = player2.getText().toString();
            game.setPlayer2Name(p2);
        }
        return false;
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
            case R.id.newGameButton:    // If the newGame button is pressed:
                game.resetGame();       // we call the PigGame's reset method
                DisplayScores();        // display zeroed scores
                DieImage(8);        // reset the original dice image
                player1.setText("");    // rename the players
                player2.setText("");
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating references to the data displaying widgets
        player1 = (EditText) findViewById(R.id.player1EditText);
        player2 = (EditText) findViewById(R.id.player2EditText);
        score1 = (TextView) findViewById(R.id.p1ScoreTextView);
        score2 = (TextView) findViewById(R.id.p2ScoreTextView);
        playerTurn = (TextView) findViewById(R.id.turnLabel);
        playerScore = (TextView) findViewById(R.id.pointTotalTextView);
        numDie = (TextView) findViewById(R.id.numDieTextView);
        dieNumber = (ImageView) findViewById(R.id.diceRollImageView);
        rollDie = (Button) findViewById(R.id.dieRollButton);
        turnEnd = (Button) findViewById(R.id.endTurnButton);
        playAgain = (Button) findViewById(R.id.newGameButton);

        // Sets the listeners to the button and EditText Widgets
        rollDie.setOnClickListener(this);
        turnEnd.setOnClickListener(this);
        playAgain.setOnClickListener(this);
        player1.setOnEditorActionListener(this);
        player2.setOnEditorActionListener(this);

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
            numDie.setText(integer.format(dieNum));
            game = new PigGame(p1, p2, s, t);
            game.setPlayer1Name(p1Name);
            game.setPlayer2Name(p2Name);
            StartGame();
            }
        else {// starts the game
            game = new PigGame();
            StartGame();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bigpig_game, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
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
