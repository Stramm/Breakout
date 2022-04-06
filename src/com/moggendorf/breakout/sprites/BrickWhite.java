package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickWhite extends Brick {

    public BrickWhite() {
        setImage(ImageCache.getImage("brickWhite"));
        setPoints(50);
        setHits(1);
    }
}
