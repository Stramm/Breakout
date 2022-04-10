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
        // todo check if this fix is working -> no, only partially, when catching then the ball does not drag and fire,
        // on the second try it did...
        // when having a triple ball and then switching to glue, that powerup only can check ball[0] ->
        // check into maybe dragging, firing all balls (if they are visible, and movable, the play, if both are false,
        // they are off and if just visible, the are dragged)
        // so we need to set here the one of the balls to ball[0] and remove the others
        Util.swapVisibleBallToZero(getGameCanvas()); // now ball0 is movable, visible
        getGameCanvas().getBalls()[1].setMovable(false);
        getGameCanvas().getBalls()[1].setVisible(false);
        getGameCanvas().getBalls()[2].setMovable(false);
        getGameCanvas().getBalls()[2].setVisible(false);

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

