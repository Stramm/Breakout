package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickLightBlue extends Brick {

    public BrickLightBlue() {
        setImage(ImageCache.getImage("brickLightBlue"));
        setPoints(70);
        setHits(1);
    }
}
