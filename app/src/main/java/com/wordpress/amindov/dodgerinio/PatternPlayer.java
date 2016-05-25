package com.wordpress.amindov.dodgerinio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Antonio Mindov on 5/25/2016.
 */
public class PatternPlayer implements Observer{

    private int level;
    private Random rand;

    private List<Pattern> patterns;
    private Pattern currPattern;

    private ScoreNotifier notifier;
    private final int LevelThreshold = 3;
    private final float LevelMultiplier = 0.02f;
    private State owner;

    public PatternPlayer(State owner) {
        this.owner = owner;
        patterns = new ArrayList<>();
        rand = new Random();
        level = 0;
    }

    public void update(float deltaTime) {
        if(currPattern == null || currPattern.isDone()) {
            currPattern = patterns.get(rand.nextInt(patterns.size()));
            currPattern.setMultiplier(1.0f + level * LevelMultiplier);
            currPattern.play(owner);
        }
    }

    public void setObserver(ScoreNotifier notifier) {
        this.notifier = notifier;
        notifier.attach(this);
    }

    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
    }

    @Override
    public void updateObserver() {
        level = notifier.getScore() / LevelThreshold;
    }
}
