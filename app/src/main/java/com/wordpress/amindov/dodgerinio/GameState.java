package com.wordpress.amindov.dodgerinio;

/**
 * Created by Antonio Mindov on 5/22/2016.
 */
public class GameState extends State {

    private Player player;

    public GameState(GameView owner) {
        super(owner);
    }

    @Override
    public void create() {
        player = new Player();
        addGameObject(player);
        super.create();
    }


}
