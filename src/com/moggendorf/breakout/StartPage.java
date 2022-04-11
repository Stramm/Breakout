package com.moggendorf.breakout;

import javax.swing.*;
import java.awt.*;


public class StartPage extends JPanel {
    private GameCanvas gameCanvas;
    private SettingsCanvas settingsCanvas;
    private SplashCanvas splashCanvas;
    private HighScoreCanvas highScoreCanvas;
    private CardLayout cardLayout;

    public StartPage() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        initComponents();
    }

    private void initComponents() {
        splashCanvas = new SplashCanvas(this);
        settingsCanvas = new SettingsCanvas(this);
        gameCanvas = new GameCanvas(this);
        highScoreCanvas = new HighScoreCanvas(this);

        add(splashCanvas, "splashCanvas");
        add(settingsCanvas, "settingsCanvas");
        add(gameCanvas, "gameCanvas");
        add(highScoreCanvas, "highScoreCanvas");
    }

    public void changeCard(String constraint) {
        cardLayout.show(this, constraint);
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public HighScoreCanvas getHighScoreCanvas() {
        return highScoreCanvas;
    }

    public SettingsCanvas getSettingsCanvas() {
        return settingsCanvas;
    }

    public SplashCanvas getSplashCanvas() {
        return splashCanvas;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
