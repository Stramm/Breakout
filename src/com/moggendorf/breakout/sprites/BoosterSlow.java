package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.powerups.Power;

public class BoosterSlow extends Booster {
    public BoosterSlow(GameCanvas gameCanvas) {
        super(gameCanvas);
        init();
    }

    @Override
    public void init(Brick brick) {
        super.init(brick);
        setPoints(1000);
        setImage(ImageCache.getImage("powerUpSlow"));
        setPower(Power.SLOW);
    }
}
