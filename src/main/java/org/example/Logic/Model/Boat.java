package org.example.Logic.Model;

import java.util.ArrayList;
import java.util.List;

public class Boat {
    private List<Pion> explorers;

    public Boat(){
        this.explorers = new ArrayList<>();
    }

    public boolean addExplorer(Pion pion){
        if(explorers.size() < 3){
            explorers.add(pion);
            return true;
        }
        return false;
    }

    public void removeExplorer(Pion pion){
        explorers.remove(pion);
    }

    public boolean isFull(){
        return explorers.size() == 3;
    }

    public boolean isEmpty(){
        return explorers.isEmpty();
    }
}
