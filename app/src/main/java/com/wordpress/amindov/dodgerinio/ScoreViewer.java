package com.wordpress.amindov.dodgerinio;

import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Antonio Mindov on 5/25/2016.
 */
public class ScoreViewer extends TextViewer implements Observer{

    ScoreNotifier scoreNotifier;

    @Override
    public void create() {
        super.create();
        setTextSize(100.0f);
        setPosition(new PointF(owner.getDisplayRect().width() / 2.0f, 120.0f));
        setText("0");
    }

    @Override
    public void updateObserver() {
        int newScore = scoreNotifier.getScore();
        setText(Integer.toString(newScore));
    }

    public void setNotifier(ScoreNotifier notifier) {
        notifier.attach(this);
        scoreNotifier = notifier;
    }
}
