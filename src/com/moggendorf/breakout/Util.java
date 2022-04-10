package com.moggendorf.breakout;

import com.moggendorf.breakout.sprites.Ball;

import javax.sound.sampled.Clip;
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

    // to avoid triple ball problems
    public static void swapVisibleBallToZero(GameCanvas gameCanvas) {
        // check if ball[0] is the active ball... otherwise swap balls
        // that way we make sure the ball we triple is on the field
        if (!gameCanvas.getBalls()[0].isVisible()) {
            for (int i = 1; i < gameCanvas.getBalls().length; i++) {
                if (gameCanvas.getBalls()[i].isVisible()) {
                    // swap, and we assume at least one ball is active
                    Ball temp = gameCanvas.getBalls()[i];
                    gameCanvas.getBalls()[i] = gameCanvas.getBalls()[0];
                    gameCanvas.getBalls()[0] = temp;
                    break;
                }
            }
        }
    }

    public static void playSound(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }
}
