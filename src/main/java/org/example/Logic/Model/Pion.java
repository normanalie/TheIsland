package org.example.Logic.Model;

import org.example.GUI.gamestates.Color;

public class Pion {
    private Color color;
    private int number;

    public Pion(Color color,int number){
        this.color = color;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
