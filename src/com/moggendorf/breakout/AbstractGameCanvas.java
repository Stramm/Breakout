package com.moggendorf.breakout;

import com.moggendorf.breakout.sprites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractGameCanvas extends JPanel {
    private StartPage startPage;
    private int lives;
    private Brick[][] bricks; // todo convert to 2d array or make it a a synchronized list
    private Paddle paddle;
    private Ball[] balls;
    private CopyOnWriteArrayList<AbstractSprite> laserBeams; // make this a synchronized list Collections.synchronizedList()
    private CopyOnWriteArrayList<Booster> booster; // maybe switch to synchronized list... but the booster list hardly updates
    private int bricksLeft;
    private Thread repaintThread;
    private Thread updateThread;
    private Runnable repaintRun;
    private Runnable updateRun;
    private boolean isPausing;
    private int score;


    public AbstractGameCanvas(StartPage startPage) {
        this.startPage = startPage;
        updateRun = new UpdateRunnable();
        repaintRun = new RepaintRunnable();
        setupKeyBindings();
    }

    public abstract void startNewGame();

    protected void initThreads() {
        pauseGame();
        if (repaintThread == null) {
            repaintThread = new Thread(repaintRun);
            repaintThread.start();
        }

        if (updateThread == null) {
            updateThread = new Thread(updateRun);
            updateThread.start();
        }
    }

    protected void startThreads() {
        initThreads();
    }

    protected void stopThreads() {
        pauseGame();
        updateThread.interrupt();
        repaintThread.interrupt();
        updateThread = null;
        repaintThread = null;
    }

    protected void pauseGame() {
        isPausing = true;
    }

    protected void resumeGame() {
        isPausing = false;
    }

    protected void updateComponents() {
        for (Booster boost : booster) {
            if (boost.isVisible() && boost.isMovable()) {
                boost.update();
            }
        }

        for (Ball ball : balls) {
            if (ball.isVisible() && ball.isMovable()) {
                ball.update();
            }
        }

        // do not use for-each loop or the copyonwritelist will necessarily loop through the entire list even if the copy is already cleared
        // that leads to removing bricks after advancing to a next level
        for (int i = 0; i < laserBeams.size(); i++) {
            if (laserBeams.get(i).isMovable()) {
                laserBeams.get(i).update();
            }
        }

        if (paddle.isVisible() && paddle.isMovable()) {
            paddle.update();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Booster boost : booster) {
            if (boost.isVisible()) {
                boost.paint(g2);
            }
        }

        for (Brick[] row : bricks) {
            for (Brick brick : row) {
                if (brick != null && brick.isVisible()) {
                    brick.paint(g2);
                }
            }
        }

        for (int i = 0; i < laserBeams.size(); i++) {
            if (laserBeams.get(i).isVisible()) {
                laserBeams.get(i).paint(g2);
            }
        }

        for (Ball ball : balls) {
            if (ball.isVisible()) {
                ball.paint(g2);
            }
        }

        if (paddle.isVisible()) {
            paddle.paint(g2);
        }

        drawLives(g2);
        drawScore(g2);
    }

    private void drawScore(Graphics2D g2) {
        String output = "Score: " + getScore();
        g2.setFont(Const.SCORE_FONT);
        FontMetrics fm = getFontMetrics(Const.SCORE_FONT);
        int outputWidth = fm.stringWidth(output);

        int yPos = getHeight() - Const.EDGE_WIDTH;
        int xPos = getWidth() - Const.EDGE_WIDTH - outputWidth;

        g2.setColor(Const.SCORE_COLOR);
        g2.drawString(output, xPos, yPos);


    }

    private void drawLives(Graphics2D g2) {
        BufferedImage paddleImage = ImageCache.getImage("paddle"); // even when powerUps are in use draw the default paddle
        int yPos = getHeight() - (int) (paddleImage.getHeight() * Const.LIVE_IMAGE_SCALE) - Const.EDGE_WIDTH;
        for (int i = 0; i < lives - 1; i++) {
            int x = (int) (i * paddleImage.getWidth() * Const.LIVE_IMAGE_SCALE);
            g2.drawImage(paddleImage,
                    x,
                    yPos,
                    (int) (paddleImage.getWidth() * Const.LIVE_IMAGE_SCALE),
                    (int) (paddleImage.getHeight() * Const.LIVE_IMAGE_SCALE),
                    null);
        }
    }

    protected class UpdateRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    if (!isPausing) {
                        updateComponents();
                    }
                    Thread.sleep(Const.UPDATE_THREAD_SLEEP);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected class RepaintRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    if (!isPausing) {
                        repaint();
                    }
                    Thread.sleep(Const.REPAINT_THREAD_SLEEP);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // add pause keys, SPACE and p
    private void setupKeyBindings() {
        ActionMap actionMap = this.getActionMap();;
        InputMap  inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "p");
        actionMap.put("p", new PauseAction());
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "SPACE-key");
        actionMap.put("SPACE-key", new PauseAction());
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0), "n");
        actionMap.put("n", new NewGameAction());
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "esc");
        actionMap.put("esc", new LeaveAction());
    }


    class PauseAction extends AbstractAction {
        public void actionPerformed(ActionEvent evt) {
            isPausing = !isPausing;
        }
    }

    class NewGameAction extends AbstractAction {
        public void actionPerformed(ActionEvent evt) {
            startNewGame();
        }
    }

    class LeaveAction extends AbstractAction {
        public void actionPerformed(ActionEvent evt) {
            pauseGame();
            stopThreads();
            getStartPage().changeCard("splashCanvas");
        }
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public void setBricks(Brick[][] bricks) {
        this.bricks = bricks;
    }

    public int getBricksLeft() {
        return bricksLeft;
    }

    public void setBricksLeft(int bricksLeft) {
        this.bricksLeft = bricksLeft;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public StartPage getStartPage() {
        return startPage;
    }

    public Ball[] getBalls() {
        return balls;
    }

    public void setBalls(Ball[] balls) {
        this.balls = balls;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public CopyOnWriteArrayList<Booster> getBooster() {
        return booster;
    }

    public void setBooster(CopyOnWriteArrayList<Booster> booster) {
        this.booster = booster;
    }

    public CopyOnWriteArrayList<AbstractSprite> getLaserBeams() {
        return laserBeams;
    }

    public void setLaserBeams(CopyOnWriteArrayList<AbstractSprite> laserBeams) {
        this.laserBeams = laserBeams;
    }
}
