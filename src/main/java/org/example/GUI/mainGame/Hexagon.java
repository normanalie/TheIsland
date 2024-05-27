package org.example.GUI.mainGame;

import org.example.Logic.Model.*;

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
    private BufferedImage serpentImage;
    private BufferedImage baleineImage;
    private BufferedImage requinImage;
    private ArrayList<Pion> pions;
    private Bateau bateau;
    private Requin requin;
    private Serpent serpent;
    private Baleine baleine;
    private BufferedImage bateauImage;

    private Game game;

    public void setBateau(Bateau bateau) {
        this.bateau = bateau;
    }

    public Serpent getSerpent() {
        return this.serpent;
    }

    public Baleine getBaleine() {
        return this.baleine;
    }

    public Requin getRequin() {
    return this.requin;
    }

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
        bateau = null;
        requin = null;
        baleine = null;
        serpent = null;
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
        bateauImage = loadImage("/jeton_bateau.png");
        requinImage = loadImage("/jeton_requin.png");
        baleineImage = loadImage("/jeton_baleine.png");
        serpentImage = loadImage("/jeton_serpent.png");
    }

    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Pion> getListPion(){
        return this.pions;
    }

    public Bateau getBateau() {
        return this.bateau;
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
        drawPawns(g);
        drawBateau(g);
        drawRequin(g);
        drawBaleine(g);
        drawSerpent(g);
    }


    public void drawBateau(Graphics g) {
        if (this.bateau != null) {
            int imageWidth = bateauImage.getWidth()/2;
            int imageHeight = bateauImage.getHeight()/2;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(bateauImage, drawX, drawY, imageWidth, imageHeight, null);
            g.drawImage(bateauImage, drawX, drawY, imageWidth, imageHeight, null);
        }
    }
    public void drawBaleine(Graphics g) {
        if (this.baleine != null) {
            int imageWidth = baleineImage.getWidth()/4;
            int imageHeight = baleineImage.getHeight()/4;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(baleineImage, drawX, drawY, imageWidth, imageHeight, null);
        }
    }
    public void drawRequin(Graphics g) {
        if (this.requin != null) {
            int imageWidth = requinImage.getWidth()/4;
            int imageHeight = requinImage.getHeight()/4;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(requinImage, drawX, drawY, imageWidth, imageHeight, null);
        }
    }
    public void drawSerpent(Graphics g) {
        if (this.serpent != null) {
            int imageWidth = serpentImage.getWidth()/4;
            int imageHeight = serpentImage.getHeight()/4;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(serpentImage, drawX, drawY, imageWidth, imageHeight, null);
        }
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

    public void setSerpent(Serpent serpent) {
        this.serpent = serpent;
    }

    public void setBaleine(Baleine baleine) {
        this.baleine = baleine;
    }

    public void setRequin(Requin requin) {
        this.requin = requin;
    }

    private BufferedImage getPionImage(Pion pion) {
        if(pion !=null) {
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
        return null;
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
        int numPions = pions.size();
        if (numPions == 0) {
            return;
        }

        double angleStep = 2 * Math.PI / numPions;
        int circleRadius = radius / 3;

        for (int i = 0; i < numPions; i++) {
            Pion pion = pions.get(i);
            BufferedImage pionImage = getPionImage(pion);
            if (pionImage != null) {
                int imageWidth = pionImage.getWidth() / 4;
                int imageHeight = pionImage.getHeight() / 4;

                double angle = i * angleStep;
                int drawX = (int) (x + circleRadius * Math.cos(angle) - imageWidth / 2);
                int drawY = (int) (y + circleRadius * Math.sin(angle) - imageHeight / 2);

                g.drawImage(pionImage, drawX, drawY, imageWidth, imageHeight, null);
            }
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
