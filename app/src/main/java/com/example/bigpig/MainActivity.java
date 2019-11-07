package com.example.bigpig;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Imports the widgets
// Imports the listeners
// imports the number format tool
// imports everything for preferences and menu not already in app

public class MainActivity extends AppCompatActivity {

    private PigGame game;
    public PigGame getGame() { return game; }
    public void setGame(PigGame game) { this.game = game; }

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bigpig_game, menu);
        return true;
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

    public void newGame(PigGame g) {
        game = g;
        SecondFragment secondFragment = (SecondFragment)getFragmentManager()
                .findFragmentById(R.id.second_fragment);
        secondFragment.StartGame(game);
    }
}
