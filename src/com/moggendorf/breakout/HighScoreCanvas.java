package com.moggendorf.breakout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class HighScoreCanvas extends JPanel {
    private final StartPage startPage;
    private boolean addNewHighScore;
    private JTextField textField;
    private JPanel buttonPanel;
    private JPanel inputPanel;


    public HighScoreCanvas(StartPage startPage) {
        this.startPage = startPage;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        initComponents();
    }

    private void initComponents() {
        JPanel highScorePanel = new JPanel();
        // the buttons
        Dimension buttonDimension = Const.BUTTON_DIMENSION;
        Color buttonColor = Const.BUTTON_COLOR;
        Font buttonFont = Const.BUTTON_FONT;
        ActionListener bl = new HighScoreCanvas.ButtonListener();
        ActionListener tfl = new HighScoreCanvas.TextFieldListener();

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(Util.initButton("Main", buttonFont, buttonDimension, buttonColor, bl));
        add(buttonPanel, BorderLayout.SOUTH);

        TitledBorder inputPanelBorder = new TitledBorder("New high score - enter your name!");
        inputPanelBorder.setTitleColor(Color.WHITE);
        inputPanelBorder.setTitleFont(Const.HIGH_SCORE_BORDER_FONT);
        inputPanel = new JPanel();
        inputPanel.setBackground(Color.BLACK);
        inputPanel.setBorder(inputPanelBorder);

        textField = new JTextField();
        textField.setColumns(30);
        textField.addActionListener(tfl);

        inputPanel.add(textField);
        inputPanel.setVisible(false);

        add(inputPanel, BorderLayout.CENTER);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage logo = ImageCache.getImage("logo");
        int imgWidth = logo.getWidth();
        int imgHeight = logo.getHeight();
        double scaleFactor = (double) getWidth() / imgWidth;
        int height = (int) (imgHeight * scaleFactor);

        g.drawImage(logo, 0, 0, (int) (imgWidth * scaleFactor), height, null);

        Font f = new Font("Dialog", Font.BOLD, 26);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();

        int startY = height + 80;

        for (HighScore.HighScoreEntry hse : new HighScore().getHighScores()) {
            int startX = Const.HIGH_SCORE_MARGIN;
            StringBuilder stringToDraw = new StringBuilder(hse.getName());
            String score = String.format("%,d", hse.getScore());

            boolean isStringStripped = false;
            while (fm.stringWidth(stringToDraw.toString()) > 300) {
                stringToDraw.setLength(stringToDraw.length() - 1);
                isStringStripped = true;
            }

            g.setColor(Color.WHITE);
            int stringWidth = fm.stringWidth(stringToDraw.toString());

            if (!isStringStripped) {
                g.drawString(stringToDraw.toString(), startX, startY);
            } else {
                String firstLetters = stringToDraw.substring(0, stringToDraw.length() - 4);
                g.drawString(firstLetters, startX, startY);
                stringWidth = fm.stringWidth(firstLetters);

                for (int i = 0; i < 4; i++) {
                    startX += stringWidth;
                    g.setColor(new Color(255 - i * 64, 255 - i * 64, 255 - i * 64));
                    String charToDraw = stringToDraw.charAt(stringToDraw.length() - 4 + i) + "";
                    stringWidth = fm.stringWidth(charToDraw);
                    g.drawString(charToDraw, startX, startY);
                }
            }

            // draw score
            g.setColor(Color.WHITE);
            g.drawString(score, getWidth() - 50 - fm.stringWidth(score), startY);
            startY += fm.getHeight();
        }
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Main":
                    startPage.changeCard("splashCanvas");
                    break;
            }
        }
    }

    private class TextFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String input = e.getActionCommand();
            HighScore hs = new HighScore();
            textField.setText("");
            if (hs.tryHighScore(startPage.getGameCanvas().getScore())) {
                hs.addHighScore(input == null || input.isEmpty() ? "no name" : input, startPage.getGameCanvas().getScore());
                buttonPanel.setVisible(true);
                inputPanel.setVisible(false);
            }

        }
    }

    public boolean isAddNewHighScore() {
        return addNewHighScore;
    }

    public void setAddNewHighScore(boolean addNewHighScore) {
        this.addNewHighScore = addNewHighScore;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public void setInputPanel(JPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

}
