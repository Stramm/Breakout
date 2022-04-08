package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.powerups.BigPaddlePowerUp;
import com.moggendorf.breakout.powerups.Power;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

// todo: switch x, y and dx, dy to atomic int and Double.doubleToLongBits... eventually switch dx to atomicInt, too (or synchronize)
public class Ball extends AbstractImageSprite {
    private GameCanvas gameCanvas;
    private double angle;
    private double speed;
    private static int contacts; // ball hitting wall, brick or the paddle, static cause we may use more several balls

    public Ball(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    @Override
    public void init() {
        setImage(ImageCache.getImage("ball"));
        setMovable(false);
        setVisible(false);
        setHeight(18);
        setWidth(18);
    }

    @Override
    public void update() {
        moveBall();
        checkWallCollision();
        checkPaddleCollision();
        checkBrickCollision();
        checkHook();
        checkIncreaseSpeed();
    }

    private void checkIncreaseSpeed() {
        if (contacts > Const.NUM_CONTACTS_TO_INCREASE_SPEED) {
            contacts = 0;
            if (getDy() > 0)
                angle = Math.toDegrees(Math.acos(getDx() / speed));
            else
                angle = Math.toDegrees(Math.asin(getDy() / speed));
            speed += Const.INCREASE_SPEED_BY;
            if (speed > Const.MAX_SPEED)
                speed = Const.MAX_SPEED;

            // update dX adn dY
            calcDxDy();
        }

    }

    public void calcDxDy() {
        setDx(Math.cos(Math.toRadians(angle)) * speed);
        setDy(Math.sin(Math.toRadians(angle)) * speed);
    }

    private void checkWallCollision() {
        if (getY() < Const.EDGE_WIDTH) {
            setDy(-getDy());
            contacts++;
            moveBall();
        } else if (getY() > Const.FRAME_HEIGHT) {
            // ball lost, next ball if any
            gameCanvas.resetSprites();
            gameCanvas.setStartListener();
            gameCanvas.getBooster().clear();
        }

        if (getX() < 0 || getX() > Const.FRAME_WIDTH - 2 * Const.EDGE_WIDTH - getWidth()) {
            setDx(-getDx());
            contacts++;
            moveBall();
        }

    }

    // the powerUp interceptor, do eventual additonal checking for powerUps here
    public void checkHook() {
        gameCanvas.getHook().hookBallWall(this);
    }

    public void moveBall() {
        setX(getX() + getDx());
        setY(getY() - getDy());
    }

    private void checkPaddleCollision() {
        Ellipse2D ballShape = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        Paddle paddle = gameCanvas.getPaddle();
        Rectangle2D paddleShape = new Rectangle2D.Double(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        if (ballShape.intersects(paddleShape)) {
            contacts++;
            // if hit in the middle
            int pxLeft = (int) (paddle.getX() - getX()); // 1 to 18 if hit (1 innermost, 18 outermost pix)
            int pxRight = (int) -(paddle.getX() + paddle.getWidth() - getX() - getWidth());
            double currAngle = Math.toDegrees(Math.acos(getDx() / speed)); // get the current angle the ball hits the paddle

            // if the ball hit a far side of the paddle recalculate the angle
            if (pxLeft > 0 || pxRight > 0) {
                currAngle = currAngle < 90 ? 90 + currAngle : currAngle;
                double degScale = currAngle / 18; // the new angle is the current angle / 18 * thePixelHit (outer pixel -> bigger angle)
                angle = pxLeft > 0 ? degScale * pxLeft : degScale * pxRight;
                // calculate a dx, dy from that angle
                calcDxDy();
            } else {
                setDy(-getDy());
            }
            // set the ball up a little bit to avoid confusions with collision detection
            setY(getY() - getDy());
        }

    }

    private void checkBrickCollision() {
        // to find out with what side the ball hit a brick I'm creating four points representing the balls top, bottom,
        // left and right point. If any of these points intersects with a brick I assume that 'ball side' made that hit
        // and I can calculate the new dx or dy
        // to make it faster, I create a ballShape and only if that ballShape intersects I check the points
        Ellipse2D ballShape = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        Point top = new Point((int) (getX() + getWidth() / 2), (int) getY());
        Point bottom = new Point((int) (getX() + getWidth() / 2), (int) (getY() + getHeight()));
        Point left = new Point((int) (getX()), (int) (getY() + getHeight() / 2));
        Point right = new Point((int) (getX() + getWidth()), (int) (getY() + getHeight() / 2));

        for (Brick[] row : gameCanvas.getBricks()) {
            for (Brick brick : row) {
                if (brick == null || !brick.isVisible())
                    continue;
                Rectangle2D brickShape = new Rectangle2D.Double(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
                if (ballShape.intersects(brickShape)) {
                    contacts++;
                    // ball has hit a brick, remove the brick (in hit method) and, if removed, add points
                    if (brick.hit()) {
                        gameCanvas.setScore(gameCanvas.getScore() + brick.getPoints());
                        gameCanvas.setBricksLeft(gameCanvas.getBricksLeft() - 1);
                        gameCanvas.checkLevelEnd();
                        // check if brick contains a powerUp and in case handle it
                        dropPowerUp(brick);
                    }
                    // now examine further
                    if (brickShape.contains(top) || brickShape.contains(bottom)) {
                        setDy(-getDy());
                    } else if (brickShape.contains(left) || brickShape.contains(right)) {
                        setDx(-getDx());
                    }

                }
            }
        }
    }

    public void dropPowerUp(Brick brick) {
        // handle powerups
        if (brick.getPower() != Power.DEFAULT) {
            // prepared for dropping several but the original game only allowed one powerUp to drop at a
            // time... so I clear the list if a new drops.
            gameCanvas.getBooster().clear();
            System.out.println(brick.getPower());
            Booster booster = null;
            switch (brick.getPower()) {
                case SLOW :
                    break;
                case LASER:
                    break;
                case EXTRA_LIVE:
                    booster = new BoosterExtraLife(gameCanvas);
                    break;
                case BOTTOM_WALL:
                    break;
                case TRIPLE_BALL:
                    break;
                case REDUCED_PADDLE:
                    break;
                case ENLARGED_PADDLE:
                    booster = new BoosterEnlargedPaddle(gameCanvas);
                    break;

            }
            if (booster != null) {
                booster.init(brick);
                gameCanvas.setScore(gameCanvas.getScore() + booster.getPoints());
                gameCanvas.getBooster().add(booster);
                System.out.println(gameCanvas.getBooster().get(0));

            }
        }
    }


    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getContacts() {
        return contacts;
    }

    public void setContacts(int contacts) {
        this.contacts = contacts;
    }
}
