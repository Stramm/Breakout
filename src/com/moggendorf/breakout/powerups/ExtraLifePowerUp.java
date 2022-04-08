package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;

public class ExtraLifePowerUp extends AbstractPowerUp {
    public ExtraLifePowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        gameCanvas.setLives(gameCanvas.getLives() + 1);
    }
}
