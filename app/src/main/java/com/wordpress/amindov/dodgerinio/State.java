package com.wordpress.amindov.dodgerinio;

import android.graphics.Canvas;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class State {

    private GameView owner;
    private List<GameObject> gameObjects;

    public State(GameView owner){
        this.owner = owner;
        gameObjects = new LinkedList<>();
    }

    public void update(float deltaTime) {
        for (GameObject obj :
                gameObjects) {
            obj.update(deltaTime);
        }
    }

    public void draw(Canvas canvas) {
        for (GameObject obj :
                gameObjects) {
            obj.draw(canvas);
        }
    }

    public void destroy() {
        for (GameObject obj :
                gameObjects) {
            obj.destroy();
        }
    }
}
