package com.moggendorf.breakout;


import com.moggendorf.breakout.listeners.PaddlePlayListener;
import com.moggendorf.breakout.listeners.PaddleStartListener;
import com.moggendorf.breakout.sprites.AbstractImageSprite;
import com.moggendorf.breakout.sprites.Ball;
import com.moggendorf.breakout.sprites.Brick;
import com.moggendorf.breakout.sprites.Paddle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.HashMap;

public class GameCanvas extends AbstractGameCanvas {
    private LevelLoader levelLoader;
    private int currentLevel;
    private MouseAdapter paddlePlayListener;
    private MouseAdapter paddleStartListener;
    private boolean levelStarted;


    public GameCanvas(StartPage startPage) {
        super(startPage);
        initLevelLoader();
    }

    private void initLevelLoader() {
        levelLoader = new LevelLoader(this);
    }

    // from here on init initiated from start button
    @Override
    public void startNewGame() {
        setBackground(Const.FIELD_BACK_COLOR);
        currentLevel = 0;
        setScore(0);
        setLives(Const.LIVES);

        startNextLevel(); // load level, init bricks
        initSprites(); // ball and paddle
        resetSprites(); // set ball and paddle to default values, decrease lives, calculate angle and speed of the ball
        initListener(); // listener
        setStartListener(); // set the drag & release listener to be able to fire the ball
    }

    private void initListener() {
        paddlePlayListener = new PaddlePlayListener(getSprites().get("paddle"));
        paddleStartListener = new PaddleStartListener(this);
    }

    private void initSprites() {
        // create ball, paddle and a map that holds them (and maybe drops)
        setSprites(new HashMap<>());

        getSprites().put("paddle", new Paddle());
        getSprites().put("ball", new Ball(this));

    }

    // set back sprite settings to initial values
    public void resetSprites() {
        // check game end
        if (getLives() == 0) {// game lost
            pauseGame();
            stopThreads();
            getStartPage().changeCard("splashCanvas");
        }
        setLives(getLives() - 1);

        // set ball and paddle to initial start values
        AbstractImageSprite paddle = getSprites().get("paddle");
        paddle.init();
        paddle.setX((getWidth() - paddle.getWidth()) / 2.);
        paddle.setY(getHeight() - Const.Y_MARGIN);

        Ball ball = (Ball) getSprites().get("ball");
        ball.init();
        ball.setX((getWidth() - ball.getWidth()) / 2.);
        ball.setY(getHeight() - Const.Y_MARGIN - ball.getHeight()); // set above paddle
        // and set speed and angle for the ball
        ball.setSpeed(levelLoader.getSpeed());
        ball.setAngle(levelLoader.getAngle());
        ball.setContacts(0);
        ball.calcDxDy();
    }

    public void setStartListener() {
        removeMouseListener(paddlePlayListener);
        removeMouseMotionListener(paddlePlayListener);

        addMouseListener(paddleStartListener);
        addMouseMotionListener(paddleStartListener);
    }

    public void setPlayListener() {
        removeMouseListener(paddleStartListener);
        removeMouseMotionListener(paddleStartListener);

        addMouseListener(paddlePlayListener);
        addMouseMotionListener(paddlePlayListener);

    }

    public void startNextLevel() {
        // set to false, when the ball is released, levelStarted is set to true (in listener)
        levelStarted = false;
        setBricks(levelLoader.getLevel(++currentLevel));
        // get the number of bricks in that level
        int numBricks = 0;
        for (Brick[] row : getBricks()) {
            for (Brick brick : row) {
                // increase if not null and no golden brick (gives no points)
                if (brick != null && brick.getPoints() != 0)
                    numBricks++;
            }
        }
        setBricksLeft(numBricks);
        startThreads();
        resumeGame();
    }

    public void checkLevelEnd() {
        if (getBricksLeft() <= 0) {
            pauseGame();
            resetSprites();
            setStartListener();
            startNextLevel();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // call the paintComponent method of the AbstractGameCanvas first (bricks, paddle, ball)
        super.paintComponent(g);
        // here just draw additional info like current level before the ball is moving
        if (!levelStarted) {
            g.setColor(Const.SCORE_COLOR);
            g.setFont(Const.ROUND_FONT);
            FontMetrics fm = g.getFontMetrics();
            String line1 = "Round " + getCurrentLevel();
            String line2 = "Ready!";
            int y = Const.SHOW_ROUND_Y;
            g.drawString(line1, (getWidth() - fm.stringWidth(line1)) / 2, y);
            g.drawString(line2, ((getWidth() - fm.stringWidth(line2)) / 2), y + fm.getHeight());
        }
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public boolean isLevelStarted() {
        return levelStarted;
    }

    public void setLevelStarted(boolean levelStarted) {
        this.levelStarted = levelStarted;
    }
}
