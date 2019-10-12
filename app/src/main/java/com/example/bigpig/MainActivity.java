package com.example.bigpig;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
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

public class MainActivity extends AppCompatActivity
implements OnEditorActionListener, OnClickListener {

    // Variables for the Widgets
    private PigGame game;
    private EditText player1, player2;
    private TextView score1, score2, playerTurn, playerScore;
    private ImageView dieNumber;
    private Button rollDie, turnEnd, playAgain;

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

        // Sets the listeners
        rollDie.setOnClickListener(this);
        turnEnd.setOnClickListener(this);
        playAgain.setOnClickListener(this);
        player1.setOnEditorActionListener(this);
        player2.setOnEditorActionListener(this);

        startGame();
    }

    // Starts the Pig Game
    public void startGame() {
        //Local variables
        String p1, p2, turn, p1ScoreString, p2ScoreString, playerTotalString;
        int p1Score, p2Score, playerTotal, dieNum;
        p1 = player1.getText().toString();
        p2 = player2.getText().toString();
        turn = playerTurn.getText().toString();
        p1ScoreString = score1.getText().toString();
        p2ScoreString = score2.getText().toString();
        playerTotalString = playerScore.getText().toString();

        // If any of the strings are empty sets the ints to 0
        if (p1ScoreString.equals("") ) {
            p1Score = 0;
        }
        // Otherwise assign the integer to the corresponding parsed string
        else
            p1Score = Integer.parseInt(p1ScoreString);

        if (p2ScoreString.equals("")) {
            p2Score = 0;
        }
        else
            p2Score = Integer.parseInt(p2ScoreString);

        if (playerTotalString.equals("")) {
            playerTotal = 0;
        }
        else
            playerTotal = Integer.parseInt(playerTotalString);

        game.setPlayer1Name(p1);
        game.setPlayer2Name(p2);

        displayScores();
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

    private void displayScores() {
        playerScore.setText(game.getTurnPoints());
        score1.setText(game.getPlayer1Score());
        score2.setText(game.getPlayer2Score());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
            actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            startGame();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int n = 0;
        switch (v.getId()) {
            case R.id.dieRollButton:
                n = game.rollDie();
                dieImage(n);
                //displayScores();
                break;
            case R.id.endTurnButton:
                n = game.changeTurn();
                break;
            case R.id.newGameButton:
                game.resetGame();
                score1.setText(game.getPlayer1Score());
                score2.setText(game.getPlayer2Score());
                break;
        }
    }
}
