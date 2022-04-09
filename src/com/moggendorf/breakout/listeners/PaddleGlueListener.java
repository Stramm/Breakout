package com.moggendorf.breakout.listeners;

import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.Ball;
import com.moggendorf.breakout.sprites.Paddle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaddleGlueListener extends MouseAdapter {
    private GameCanvas gameCanvas;
    private Paddle paddle;
    private Ball ball;

    public PaddleGlueListener(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        ball = gameCanvas.getBalls()[0];
        paddle = gameCanvas.getPaddle();

    }
    @Override
    public void mouseDragged(MouseEvent e) {
        checkBallMovable(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        checkBallMovable(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ball.setMovable(true);
    }

    private void checkBallMovable(MouseEvent e) {
        if (ball.isMovable())
            mouseDragMove(e);
        else
            mouseDragMoveGlued(e);
    }

    private void mouseDragMove(MouseEvent e) {
        paddle.setX(e.getX() - paddle.getWidth() / 2);

        if (paddle.getX() < Const.EDGE_WIDTH)
            paddle.setX(0);
        else if (paddle.getX() > Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth())
            paddle.setX(Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth());
    }

    private void mouseDragMoveGlued(MouseEvent e) {
        paddle.setX(e.getX() - paddle.getWidth() / 2.);
        ball.setX(paddle.getX() + paddle.getWidth() / 2. - ball.getWidth() / 2.);

        if (paddle.getX() < Const.EDGE_WIDTH) {
            paddle.setX(0);
            ball.setX(paddle.getWidth() / 2. - ball.getWidth() / 2.);
        } else if (paddle.getX() > Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth()) {
            paddle.setX(Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth());
            ball.setX(Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth() / 2. - ball.getWidth() / 2.);
        }
    }
}