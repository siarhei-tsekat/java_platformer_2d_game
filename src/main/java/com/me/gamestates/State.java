package com.me.gamestates;

import com.me.Game;

public class State {

    private Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
