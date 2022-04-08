package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.sprites.Paddle;

public class SmallPaddlePowerUp extends AbstractPowerUp {
    public SmallPaddlePowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    private void init() {
        Paddle paddle = (Paddle) getGameCanvas().getSprites().get("paddle");
        paddle.setWidth(90);
        paddle.setImage(ImageCache.getImage("smallPaddle"));


    }
}
