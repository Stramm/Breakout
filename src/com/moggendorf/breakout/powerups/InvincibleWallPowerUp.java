package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.Ball;

import java.awt.*;

public class InvincibleWallPowerUp extends AbstractPowerUp {
    public InvincibleWallPowerUp(GameCanvas gameCanvas) {
        super(gameCanvas);
    }

    @Override
    public void hookBallWall(Ball ball) {
        if (ball.getY() > Const.FRAME_HEIGHT - Const.Y_MARGIN - 2 * Const.EDGE_WIDTH) {
            ball.setDy(-ball.getDy());
            ball.setContacts(ball.getContacts() + 1);
            ball.moveBall();
        }
    }

    @Override
    public void hookDraw(Graphics2D g2) {
        g2.setColor(Const.SCORE_COLOR);
        g2.drawLine(0, Const.FRAME_HEIGHT - Const.Y_MARGIN,
                Const.FRAME_WIDTH, Const.FRAME_HEIGHT - Const.Y_MARGIN);
    }
}
