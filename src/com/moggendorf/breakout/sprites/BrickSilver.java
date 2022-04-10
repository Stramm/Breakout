package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ClipCache;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.Util;

public class BrickSilver extends Brick {

    public BrickSilver(int currentLevel) {
        setImage(ImageCache.getImage("brickSilver"));
        setPoints(50 * currentLevel);
        setHits(2 + currentLevel / 8);
    }

    @Override
    public boolean hit() {
        setHits(getHits() - 1);
        if (getHits() == 0) {
            setVisible(false);
            Util.playSound(ClipCache.getClip("hitSilver"));
            return true;
        }
        Util.playSound(ClipCache.getClip("hitSilver"));
        return false;
    }
}
