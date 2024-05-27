package org.example.GUI.mainGame;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.example.GUI.Inputs.KeyboardInputs;
import org.example.GUI.Inputs.MouseInputs;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    private Game game;

    public GamePanel(Game game) {
        this.game = game;

        mouseInputs = new MouseInputs(this);

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
        requestFocusInWindow();
    }


    private void setPanelSize() {
        Dimension size = new Dimension(1200, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }


    public void updateGame()
    {

    }

    public Game getGame(){
        return this.game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

}
