package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.powerups.Power;

public class BoosterTripleBall extends Booster {
    public BoosterTripleBall(GameCanvas gameCanvas) {
        super(gameCanvas);
    }

    @Override
    public void init(Brick brick) {
        super.init(brick);
        setPoints(1000);
        setImage(ImageCache.getImage("powerUpTriBall"));
        setPower(Power.TRIPLE_BALL);
    }
}