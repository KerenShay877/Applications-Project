package com.example.applicationproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Blast {
    // Class blast in order to create an explosion like animation to notify the user when they hit the computer.
    int blastFrame;
    int blastX, blastY;
    Bitmap blast[] = new Bitmap [9];

    public Blast(Context context, int blastX, int blastY) {
        // Save all the explosions together in order to display it in the game

        blastFrame = 0;
        this.blastX = blastX;
        this.blastY = blastY;

        blast[0] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast0);
        blast[1] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast1);
        blast[2] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast2);
        blast[3] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast3);
        blast[4] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast4);
        blast[5] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast5);
        blast[6] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast6);
        blast[7] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast7);
        blast[8] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.blast8);
    }

    public Bitmap getBlast(int blastFrame){
        return blast[blastFrame];
    }
}
