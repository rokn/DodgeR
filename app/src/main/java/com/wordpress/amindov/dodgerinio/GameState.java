package com.wordpress.amindov.dodgerinio;

import android.content.Intent;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class GameState extends State implements Observer{

    public String TAG = getClass().getName();

    private Player player;
    private boolean gameOver = false;
    private Pattern currPattern;
    private ScoreNotifier scoreNotifier;

    public GameState(GameView owner) {
        super(owner);
    }

    @Override
    public void create() {

        player = new Player();
        addGameObject(player);
        scoreNotifier = player;
        scoreNotifier.attach(this);

        currPattern = new Pattern();
        currPattern.setMultiplier(1.0f);
        currPattern.addEntry(75.0f, new PointF(0.0f, -50.0f), new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(75.0f, new PointF(0.0f, -50.0f), new PointF(0.0f, 300.0f), 0.0f, true);
//        currPattern.addEntry(50.0f, new PointF(300.0f, -50.0f), new PointF(0.0f, 300.0f), 0.0f);
//        currPattern.addEntry(50.0f, new PointF(160.0f, -150.0f), new PointF(0.0f, 300.0f), 0.0f);
//        currPattern.addEntry(50.0f, new PointF(460.0f, -150.0f), new PointF(0.0f, 300.0f), 0.0f);
//        currPattern.addEntry(50.0f, new PointF(120.0f, -250.0f), new PointF(0.0f, 300.0f), 0.0f);
//        currPattern.addEntry(50.0f, new PointF(420.0f, -250.0f), new PointF(0.0f, 300.0f), 0.0f);
        currPattern.play(this);

        gameOver = false;

        super.create();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(currPattern.isDone()) {
            currPattern.play(this);
        }

        if(!RectF.intersects(displayRect, player.getRect()) && !gameOver) {
            gameOver = true;
            goHome();
        }
    }

    public void goHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        owner.getContext().startActivity(intent);
    }

    @Override
    public void updateObserver() {
        int newScore = scoreNotifier.getScore();
        Log.i(TAG, Integer.toString(newScore));
    }
}
