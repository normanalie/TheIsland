package org.example.Logic.Model;

import org.example.GUI.gamestates.Color;

public class Player {
    private String name;
    private int[] pawnsCollection;

    private Color color;

    public Player(String name, int[] pawnsCollection,Color color) {
        this.name = name;
        this.pawnsCollection = pawnsCollection.clone();
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor(){
        return this.color;
    }
    public void setColor(Color color){
        this.color = color;
    }
    public int[] getPawnsCollection() {
        return pawnsCollection;
    }

    public int updatePawnsCollection(int index) {
        if (pawnsCollection[index] > 0) {
            pawnsCollection[index]--;
            return 1;
        } else {
            for( int i=0;i<pawnsCollection.length;i++){
                if(pawnsCollection[i] !=0 ){
                    return -1;
                }
            }
            return 0;
        }
    }
}
