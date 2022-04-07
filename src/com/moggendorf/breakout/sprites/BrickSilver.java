package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickSilver extends Brick {

    public BrickSilver(int currentLevel) {
        setImage(ImageCache.getImage("brickSilver"));
        setPoints(50 * currentLevel);
        setHits(2 + currentLevel / 8);
    }

}
