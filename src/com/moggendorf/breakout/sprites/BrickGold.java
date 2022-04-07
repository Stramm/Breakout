package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;

// todo
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
