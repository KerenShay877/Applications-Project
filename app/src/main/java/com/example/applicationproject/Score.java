package com.example.applicationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Score extends AppCompatActivity{
    TextToSpeech textToSpeech;
    ImageView speech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Get access to Shared Preferences DB, update the best score, and display it.
        MyDB myDB = new MyDB();
        TextView bestScore = findViewById(R.id.bestScore);
        int points = myDB.getBestScore();
        bestScore.setText("BEST SCORE IS: " + points);

        speech = findViewById(R.id.speech);

        // Create an object textToSpeech and adding features into it
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // If no error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // Choose the language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        // Adding OnClickListener
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak("BEST SCORE IS: " + points,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

    // Go back to the main activity.
    public void toMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}