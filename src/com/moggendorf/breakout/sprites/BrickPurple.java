package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickPurple extends Brick {

    public BrickPurple() {
        setImage(ImageCache.getImage("brickPurple"));
        setPoints(110);
        setHits(1);
    }
}
