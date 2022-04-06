package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickBlue extends Brick {

    public BrickBlue() {
        setImage(ImageCache.getImage("brickBlue"));
        setPoints(100);
        setHits(1);
    }
}
