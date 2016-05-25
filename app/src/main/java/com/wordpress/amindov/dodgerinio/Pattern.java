package com.wordpress.amindov.dodgerinio;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio Mindov on 5/24/2016.
 */
public class Pattern{

    private class Entry {
        public Entry() {
            percentage = 0.0f;
            position = 0.0f;
            mirror = false;
            velocity = new PointF();
            rotation = 0.0f;
            isScore = false;
        }

        public float percentage;
        public float position;
        public  boolean mirror;
        public PointF velocity;
        public float rotation;
        public  boolean isScore;
    }

    private List<Entry> entries;
    private List<Block> blocks;
    private float multiplier;

    public Pattern() {
        entries = new ArrayList<>();
        blocks = new ArrayList<>();
        multiplier = 1.0f;
    }

    public void addEntry(float percentage, float offset, boolean mirror, PointF velocity, float rotation, boolean isScore) {
        Entry newEntry = new Entry();
        newEntry.percentage = percentage;
        newEntry.position = offset;
        newEntry.mirror = mirror;
        newEntry.velocity = velocity;
        newEntry.rotation = rotation;
        newEntry.isScore = isScore;
        entries.add(newEntry);
    }

    public void play(State state) {
        for (Entry entry : entries) {
            PointF velocity = new PointF();
            velocity.x = entry.velocity.x * multiplier;
            velocity.y = entry.velocity.y * multiplier;
            Block block = new Block(entry.percentage, entry.mirror, entry.position, velocity);
            block.setScoreBlock(entry.isScore);
            state.addGameObject(block);
            blocks.add(block);
        }
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public boolean isDone() {
        boolean done = true;

        for (Block block : blocks) {
            done &= block.isDestroyed();
        }

        if(done) {
            blocks.clear();
        }

        return done;
    }
}
