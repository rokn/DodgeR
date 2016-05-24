package com.wordpress.amindov.dodgerinio;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Antonio Mindov on 5/23/2016.
 */
public abstract class Transformable extends GameObject{
    protected RectF rect;
    protected Bitmap sprite;
    private PointF velocity;
    private float rotation;
    private float maxVelocity;

    public Transformable() {
        velocity = new PointF();
    }

    @Override
    public void update(float deltaTime) {
        updateRect(deltaTime);
    }

    public RectF getRect() {
        return rect;
    }

    public PointF getVelocity() {
        return velocity;
    }

    public void setVelocity(PointF velocity) {
        this.velocity = velocity;

        //clamping
        this.velocity.x = Math.max(-maxVelocity, Math.min(maxVelocity, this.velocity.x));
        this.velocity.y = Math.max(-maxVelocity, Math.min(maxVelocity, this.velocity.y));
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    private void updateRect(float deltaTime) {
        rect.left += velocity.x * deltaTime;
        rect.right += velocity.x * deltaTime;
        rect.top += velocity.y * deltaTime;
        rect.bottom += velocity.y * deltaTime;
    }

    public void setPosition(PointF position) {
        float width = rect.width();
        float height = rect.height();
        rect.left = position.x;
        rect.top = position.y;
        rect.right = position.x + width;
        rect.bottom = position.y + height;
    }

    public PointF getPosition() {
        return new PointF(rect.left, rect.top);
    }

    protected void updateSprite() {
        if(sprite != null) {
            sprite = Bitmap.createScaledBitmap(sprite, (int) rect.width(), (int) rect.height(), false);
            sprite = Helper.RotateBitmap(sprite, rotation);
        }
    }
}
