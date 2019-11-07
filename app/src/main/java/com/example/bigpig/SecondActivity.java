package com.example.bigpig;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import java.text.NumberFormat;

public class SecondActivity extends AppCompatActivity {

    private PigGame game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view using XML
        setContentView(R.layout.second_activity_main);

        SecondFragment secondFragment = (SecondFragment)getFragmentManager()
                .findFragmentById(R.id.second_fragment);

        int p1 = 0, p2 = 0, s = 0, t = 0, dieNum = 0;
        String p1Name = "", p2Name = "";
        if(savedInstanceState != null) {
            p1 = savedInstanceState.getInt(SecondFragment.SCORE_1);
            p2 = savedInstanceState.getInt(SecondFragment.SCORE_2);
            s = savedInstanceState.getInt(SecondFragment.CURRENT_SCORE);
            t = savedInstanceState.getInt(SecondFragment.NUM_TURNS);
            p1Name = savedInstanceState.getString(SecondFragment.PLAYER_1);
            p2Name = savedInstanceState.getString(SecondFragment.Player_2);
            dieNum = savedInstanceState.getInt(SecondFragment.NUM_DIE);

            NumberFormat integer = NumberFormat.getIntegerInstance();
            SecondFragment.numDie.setText(integer.format(dieNum));
        }
        else {// starts the game
            game = new PigGame();
            p1Name = getIntent().getExtras().getString(FirstFragment.PLAYER_1_NAME);
            p2Name = getIntent().getExtras().getString(FirstFragment.PLAYER_2_NAME);
            game.setPlayer1Name(p1Name);
            game.setPlayer2Name(p2Name);
            secondFragment.StartGame(game);
        }


    }
}
