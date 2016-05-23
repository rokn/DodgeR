package com.wordpress.amindov.dodgerinio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class GameView extends SurfaceView implements Runnable, SensorEventListener {

    public final String TAG = getClass().getName();

    private SensorManager sensorManager;
    private Thread gameThread;
    private SurfaceHolder holder;
    private Canvas canvas;
    private State currState;
    private int clearColor;

    volatile boolean playing;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        playing = true;
        currState = null;
        clearColor = Color.parseColor(getContext().getString(R.string.clearColor));

        // Register
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void run() {
        final float deltaTime = 0.016f;
        float currTime;
        float addedTime = 0.0f;
        float oldTime = SystemClock.elapsedRealtime() / 1000.0f;

        while (playing) {
            currTime = SystemClock.elapsedRealtime() / 1000.0f;
            addedTime += currTime - oldTime;
            oldTime = currTime;

            if (addedTime >= deltaTime) {
                update(deltaTime);
                addedTime -= deltaTime;
            }

            draw();
        }
    }

    public void update(float deltaTime) {
        if(currState != null) {
            currState.update(deltaTime);
        }
    }

    public void draw() {
        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();

            canvas.drawColor(clearColor);

            if(currState != null) {
                currState.draw(canvas);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        boolean stopped = false;
        playing = false;

        while(!stopped) {
            try {
                gameThread.join();
                stopped = true;
            }catch (InterruptedException ignored){}
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void changeState(State newState) {
        if(currState != null) {
            currState.destroy();
        }

        currState = newState;
        currState.create();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(currState != null) {
            currState.onSensorChanged(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
