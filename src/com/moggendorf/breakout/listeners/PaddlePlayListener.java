package com.moggendorf.breakout.listeners;

import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.LaserBeam2DSprite;
import com.moggendorf.breakout.sprites.Paddle;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaddlePlayListener extends MouseAdapter {
    private Paddle paddle;
    private GameCanvas gameCanvas;

    public PaddlePlayListener(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        this.paddle = gameCanvas.getPaddle();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseDragMove(e);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseDragMove(e);
    }

    private void mouseDragMove(MouseEvent e) {
        paddle.setX(e.getX() - paddle.getWidth() / 2);

        if (paddle.getX() < Const.EDGE_WIDTH)
            paddle.setX(0);
        else if (paddle.getX() > Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth())
            paddle.setX(Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gameCanvas.getHook().hookPlayListenerRelease(paddle);
    }
}
