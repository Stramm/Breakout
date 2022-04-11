package com.moggendorf.breakout;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HighScore implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<HighScoreEntry> scores;


    public HighScore() {
        scores = new ArrayList<>();
        // make sure a file exists, if not serialize the empty list to avoid exceptions when deserializing first
        try {
            if (!Files.exists(getPath().toPath())) {
                save();
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }


    public void addHighScore(String name, int score) {
        // invoke tryHighScore before adding a new one, that'll reload the high scores and test if the score is addable

        scores.add(new HighScoreEntry(name, score));
        Collections.sort(scores, Comparator.comparing(HighScoreEntry::getScore).reversed());

        while (scores.size() > Const.HIGH_SCORE_MAX_ENTRY) {
            scores.remove(scores.size() - 1);
        }

        try {
            save();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<HighScoreEntry> getHighScores() {
        try {
            load();
        } catch (IOException | ClassNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public boolean tryHighScore(long score) {
        getHighScores();
        if (scores.size() > Const.HIGH_SCORE_MAX_ENTRY) {
            long min = Long.MAX_VALUE;
            for (HighScoreEntry hs : scores) {
                if (hs.getScore() < min)
                    min = hs.getScore();
            }
            if (min >= score) {
                return false;
            }
        }
        return true;
    }

    public static class HighScoreEntry implements Serializable {
        private String name;
        private long score;

        public HighScoreEntry(String name, long score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public long getScore() {
            return score;
        }
    }

    /* ----------------- (de-)serialization methods ------------------- */
    private void save() throws IOException, URISyntaxException {
        ObjectOutputStream oos = new ObjectOutputStream(getOutputStream());
        oos.writeObject(scores);
        oos.close();
    }

    private void load() throws IOException, ClassNotFoundException, URISyntaxException {
        ObjectInputStream ois = new ObjectInputStream(getInputStream());
        scores = (ArrayList<HighScoreEntry>) ois.readObject();
        ois.close();
    }

    private InputStream getInputStream() throws URISyntaxException, FileNotFoundException {
        return new FileInputStream(getPath());
    }

    private OutputStream getOutputStream() throws URISyntaxException, FileNotFoundException {
        return new FileOutputStream(getPath());
    }

    private File getPath() throws URISyntaxException {
        Path p = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toPath();
        return p.getParent().resolve(Const.HIGH_SCORE_FILE_NAME).toFile();
    }
}
