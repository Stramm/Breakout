package com.moggendorf.breakout.sprites;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractImageSprite extends AbstractSprite {
    private BufferedImage image;
    private double x;
    private double y;
    private int width;
    private int height;
    private double dx;
    private double dy;

    public AbstractImageSprite() {}

    public abstract void init();

    public abstract void update();

    public void paint(Graphics2D g2) {
        g2.drawImage(image, (int) x, (int) y, null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}
