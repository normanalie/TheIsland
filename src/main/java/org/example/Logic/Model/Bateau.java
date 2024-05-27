package org.example.Logic.Model;

import org.example.GUI.gamestates.Color;

public class Bateau {
    private Color playerColor;

    public Bateau(Color playerColor){
        this.playerColor = playerColor;
    }

    public Color getPlayerColor(){
        return this.playerColor;
    }

}
