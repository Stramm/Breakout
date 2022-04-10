package com.moggendorf.breakout.sprites;

import com.moggendorf.breakout.ClipCache;
import com.moggendorf.breakout.GameCanvas;
import com.moggendorf.breakout.ImageCache;
import com.moggendorf.breakout.Util;
import com.moggendorf.breakout.powerups.Power;


import java.awt.geom.Rectangle2D;


public class Paddle extends AbstractImageSprite {

    private final GameCanvas gameCanvas;

    public Paddle(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }


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
        checkPowerUpCollision();
    }

    private void checkPowerUpCollision() {
        for (int i = 0; i < gameCanvas.getBooster().size(); i++) {
            Booster b = gameCanvas.getBooster().get(i);
            if (b.getY() + b.getHeight() >= getY()) { // the powerUp dropped to the height of the paddle, now check intersection
                Rectangle2D paddleShape = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
                Rectangle2D boosterShape = new Rectangle2D.Double(b.getX(), b.getY(), b.getWidth(), b.getHeight());
                if (paddleShape.intersects(boosterShape)) {
                    // add the points to the score
                    gameCanvas.setScore(gameCanvas.getScore() + b.getPoints());
                    // reset possible currently active power ups
                    gameCanvas.setHook(gameCanvas.getHookFactory().getHook(Power.DEFAULT));
                    // set the ball movable again (if we had the glue powerUp before and the ball sticks to the paddle)
                    gameCanvas.getBalls()[0].setMovable(true);
                    // sets the powerup connected with the sprite as active hook in gameCanvas
                    gameCanvas.setHook(gameCanvas.getHookFactory().getHook(b.getPower()));
                    // and remove the powerUp sprite from the list
                    gameCanvas.getBooster().remove(b);

                    // and play a sound
                    Util.playSound(ClipCache.getClip("yeah"));
                }
            }
        }
    }

}
