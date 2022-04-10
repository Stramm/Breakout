package com.moggendorf.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Util {
    private Util() {}

    public static JButton initButton(String name, Font font, Dimension dimension, Color color, ActionListener actionListener) {
        JButton button = new JButton(name);
        button.addActionListener(actionListener);
        button.setOpaque(true);
        button.setPreferredSize(dimension);
        button.setBackground(color);
        button.setFont(font);
        return button;
    }

    public static double getCurrentAngle(double dx, double dy, double speed) {
        if (dy > 0)
            return Math.toDegrees(Math.acos(dx / speed));
        else
            return Math.toDegrees(Math.asin(dy / speed));
    }

    public static void removeAllMouseListeners(GameCanvas gameCanvas) {
        for (MouseListener ml : gameCanvas.getMouseListeners()) {
            gameCanvas.removeMouseListener(ml);
        }
        for (MouseMotionListener mml : gameCanvas.getMouseMotionListeners()) {
            gameCanvas.removeMouseMotionListener(mml);
        }
    }
}
