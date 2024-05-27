package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.ui.PawnSelectionOverlay;
import org.example.Logic.Model.Board;
import org.example.Logic.Model.Pion;
import org.example.Logic.Model.Tile;
import org.example.Logic.Model.TileType;
import org.example.GUI.mainGame.Matrice;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.Point2D;
import java.util.List;

public class PionSelection extends State implements StateInterface {
    private BufferedImage backgroundImage;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;

    private Board board;
    private Matrice matrice;
    private final int radius = 40;
    private float xDelta = 100, yDelta = 100;

    private PawnSelectionOverlay pawnSelectionOverlay;

    public PionSelection(Game game, Board board) {
        super(game);
        this.board = board;
        this.matrice = new Matrice(13); // Taille de la matrice
        initClasses();
        loadImages();
        generateHexagonGrid();  // Appel à generateHexagonGrid
        System.out.println("Successfully init PionSelection");
    }

    private void generateHexagonGrid() {
        int xOffset = (int) (radius * 1.71);
        int yOffset = (int) (radius * 1.5);

        int windowStart = 270;
        int startY = radius;

        for (int row = 0; row < matrice.matrix.length; row++) {
            int startX = getStartX(row, windowStart, xOffset);

            for (int col = 0; col < matrice.matrix[row].length; col++) {
                if (matrice.matrix[row][col] != TileType.NONE) {
                    int x = startX + xOffset * col + (row % 2) * (xOffset / 2);
                    TileType type = matrice.matrix[row][col];
                    board.placeTile(new Tile(x, startY + yOffset * row, radius, type), row, col);
                }
            }
        }
    }

    private int getStartX(int row, int windowStart, int xOffset) {
        if (row == 0 || row == matrice.matrix.length - 1) {
            return windowStart + xOffset * 2;
        } else if (row == 5 || row == 7) {
            return windowStart - xOffset;
        } else {
            return windowStart;
        }
    }

    @Override
    public void update() {
        pawnSelectionOverlay.update();
    }

    private void initClasses() {
        this.pawnSelectionOverlay = new PawnSelectionOverlay(this, game);
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
    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        board.drawBoard(g2d); // Appel à la méthode drawBoard de Board

        if (!pawnSelectionOverlay.getPawnSelected()) {
            pawnSelectionOverlay.draw(g);
        } else {
            BufferedImage pionImage = getPionImage();
            g.drawImage(pionImage, (int) xDelta, (int) yDelta, 40, 20, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (int row = 0; row < board.getTiles().length; row++) {
            for (int col = 0; col < board.getTiles()[0].length; col++) {
                Tile tile = board.getTile(row, col);
                if (tile != null && isPointInsideTile(mouseX, mouseY, tile) && pawnSelectionOverlay.getPawnSelected()) {
                    handleTileClick(tile);
                    pawnSelectionOverlay.setPawnSelected(false);
                    game.nextTurn();
                    break;
                }
            }
        }
    }

    private boolean isPointInsideTile(int mouseX, int mouseY, Tile tile) {
        double dist = Point2D.distance(mouseX, mouseY, tile.getX(), tile.getY());
        return dist < radius;
    }

    private void handleTileClick(Tile tile) {
        tile.addPawnToTile(new Pion(game.getCurrentPlayer().getColor(), 1));
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

    public PawnSelectionOverlay getPawnSelectionOverlay() {
        return pawnSelectionOverlay;
    }

    public List<Tile> getHexagons() {
        return board.getHexagons();
    }
}
