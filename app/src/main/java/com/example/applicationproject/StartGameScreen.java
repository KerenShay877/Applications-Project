package com.example.applicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartGameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_screen);
    }

    // Move from the start screen to the game itself
    public void startGame(View v) {
        Intent start = new Intent(this, Game.class);
        startActivity(start);
        finish();
    }
}