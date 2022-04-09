package com.moggendorf.breakout.sprites;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractImageSprite extends AbstractSprite {
    private BufferedImage image;

    public AbstractImageSprite() {}

    public void paint(Graphics2D g2) {
        g2.drawImage(image, (int) getX(), (int) getY(), null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
