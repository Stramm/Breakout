package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.powerups.Power;

public class BoosterWall extends Booster {
    public BoosterWall(GameCanvas gameCanvas) {
        super(gameCanvas);
    }

    @Override
    public void init(Brick brick) {
        super.init(brick);
        setPoints(1000);
        setImage(ImageCache.getImage("powerUpWall"));
        setPower(Power.BOTTOM_WALL);
    }
}
