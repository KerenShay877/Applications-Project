package com.example.applicationproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Computer {
    // Class to display and set the appropriate properties for the computer spaceship
    Context context;
    Random random;
    Bitmap computerSpaceship;
    int computerX, computerY, computerSpeed;

    public Computer(Context context) {
        // Draw the computer spaceship, and set its initial position
        this.context = context;
        random = new Random();
        computerSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.computer);
        resetTheComputer();
    }
    private void resetTheComputer() {
        // Reset the computer spaceship to a position
        computerSpeed = 14 + random.nextInt(10);
        computerX = 200 + random.nextInt(400);
        computerY = 0;
    }
    int getComputerSpaceshipWidth(){
        return computerSpaceship.getWidth();
    }
    public Bitmap getComputerSpaceship(){
        return computerSpaceship;
    }
    int getComputerSpaceshipHeight(){
        return computerSpaceship.getHeight();
    }

}
