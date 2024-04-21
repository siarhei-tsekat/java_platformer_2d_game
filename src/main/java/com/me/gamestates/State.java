package com.me.gamestates;

import com.me.Game;
import com.me.ui.MenuBtn;

import java.awt.event.MouseEvent;

public class State {

    private Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isIn(MouseEvent e, MenuBtn mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }
}
