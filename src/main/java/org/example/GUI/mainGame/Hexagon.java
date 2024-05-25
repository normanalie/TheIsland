package org.example.GUI.mainGame;

import org.example.Logic.Model.Pion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Hexagon {
    private int x, y, radius;
    private Polygon polygon;
    private Type type;
    private BufferedImage pionRougeImage;
    private BufferedImage pionJauneImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionBleuImage;

    private BufferedImage tuilePlage;
    private BufferedImage tuileForet;
    private BufferedImage tuileMontagne;
    private ArrayList<Pion> pions;

    private Game game;

    public enum Type {
        LAND, FOREST, MOUNTAIN, NONE
    }

    public Hexagon(int x, int y, int radius, String type) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.type = Type.valueOf(type.toUpperCase());
        this.polygon = createHexagon(x, y, radius);
        this.pions = new ArrayList<Pion>();
        loadImages();
    }

    private void loadImages() {
        tuilePlage = loadImage("/tuile_plage.png");
        tuileForet = loadImage("/tuile_foret.png");
        tuileMontagne = loadImage("/tuile_montagne.png");
        pionRougeImage = loadImage("/pion_rouge.png");
        pionVertImage = loadImage("/pion_vert.png");
        pionJauneImage = loadImage("/pion_jaune.png");
        pionBleuImage = loadImage("/pion_bleu.png");
    }

    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Polygon createHexagon(int x, int y, int radius) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            int angle = 60 * i - 30; // Offset by -30 degrees to align the hexagon
            int xOffset = (int) (radius * Math.cos(Math.toRadians(angle)));
            int yOffset = (int) (radius * Math.sin(Math.toRadians(angle)));
            hex.addPoint(x + xOffset, y + yOffset);
        }
        return hex;
    }

    public void draw(Graphics2D g) {
        BufferedImage tuileImageToDraw = getTuileImageToDraw();

        drawImage(g, tuileImageToDraw);
        drawBorder(g);

        // Draw pawns
        drawPawns(g);
    }

    private BufferedImage getTuileImageToDraw() {
        switch (type) {
            case LAND:
                return tuilePlage;
            case FOREST:
                return tuileForet;
            case MOUNTAIN:
                return tuileMontagne;
            default:
                return null;
        }
    }

    private BufferedImage getPionImage(Pion pion) {
        switch (pion.getColor()) {
            case ROUGE:
                return pionRougeImage;
            case BLEU:
                return pionBleuImage;
            case VERT:
                return pionVertImage;
            case JAUNE:
                return pionJauneImage;
            default:
                return null;
        }
    }

    private void drawImage(Graphics2D g, BufferedImage image) {
        if (image != null) {
            g.setClip(polygon);
            g.drawImage(image, x - radius, y - radius, radius * 2, radius * 2, null);
            g.setClip(null);
        } else {
            drawPlaceholder(g);
        }
    }

    private void drawPlaceholder(Graphics2D g) {
        Color fillColor;
        switch (type) {
            case LAND:
                fillColor = Color.YELLOW;
                g.setColor(fillColor);
                g.fillPolygon(polygon);
                break;
            case FOREST:
                fillColor = Color.GREEN;
                g.setColor(fillColor);
                g.fillPolygon(polygon);
                break;
            case MOUNTAIN:
                fillColor = Color.GRAY;
                g.setColor(fillColor);
                g.fillPolygon(polygon);
                break;
            default:
                break;
        }
    }

    private void drawBorder(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2)); // Set the stroke thickness to 2
        g.drawPolygon(polygon);
    }

    public void addPawnToHexagon(Pion pion) {
        pions.add(pion);
    }

    private void drawPawns(Graphics2D g) {
        int offsetX = 0;
        int offsetY = 0;
        int step = 20;

        for (Pion pion : pions) {
            BufferedImage pionImage = getPionImage(pion);
            if (pionImage != null) {
                g.drawImage(pionImage, this.x - pionImage.getWidth() / 2 - offsetX, this.y - pionImage.getHeight() / 2 - offsetY, null);
            }
            offsetX += step;
            offsetY += step;
            if (offsetX > radius) offsetX = 0;
            if (offsetY > radius) offsetY = 0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
