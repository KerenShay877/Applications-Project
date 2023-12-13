package com.example.applicationproject;

import android.content.Context;
import android.content.Intent;

public class MusicManager {
    private static Intent musicServiceIntent;
    private static boolean musicOn;
    private static Context context;
    MusicManager(Context cnt)
    {
        musicServiceIntent = new Intent(cnt, MusicService.class);
        musicOn = false;
        context = cnt;
    }

    MusicManager()
    {

    }

    // Start the music service
    public void startMusic()
    {
        context.startService(musicServiceIntent);
        musicOn = true;
    }

    // Stop the music service
    public void stopMusic()
    {
        context.stopService(musicServiceIntent);
        musicOn = false;
    }

    // Get the music on attribute in order to determine what to do with the music
    public boolean getMusicOn()
    {
        return musicOn;
    }
}
