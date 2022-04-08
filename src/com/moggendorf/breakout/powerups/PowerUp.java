package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.sprites.Ball;

import java.awt.*;

public interface PowerUp {

    void hookBallWall(Ball ball);

    void hookDraw(Graphics2D g2);
}
