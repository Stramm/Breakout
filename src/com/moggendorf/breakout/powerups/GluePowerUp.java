package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.Util;
import com.moggendorf.breakout.sprites.Ball;
import com.moggendorf.breakout.sprites.Paddle;


public class GluePowerUp extends AbstractPowerUp {
    public GluePowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    private void init() {
        Util.removeAllMouseListeners(getGameCanvas());

        getGameCanvas().addMouseListener(getGameCanvas().getPaddleGlueListener());
        getGameCanvas().addMouseMotionListener(getGameCanvas().getPaddleGlueListener());
    }

    @Override
    public void hookBallHitPaddle(Ball ball, Paddle paddle) {
        ball.setMovable(false);
        ball.setY(paddle.getY() - ball.getHeight());
        ball.setAngle(getGameCanvas().getLevelLoader().getAngle());
        ball.calcDxDy();
    }
}

