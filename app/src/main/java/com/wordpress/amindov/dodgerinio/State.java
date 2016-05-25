package com.wordpress.amindov.dodgerinio;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public abstract class State {
    public String TAG = getClass().getName();

    protected GameView owner;
    private LinkedList<GameObject> gameObjects;
    private List<GameObject> addList;
    private List<GameObject> removeList;
    private volatile boolean created;

    public State(GameView owner){
        this.owner = owner;
        gameObjects = new LinkedList<>();
        addList = new ArrayList<>();
        removeList = new ArrayList<>();
        created = false;
    }

    public void create() {
//        for (GameObject obj : gameObjects) {
//            obj.create();
//        }
        created = true;
    }

    public void update(float deltaTime) {
        for (GameObject obj : addList) {
            gameObjects.addFirst(obj);
        }

        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }

        for (GameObject obj : removeList) {
            gameObjects.remove(obj);
        }

        addList.clear();
        removeList.clear();
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
        object.create();

        if(created) {
            addList.add(object);
        } else {
            gameObjects.addFirst(object);
        }
    }

    public void onTouchEvent(MotionEvent event) {
        for (GameObject obj : gameObjects) {
            obj.onTouchEvent(event);
        }
    }

    public void removeGameObject(GameObject object) {
        object.destroy();
        removeList.add(object);
    }

    public Resources getResources() {
        return owner.getResources();
    }

    public Context getContext() { return owner.getContext(); }

    public GameView getOwner() {
        return owner;
    }

    public RectF getDisplayRect() {
        return owner.getDisplayRect();
    }

    public List<GameObject> getGameObjects(Predicate<GameObject> predicate) {
        List<GameObject> result = new LinkedList<>();

        for (GameObject obj : gameObjects) {
            if(predicate.apply(obj)) {
                result.add(obj);
            }
        }

        return result;
    }

    public boolean isCreated() {
        return created;
    }
}
