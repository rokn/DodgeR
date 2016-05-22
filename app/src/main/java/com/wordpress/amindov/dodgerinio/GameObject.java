package com.wordpress.amindov.dodgerinio;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public abstract class GameObject {

    protected State owner;

    public abstract void create();

    public abstract void update(float deltaTime);

    public abstract void draw(Canvas canvas);

    public abstract void destroy();

    public abstract void onSensorChanged(SensorEvent event);

    public State getOwner() {
        return owner;
    }

    public void setOwner(State owner) { this.owner = owner; }

}
