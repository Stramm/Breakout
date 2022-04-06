package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickRed extends Brick {

    public BrickRed() {
        setImage(ImageCache.getImage("brickRed"));
        setPoints(90);
        setHits(1);
    }
}
