package com.example.applicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // Setup for FireBase authentication
    FirebaseAuth auth;
    ImageView button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        MyDB myDB = new MyDB(this);
        // If there's no user in the DB, you can't be in a main screen
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        // Display welcome message
        else {
            textView.setText("Hello, " + user.getEmail());
        }

        // Add onclick listener for the logout button, and logout out of the account
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the high score everytime the user logs out
                myDB.reset();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Go to the music activity to enable background music
    public void toMusic(View v) {
        Intent toMusic = new Intent(this, Music.class);
        startActivity(toMusic);
    }

    // Go to the start screen of the main game
    public void toGame(View v) {
        Intent toGame = new Intent(this, StartGameScreen.class);
        startActivity(toGame);
    }

    // Go to the screen that displays the best score
    public void toScore(View v) {
        Intent toScore = new Intent(this, Score.class);
        startActivity(toScore);
    }

    // Go to the screen that represents the info about the game
    public void toInfo(View v) {
        Intent toInfo = new Intent(this, Info.class);
        startActivity(toInfo);
    }

}