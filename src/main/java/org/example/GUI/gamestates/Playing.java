package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.Logic.Model.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.awt.geom.Point2D.distance;

public class Playing extends State implements StateInterface {
    private BufferedImage backgroundImage;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;

    private Board board;
    private final int radius = 40;
    private Tile selectedTile;
    private Dice dice;
    private int movesRemaining;
    private int xDelta, yDelta;
    private boolean removeTileMode;

    public Playing(Game game, Board board) {
        super(game);
        this.board = board;
        initClasses();
        loadImages();
        dice = new Dice();
        movesRemaining = 3;
        removeTileMode = false;
        System.out.println("Successfully init Playing Game");
    }

    private void initClasses() {
        // Initialize any additional classes or resources here
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
            pionBleuImage = ImageIO.read(getClass().getResource("/pion_bleu.png"));
            pionRougeImage = ImageIO.read(getClass().getResource("/pion_rouge.png"));
            pionVertImage = ImageIO.read(getClass().getResource("/pion_vert.png"));
            pionJauneImage = ImageIO.read(getClass().getResource("/pion_jaune.png"));
        } catch (IOException e) {
            System.err.println("Error loading background image ");
            e.printStackTrace();
        }
    }

    public BufferedImage getPionImage() {
        switch (game.getCurrentPlayer().getColor()) {
            case ROUGE:
                return pionRougeImage;
            case BLEU:
                return pionBleuImage;
            case JAUNE:
                return pionJauneImage;
            case VERT:
                return pionVertImage;
            default:
                return null;
        }
    }

    @Override
    public void update() {
        // Update the game state here
        // Check if the player's turn is complete and move to the next player
        if (movesRemaining <= 0 && !removeTileMode) {
            enableTileRemoval();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        board.drawBoard(g2d); // Appel à la méthode drawBoard de Board

        // Draw current player color
        drawCurrentPlayerColor(g2d);
    }

    private void drawCurrentPlayerColor(Graphics2D g) {
        Player currentPlayer = game.getCurrentPlayer();
        java.awt.Color color = convertColor(currentPlayer.getColor());
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Current Player: " + currentPlayer.getName(), 20, 20);
        g.drawString("Moves Remaining: " + movesRemaining, 20, 40);
    }

    private java.awt.Color convertColor(org.example.GUI.gamestates.Color playerColor) {
        switch (playerColor) {
            case ROUGE:
                return java.awt.Color.RED;
            case BLEU:
                return java.awt.Color.BLUE;
            case JAUNE:
                return java.awt.Color.YELLOW;
            case VERT:
                return java.awt.Color.GREEN;
            default:
                return java.awt.Color.BLACK;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (int row = 0; row < board.getTiles().length; row++) {
            for (int col = 0; col < board.getTiles()[0].length; col++) {
                Tile hex = board.getTile(row, col);
                if (hex != null && isPointInsideHexagon(mouseX, mouseY, hex)) {
                    if (removeTileMode) {
                        handleTileRemoval(hex);
                    } else {
                        handleHexagonClick(hex);
                    }
                    break;
                }
            }
        }
    }

    private boolean isPointInsideHexagon(int mouseX, int mouseY, Tile hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    private void handleHexagonClick(Tile hex) {
        if (selectedTile == null) {
            // Check if the tile has a pawn and if it belongs to the current player
            Pion pion = hex.getPion();
            if (pion != null && pion.getColor() == game.getCurrentPlayer().getColor()) {
                // Select the tile
                selectedTile = hex;
                selectedTile.setSelected(true);
                highlightValidDestinations(selectedTile);
            }
        } else {
            // Move the piece from selectedTile to hex
            if (hex.isValidDestination()) {
                movePiece(selectedTile, hex);
                clearHighlights();
                selectedTile.setSelected(false);
                selectedTile = null; // Deselect the tile after moving the piece

                if (movesRemaining <= 0) {
                    enableTileRemoval();
                }
            } else {
                // If the selected destination is not valid, deselect the current tile
                selectedTile.setSelected(false);
                selectedTile = null;
                clearHighlights();
            }
        }
    }

    private void highlightValidDestinations(Tile fromTile) {
        // Clear any previous highlights
        clearHighlights();

        // Check and highlight valid destinations
        for (int row = 0; row < board.getTiles().length; row++) {
            for (int col = 0; col < board.getTiles()[0].length; col++) {
                Tile hex = board.getTile(row, col);
                if (hex != null && isValidMove(fromTile, hex)) {
                    hex.setValidDestination(true);
                }
            }
        }
    }

    private void clearHighlights() {
        for (int row = 0; row < board.getTiles().length; row++) {
            for (int col = 0; col < board.getTiles()[0].length; col++) {
                Tile hex = board.getTile(row, col);
                if (hex != null) {
                    hex.setSelected(false);
                    hex.setValidDestination(false);
                }
            }
        }
    }

    private void movePiece(Tile fromTile, Tile toTile) {
        // Implement the logic to move a piece from fromTile to toTile
        // Ensure the move follows game rules
        int distance = calculateDistance(fromTile, toTile);
        if (distance <= movesRemaining) {
            Pion explorer = fromTile.getPion(); // Get the explorer from the fromTile
            if (explorer != null && isValidMove(fromTile, toTile)) {
                fromTile.removePion(explorer);
                toTile.addPawnToTile(explorer);
                movesRemaining -= distance; // Decrease the moves remaining
            }
        }
    }

    private int calculateDistance(Tile fromTile, Tile toTile) {
        // Implement logic to calculate the distance between two tiles
        // Here we use the Manhattan distance for simplicity
        int dx = Math.abs(fromTile.getX() - toTile.getX());
        int dy = Math.abs(fromTile.getY() - toTile.getY());
        return (dx + dy) / radius; // Adjust calculation as needed based on your game logic
    }

    private boolean isValidMove(Tile fromTile, Tile toTile) {
        // Implement the logic to check if the move is valid
        // This should include boundary checks and game rules
        if (toTile == null || fromTile == toTile) {
            return false;
        }
        int distance = calculateDistance(fromTile, toTile);
        return distance <= movesRemaining && toTile.getType() != TileType.MOUNTAIN;
    }

    private void enableTileRemoval() {
        removeTileMode = true;
        // Indiquer à l'utilisateur qu'il doit maintenant retirer une tuile
        System.out.println("Remove a tile adjacent to sea before ending your turn.");
    }

    private void handleTileRemoval(Tile tile) {
        if (canRemoveTile(tile)) {
            removeTile(tile);
            removeTileMode = false; // Désactiver le mode de suppression des tuiles après la suppression
            game.nextTurn(); // Passer au tour du joueur suivant
            movesRemaining = 3; // Réinitialiser les mouvements pour le prochain joueur
        }
    }

    private boolean canRemoveTile(Tile tile) {
        return true;
        // TODO
        // Vérifier si la tuile est adjacente à une case de mer
        // Vérifier l'ordre de suppression (plage, forêt, montagne)
    }

    private boolean isAdjacentToSea(Tile tile) {
        int x = tile.getX();
        int y = tile.getY();
        Tile[][] tiles = board.getTiles();

        // Taille d'une tuile
        int tileWidth = 2 * radius;
        int tileHeight = (int) (Math.sqrt(3) * radius);

        // Vérifier les tuiles adjacentes
        int[][] directions = {
                {tileWidth, 0}, {-tileWidth, 0}, // Droite, Gauche
                {tileWidth / 2, tileHeight}, {-tileWidth / 2, tileHeight}, // Bas Droite, Bas Gauche
                {tileWidth / 2, -tileHeight}, {-tileWidth / 2, -tileHeight} // Haut Droite, Haut Gauche
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            Tile adjacentTile = getTileByCoordinates(newX, newY);
            if (adjacentTile != null && adjacentTile.getType() == TileType.NONE) {
                return true;
            }
        }

        return false;
    }

    private Tile getTileByCoordinates(int x, int y) {
        for (int row = 0; row < board.getTiles().length; row++) {
            for (int col = 0; col < board.getTiles()[0].length; col++) {
                Tile tile = board.getTile(row, col);
                if (tile != null && tile.getX() == x && tile.getY() == y) {
                    return tile;
                }
            }
        }
        return null;
    }


    private boolean isValidRemovalOrder(Tile tile) {
        // Implement the logic to check the removal order: beach -> forest -> mountain
        TileType type = tile.getType();
        if (type == TileType.LAND) {
            return true;
        } else if (type == TileType.FOREST) {
            // Check if no beach tiles are left
            for (int row = 0; row < board.getTiles().length; row++) {
                for (int col = 0; col < board.getTiles()[0].length; col++) {
                    Tile t = board.getTile(row, col);
                    if (t != null && t.getType() == TileType.LAND) {
                        return false;
                    }
                }
            }
            return true;
        } else if (type == TileType.MOUNTAIN) {
            // Check if no beach or forest tiles are left
            for (int row = 0; row < board.getTiles().length; row++) {
                for (int col = 0; col < board.getTiles()[0].length; col++) {
                    Tile t = board.getTile(row, col);
                    if (t != null && (t.getType() == TileType.LAND || t.getType() == TileType.FOREST)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void removeTile(Tile tile) {
        if (tile.getPion() != null) {
            // Si un explorateur se trouvait sur la tuile, il devient nageur
            tile.getPion().setNageur(true);
        }
        board.removeTile(tile.getX(), tile.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    public void mouseMoved(MouseEvent e) {
        this.setRectPos(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }
}
