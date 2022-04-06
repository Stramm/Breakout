package com.moggendorf.breakout;

import javax.swing.*;
import java.awt.*;


public class StartPage extends JPanel {
    private GameCanvas gameCanvas;
    private SettingsCanvas settingsCanvas;
    private SplashCanvas splashCanvas;
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

        add(splashCanvas, "splashCanvas");
        add(settingsCanvas, "settingsCanvas");
        add(gameCanvas, "gameCanvas");
    }

    public void changeCard(String constraint) {
        cardLayout.show(this, constraint);
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
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
