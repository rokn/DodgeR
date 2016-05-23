package com.wordpress.amindov.dodgerinio;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public abstract class State {

    public String TAG = getClass().getName();

    protected GameView owner;
    protected RectF displayRect;
    private LinkedList<GameObject> gameObjects;
    private List<GameObject> removeList;
    private boolean created;

    public State(GameView owner){
        this.owner = owner;
        gameObjects = new LinkedList<>();
        removeList = new ArrayList<>();
        created = false;
    }

    public void create() {
        WindowManager wm = (WindowManager) owner.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayRect = new RectF(0, 0, size.x, size.y);

        for (GameObject obj : gameObjects) {
            obj.create();
        }

        created = true;
    }

    public void update(float deltaTime) {
        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }

        for (GameObject obj : removeList) {
            gameObjects.remove(obj);
        }

        removeList.clear();

        Log.i(TAG, Integer.toString(gameObjects.size()));
    }

    public void draw(Canvas canvas) {
        for (GameObject obj : gameObjects) {
            if(!obj.isDestroyed()) {
                obj.draw(canvas);
            }
        }
    }

    public void destroy() {
        for (GameObject obj : gameObjects) {
            obj.destroy();
        }
    }

    public void onSensorChanged(SensorEvent event) {
        for (GameObject obj : gameObjects) {
            obj.onSensorChanged(event);
        }
    }

    public void addGameObject(GameObject object) {
        object.setOwner(this);

        if(created) {
            object.create();
        }

        gameObjects.addFirst(object);
    }

    public void removeGameObject(GameObject object) {
        object.destroy();
        removeList.add(object);
    }

    public Resources getResources() {
        return owner.getResources();
    }

    public GameView getOwner() {
        return owner;
    }

    public RectF getDisplayRect() {
        return displayRect;
    }
}
