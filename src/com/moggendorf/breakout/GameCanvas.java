package com.moggendorf.breakout;


import com.moggendorf.breakout.listeners.PaddleGlueListener;
import com.moggendorf.breakout.listeners.PaddlePlayListener;
import com.moggendorf.breakout.listeners.PaddleStartListener;
import com.moggendorf.breakout.powerups.*;
import com.moggendorf.breakout.sprites.Ball;
import com.moggendorf.breakout.sprites.Brick;
import com.moggendorf.breakout.sprites.Paddle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameCanvas extends AbstractGameCanvas {
    private LevelLoader levelLoader;
    private HookFactory hookFactory;
    private int currentLevel;
    private MouseAdapter paddlePlayListener;
    private MouseAdapter paddleStartListener;
    private MouseAdapter paddleGlueListener;
    private boolean levelStarted;
    private PowerUp hook;


    public GameCanvas(StartPage startPage) {
        super(startPage);
        initComponents();
    }

    private void initComponents() {
        levelLoader = new LevelLoader(this);
        hookFactory = new HookFactory(this);
    }

    // from here on init initiated from start button
    @Override
    public void startNewGame() {
        setBackground(Const.FIELD_BACK_COLOR);
        currentLevel = 0;
        setScore(0);
        setLives(Const.LIVES);

        initSprites(); // ball and paddle and booster list
        startNextLevel(); // load level, init bricks
        resetSprites(); // set ball and paddle to default values, decrease lives, calculate angle and speed of the ball
        initListener(); // listener
        setStartListener(); // set the drag & release listener to be able to fire the ball
    }

    private void initListener() {
        paddlePlayListener = new PaddlePlayListener(this);
        paddleStartListener = new PaddleStartListener(this);
        paddleGlueListener = new PaddleGlueListener(this);
    }

    private void initSprites() {
        // create ball, paddle and a map that holds them (and maybe drops)
        setPaddle(new Paddle(this));

        setBalls(new Ball[Const.POWER_UP_MAX_BALLS]);
        for (int i = 0; i < Const.POWER_UP_MAX_BALLS; i++) {
            getBalls()[i] = new Ball(this);
        }

        setBooster(new CopyOnWriteArrayList<>());
        setLaserBeams(new CopyOnWriteArrayList<>()); // AbstractSprite
    }

    public void deductLive() {
        setLives(getLives() - 1);
    }

    // set back sprite settings to initial values
    public void resetSprites() {

        // when resetting we assign no powerUp and clear the booster sprites list
        getBooster().clear();
        hook = new NoPowerUp(this);

        // clear the beams
        getLaserBeams().clear();

        // check game end
        if (getLives() == 0) {// game lost
            pauseGame();
            stopThreads();
            getStartPage().changeCard("splashCanvas");
        }

        // set ball and paddle to initial start values
        Paddle paddle = getPaddle();
        paddle.init();
        paddle.setX((getWidth() - paddle.getWidth()) / 2.);
        paddle.setY(getHeight() - Const.Y_MARGIN);

        // set up the balls with some initial values, for the normal play we use 0, when powerUp, 1 and 2
        // then we set 1, 2 to visible and new x, y, dx, dy
        for (int i = 0; i < Const.POWER_UP_MAX_BALLS; i++) {
            Ball ball = getBalls()[i];
            ball.init();
            ball.setX((getWidth() - ball.getWidth()) / 2.);
            ball.setY(getHeight() - Const.Y_MARGIN - ball.getHeight()); // set above paddle
            // and set speed and angle for the ball
            ball.setSpeed(levelLoader.getSpeed());
            ball.setAngle(levelLoader.getAngle());
            ball.calcDxDy();
        }
        getBalls()[0].setContacts(0);
        getBalls()[0].setVisible(true);


     }

    public void setStartListener() {
        // if still set from the glue power up
        removeMouseListener(paddleGlueListener);
        removeMouseListener(paddleGlueListener);

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
        Graphics2D g2 = (Graphics2D) g;
        // the powerUp hook for drawing additional content
        hook.hookDraw((Graphics2D) g);

        // here just draw additional info like current level before the ball is moving
        if (!levelStarted) {
            g2.setColor(Const.SCORE_COLOR);
            g2.setFont(Const.ROUND_FONT);
            FontMetrics fm = g2.getFontMetrics();
            String line1 = "Round " + getCurrentLevel();
            String line2 = "Ready!";
            int y = Const.SHOW_ROUND_Y;
            g2.drawString(line1, (getWidth() - fm.stringWidth(line1)) / 2, y);
            g2.drawString(line2, ((getWidth() - fm.stringWidth(line2)) / 2), y + fm.getHeight());
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

    public PowerUp getHook() {
        return hook;
    }

    public void setHook(PowerUp hook) {
        this.hook = hook;
    }

    public HookFactory getHookFactory() {
        return hookFactory;
    }

    public void setHookFactory(HookFactory hookFactory) {
        this.hookFactory = hookFactory;
    }

    public MouseAdapter getPaddlePlayListener() {
        return paddlePlayListener;
    }

    public void setPaddlePlayListener(MouseAdapter paddlePlayListener) {
        this.paddlePlayListener = paddlePlayListener;
    }

    public MouseAdapter getPaddleStartListener() {
        return paddleStartListener;
    }

    public void setPaddleStartListener(MouseAdapter paddleStartListener) {
        this.paddleStartListener = paddleStartListener;
    }

    public MouseAdapter getPaddleGlueListener() {
        return paddleGlueListener;
    }

    public void setPaddleGlueListener(MouseAdapter paddleGlueListener) {
        this.paddleGlueListener = paddleGlueListener;
    }

    public LevelLoader getLevelLoader() {
        return levelLoader;
    }

    public void setLevelLoader(LevelLoader levelLoader) {
        this.levelLoader = levelLoader;
    }
}
