package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

public class BrickGold extends Brick {

    public BrickGold() {
        setImage(ImageCache.getImage("brickGold"));
        setPoints(0);
    }

    @Override
    public boolean hit() {
        return false;
    }
}
