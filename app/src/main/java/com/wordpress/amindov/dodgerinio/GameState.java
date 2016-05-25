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
        currPattern.addEntry(35.0f, 60.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 60.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 60.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 20.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(65.0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(65.0f, 150.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(65.0f, 250.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(0f, 150.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(0f, 250.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(65.0f, 50.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(65.0f, 150.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(65.0f, 250.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(0f, 150.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(0f, 250.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();

        currPattern.addEntry(20.0f, 50.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(25.0f, 200.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(30.0f, 350.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 500.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 650.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(45.0f, 800.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(50.0f, 950.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);

        currPattern.addEntry(65.0f, 50.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(55.0f, 350.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(50.0f, 500.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(45.0f, 650.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 800.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(60.0f, 200.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 950.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);

        currPattern.addEntry(65.0f, 50.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(60.0f, 200.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(55.0f, 350.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(50.0f, 500.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(45.0f, 650.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(40.0f, 800.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 950.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(20.0f, 550.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 800.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(60.0f, 950.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(80.0f, 1100.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);

        currPattern.addEntry(60.0f, 450.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 700.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(20.0f, 850.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);

        currPattern.addEntry(0.0f, 450.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(0.0f, 700.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(0.0f, 850.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(0.0f, 1000.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);

        currPattern.addEntry(40.0f, 50.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 50.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(40.0f, 50.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 150.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 150.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(40.0f, 150.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 250.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 250.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(40.0f, 250.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 350.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 350.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(40.0f, 350.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 450.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 450.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(40.0f, 450.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 550.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(40.0f, 550.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(40.0f, 550.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(60.0f, 50.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(60.0f, 650.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 650.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(60.0f, 1250.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 1250.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(60.0f, 1850.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 1850.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(45.0f, 50.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(45.0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 20.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
//        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(45.0f, 50.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(45.0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 20.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
//        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(45.0f, 50.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(45.0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 20.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
//        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(45.0f, 50.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(45.0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 20.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
//        patternPlayer.addPattern(currPattern);

        currPattern = new Pattern();
        currPattern.addEntry(45.0f, 50.0f, true, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, true);
        currPattern.addEntry(45.0f, 50.0f, false, new PointF(300.0f, 0.0f), 0.0f, false);
        currPattern.addEntry(35.0f, 20.0f, true, new PointF(0.0f, 300.0f), 0.0f, false);
        currPattern.addEntry(0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, true);
        currPattern.addEntry(35.0f, 20.0f, false, new PointF(0.0f, 300.0f), 0.0f, false);
//        patternPlayer.addPattern(currPattern);

        final ScoreViewer scoreText = new ScoreViewer();
        scoreText.setNotifier(player);
        addGameObject(scoreText);

        super.create();
    }

    @Override
    public void update(float deltaTime) {

        patternPlayer.update(deltaTime);

        if(!RectF.intersects(getDisplayRect(), player.getRect())) {
            owner.changeState(new EndState(owner, player.getScore()));
        }

        super.update(deltaTime);
    }
}
