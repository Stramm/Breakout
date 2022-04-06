package com.moggendorf.breakout;

import com.moggendorf.breakout.sprites.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LevelLoader {
    private Path levels;
    private double speed;
    private double angle;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public Brick[][] getLevel(int level) {
        int loadLevel = level == Const.LEVELS ? Const.LEVELS : level % Const.LEVELS; // for 20 level
        List<String> allLevels;
        try {
            allLevels = Files.readAllLines(levels);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        for (int i = 0; i < allLevels.size(); i++) {
            if (allLevels.get(i).startsWith("Level: " + loadLevel)) {
                angle = Double.parseDouble(allLevels.get(i + 1).split(":")[1].trim());
                speed = Double.parseDouble(allLevels.get(i + 2).split(":")[1].trim());
                int startLine = i + 4;
                while(!allLevels.get(i).startsWith("***"))
                    i++;
                int endLine = i - 1;

                return buildBrickArray(allLevels,startLine, endLine);
            }
        }
        return null;
    }

    private Brick[][] buildBrickArray(List<String> level, int startLine, int endLine) {
        Brick[][] bricks = new Brick[endLine - startLine][Const.BRICKS_IN_ROW];
        Brick brick = null; // temp holder
        // now loop over the levels list
        int y = 0;
        for (int linePos = startLine; linePos < endLine; linePos++) {
            String line =  level.get(linePos);
            for (int charPos = 0; charPos < line.length(); charPos += 2) {
                switch (line.charAt(charPos)) {
                    case '.': // no tile
                    case ' ': // no tile
                        brick = null;
                        break;
                    case 'b': // blue tile
                        brick = new BrickBlue();
                        break;
                    case 'g': // green tile
                        brick = new BrickGreen();
                        break;
                    case 'l': // light blue tile
                        brick = new BrickLightBlue();
                        break;
                    case 'o': // orange tile
                        brick = new BrickOrange();
                        break;
                    case 'p': // purple tile
                        brick = new BrickPurple();
                        break;
                    case 'r': // red tile
                        brick = new BrickRed();
                        break;
                    case 'w': // white tile
                        brick = new BrickWhite();
                        break;
                    case 'y': // yellow tile
                        brick = new BrickYellow();
                        break;
                    case 'S': // silver tile

                        break;
                    case 'G': // gold tile

                        break;

                } // end switch
                if (brick != null) {
                    brick.init();
                    brick.setX(charPos / 2 * 48);
                    brick.setY(y * 24);
                    // the next line defines a special... we now save the Brick and set its special if appropriate
                    setSpecial(line, charPos + 1);
                }
                bricks[y][charPos / 2] = brick;
                // advance
            } // end char loop
            y += 1;
        } // end line loop
        return bricks;
    }

    private void setSpecial(String line, int pos) {
        switch(line.charAt(pos)) {
            case ' ' :
                break;
        }
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }
}