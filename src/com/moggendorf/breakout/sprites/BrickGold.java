package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ClipCache;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.Util;

public class BrickGold extends Brick {

    public BrickGold() {
        setImage(ImageCache.getImage("brickGold"));
        setPoints(0);
    }

    @Override
    public boolean hit() {
        Util.playSound(ClipCache.getClip("hitGold"));
        return false;
    }
}
