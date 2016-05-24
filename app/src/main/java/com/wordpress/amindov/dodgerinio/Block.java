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

    private static Bitmap DefaultBlockSprite;
    private static Bitmap ScoreBlockSprite;
    private static float MinSide = 50.0f;

    private PointF startPos;
    private boolean entered;
    private Paint redPaint;
    private float screenPercent;
    private boolean vertical;

    private boolean scoreBlock;

    public Block(float percent, PointF position, PointF velocity) {
        startPos = position;
        entered = false;
        screenPercent = percent;

        this.rect = new RectF(position.x, position.y, 0,0);
        setMaxVelocity(Math.max(velocity.x, velocity.y));
        setVelocity(velocity);
        setRotation(0);

        vertical = Math.abs(velocity.x) < Math.abs(velocity.y);

        redPaint = new Paint();
        redPaint.setColor(Color.parseColor("#FF0000"));
    }

    @Override
    public void create() {
        float width;
        float height;

        if(vertical) {
            if(isScoreBlock()) {
                width = owner.getDisplayRect().width();
            } else {
                width = owner.getDisplayRect().width() * screenPercent / 100.0f;
            }

            height = MinSide;
        } else {
            width = MinSide;

            if(isScoreBlock()) {
                height = owner.getDisplayRect().height();
            } else {
                height = owner.getDisplayRect().width() * screenPercent / 100.0f;
            }
        }

        rect.right = rect.left + width;
        rect.bottom = rect.top + height;

        if(!isScoreBlock()) {
            sprite = DefaultBlockSprite.copy(DefaultBlockSprite.getConfig(), false);
            updateSprite();
        }
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
            canvas.drawBitmap(sprite, rect.left, rect.top, null);
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

    private void reset() {
        owner.removeGameObject(this);
    }

    public boolean isScoreBlock() {
        return scoreBlock;
    }

    public void setScoreBlock(boolean scoreBlock) {
        this.scoreBlock = scoreBlock;
    }
}
