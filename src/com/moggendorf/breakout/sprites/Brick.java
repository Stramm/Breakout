package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.powerups.Power;

public class Brick extends AbstractImageSprite {
    private int points;
    private int hits;
    private Power power;

    public Brick() {}

    @Override
    public void init() {
        // no dx, dy needed,  x and y in levelLoader, points and hits and image in children
        setMovable(false);
        setVisible(true);
        // setting width and height here... to the image width height
        setWidth(48);
        setHeight(24);
    }

    @Override
    public void update() {
        // not moving... no update
    }

    // return true if the brick got destroyed, set to visible false in that case here, points get added in collision
    // detection in Ball class
    public boolean hit() {
        if (--hits == 0) {
            setVisible(false);
            return true;
        }
        return false;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }
}
