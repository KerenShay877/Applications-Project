package com.example.applicationproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    // Create the needed object for the music to play
    MediaPlayer myPlayer;
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Initialize the service
    @Override
    public void onCreate() {
        super.onCreate();
        myPlayer = MediaPlayer.create(this, R.raw.music);
        myPlayer.setLooping(true); // Set looping
        myPlayer.setVolume(100,100);
        myPlayer.start();

    }

    // Destroy the service
    @Override
    public void onDestroy() {
        super.onDestroy();
        myPlayer.stop();
        myPlayer.release();
    }
}

