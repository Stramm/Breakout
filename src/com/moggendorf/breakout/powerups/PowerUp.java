package com.moggendorf.breakout.powerups;

import com.moggendorf.breakout.sprites.Ball;
import com.moggendorf.breakout.sprites.Paddle;

import java.awt.*;

/**
 * adding powerups:
 * 1. Const... add image to array containing image names to load
 * 2. Power enum... add an identifier for it
 * 3. LevelLoader, add to switch
 * 4. add a sprite, extend Booster ... needs points, brick (for start x, y pos), image, Power enum
 * 5. add a PowerUp (extend AbstractPowerUp -> impl. PowerUp). Here we define the hooks
 * 6. HookFactory, add the just created PowerUp to it
 * 7. collision 1: ball hits brick containing a powerUp... create the sprite in switch in dropPowerUp()
 * 8. collision 2: paddle here we use the factory to create the powerUp and assign it to gameCanvas, nothing to do
 */
public interface PowerUp {

    void hookBallWall(Ball ball);

    void hookDraw(Graphics2D g2);

    void hookBallHitPaddle(Ball ball, Paddle paddle);
}
