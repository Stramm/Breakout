package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;

public class HookFactory {
    private GameCanvas gameCanvas;
    public HookFactory(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public PowerUp getHook(Power power) {
        switch (power) {
            case EXTRA_LIVE:
                return new ExtraLifePowerUp(gameCanvas);
            case ENLARGED_PADDLE:
                return new EnlargedPaddlePowerUp(gameCanvas);
            case REDUCED_PADDLE:
                return new ReducedPaddlePowerUp(gameCanvas);
            case BOTTOM_WALL:
                return new InvincibleWallPowerUp(gameCanvas);
            default:
                throw new IllegalArgumentException();
        }
    }
}
