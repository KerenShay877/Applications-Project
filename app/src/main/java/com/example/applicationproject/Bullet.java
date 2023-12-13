package com.example.applicationproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

    public class Bullet {
    // Class bullet for the bullets that will be displayed in the game.
    int bulletX, bulletY;
    Bitmap bullet;
    Context context;

    public Bullet(Context context, int bulletX, int bulletY) {
        // Draw the bullet on the screen using the BitmapFactory, and set its initial position.
        this.context = context;
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
    }
    public Bitmap getTheBullet(){
        return bullet;
    }
    public int getBulletWidth() {
        return bullet.getWidth();
    }
    public int getBulletHeight() {
        return bullet.getHeight();
    }
}
