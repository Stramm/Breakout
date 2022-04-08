package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.Ball;

import java.util.concurrent.ThreadLocalRandom;


public class TripleBallPowerUp extends AbstractPowerUp {
    public TripleBallPowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    private void init() {
        Ball ball = getGameCanvas().getBalls()[0];
        Ball ball2 = getGameCanvas().getBalls()[1];
        Ball ball3 = getGameCanvas().getBalls()[2];

        ball2.setX(ball.getX());
        ball2.setY(ball.getY());
        ball2.setSpeed(ball.getSpeed());
        ball2.setAngle(ThreadLocalRandom.current().nextInt(30, 150));
        ball2.calcDxDy();

        ball3.setX(ball.getX());
        ball3.setY(ball.getY());
        ball3.setSpeed(ball.getSpeed());
        ball3.setAngle(ThreadLocalRandom.current().nextInt(210, 330));
        ball3.calcDxDy();

        ball2.setVisible(true);
        ball2.setMovable(true);

        ball3.setVisible(true);
        ball3.setMovable(true);
    }
}
