package org.example.GUI.mainGame;

import org.example.GUI.Inputs.MouseInputs;
import org.example.GUI.gamestates.GameState;
import org.example.GUI.gamestates.Playing;
import org.example.GUI.gamestates.PionSelection;
import org.example.Logic.Model.Board;
import org.example.Logic.Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.example.GUI.gamestates.Color.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 800;
    private Playing playing;
    private PionSelection pionSelection;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private Board board;

    public Game() {
        board = new Board(13); // Taille de l'exemple, ajustez si nécessaire
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        pionSelection = new PionSelection(this, board);
        playing = new Playing(this, board);

        players = new ArrayList<>(4);
        Player P1 = new Player("ROUGE", new int[]{3, 2, 2, 1, 1, 1}, ROUGE);
        Player P2 = new Player("BLEU", new int[]{3, 2, 2, 1, 1, 1}, BLEU);
        Player P3 = new Player("JAUNE", new int[]{3, 2, 2, 1, 1, 1}, JAUNE);
        Player P4 = new Player("VERT", new int[]{3, 2, 2, 1, 1, 1}, VERT);
        players.add(P1);
        players.add(P2);
        players.add(P3);
        players.add(P4);

        GameState.state = GameState.PIONS_SELECTION;
    }

    public void startGame() {
        GameState.state = GameState.PLAYING;
        System.out.println("Game Started !!");
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public GameState getCurrentState() {
        return GameState.state;
    }

    public void setCurrentState(GameState currentState) {
        GameState.state = currentState;
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                break;
            case PIONS_SELECTION:
                pionSelection.draw(g);
                break;
        }
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case PLAYING:
                playing.update();
                break;
            case MENU:
                break;
            case PIONS_SELECTION:
                pionSelection.update();
                break;
        }
    }

    public PionSelection getPionSelection() {
        return pionSelection;
    }

    public Playing getPlaying() {
        return playing;
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}
