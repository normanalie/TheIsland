package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.TuileEffectOverlay;
import org.example.Logic.Model.Pion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static java.awt.geom.Point2D.distance;

public class Playing extends State implements StateInterface {
    private BufferedImage backgroundImage;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;

    private List<Hexagon> hexagons;
    private final int radius = 40;
    private final int rows = 13;
    private final int cols = 7;
    private float xDelta = 100, yDelta = 100;
    private boolean gridGenerated = false;

    private boolean tuileSelected =false;
    private Hexagon selectedHex;

    private TuileEffectOverlay tuileEffectOverlay;


    public Hexagon getSelectedHex() {
        return selectedHex;
    }

    public void setSelectedHex(Hexagon selectedHex) {
        this.selectedHex = selectedHex;
    }

    public boolean isTuileSelected() {
        return tuileSelected;
    }

    public void setTuileSelected(boolean tuileSelected) {
        this.tuileSelected = tuileSelected;
    }




    public Playing(Game game) throws IOException {
        super(game);
        initClasses();
        loadImages();
        System.out.println("Succesfuly init Playing Game");
    }

    public void setHexagons(List<Hexagon>hexagons){
        this.hexagons = hexagons;
    }



    private int getStartX(int row, int windowStart, int xOffset) {
        if (row == 0 || row == rows - 1) {
            return windowStart + xOffset * 2;
        } else if (row == 5 || row == 7) {
            return windowStart - xOffset;
        } else {
            return windowStart;
        }
    }

    private int getCurrentCols(int row) {
        if (row == 0 || row == rows - 1) {
            return 7;
        } else if (row == 5 || row == 7) {
            return 12;
        } else if (row % 2 == 0) {
            return 11;
        } else {
            return 10;
        }
    }

    private String getHexagonType(int matrixValue) {
        switch (matrixValue) {
            case 1:
                return "land";
            case 2:
                return "forest";
            case 3:
                return "mountain";
            default:
                return "none";
        }
    }

    //pour avoir l'effet de la tuile tirée
    private String getHexagonEffect(int effectValue){
        switch (effectValue){
            case 1:
                return "greenshark";
            case 2:
                return "greenwhale";
            case 3:
                return "greenboat";
            case 4:
                return "tourbillon";
            case 5:
                return "volcano";
            case 6:
                return "daulphin";
            case 7:
                return "redboat";
            case 8:
                return "snake";
            case 9:
                return "redshark";
            case 10:
                return "redwhale";
            case 11:
                return "sharkdefense";
            case 12:
                return "whaledefense";
            default:
                return "none";
        }
    }


    @Override
    public void update() {
        tuileEffectOverlay.update();
    }

    private void initClasses() throws IOException {
        this.tuileEffectOverlay=new TuileEffectOverlay(this,this.game);
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

    public BufferedImage getPionImage(){
        switch (game.getCurrentPlayer().getColor()){
            case ROUGE :
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
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }

        if(isTuileSelected()){
            System.out.println("drawing tuile\n");
            tuileEffectOverlay.draw(g,selectedHex);
        }


    }


    @Override
    public void mouseClicked(MouseEvent e) {
        /*------------*/
        if(isTuileSelected()){
            setTuileSelected(false);
        }
        /*------------*/

        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println("mouse clicked\n");

        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex) && !hex.isClicked() ) {
                handleHexagonClick(hex);
                break;
            }
        }
    }
    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    /// SHOULD CHANGE THIS LATER

    /*------------*/
    private void handleHexagonClick(Hexagon hex) {
        Color couleur=game.getCurrentPlayer().getColor();
        System.out.println("current is : "+couleur+"\n");
        //verif que le player possede un pion sur le hex
        for(Pion P: hex.getListPion()){
            if(P.getColor()==couleur){
                setTuileSelected(true);
                setSelectedHex(hex);
                hex.setClicked(true);
                game.nextTurn();
                break;
            }
        }
    }
    /*------------*/




    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    public void mouseMoved(MouseEvent e){
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
