package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickGreen extends Brick {

    public BrickGreen() {
        setImage(ImageCache.getImage("brickGreen"));
        setPoints(80);
        setHits(1);
    }
}
