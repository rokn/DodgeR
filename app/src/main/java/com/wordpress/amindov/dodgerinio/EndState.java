package com.wordpress.amindov.dodgerinio;

import android.content.SharedPreferences;
import android.graphics.PointF;

/**
 * Created by Antonio Mindov on 5/25/2016.
 */
public class EndState extends State {

    private int score;
    private int highScore;

    public EndState(GameView owner, int score) {
        super(owner);
        this.score = score;
    }

    @Override
    public void create() {
        super.create();
        updateHighScore(score);

        Button reset = new Button(0);

        reset.attach(new Observer() {
            @Override
            public void updateObserver() {
                owner.changeState(new GameState(owner));
            }
        });

        addGameObject(reset);
        reset.setInCenter(new PointF(0, 200));
        reset.show();

        TextViewer highScoreText = new TextViewer();
        highScoreText.setPosition(new PointF(getDisplayRect().width() / 2.0f, getDisplayRect().height() / 2.0f - 200.0f));
        highScoreText.setText("HighScore");
        highScoreText.setTextSize(40.0f);
        addGameObject(highScoreText);

        TextViewer highScoreValue = new TextViewer();
        highScoreValue.setPosition(new PointF(getDisplayRect().width()/2.0f, getDisplayRect().height()/2.0f - 100.0f));
        highScoreValue.setText(Integer.toString(highScore));
        highScoreValue.setTextSize(40.0f);
        addGameObject(highScoreValue);

        TextViewer scoreText = new TextViewer();
        scoreText.setPosition(new PointF(getDisplayRect().width()/2.0f, getDisplayRect().height()/2.0f));
        scoreText.setText("Your Score");
        scoreText.setTextSize(40.0f);
        addGameObject(scoreText);

        TextViewer scoreValue = new TextViewer();
        scoreValue.setPosition(new PointF(getDisplayRect().width()/2.0f, getDisplayRect().height()/2.0f + 100.0f));
        scoreValue.setText(Integer.toString(score));
        scoreValue.setTextSize(40.0f);
        addGameObject(scoreValue);
    }

    private boolean updateHighScore(int score) {
        SharedPreferences prefs = owner.getPreferences();
        boolean changed = false;
        highScore = prefs.getInt("HighScore", 0);

        if(highScore < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("HighScore", score);
            editor.apply();
            changed = true;
            highScore = score;
        }

        return changed;
    }
}
