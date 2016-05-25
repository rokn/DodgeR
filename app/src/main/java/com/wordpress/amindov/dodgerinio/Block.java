package com.wordpress.amindov.dodgerinio;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

/**
 * Created by Antonio Mindov on 5/23/2016.
 */
public class Block extends Transformable{

    private static Bitmap DefaultBlockSprite;
    private static Bitmap ScoreBlockSprite;
    private static float MinSide = 50.0f;

    private boolean entered;
    private Paint paint;
    private Paint redPaint;
    private float screenPercent;
    private boolean vertical;

    private boolean scoreBlock;
    private boolean mirror;
    private float offset;

    public Block(float percent, boolean mirror, float offset, PointF velocity) {
        paint = new Paint();
        entered = false;
        screenPercent = percent;

        setMaxVelocity(Math.max(Math.abs(velocity.x), Math.abs(velocity.y)));
        setVelocity(velocity);
        setRotation(0);

        vertical = Math.abs(velocity.x) < Math.abs(velocity.y);

        this.rect = new RectF(0.0f,0.0f,0.0f,0.0f);

        redPaint = new Paint();
        redPaint.setColor(Color.parseColor("#FF0000"));

        this.mirror = mirror;
        this.offset = offset;
    }

    @Override
    public void create() {
        float width;
        float height;

        if(vertical) {
            height = MinSide;

            if(isScoreBlock()) {
                width = owner.getDisplayRect().width();
            } else {
                width = owner.getDisplayRect().width() * screenPercent / 100.0f;

                if(mirror) {
                    rect.left = owner.getDisplayRect().width() - width;
                }
            }

            if(getVelocity().y > 0) {
                rect.top = -offset - height;
            } else {
                rect.top = owner.getDisplayRect().height() + offset;
            }

            if(isScoreBlock()) {
                height = 1;
            }
        } else {
            width = MinSide;

            if(isScoreBlock()) {
                height = owner.getDisplayRect().height();
            } else {
                height = owner.getDisplayRect().height() * screenPercent / 100.0f;

                if(mirror) {
                    rect.top = owner.getDisplayRect().height() - height;
                }
            }

            if(getVelocity().x > 0) {
                rect.left = -offset - width;
            } else {
                rect.left = owner.getDisplayRect().width() + offset;
            }

            if(isScoreBlock()) {
                width = 1;
            }
        }

        rect.right = rect.left + width;
        rect.bottom = rect.top + height;

        if(!isScoreBlock()) {
            sprite = DefaultBlockSprite.copy(DefaultBlockSprite.getConfig(), false);
            updateSprite();
        }

        setColor(Color.argb(255,220,30,30));
    }

    public static void loadBlocks(Resources resources) {
        DefaultBlockSprite = BitmapFactory.decodeResource(resources, R.drawable.block);
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
        if(!isScoreBlock()) {
            canvas.drawBitmap(sprite, rect.left, rect.top, paint);
        }

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

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    private void reset() {
        owner.removeGameObject(this);
    }

    public boolean isScoreBlock() {
        return scoreBlock;
    }

    public void setScoreBlock(boolean scoreBlock) {
        this.scoreBlock = scoreBlock;
    }

    public void setColor(int color) {
        paint.setColorFilter(new LightingColorFilter(color, 0));
    }
}
