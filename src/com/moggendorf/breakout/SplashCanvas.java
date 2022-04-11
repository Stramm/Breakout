package com.moggendorf.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SplashCanvas extends JPanel {
    private final StartPage startPage;

    public SplashCanvas(StartPage startPage) {
        this.startPage = startPage;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        initComponents();
    }

    private void initComponents() {
        JPanel splashPanel = new JPanel();

       // add(splashPanel, BorderLayout.CENTER);

        // the buttons
        Dimension buttonDimension = Const.BUTTON_DIMENSION;
        Color buttonColor = Const.BUTTON_COLOR;
        Font buttonFont = Const.BUTTON_FONT;
        ActionListener al = new ButtonListener();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(Util.initButton("Start", buttonFont, buttonDimension, buttonColor, al));
        buttonPanel.add(Util.initButton("Settings", buttonFont, buttonDimension, buttonColor, al));
        buttonPanel.add(Util.initButton("High Score", buttonFont, buttonDimension, buttonColor, al));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage logo = ImageCache.getImage("logo");
        int imgWidth = logo.getWidth();
        int imgHeight = logo.getHeight();
        double scaleFactor = (double) getWidth() / imgWidth;
        g.drawImage(logo, 0,0,
                (int) (imgWidth * scaleFactor), (int) (imgHeight * scaleFactor), null);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Start":
                    startPage.getGameCanvas().requestFocusInWindow();
                    startPage.getGameCanvas().setFocusable(true);
                    startPage.getGameCanvas().startNewGame();
                    startPage.getGameCanvas().repaint();
                    startPage.changeCard("gameCanvas");
                    break;
                case "Settings":
                    // startPage.changeCard("settingsCanvas");
                    break;
                case "High Score":
                    startPage.changeCard("highScoreCanvas");
                    break;
            }
        }
    }

}
