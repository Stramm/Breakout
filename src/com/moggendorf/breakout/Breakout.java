package com.moggendorf.breakout;

import javax.swing.*;

public class Breakout extends JFrame {
    public void initAndStartGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Breakout");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);

        add(new StartPage());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Breakout()::initAndStartGUI);
    }
}
