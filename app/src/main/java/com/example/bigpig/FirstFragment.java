package com.example.bigpig;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;

// Imports the widgets
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import android.app.Fragment;
import android.widget.Toast;


public class FirstFragment extends Fragment
implements OnClickListener{

    protected static final String PLAYER_1_NAME = "player1Name";
    protected static final String PLAYER_2_NAME = "player2Name";
    // Global variables for the Widgets
    public PigGame game;
    private EditText player1, player2;
    private TextView gameRules;
    private MainActivity activity;
    private boolean twoPane;

    // local var for Button widget
    Button playAgain;


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
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        // return view for layout
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = (MainActivity)getActivity();

        // Creating references to the data displaying widgets
        player1 = (EditText) activity.findViewById(R.id.player1EditText);
        player2 = (EditText) activity.findViewById(R.id.player2EditText);
        playAgain = (Button) activity.findViewById(R.id.newGameButton);
        gameRules = (TextView) activity.findViewById(R.id.gameRulesTextView);

        game = new PigGame();
        activity.setGame(game);

        // The rules of the game
        String gameDescription = "Welcome to Big Pig!" + "\n" + "The Rules of the Game:" + "\n" +
                "Each turn, a player rolls a die until either the player decides to end their turn," +
                " or rolls an 8." + "\n" + "If they roll an 8, they will score nothing and it becomes " +
                "the  next player's turn." + "\n" + "Any other number rolled is added to their turn " +
                "total and their turn continues." + "\n" + "If the player chooses to end their turn, " +
                "then their turn total is added to their score, and it becomes the next player's turn." +
                "\n" + "The first player to score 100 or higher wins, so long as both players have had " +
                "an equal number of turns.";

        gameRules.setText(gameDescription);

        // Set the listeners for the Edit Texts and Button
        playAgain.setOnClickListener(this);

        // Checks to see if Activity has loaded a single or two pane layout
        twoPane = activity.findViewById(R.id.second_fragment) != null;

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.newGameButton){
            String p1Name = player1.getText().toString();
            String p2Name = player2.getText().toString();
            if(!p1Name.isEmpty() && !p2Name.isEmpty()) {
                game.setPlayer1Name(p1Name);
                game.setPlayer2Name(p2Name);
                if(twoPane) {
                    activity.newGame(game);
                }
                else {
                    Intent intent = new Intent(getActivity(), SecondActivity.class);
                    intent.putExtra(PLAYER_1_NAME, p1Name);
                    intent.putExtra(PLAYER_2_NAME, p2Name);
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(getActivity(), "Please enter player names", Toast.LENGTH_LONG).show();
            }
        }

        /*
        SecondFragment.StartGame(game);
        game.resetGame();       // we call the PigGame's reset method
        SecondFragment.DisplayScores(game);        // display zeroed scores
        SecondFragment.DieImage(8);        // reset the original dice image
        player1.setText("");    // rename the players
        player2.setText("");
         */
    }

    // if the enter key on the hard keyboard or the done key on the soft
    // keyboard are entered this event listener will activate and set the
    // players' names
  /*  @Override
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
   */
}
