package org.example.GUI.animations;

import java.awt.image.BufferedImage;

public class DiceAnimation {
    private BufferedImage[] diceImages;
    private BufferedImage currentDiceImage;
    private long startTime;
    private int duration;
    private boolean rolling;

    public DiceAnimation(BufferedImage[] diceImages) {
        this.diceImages = diceImages;
        this.currentDiceImage = diceImages[0];
        this.rolling = false;
    }

    public void startRoll(int duration) {
        this.startTime = System.currentTimeMillis();
        this.duration = duration;
        this.rolling = true;
    }

    public void update() {
        if (rolling) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= duration) {
                rolling = false;
                currentDiceImage = diceImages[(int) (Math.random() * diceImages.length)];
            } else {
                // Calculate the frame based on elapsed time and total duration
                int frameIndex = (int) ((elapsedTime / (float) duration) * diceImages.length);
                currentDiceImage = diceImages[frameIndex % diceImages.length];
            }
        }
    }

    public BufferedImage getCurrentDiceImage() {
        return currentDiceImage;
    }

    public boolean isRolling() {
        return rolling;
    }
}
