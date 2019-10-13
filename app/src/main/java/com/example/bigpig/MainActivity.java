package com.example.bigpig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
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

import java.util.Locale;

public class MainActivity extends AppCompatActivity
implements OnEditorActionListener, OnClickListener {

    // Global variables for the Widgets
    private PigGame game;
    private EditText player1, player2;
    private TextView score1, score2, playerTurn, playerScore;
    private ImageView dieNumber;
    private static final String SCORE_1 = "player_1_score";
    private static final String SCORE_2 = "player_2_score";
    private static final String CURRENT_SCORE = "current_player_score";
    private static final String PLAYER_1 = "player_1_name";
    private static final String Player_2 = "player_2_name";
    private static final String CURRENT_PLAYER = "current_player";
    // local variable for button widgets as they're not used elsewhere
    Button rollDie, turnEnd, playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating references to the data displaying widgets
        game = new PigGame();
        player1 = (EditText) findViewById(R.id.player1EditText);
        player2 = (EditText) findViewById(R.id.player2EditText);
        score1 = (TextView) findViewById(R.id.p1ScoreTextView);
        score2 = (TextView) findViewById(R.id.p2ScoreTextView);
        playerTurn = (TextView) findViewById(R.id.turnLabel);
        playerScore = (TextView) findViewById(R.id.pointTotalTextView);
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

        // starts the game
        startGame();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(SCORE_1, game.getPlayer1Score());
        savedInstanceState.putInt(SCORE_2, game.getPlayer2Score());
        savedInstanceState.putInt(CURRENT_SCORE, game.getTurnPoints());
        savedInstanceState.putString(PLAYER_1, game.getPlayer1Name());
        savedInstanceState.putString(Player_2, game.getPlayer2Name());
        savedInstanceState.putString(CURRENT_PLAYER, game.getCurrentPlayer());
        super.onSaveInstanceState(savedInstanceState);
    }

    // Starts the Pig Game
    public void startGame() {
        //Local variables
        String p1, p2, pTurn;

        // gets the players' names
        p1 = game.getPlayer1Name();
        p2 = game.getPlayer2Name();
        // Sets the players' names
        player1.setText(p1);
        player2.setText(p2);

        // displays the initial scores
        displayScores();

        // Displays whose turn it is
        displayPlayerName();

    }

    // Sets the die Image using the rolled die number
    public void dieImage(int n) {
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
    private void displayScores() {
        // local variables
        int p1Score, p2Score, playerTotal;

        // gets the scores
        p1Score = game.getPlayer1Score();
        p2Score = game.getPlayer2Score();
        playerTotal = game.getTurnPoints();

        // sets the scores using formatting
        NumberFormat integer = NumberFormat.getIntegerInstance();
        playerScore.setText(integer.format(playerTotal));
        score1.setText(integer.format(p1Score));
        score2.setText(integer.format(p2Score));
    }

    private void displayPlayerName() {
        String pTurn;
        pTurn = game.getCurrentPlayer()+"'s Turn";  // we'll get who's playing now
        playerTurn.setText(pTurn);  // and set the new text
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
        int n = 0;
        switch (v.getId()) {
            case R.id.dieRollButton:    // If the dieRoll button is pressed:
                n = game.rollDie();     // we'll roll the die and assign it to a var
                dieImage(n);            // and assign an image based on that var
                displayScores();        // display scores
                displayPlayerName();    // display player names
                break;
            case R.id.endTurnButton:    // If the endTurn button is pressed:
                game.changeTurn();      // we'll change who's playing
                displayPlayerName();    // displays the next player's name
                break;
            case R.id.newGameButton:    // If the newGame button is pressed:
                game.resetGame();       // we call the PigGame's reset method
                displayScores();        // display zeroed scores
                dieImage(8);        // reset the original dice image
                player1.setText("");    // rename the players
                player2.setText("");
                break;
        }
    }
}
