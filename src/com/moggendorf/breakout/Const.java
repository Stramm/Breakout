package com.moggendorf.breakout;

import java.awt.*;

public class Const {

    private Const() {}

    // threads
    public static final long UPDATE_THREAD_SLEEP = 5;
    public static final long REPAINT_THREAD_SLEEP = 20;

    // resource
    public static final String PATH_TO_RESOURCES = "resource/levels.txt";

    // ImageCache
    public static final String IMAGE_PATH = "images";
    public static String[] IMAGE_NAMES = new String[]{"paddle", "ball", "logo", "brickBlue", "brickGreen", "brickLightBlue", "brickOrange",
            "brickPurple", "brickRed", "brickWhite", "brickYellow", "brickSilver", "brickGold"};

    // Button init
    public final static Dimension BUTTON_DIMENSION = new Dimension(200, 60);
    public final static Color BUTTON_COLOR = new Color(73, 127, 197);
    public final static Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 30);

    // frame
    public final static int EDGE_WIDTH = 6;
    public final static int FRAME_WIDTH = 624 + 2 * EDGE_WIDTH;
    public final static int FRAME_HEIGHT = 800;

    // field
    public final static int BRICKS_IN_ROW = 13;
    public static final double LIVE_IMAGE_SCALE = .3;
    public static final int Y_MARGIN = 65;
    public static final Color FIELD_BACK_COLOR = new Color(2, 3, 59);
    public final static int LIVES = 3;
    public final static int LEVELS = 3;
    public final static int NUM_CONTACTS_TO_INCREASE_SPEED = 25;
    public final static double INCREASE_SPEED_BY = .05;
    public final static double MAX_SPEED = 6;
    public final static Font ROUND_FONT = new Font("SansSerif", Font.ITALIC & Font.BOLD, 40);
    public final static int SHOW_ROUND_Y = 500;

    // score
    public final static Font SCORE_FONT = new Font("SansSerif", Font.ITALIC & Font.BOLD, 22);
    public final static Color SCORE_COLOR = new Color(150, 150, 150);

}
