package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ImageCache;


public class Paddle extends AbstractImageSprite {

    public Paddle() {}

    @Override
    public void init() {
        setMovable(true);
        setVisible(true);
        setWidth(120);
        setHeight(33);
        setImage(ImageCache.getImage("paddle"));
    }

    @Override
    public void update() {

    }
}
