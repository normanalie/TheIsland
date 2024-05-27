package org.example.Logic.Model;

public class Creature {
    private CreatureType type;

    public Creature(CreatureType type) {
        this.type = type;
    }

    public CreatureType getType() {
        return type;
    }

    public void setType(CreatureType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "type=" + type +
                '}';
    }
}
