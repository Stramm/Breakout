package com.moggendorf.breakout;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClipCache {

    private static Map<String, Clip> clips;

    static {
        clips = new HashMap<>();
        try {
            for (int i = 0; i < Const.CLIP_NAMES.length; i++) {
                // Set up an audio input stream piped from the sound file
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(ClipCache.class.
                        getResourceAsStream(Const.CLIP_PATH + "/" + Const.CLIP_NAMES[i] + ".wav"));
                // Get a clip resource
                Clip clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioInputStream);
                clips.put(Const.CLIP_NAMES[i], clip);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Clip> getCache() {
        return clips;
    }

    public static Clip getClip(String needle) {
        if (clips.get(needle) == null)
            throw new IllegalArgumentException(needle);
        return clips.get(needle);
    }


}
