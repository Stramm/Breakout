package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickOrange extends Brick {

    public BrickOrange() {
        setImage(ImageCache.getImage("brickOrange"));
        setPoints(60);
        setHits(1);
    }
}
