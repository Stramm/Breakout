package com.moggendorf.breakout.listeners;

import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.sprites.AbstractImageSprite;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PaddlePlayListener extends MouseAdapter {
    private AbstractImageSprite paddle;

    public PaddlePlayListener(AbstractImageSprite paddle) {
        this.paddle = paddle;
    }
    @Override
    public void mouseMoved(MouseEvent e) {

        paddle.setX(e.getX() - paddle.getWidth() / 2);

        if (paddle.getX() < Const.EDGE_WIDTH)
            paddle.setX(0);
        else if (paddle.getX() > Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth())
            paddle.setX(Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - paddle.getWidth());

    }
}
