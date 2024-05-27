package org.example.GUI.Inputs;

import org.example.GUI.gamestates.GameState;
import org.example.GUI.gamestates.Playing;
import org.example.GUI.gamestates.State;
import org.example.GUI.mainGame.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.getGame().getPionSelection().mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gamePanel.getGame().getCurrentState()== GameState.PIONS_SELECTION){
            gamePanel.getGame().getPionSelection().mouseClicked(e);
        }
        else{
            if(gamePanel.getGame().getCurrentState()== GameState.PLAYING){
                gamePanel.getGame().getPlaying().mouseClicked(e);
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
