package org.example.GUI.ui;

import org.example.GUI.mainGame.Game;
import org.example.Logic.Model.Player;
import org.example.GUI.gamestates.PionSelection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PawnSelectionOverlay {

    private PionSelection pionSelection;
    private BufferedImage pionsBackground;
    private BufferedImage pionsRouges;
    private BufferedImage piosnVerts;
    private BufferedImage pionsJaunes;
    private BufferedImage pionsBleus;
    private boolean pawnSelected;
    private Game game;

    public PawnSelectionOverlay(PionSelection pionSelection,Game game) {
        this.pionSelection = pionSelection;
        this.pawnSelected = false;
        this.game = game;
        loadImages();
    }

    public void loadImages(){
        try {
            pionsBackground = ImageIO.read(getClass().getResource("/Plaque.png"));
            pionsRouges = ImageIO.read(getClass().getResource("/red_pawns.png"));
            pionsBleus = ImageIO.read(getClass().getResource("/blue_pawns.png"));
            pionsJaunes = ImageIO.read(getClass().getResource("/yellow_pawns.png"));
            piosnVerts = ImageIO.read(getClass().getResource("/green_pawns.png"));
        } catch (IOException e) {
            System.err.println("Error loading background image: " );
            e.printStackTrace();
        }
    }

    public void updatePawnsCollection(int index) {
        Player currentPlayer = game.getCurrentPlayer();
        int result = currentPlayer.updatePawnsCollection(index);
        if( result == 1) {
            pawnSelected = true;
        }
        else if(result == 0){
            game.startGame();
        }
    }

    public boolean getPawnSelected(){

        return this.pawnSelected;

    }
    public void setPawnSelected(Boolean pawnSelected){

      this.pawnSelected = pawnSelected;

    }
    public void update(){
    }
    
    public void keyPressed(KeyEvent e) {
        if(pawnSelected == false){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1:
                updatePawnsCollection(0);
                break;
            case KeyEvent.VK_2:
                updatePawnsCollection(1);
                break;
            case KeyEvent.VK_3:
                updatePawnsCollection(2);

                break;
            case KeyEvent.VK_4:
                updatePawnsCollection(3);

                break;
            case KeyEvent.VK_5:
                updatePawnsCollection(4);

                break;
            case KeyEvent.VK_6:
                updatePawnsCollection(5);

                break;
            default:
                break;
            }
        }
    }

    public void draw(Graphics g) {

        if (pionsBackground != null) {
            g.drawImage(pionsBackground, Game.GAME_WIDTH / 4, Game.GAME_HEIGHT / 4, Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2, null);
        }
        switch (game.getCurrentPlayer().getColor()){
            case ROUGE:
                if (pionsRouges != null) {
                    g.drawImage(pionsRouges, Game.GAME_WIDTH / 3, (int) (Game.GAME_HEIGHT * 0.4),
                            Game.GAME_WIDTH / 3, Game.GAME_HEIGHT / 5, null);
                }
                break;
            case BLEU:
                if (pionsBleus != null) {
                    g.drawImage(pionsBleus, Game.GAME_WIDTH / 3, (int) (Game.GAME_HEIGHT * 0.4),
                            Game.GAME_WIDTH / 3, Game.GAME_HEIGHT / 5, null);
                }
                break;
            case VERT:
                if (piosnVerts != null) {
                    g.drawImage(piosnVerts, Game.GAME_WIDTH / 3, (int) (Game.GAME_HEIGHT * 0.4),
                            Game.GAME_WIDTH / 3, Game.GAME_HEIGHT / 5, null);
                }
                break;
            case JAUNE:
                if (pionsJaunes != null) {
                    g.drawImage(pionsJaunes, Game.GAME_WIDTH / 3, (int) (Game.GAME_HEIGHT * 0.4),
                            Game.GAME_WIDTH / 3, Game.GAME_HEIGHT / 5, null);
                }
                break;
        }
            // Draw numbers under each pawn
            int startX = Game.GAME_WIDTH / 3 + 13 ;
            int startY = (int) (Game.GAME_HEIGHT * 0.6);
            int pawnSpacing = 68;

            Font font = new Font("Arial", Font.BOLD, 22);
            g.setFont(font);
            for (int i = 0; i < 6; i++) {
                int number = i + 1;
                int xPos = startX + i * pawnSpacing + 15;
                g.setColor(Color.white);
                g.drawString(String.valueOf(number), xPos, startY - 50);
                g.drawString(String.valueOf(game.getCurrentPlayer().getPawnsCollection()[i]), xPos, startY);

            }


        g.setColor(Color.black);
        g.drawString("Select a pawn by pressing a keyboard button", Game.GAME_WIDTH / 2 - 250, 300);
    }
}
