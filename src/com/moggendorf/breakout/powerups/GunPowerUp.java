package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.LaserBeam2DSprite;
import com.moggendorf.breakout.sprites.Paddle;

public class GunPowerUp extends AbstractPowerUp {
    public GunPowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
    }

    @Override
    public void hookPlayListenerRelease(Paddle paddle) {
        getGameCanvas().getLaserBeams().add(new LaserBeam2DSprite(getGameCanvas(),
                (int) paddle.getX() + paddle.getWidth() * 1 / 3));
        getGameCanvas().getLaserBeams().add(new LaserBeam2DSprite(getGameCanvas(),
                (int) paddle.getX() + paddle.getWidth() * 2 / 3));

    }
}

