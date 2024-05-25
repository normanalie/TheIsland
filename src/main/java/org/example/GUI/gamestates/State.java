package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;


public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}