package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.Util;
import com.moggendorf.breakout.sprites.Paddle;

import java.util.Arrays;

public class NoPowerUp extends AbstractPowerUp {
    public NoPowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    public void init() {
        // use to reset some possible in use power ups, call in paddle collision detection
        // leave untouched: speed, triple ball (all power ups using hooks reset themselves like the invincible wall)
        // so I need to reset the size mainly
        Paddle paddle = getGameCanvas().getPaddle();
        paddle.setWidth(120);
        paddle.setImage(ImageCache.getImage("paddle"));

        // remove all listeners
        Util.removeAllMouseListeners(getGameCanvas());
        // set play listener
        getGameCanvas().addMouseListener(getGameCanvas().getPaddlePlayListener());
        getGameCanvas().addMouseMotionListener(getGameCanvas().getPaddlePlayListener());
    }

}
