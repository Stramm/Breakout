package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.Const;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.powerups.Power;


public abstract class Booster extends AbstractImageSprite {
    private int points;
    private Power power;
    private GameCanvas gameCanvas;

    public Booster(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }


    public void init(Brick brick) {
        setX(brick.getX());
        setY(brick.getY());
        setDy(Const.POWER_UP_DROP_SPEED);
        setDx(0);
        setWidth(48);
        setHeight(21);
        setVisible(true);
        setMovable(true);
        // points and image in the children
    }
    @Override
    public void init() {

    }
    @Override
    public void update() {
        setY(getY() + getDy());
        // check if the powerUp is out of the field
        if (getY() > Const.FRAME_HEIGHT) {
            gameCanvas.getBooster().remove(this);
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }
}
