package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.LaserBeam2DSprite;
import com.moggendorf.breakout.sprites.Paddle;

public class GunPowerUp extends AbstractPowerUp {
    public GunPowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        // when the glue power up is active when creating this powerUp and setting it as hook and the ball was sticking
        // to the paddle, then we can not release it anymore... either do this when catching the gun powerUP (here) or
        // set the ball movable everytime we shoot, but the ball wouldn't move with the paddle till we click the lmb
        gameCanvas.getBalls()[0].setMovable(true);
    }

    @Override
    public void hookPlayListenerRelease(Paddle paddle) {
        getGameCanvas().getLaserBeams().add(new LaserBeam2DSprite(getGameCanvas(),
                (int) paddle.getX() + paddle.getWidth() * 1 / 3));
        getGameCanvas().getLaserBeams().add(new LaserBeam2DSprite(getGameCanvas(),
                (int) paddle.getX() + paddle.getWidth() * 2 / 3));

    }
}

