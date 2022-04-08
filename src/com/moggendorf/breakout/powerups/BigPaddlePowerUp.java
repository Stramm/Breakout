package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.sprites.Paddle;

public class BigPaddlePowerUp extends AbstractPowerUp {
    public BigPaddlePowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    private void init() {
        Paddle paddle = (Paddle) getGameCanvas().getSprites().get("paddle");
        paddle.setWidth(200);
        paddle.setImage(ImageCache.getImage("bigPaddle"));


    }
}
