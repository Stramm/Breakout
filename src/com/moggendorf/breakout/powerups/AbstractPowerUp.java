package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.sprites.Ball;

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

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
