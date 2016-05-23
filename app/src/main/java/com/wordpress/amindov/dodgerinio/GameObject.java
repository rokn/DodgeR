package com.wordpress.amindov.dodgerinio;

import android.graphics.Canvas;
import android.hardware.SensorEvent;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public abstract class GameObject {

    private static long IdCounter = 0;

    protected State owner;
    private long id;
    private boolean destroyed;

    public GameObject() {
        id = IdCounter++;
    }

    public abstract void create();

    public abstract void update(float deltaTime);

    public abstract void draw(Canvas canvas);

    public void destroy() {
        destroyed = true;
    }

    public abstract void onSensorChanged(SensorEvent event);

    public State getOwner() {
        return owner;
    }

    public void setOwner(State owner) { this.owner = owner; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
