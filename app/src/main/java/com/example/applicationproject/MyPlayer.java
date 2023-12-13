package com.example.applicationproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;

public class MyPlayer {
    // Class that displays our player, and resets its initial position.
    Context context;
    Random random;
    int myPlayerX, myPlayerY, myPlayerSpeed;
    boolean stillAlive = true;
    Bitmap myPlayer;

    public MyPlayer(Context context) {
        // Draw the player, and set its initial position
        this.context = context;
        myPlayer = BitmapFactory.decodeResource(context.getResources(), R.drawable.myplayer);
        random = new Random();
        resetPlayer();
    }

    private void resetPlayer() {
        // Reset the player position
        myPlayerSpeed = 10 + random.nextInt(6);
        myPlayerX = random.nextInt(GameLogic.gameWidth);
        myPlayerY = GameLogic.gameHeight - (myPlayer.getHeight() / 2 + 350);
    }

    public Bitmap getMyPlayer(){
        return myPlayer;
    }
    int getPlayerWidth(){ return myPlayer.getWidth(); }
}
