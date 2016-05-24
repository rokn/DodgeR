package com.wordpress.amindov.dodgerinio;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.SensorEvent;

/**
 * Created by Antonio Mindov on 5/23/2016.
 */
public class Block extends Transformable{

    private static Bitmap DefaultBlock;
    private static Bitmap ScoreBlock;


    private PointF startPos;
    private boolean entered;
    private Paint redPaint;
    private float screenPercent;
    private boolean vertical;

    public Block(float percent, PointF position, PointF velocity, float rotation) {
        startPos = position;
        entered = false;
        screenPercent = percent;

        this.rect = new RectF(position.x, position.y, 0,0);
        setMaxVelocity(Math.max(velocity.x, velocity.y));
        setVelocity(velocity);
        setRotation(rotation);

        vertical = Math.abs(velocity.x) < Math.abs(velocity.y);
    }

    public static void loadBlocks(Resources resources) {
        DefaultBlock = BitmapFactory.decodeResource(resources, R.drawable.block);
    }

    @Override
    public void create() {
        int scale = (int)(((vertical) ? owner.getDisplayRect().width() : owner.getDisplayRect().height()) * screenPercent / 100.0f);
        sprite = Bitmap.createScaledBitmap(DefaultBlock, scale, DefaultBlock.getHeight(), false);
        rect.right = rect.left + sprite.getWidth();
        rect.bottom = rect.top + sprite.getHeight();
        updateSprite();
        redPaint = new Paint();
        redPaint.setColor(Color.parseColor("#FF0000"));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(entered) {
            if (!RectF.intersects(rect, owner.getDisplayRect())) {
                reset();
            }
        } else {
            entered = RectF.intersects(rect, owner.getDisplayRect());
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, rect.left, rect.top, null);

        if(MainActivity.DEBUG) {
            canvas.drawRect(rect, redPaint);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    private void reset() {
        owner.removeGameObject(this);
    }
}
