package org.example.Logic.Model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Tile[][] tiles;
    private List<Boat> boats;
    private List<Creature> creatures;

    public Board(int size) {
        tiles = new Tile[size][size];
        initializeBoard(size);
    }

    private void initializeBoard(int size) {
        // Initialiser les tuiles avec des types et positions appropriés
        // Exemple simplifié:
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                TileType type = getTileTypeFromMatrix(row, col); // Définir en fonction de votre logique
                tiles[row][col] = new Tile(row, col, 40, type); // 40 est un exemple de rayon
            }
        }
    }

    private TileType getTileTypeFromMatrix(int row, int col) {
        // Implémentez la logique pour obtenir le type de tuile à partir de la matrice
        return TileType.LAND; // Exemple par défaut
    }

    public Tile getTile(int row, int col) {
        if (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length) {
            return tiles[row][col];
        }
        return null;
    }

    public void placeTile(Tile tile, int row, int col) {
        tiles[row][col] = tile;
    }

    public void removeTile(int row, int col) {
        tiles[row][col] = null;
    }

    public void moveBoat(Boat boat, int row, int col) {
        // Logique pour déplacer un bateau
    }

    public void moveCreature(Creature creature, int row, int col) {
        // Logique pour déplacer une créature
    }

    public void placePion(Pion pion, int row, int col) {
        Tile tile = getTile(row, col);
        if (tile != null) {
            tile.addPawnToTile(pion);
        }
    }

    public Pion getPion(int row, int col) {
        Tile tile = getTile(row, col);
        if (tile != null) {
            return tile.getPion();
        }
        return null;
    }

    public void removePion(Pion pion, int row, int col) {
        Tile tile = getTile(row, col);
        if (tile != null) {
            tile.removePion(pion);
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void drawBoard(Graphics2D g) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                Tile hex = tiles[row][col];
                if (hex != null) {
                    hex.draw(g);
                }
            }
        }
    }

    public List<Tile> getHexagons() {
        // Convert 2D array to List for compatibility with previous logic
        List<Tile> hexagons = new ArrayList<>();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                if (tiles[row][col] != null) {
                    hexagons.add(tiles[row][col]);
                }
            }
        }
        return hexagons;
    }
}
