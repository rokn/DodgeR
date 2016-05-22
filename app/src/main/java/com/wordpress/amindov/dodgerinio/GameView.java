package com.wordpress.amindov.dodgerinio;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class GameView extends SurfaceView implements Runnable {

    Thread gameThread;
    SurfaceHolder holder;
    Canvas canvas;
    State currState;

    volatile boolean playing;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        playing = true;
        currState = null;
    }

    @Override
    public void run() {
        final float deltaTime = 0.016f;
        float currTime;
        float addedTime = 0.0f;
        float oldTime = 0.0f;

        while (playing) {
            currTime = System.currentTimeMillis() / 1000.0f;
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

            if(currState != null) {
                currState.draw(canvas);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        while(playing) {
            try {
                gameThread.join();
                playing = false;
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
    }

}
