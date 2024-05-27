package org.example.GUI.mainGame;

import org.example.GUI.gamestates.*;
import org.example.Logic.Model.Player;

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
    public  static final int GAME_HEIGHT = 800;
    public  static final int PLAYER_COUNT = 0;
    private Playing playing;
    private PionSelection pionSelection;
    private BateauSelection bateauSelection;
    private LancerDe lancerDe;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private MoveElement moveElement;



    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }
    private void initClasses() {
        pionSelection = new PionSelection(this);
        bateauSelection = new BateauSelection(this);
        lancerDe = new LancerDe(this);
        playing = new Playing(this);
        moveElement = new MoveElement(this);
        players = new ArrayList<>(4);
            Player P1 = new Player("¨Player1",new int[]{3, 2, 2, 1, 1, 1},ROUGE);
            Player P2 = new Player("Player2",new int[]{3, 2, 2, 1, 1, 1},BLEU);
            Player P3 = new Player("Player3",new int[]{3, 2, 2, 1, 1, 1},JAUNE);
            Player P4 = new Player("Player4",new int[]{3, 2, 2, 1, 1, 1},VERT);
        players.add(P1);
        players.add(P2);
        players.add(P3);
        players.add(P4);

        GameState.state = GameState.PIONS_SELECTION;
    }
    public void startBateauSelection(){
        bateauSelection.setHexagons(pionSelection.getHexagons());
        GameState.state = GameState.BATEAU_SELECTION;
    }
    public void startGame(){
        GameState.state = GameState.PLAYING;
        System.out.println("Game Started !!");
        moveElement.setHexagons(bateauSelection.getHexagons());
    }
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayerRound() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }


    //CHANGE LATER
    public void nextTurn() {
        switch (CurrentTurn.currentTurn){
            case JOUER_TUILE :
                break;
            case DEPLACER_ELEMENT:
                lancerDe.setHexagons(moveElement.getHexagons());
                CurrentTurn.currentTurn = CurrentTurn.LANCER_DE;
                break;
            case RETIRER_TUILE:
                break;
            case LANCER_DE:
                moveElement.setHexagons(lancerDe.getHexagons());
                CurrentTurn.currentTurn = CurrentTurn.DEPLACER_ELEMENT;
                break;

        }
    }
    public GameState getCurrentState() {
        return GameState.state;
    }

    public void setCurrentState(GameState currentState) {
       GameState.state  = currentState;
    }

    public MoveElement getMoveElement(){
        return this.moveElement;
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case PLAYING :
                switch (CurrentTurn.currentTurn){
                    case DEPLACER_ELEMENT :
                        moveElement.draw(g);
                        break;
                    case LANCER_DE:
                        lancerDe.draw(g);
                        break;
                }
                break;
            case MENU:
                break;
            case PIONS_SELECTION:
                pionSelection.draw(g);
                break;
            case BATEAU_SELECTION:
                bateauSelection.draw(g);
                break;

        }
    }
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }



    public BateauSelection getBateauSelection() {
        return bateauSelection;
    }

    public void update() {
        switch (GameState.state){
            case PLAYING :
                playing.update();
            case MENU:
                break;
            case PIONS_SELECTION:
                pionSelection.update();
                break;
            case BATEAU_SELECTION:
                bateauSelection.update();
                break;
        }
    }
    public PionSelection getPionSelection() {
        return pionSelection;
    }

    public LancerDe getLancerDe(){return lancerDe;}

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