package com.wordpress.amindov.dodgerinio;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/25/2016.
 */
public class Button extends Transformable implements ClickNotifier{

    private boolean clicked;
    private Bitmap clickedSprite;
    private List<Observer> observers;
    int id;

    public Button(int id) {
        observers = new ArrayList<>();
        this.id = id;
        clicked = false;
        hidden = false;
    }

    @Override
    public void create() {

        switch(id) {
            case 0:
                sprite = BitmapFactory.decodeResource(owner.getResources(), R.drawable.reset_button);
                break;
            case 1:
                sprite = BitmapFactory.decodeResource(owner.getResources(), R.drawable.play_button);
                break;
            case 2:
                sprite = BitmapFactory.decodeResource(owner.getResources(), R.drawable.options_button);
                break;
        }

        clickedSprite = sprite;

        if(sprite == null) {
            throw new Resources.NotFoundException("Block sprite with id : " + Integer.toString(id));
        }

        rect = new RectF(0,0,sprite.getWidth(),sprite.getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        if(!hidden) {
            if (clicked) {
                canvas.drawBitmap(clickedSprite, rect.left, rect.top, null);
            } else {
                canvas.drawBitmap(sprite, rect.left, rect.top, null);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!hidden) {
            if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                if (rect.contains(event.getRawX(), event.getRawY())) {
                    clicked = true;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (clicked) {
                    notifyObservers();
                }
            }
        }
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateObserver();
        }
    }

    public void setInCenter(PointF offset) {
        float x = owner.displayRect.width()/2 - rect.width()/2 + offset.x;
        float y = owner.displayRect.height()/2 - rect.height()/2 + offset.y;
        setPosition(new PointF(x, y));
    }

}
