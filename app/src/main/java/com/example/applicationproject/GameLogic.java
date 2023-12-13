package com.example.applicationproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic extends View {
    // This class handles all the complex logic behind the game.
    private static final long UPDATE_TIME = 30;
    private static final int MAX_BULLETS = 3;
    private static final int BLAST_FRAMES = 8;
    private Context context;
    private Bitmap backgroundImage, lifePicture;
    private Random random;
    private MyPlayer myPlayer;
    private Computer computerSpaceship;
    private Handler handler;
    private int score = 0, hearts = 3, TEXT_SIZE = 50;
    private Paint scorePaint;
    private ArrayList<Bullet> computerBullets, ourBullets;
    private Blast blast;
    private ArrayList<Blast> blasts;
    private boolean onHold = false, computerBulletAction = false;
    private boolean computerBlast = false;
    public static int gameWidth;
    public static int gameHeight;


    // Initializes a new instance of the GameLogic class.
    public GameLogic(Context context) {
        super(context);
        this.context = context;
        random = new Random();
        initGameProperties();
    }

    // Initializes game properties such as screen dimensions, player, computer, images, and handler.
    private void initGameProperties() {
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameWidth = size.x;
        gameHeight = size.y;

        ourBullets = new ArrayList<>();
        computerBullets = new ArrayList<>();
        blasts = new ArrayList<>();

        myPlayer = new MyPlayer(context);
        computerSpaceship = new Computer(context);

        backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        lifePicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);

        handler = new Handler();

        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    //  Called every 30 milliseconds to update the graphics.
    //  Calls various methods to draw background, hearts, move computer spaceship, handle bullets, draw spaceships, handle collisions, draw explosions, and check the game over.
    //  Triggers continuous updates using a Handler.
    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawHearts(canvas);
        moveComputerSpaceship();
        handleComputerBulletAction();
        drawComputerSpaceship(canvas);
        drawOurSpaceship(canvas);
        handleComputerBulletCollision(canvas);
        handleOurBulletCollision(canvas);
        drawExplosions(canvas);
        checkGameOver();
        if (!onHold) {
            handler.postDelayed(runnable, UPDATE_TIME);
        }
    }

    // Draws the background image and displays the current score on the canvas.
    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(backgroundImage, 0, 0, null);
        canvas.drawText("Current score: " + score, 0, TEXT_SIZE, scorePaint);
    }

    // Draws the remaining hearts (lives) on the screen.
    private void drawHearts(Canvas canvas) {
        for (int i = hearts; i >= 1; i--) {
            canvas.drawBitmap(lifePicture, gameWidth - lifePicture.getWidth() * i, 0, null);
        }
    }

    // Moves the computer spaceship horizontally and handles its direction change when reaching screen edges.
    private void moveComputerSpaceship() {
        computerSpaceship.computerX += computerSpaceship.computerSpeed;
        if (computerSpaceship.computerX + computerSpaceship.getComputerSpaceshipWidth() >= gameWidth || computerSpaceship.computerX <= 0) {
            computerSpaceship.computerSpeed *= -1;
        }
    }

    // Generates computer bullets based on certain conditions.
    private void handleComputerBulletAction() {
        if (!computerBulletAction && computerSpaceship.computerX >= 200 + random.nextInt(400)) {
            Bullet enemyShot = new Bullet(context, computerSpaceship.computerX + computerSpaceship.getComputerSpaceshipWidth() / 2, computerSpaceship.computerY);
            computerBullets.add(enemyShot);
            computerBulletAction = true;
        }
    }

    // Draws the computer spaceship on the canvas if there is no explosion.
    private void drawComputerSpaceship(Canvas canvas) {
        if (!computerBlast) {
            canvas.drawBitmap(computerSpaceship.getComputerSpaceship(), computerSpaceship.computerX, computerSpaceship.computerY, null);
        }
    }

    // Draws the player's spaceship on the canvas, handling position limits.
    private void drawOurSpaceship(Canvas canvas) {
        if (myPlayer.stillAlive) {
            if (myPlayer.myPlayerX > gameWidth - myPlayer.getPlayerWidth()) {
                myPlayer.myPlayerX = gameWidth - myPlayer.getPlayerWidth();
            } else if (myPlayer.myPlayerX < 0) {
                myPlayer.myPlayerX = 0;
            }
            canvas.drawBitmap(myPlayer.getMyPlayer(), myPlayer.myPlayerX, myPlayer.myPlayerY, null);
        }
    }

    // Handles the movement and collision detection of computer bullets with the player.
    private void handleComputerBulletCollision(Canvas canvas) {
        for (int i = 0; i < computerBullets.size(); i++) {
            computerBullets.get(i).bulletY += 15;
            drawBullet(canvas, computerBullets.get(i), computerBullets.get(i).bulletX, computerBullets.get(i).bulletY);

            if (bulletHitsPlayer(computerBullets.get(i))) {
                hearts--;
                computerBullets.remove(i);
                createExplosion(myPlayer.myPlayerX, myPlayer.myPlayerY);
            } else if (computerBullets.get(i).bulletY >= gameHeight) {
                computerBullets.remove(i);
            }
            if (computerBullets.size() == 0) {
                computerBulletAction = false;
            }
        }
    }

    // Handles the movement and collision detection of player bullets with the computer.
    private void handleOurBulletCollision(Canvas canvas) {
        for (int i = 0; i < ourBullets.size(); i++) {
            ourBullets.get(i).bulletY -= 15;
            drawBullet(canvas, ourBullets.get(i), ourBullets.get(i).bulletX, ourBullets.get(i).bulletY);

            if (bulletHitsComputer(ourBullets.get(i))) {
                score++;
                ourBullets.remove(i);
                createExplosion(computerSpaceship.computerX, computerSpaceship.computerY);
            } else if (ourBullets.get(i).bulletY <= 0) {
                ourBullets.remove(i);
            }
        }
    }

    // Checks if a given bullet has hit the player.
    private boolean bulletHitsPlayer(Bullet bullet) {
        return bullet.bulletX >= myPlayer.myPlayerX &&
                bullet.bulletX <= myPlayer.myPlayerX + myPlayer.getPlayerWidth() &&
                bullet.bulletY >= myPlayer.myPlayerY &&
                bullet.bulletY <= gameHeight;
    }

    // Checks if a given bullet has hit the computer.
    private boolean bulletHitsComputer(Bullet bullet) {
        return bullet.bulletX >= computerSpaceship.computerX &&
                bullet.bulletX <= computerSpaceship.computerX + computerSpaceship.getComputerSpaceshipWidth() &&
                bullet.bulletY <= computerSpaceship.getComputerSpaceshipHeight() &&
                bullet.bulletY >= computerSpaceship.computerY;
    }

    // Draws explosion animations on the canvas.
    private void drawExplosions(Canvas canvas) {
        for (int i = 0; i < blasts.size(); i++) {
            canvas.drawBitmap(blasts.get(i).getBlast(blasts.get(i).blastFrame), blasts.get(i).blastX, blasts.get(i).blastY, null);
            blasts.get(i).blastFrame++;
            if (blasts.get(i).blastFrame > BLAST_FRAMES) {
                blasts.remove(i);
            }
        }
    }

    // Creates an explosion object at the specified coordinates.
    private void createExplosion(float x, float y) {
        Blast explosion = new Blast(context, (int) x, (int) y);
        blasts.add(explosion);
    }

    // Checks if the game is over (no remaining hearts) and initiates the end-game process.
    private void checkGameOver() {
        if (hearts == 0) {
            onHold = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score", score);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
    }

    // Handles touch events, allowing the player to control their spaceship and shoot bullets.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (ourBullets.size() < MAX_BULLETS) {
                Bullet ourShot = new Bullet(context, myPlayer.myPlayerX + myPlayer.getPlayerWidth() / 2, myPlayer.myPlayerY);
                ourBullets.add(ourShot);
            }
        } else if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            myPlayer.myPlayerX = touchX;
        }
        return true;
    }

    // Invalidates the view, triggering a redraw, to continuously update the graphics.
    private final Runnable runnable = this::invalidate;

    // Draws a bullet on the canvas at the specified position.
    private void drawBullet(Canvas canvas, Bullet bullet, float x, float y) {
        canvas.drawBitmap(bullet.getTheBullet(), x, y, null);
    }
}
