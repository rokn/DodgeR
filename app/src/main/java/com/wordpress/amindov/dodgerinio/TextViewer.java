package com.wordpress.amindov.dodgerinio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.hardware.SensorEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Antonio Mindov on 5/24/2016.
 */
public class TextViewer extends GameObject{

    protected String text;
    protected Paint textPaint;
    protected PointF position;

    public TextViewer() {
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        text = "";
        position = new PointF();
    }

    @Override
    public void create() {
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, position.x, position.y, textPaint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public PointF getPosition() {
        return position;
    }

    public void setColor(int color) {
        textPaint.setColor(color);
    }
}
