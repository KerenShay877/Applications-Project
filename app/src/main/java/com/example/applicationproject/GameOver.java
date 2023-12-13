package com.example.applicationproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends AppCompatActivity {
    TextView tvPoints;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Get the number of points for the game that has ended
        int points = getIntent().getExtras().getInt("score");
        // Show the points on screen
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + points);

        // Store the points in the database, if they're the highest achieved
        MyDB myDB = new MyDB(this);
        myDB.saveBestScore(points);
    }

    // Restart the game
    public void restart(View view) {
        Intent intent = new Intent(GameOver.this, StartGameScreen.class);
        startActivity(intent);
        finish();
    }

    // Return back to the main screen
    public void exit(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}