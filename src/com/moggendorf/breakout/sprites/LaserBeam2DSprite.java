package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ClipCache;
import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.Util;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LaserBeam2DSprite extends AbstractShapeSprite {
    private GameCanvas gameCanvas;

    public LaserBeam2DSprite(GameCanvas gameCanvas, int x) {
        this.gameCanvas = gameCanvas;
        setX(x); // to be able to set the position of the cannon left and right of the middle
        init();
    }


    @Override
    public void init() {
        Paddle paddle = gameCanvas.getPaddle();
        setY(paddle.getY());
        setDy(Const.POWER_UP_LASER_SPEED);
        setWidth(5);
        setHeight(33);
        setMovable(true);
        setVisible(true);
        // and finally play the sound
        Util.playSound(ClipCache.getClip("laser"));
    }

    @Override
    public void update() {
        setY(getY() - getDy());
        checkFieldLeft();
        checkCollision();
    }

    private void checkFieldLeft() {
        if (getY() < -getHeight()) {
            removeLaser();
        }
    }

    private void checkCollision() {
        Rectangle2D laserShape = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        for (Brick[] row : gameCanvas.getBricks()) {
            for (Brick brick : row) {
                if (brick == null || !brick.isVisible())
                    continue;
                Rectangle2D brickShape = new Rectangle2D.Double(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                if (laserShape.intersects(brickShape)) {
                    // we have a hit, set the brick invisible, not movable, remove the laser, add the points, drop power up
                    // increase contacts

                    // 1. increase contacts
                    gameCanvas.getBalls()[0].setContacts(gameCanvas.getBalls()[0].getContacts() + 1);

                    // ball has hit a brick, remove the brick (in hit method) and, if removed, add points
                    if (brick.hit()) {
                        gameCanvas.setScore(gameCanvas.getScore() + brick.getPoints());
                        gameCanvas.setBricksLeft(gameCanvas.getBricksLeft() - 1);
                        gameCanvas.checkLevelEnd();
                        // check if brick contains a powerUp and in case handle it
                        gameCanvas.getBalls()[0].dropPowerUp(brick);
                    }

                    // finally we need to destroy this
                    removeLaser();

                }
            }
        }
    }

    private void removeLaser() {
        setVisible(false);
        setMovable(false);
        gameCanvas.getLaserBeams().remove(this);
    }

    @Override
    public void paint(Graphics2D g2) {
        g2.setColor(Const.POWER_UP_COLOR_LASER_BEAM);
        g2.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
    }
}
