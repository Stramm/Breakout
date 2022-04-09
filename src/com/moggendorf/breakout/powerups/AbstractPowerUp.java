package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.Ball;
import com.moggendorf.breakout.sprites.Paddle;

import java.awt.*;

public class AbstractPowerUp implements PowerUp {
    private GameCanvas gameCanvas;

    public AbstractPowerUp(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    @Override
    public void hookBallWall(Ball ball) {

    }

    @Override
    public void hookDraw(Graphics2D g2) {

    }

    @Override
    public void hookBallHitPaddle(Ball ball, Paddle paddle) {

    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
