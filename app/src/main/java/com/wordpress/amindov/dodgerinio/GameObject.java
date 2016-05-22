package com.wordpress.amindov.dodgerinio;

import android.graphics.Canvas;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public abstract class GameObject {

    State owner;

    public GameObject(State owner) {
        this.owner = owner;
    }

    public abstract void update(float deltaTime);

    public abstract void draw(Canvas canvas);

    public abstract void destroy();

    public State getOwner() {
        return owner;
    }
}
