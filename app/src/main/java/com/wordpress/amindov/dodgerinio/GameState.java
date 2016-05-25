package com.wordpress.amindov.dodgerinio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class GameState extends State{

    public String TAG = getClass().getName();

    private Player player;
    private PatternPlayer patternPlayer;
    private boolean gameOver = false;
    Button reset;

    public GameState(GameView owner) {
        super(owner);
    }

    @Override
    public void create() {

        player = new Player();
        addGameObject(player);

        patternPlayer = new PatternPlayer(this);
        patternPlayer.setObserver(player);

        Pattern currPattern = new Pattern();
        currPattern.addEntry(25.0f, 50.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(25.0f, 50.0f, false,  new PointF(0.0f, 300.0f), 0.0f, false);
        patternPlayer.addPattern(currPattern);

        final ScoreViewer scoreText = new ScoreViewer();
        scoreText.setNotifier(player);
        addGameObject(scoreText);

        gameOver = false;

        reset = new Button(0);

        reset.attach(new Observer() {
            @Override
            public void updateObserver() {
                player.reset();
                reset.hide();
                gameOver = false;
            }
        });

        addGameObject(reset);
        reset.setInCenter(new PointF(0,0));
        reset.hide();

        super.create();
    }

    @Override
    public void update(float deltaTime) {

        patternPlayer.update(deltaTime);

        if(!RectF.intersects(displayRect, player.getRect()) && !gameOver) {
            gameOver = true;
            goHome();
        }

        super.update(deltaTime);
    }

    public void goHome() {
        reset.show();
        updateHighscore(player.getScore());
        player.hide();
    }

    private boolean updateHighscore(int score) {
        SharedPreferences prefs = owner.getPreferences();
        boolean changed = false;

        if(prefs.getInt("HighScore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("HighScore", score);
            editor.apply();
            changed = true;
            Log.d(TAG, "Changed");
        }

        return changed;
    }
}
