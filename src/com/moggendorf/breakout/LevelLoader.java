package com.moggendorf.breakout;

import com.moggendorf.breakout.powerups.Power;
import com.moggendorf.breakout.sprites.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    private double speed;
    private double angle;

    private GameCanvas gameCanvas;

    public LevelLoader(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public Brick[][] getLevel(int level) {
        // todo check the problem here... gives nullPointerException after turn over
        // int loadLevel = level == Const.LEVELS ? Const.LEVELS : level % Const.LEVELS;
        int loadLevel = (level - 1) % Const.LEVELS + 1;
        List<String> allLevels = new ArrayList<>();
        try (InputStream in = getClass().getResourceAsStream(Const.PATH_TO_RESOURCES);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            while (reader.ready())
                allLevels.add(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < allLevels.size(); i++) {
            if (allLevels.get(i).startsWith("Level: " + loadLevel)) {
                angle = Double.parseDouble(allLevels.get(i + 1).split(":")[1].trim());
                speed = Double.parseDouble(allLevels.get(i + 2).split(":")[1].trim());
                int startLine = i + 4;
                while (!allLevels.get(i).startsWith("***"))
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
                        brick = new BrickSilver(gameCanvas.getCurrentLevel());
                        break;
                    case 'G': // gold tile
                        brick = new BrickGold();
                        break;

                } // end switch
                if (brick != null) {
                    brick.init();
                    brick.setX(charPos / 2 * 48);
                    brick.setY(y * 24);
                    // the next line defines a special... we now save the Brick and set its special if appropriate
                    setSpecial(brick,line, charPos + 1);
                }
                bricks[y][charPos / 2] = brick;
                // advance
            } // end char loop
            y += 1;
        } // end line loop
        return bricks;
    }

    private void setSpecial(Brick brick, String line, int pos) {
        switch(line.charAt(pos)) {
            case 'L':
                brick.setPower(Power.EXTRA_LIVE); // red
                break;
            case 'T':
                brick.setPower(Power.TRIPLE_BALL); // dark blue
                break;
            case 'S':
                brick.setPower(Power.SLOW); // orange
                break;
            case 'G':
                brick.setPower(Power.LASER); // silver todo
                break;
            case 'E':
                brick.setPower(Power.ENLARGED_PADDLE); // blue
                break;
            case 'R':
                brick.setPower(Power.REDUCED_PADDLE); // black
                break;
            case 'W':
                brick.setPower(Power.BOTTOM_WALL); // gray
                break;
            case 'C':
                brick.setPower(Power.GLUE); // green
                break;
            default:
                brick.setPower(Power.DEFAULT);
        }
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }
}