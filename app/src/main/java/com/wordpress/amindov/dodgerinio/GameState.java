package com.wordpress.amindov.dodgerinio;

import android.content.Intent;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class GameState extends State {

    public String TAG = getClass().getName();

    private Player player;
    private boolean gameOver = false;

    public GameState(GameView owner) {
        super(owner);
    }

    @Override
    public void create() {

        player = new Player();
        addGameObject(player);

        addGameObject(new Block(new PointF(0.0f,0.0f), new PointF(0.0f,300.0f), 0.0f));

        gameOver = false;

        super.create();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

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
}
