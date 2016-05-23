package com.wordpress.amindov.dodgerinio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class Player extends Transformable {

    public String TAG = getClass().getName();

    // At this value of accelerometer velocity is max
    private final float accelerometerThreshold = 7.5f;
    private final float defaultMaxVelocity = 500.0f;

    private Paint paint;

    public Player() {
        super();
    }

    @Override
    public void create() {
        sprite = BitmapFactory.decodeResource(owner.getResources(), R.drawable.player);
        paint = new Paint();
        setColor(Color.parseColor(owner.getResources().getString(R.string.playerColor)));
        setMaxVelocity(defaultMaxVelocity);
        rect = new RectF(200,200, 200 + sprite.getWidth(), 200 + sprite.getHeight());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, rect.left, rect.top, paint);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            PointF newVelocity = new PointF();

            newVelocity.x = -event.values[0] * getMaxVelocity() / accelerometerThreshold;
            newVelocity.y = event.values[1]  * getMaxVelocity() / accelerometerThreshold;

            setVelocity(newVelocity);
        }
    }

    public void setColor(int color) {
        paint.setColorFilter(new LightingColorFilter(color, 0));
    }
}
