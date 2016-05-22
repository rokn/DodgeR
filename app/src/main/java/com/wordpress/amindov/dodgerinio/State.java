package com.wordpress.amindov.dodgerinio;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.hardware.SensorEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public abstract class State {

    private GameView owner;
    private List<GameObject> gameObjects;
    private boolean created;

    public State(GameView owner){
        this.owner = owner;
        gameObjects = new LinkedList<>();
        created = false;
    }

    public void create() {
        for (GameObject obj :
                gameObjects) {
            obj.create();
        }

        created = true;
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

    public void onSensorChanged(SensorEvent event) {
        for (GameObject obj :
                gameObjects) {
            obj.onSensorChanged(event);
        }
    }

    public void addGameObject(GameObject object) {
        object.setOwner(this);

        if(created) {
            object.create();
        }

        gameObjects.add(object);
    }

    public Resources getResources() {
        return owner.getResources();
    }
}
