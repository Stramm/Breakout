package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.powerups.Power;


public class BoosterEnlargedPaddle extends Booster {
    public BoosterEnlargedPaddle(GameCanvas gameCanvas) {
        super(gameCanvas);
    }

    @Override
    public void init(Brick brick) {
        super.init(brick);
        setPoints(1000);
        setImage(ImageCache.getImage("powerUpEnlargePaddle"));
        setPower(Power.ENLARGED_PADDLE);
    }
}
