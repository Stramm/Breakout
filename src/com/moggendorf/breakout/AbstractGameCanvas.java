package com.moggendorf.breakout;

import com.moggendorf.breakout.sprites.AbstractImageSprite;
import com.moggendorf.breakout.sprites.Brick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

public abstract class AbstractGameCanvas extends JPanel {
    private StartPage startPage;
    private int lives;
    private Brick[][] bricks;
    private Map<String, AbstractImageSprite> sprites;
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
        for (AbstractImageSprite sprite : sprites.values()) {
            if (sprite.isVisible() && sprite.isMovable()) {
                sprite.update();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Brick[] row : bricks) {
            for (Brick brick : row) {
                if (brick != null && brick.isVisible()) {
                    brick.paint(g2);
                }
            }
        }

        for (AbstractImageSprite sprite : sprites.values()) {
            if (sprite.isVisible()) {
                sprite.paint(g2);
            }
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
        AbstractImageSprite paddle = getSprites().get("paddle");
        int yPos = getHeight() - (int) (paddle.getHeight() * Const.LIVE_IMAGE_SCALE) - Const.EDGE_WIDTH;
        for (int i = 0; i < lives; i++) {
            int x = (int) (i * paddle.getWidth() * Const.LIVE_IMAGE_SCALE);
            g2.drawImage(paddle.getImage(),
                    x,
                    yPos,
                    (int) (paddle.getWidth() * Const.LIVE_IMAGE_SCALE),
                    (int) (paddle.getHeight() * Const.LIVE_IMAGE_SCALE),
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

    public Map<String, AbstractImageSprite> getSprites() {
        return sprites;
    }

    public void setSprites(Map<String, AbstractImageSprite> sprites) {
        this.sprites = sprites;
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

}
