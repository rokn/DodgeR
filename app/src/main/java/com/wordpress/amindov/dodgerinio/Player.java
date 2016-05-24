package com.wordpress.amindov.dodgerinio;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class Player extends Transformable implements ScoreNotifier{

    public String TAG = getClass().getName();

    // At this value of accelerometer velocity is max
    private final float accelerometerThreshold = 7.5f;
    private final float defaultMaxVelocity = 500.0f;

    private Paint paint;
    private Paint redPaint;
    private float radius;
    private int score;
    List<PointF> myPoly;
    List<Observer> scoreObservers;

    public Player() {
        scoreObservers = new ArrayList<>();
        myPoly = new ArrayList<>(4);
    }

    @Override
    public void create() {
        sprite = BitmapFactory.decodeResource(owner.getResources(), R.drawable.player);
        paint = new Paint();
        setColor(Color.parseColor(owner.getResources().getString(R.string.playerColor)));
        redPaint = new Paint();
        redPaint.setColor(Color.parseColor("#FF0000"));
        setMaxVelocity(defaultMaxVelocity);
        PointF center = new PointF(owner.getDisplayRect().width()/2.0f, owner.getDisplayRect().height()/2.0f);
        rect = new RectF(center.x, center.y, center.x + sprite.getWidth(), center.y + sprite.getHeight());
        radius = sprite.getWidth() / 2.0f;
        setScore(0);
    }

    @Override
    public void update(float deltaTime) {
        PointF prevPos = getPosition();
        super.update(deltaTime);
        checkForCollision(prevPos);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, rect.left, rect.top, paint);

        if(MainActivity.DEBUG) {
            canvas.drawLine(myPoly.get(0).x, myPoly.get(0).y, myPoly.get(1).x, myPoly.get(1).y, redPaint);
            canvas.drawLine(myPoly.get(1).x, myPoly.get(1).y, myPoly.get(2).x, myPoly.get(2).y, redPaint);
            canvas.drawLine(myPoly.get(2).x, myPoly.get(2).y, myPoly.get(3).x, myPoly.get(3).y, redPaint);
            canvas.drawLine(myPoly.get(3).x, myPoly.get(3).y, myPoly.get(0).x, myPoly.get(0).y, redPaint);

            canvas.drawCircle(myPoly.get(0).x, myPoly.get(0).y, 3, redPaint);
            redPaint.setColor(Color.parseColor("#00FF00"));
            canvas.drawCircle(myPoly.get(1).x, myPoly.get(1).y, 3, redPaint);
            redPaint.setColor(Color.parseColor("#0000FF"));
            canvas.drawCircle(myPoly.get(2).x, myPoly.get(2).y, 3, redPaint);
            redPaint.setColor(Color.parseColor("#FF0FF0"));
            canvas.drawCircle(myPoly.get(3).x, myPoly.get(3).y, 3, redPaint);
            redPaint.setColor(Color.parseColor("#FF0000"));

        }
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        notifyObservers();
    }

    public void addScore(int score) {
        setScore(getScore() + score);
    }

    private void checkForCollision(PointF prevPos) {
        List<GameObject> blocks = owner.getGameObjects(new Predicate<GameObject>() {
            @Override
            public boolean apply(GameObject gameObject) {
                return gameObject instanceof Block;
            }
        });

        prevPos.x += radius;
        prevPos.y += radius;

        PointF currPos = new PointF(getPosition().x + radius, getPosition().y + radius);
        PointF dir = new PointF(currPos.x - prevPos.x, currPos.y - prevPos.y);
        float moveDistance = dir.length();
        PointF normDir = new PointF(dir.x / moveDistance, dir.y / moveDistance);
        float alpha = (float)Math.atan2(normDir.y, normDir.x);
        PointF bigCenter = new PointF(prevPos.x + dir.x/2.0f, prevPos.y + dir.y/2.0f);
        float bigRadius = moveDistance/2.0f;
        float d = (float)Math.sqrt(radius*radius + radius*radius);

        myPoly.clear();

        myPoly.add(new PointF(prevPos.x + (float)Math.cos(5*Math.PI/4 + alpha) * d, prevPos.y + (float)Math.sin(5*Math.PI/4 + alpha) * d));
        myPoly.add(new PointF(prevPos.x + (float)Math.cos(3*Math.PI/4 + alpha) * d, prevPos.y + (float)Math.sin(3*Math.PI/4 + alpha) * d));
        myPoly.add(new PointF(currPos.x + (float)Math.cos(Math.PI/4 + alpha) * d, currPos.y + (float) Math.sin(Math.PI/4 + alpha) * d));
        myPoly.add(new PointF(currPos.x + (float)Math.cos(alpha - Math.PI/4) * d, currPos.y + (float)Math.sin(alpha - Math.PI/4) * d));

        NAABB checker = new NAABB();
        checker.setA(myPoly);

        Block block;
        float blockRadius;
        PointF distanceToBlock = new PointF();

        for (GameObject obj : blocks) {
            block = (Block)obj;
            distanceToBlock.x = block.rect.centerX() - bigCenter.x;
            distanceToBlock.x = block.rect.centerY() - bigCenter.y;
            blockRadius = Math.max(block.rect.width(), block.rect.height()) / 2.0f;

            if(distanceToBlock.length() <= bigRadius + blockRadius) {
                boolean intersect;

                if(moveDistance > Math.min(block.rect.width(), block.rect.height())) {
                    //region NAABB
                    checker.setB(block.rect);
                    intersect = checker.isPolygonsIntersecting();

                    if(intersect && !block.isScoreBlock()) {
                        setPosition(prevPos);
                        float wy = (rect.width() + block.rect.width()) * (rect.centerY() - block.rect.centerY());
                        float hx = (rect.height() + block.rect.height()) * (rect.centerX() - block.rect.centerX());

                        if (wy > hx) {
                            if (wy > -hx) {
                                currPos.y = block.rect.bottom + radius;
                            } else {
                                currPos.x = block.rect.left - radius;
                            }
                        } else {
                            if (wy > -hx) {
                                currPos.x = block.rect.right + radius;
                            } else {
                                currPos.y = block.rect.top - radius;
                            }
                        }
                    }
                    //endregion
                } else {
                    //region Rect to Rect
                    RectF area = new RectF(rect);
                    intersect = area.intersect(block.rect);

                    if(intersect && !block.isScoreBlock()) {
                        if(area.width() >= area.height()) {
                            if(rect.centerY() < block.rect.centerY()) {
                                currPos.y = block.rect.top - radius;
                            }
                            else {
                                currPos.y = block.rect.bottom + radius;
                            }
                        }
                        else {
                            if(rect.centerX() < block.rect.centerX()) {
                                currPos.x = block.rect.left - radius;
                            }
                            else {
                                currPos.x = block.rect.right + radius;
                            }
                        }
                    }
                    //endregion
                }

                if(intersect && block.isScoreBlock()) {
                    owner.removeGameObject(block);
                    addScore(1);
                }
            }
        }

        setPosition(new PointF(currPos.x - radius, currPos.y - radius));
    }

    @Override
    public void attach(Observer observer) {
        scoreObservers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        scoreObservers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : scoreObservers) {
            observer.updateObserver();
        }
    }
}
