package com.wordpress.amindov.dodgerinio;

import android.graphics.PointF;

/**
 * Created by Antonio Mindov on 5/25/2016.
 */
public class MainMenuState extends State {
    public MainMenuState(GameView owner) {
        super(owner);
    }

    @Override
    public void create() {

        Button playButton = new Button(1);
        Button optionsButton = new Button(2);
        addGameObject(playButton);
        addGameObject(optionsButton);

        playButton.attach(new Observer() {
            @Override
            public void updateObserver() {
                owner.changeState(new GameState(owner));
            }
        });

        optionsButton.attach(new Observer() {
            @Override
            public void updateObserver() {
                owner.showToast("No options for you son... just play.");
            }
        });

        playButton.setInCenter(new PointF(0,-50));
        optionsButton.setInCenter(new PointF(0,50));

        playButton.show();
        optionsButton.show();

        super.create();
    }
}
