package com.example.applicationproject;

import android.content.Context;
import android.content.SharedPreferences;

public class MyDB {
    // Shared Preferences DB setup
    private static SharedPreferences sp;
    private static SharedPreferences.Editor speEdit;
    private static Context cnt;


    MyDB(Context context){
        cnt = context;
        sp = cnt.getSharedPreferences("Score", Context.MODE_PRIVATE);
        speEdit = sp.edit();
    }

    MyDB(){

    }
    // Update best score
    public void saveBestScore(int score){
        int dbPoints = sp.getInt("Score", 0); // Get score from DB
        // Update if needed
        if(score > dbPoints) {
            speEdit.putInt("Score", score);
            speEdit.commit();
       }
    }

    // Get the best score to display it
    public int getBestScore() {
        return sp.getInt("Score", 0);
    }


    // Reset the score
    public void reset() {
        speEdit.clear();
        speEdit.commit();
    }
}
