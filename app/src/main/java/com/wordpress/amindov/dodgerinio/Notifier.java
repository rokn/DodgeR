package com.wordpress.amindov.dodgerinio;

/**
 * Created by Antonio Mindov on 5/24/2016.
 */
public interface Notifier {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
