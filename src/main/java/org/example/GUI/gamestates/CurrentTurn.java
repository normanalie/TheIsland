package org.example.GUI.gamestates;

public enum CurrentTurn {
    JOUER_TUILE,DEPLACER_ELEMENT,RETIRER_TUILE,LANCER_DE;
    public static CurrentTurn currentTurn = RETIRER_TUILE;
}