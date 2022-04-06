package com.moggendorf.breakout.sprites;

public abstract class AbstractSprite {
    private boolean isMovable;
    private boolean isVisible;

    public AbstractSprite() { }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
