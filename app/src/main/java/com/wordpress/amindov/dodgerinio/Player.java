package com.wordpress.amindov.dodgerinio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class Player extends GameObject {

    private Bitmap sprite;
    private Paint paint;
    private PointF position;
    private PointF velocity;
    private float maxVelocity;
    private final float defaultMaxVelocity = 300.0f;

    // At this value of accelerometer velocity is max
    final float accelerometerThreshold = 9.81f;

    public Player() {
    }

    @Override
    public void create() {
        sprite = BitmapFactory.decodeResource(owner.getResources(), R.drawable.player);
        paint = new Paint();
        setColor(Color.parseColor(owner.getResources().getString(R.string.playerColor)));
        position = new PointF();
        velocity = new PointF();
        maxVelocity = defaultMaxVelocity;
    }

    @Override
    public void update(float deltaTime) {
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, position.x, position.y, paint);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            PointF newVelocity = new PointF();

            newVelocity.x = -event.values[0] * maxVelocity / accelerometerThreshold;
            newVelocity.y = event.values[1]  * maxVelocity / accelerometerThreshold;

            setVelocity(newVelocity);
        }
    }

    public void setVelocity(PointF velocity) {
        this.velocity = velocity;

        //clamping
        this.velocity.x = Math.max(-maxVelocity, Math.min(maxVelocity, this.velocity.x));
        this.velocity.y = Math.max(-maxVelocity, Math.min(maxVelocity, this.velocity.y));
    }

    public PointF getVelocity() {
        return velocity;
    }

    public void setColor(int color) {
        paint.setColorFilter(new LightingColorFilter(color, 0));
    }
}
