package org.example.Logic.Model;

import org.example.GUI.gamestates.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Tile> handTiles;
    private List<Pion> explorers;
    private int[] pawnsCollection;
    private Color color;

    public Player(String name, int[] pawnsCollection, Color color) {
        this.name = name;
        this.pawnsCollection = pawnsCollection.clone();
        this.color = color;
        this.handTiles = new ArrayList<>();
        this.explorers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int[] getPawnsCollection() {
        return pawnsCollection;
    }

    public int updatePawnsCollection(int index) {
        if (pawnsCollection[index] > 0) {
            pawnsCollection[index]--;
            return 1;
        } else {
            for (int i = 0; i < pawnsCollection.length; i++) {
                if (pawnsCollection[i] != 0) {
                    return -1;
                }
            }
            return 0;
        }
    }

    public void takeTurn(Board board, Dice dice) {
        playTile(board);
        // movePiece(board); // This will now be handled by the Playing class
        removeTile(board);
        rollDieAndMoveCreature(board, dice);
    }

    private void playTile(Board board) {
        if (!handTiles.isEmpty()) {
            Tile tile = handTiles.remove(0);
            // Place the tile on the board at a valid position (this requires board logic)
            board.placeTile(tile, tile.getX(), tile.getY());
        }
    }

    private void removeTile(Board board) {
        // Implement the logic to remove a tile from the board according to game rules
    }

    private void rollDieAndMoveCreature(Board board, Dice dice) {
        Creature creature = dice.lancer();
        // Implement logic to move the creature on the board
    }
}
