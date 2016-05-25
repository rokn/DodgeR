package com.wordpress.amindov.dodgerinio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class GameView extends SurfaceView implements Runnable, SensorEventListener {

    public final String TAG = getClass().getName();

    private SensorManager sensorManager;
    private Thread gameThread;
    private SurfaceHolder holder;
    protected RectF displayRect;
    private Canvas canvas;
    private State currState;
    private int clearColor;
    private SharedPreferences preferences;
    private MainActivity owner;
    private Bitmap background;

    private volatile boolean playing;

    public GameView(MainActivity context, SharedPreferences prefs) {
        super(context);
        holder = getHolder();
        playing = true;
        currState = null;
        clearColor = Color.parseColor(getContext().getString(R.string.clearColor));
        owner = context;

        // Register
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        preferences = prefs;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.back);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayRect = new RectF(0, 0, size.x, size.y);
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
        if(currState != null && currState.isCreated()) {
            currState.update(deltaTime);
        }
    }

    public void draw() {
        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();

            canvas.drawColor(clearColor);
            canvas.drawBitmap(background, null, displayRect, null);

            if(currState != null && currState.isCreated()) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currState.onTouchEvent(event);
        return true;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void showToast(final String toast) {
        owner.showToast(toast);
    }

    public RectF getDisplayRect() {
        return displayRect;
    }
}
