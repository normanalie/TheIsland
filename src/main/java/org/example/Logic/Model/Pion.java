package org.example.Logic.Model;

import org.example.GUI.gamestates.Color;

public class Pion {
    private Color color;
    private int points;
    private int x;
    private int y;

    public Pion(Color color, int points) {
        this.color = color;
        this.points = points;
    }

    public Color getColor() {
        return color;
    }

    public int getPoints() {
        return points;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
