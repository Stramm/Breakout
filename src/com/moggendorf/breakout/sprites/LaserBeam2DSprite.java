package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.Const;

import java.awt.*;

public class LaserBeam2DSprite extends AbstractShapeSprite {
    @Override
    public void init() {

    }

    @Override
    public void update() {
        setDy(getDy() + Const.POWER_UP_LASER_SPEED);
    }

    @Override
    public void paint(Graphics2D g2) {
        g2.setColor(Const.POWER_UP_COLOR_LASER_BEAM);
        g2.drawRect((int) getX(), (int) getY(), 5, 33);
    }
}
