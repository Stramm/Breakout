package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.sprites.Paddle;

public class EnlargedPaddlePowerUp extends AbstractPowerUp {
    public EnlargedPaddlePowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    private void init() {
        Paddle paddle = getGameCanvas().getPaddle();
        paddle.setWidth(200);
        paddle.setImage(ImageCache.getImage("bigPaddle"));
    }
}
