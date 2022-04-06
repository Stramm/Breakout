package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickYellow extends Brick {

    public BrickYellow() {
        setImage(ImageCache.getImage("brickYellow"));
        setPoints(120);
        setHits(1);
    }
}
