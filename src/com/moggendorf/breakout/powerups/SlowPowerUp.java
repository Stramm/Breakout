package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.Util;
import com.moggendorf.breakout.sprites.Ball;


public class SlowPowerUp extends AbstractPowerUp {
    public SlowPowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    private void init() {
        Ball[] balls = getGameCanvas().getBalls();
        for (Ball ball : balls) {
            // first get the current angle
            double angle = Util.getCurrentAngle(ball.getDx(), ball.getDy(), ball.getSpeed());

            // then calc a new speed
            double speed = ball.getSpeed() - Const.POWER_UP_SLOW_SPEED_REDUCTION;
            speed = Math.max(speed, Const.POWER_UP_SLOW_REDUCE_TO_MIN);

            // set new speed and angle
            ball.setSpeed(speed);
            ball.setAngle(angle);
            // and calc the new dx dy
            ball.calcDxDy();
        }
    }
}
