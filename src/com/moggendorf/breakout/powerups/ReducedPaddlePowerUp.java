package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.sprites.Paddle;

public class ReducedPaddlePowerUp extends AbstractPowerUp {
    public ReducedPaddlePowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    private void init() {
        Paddle paddle = getGameCanvas().getPaddle();
        paddle.setWidth(90);
        paddle.setImage(ImageCache.getImage("smallPaddle"));
    }
}
