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
            case TRIPLE_BALL:
                return new TripleBallPowerUp(gameCanvas);
            case SLOW:
                return new SlowPowerUp(gameCanvas);
            case GLUE:
                return new GluePowerUp(gameCanvas);
            case DEFAULT:
                return new NoPowerUp(gameCanvas);
            default:
                throw new IllegalArgumentException();
        }
    }
}
