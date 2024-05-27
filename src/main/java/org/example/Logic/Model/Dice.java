package org.example.Logic.Model;

import java.util.Random;

public class Dice {
    private Random random;

    public Dice() {
        this.random = new Random();
    }

    public Creature lancer() {
        int result = random.nextInt(CreatureType.values().length);

        switch(result) {
            case 0:
                return new Creature(CreatureType.BALEINE);
            case 1:
                return new Creature(CreatureType.REQUIN);
            case 2:
                return new Creature(CreatureType.SERPENT);
            default:
                return null;
        }
    }
}
