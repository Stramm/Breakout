package com.moggendorf.breakout;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private static Map<String, BufferedImage> images;

    static {
        images = new HashMap<>();
        try {
            for (int i = 0; i < Const.IMAGE_NAMES.length; i++) {
                images.put(Const.IMAGE_NAMES[i],
                        ImageIO.read(ImageCache.class.getResource(Const.IMAGE_PATH + "/" + Const.IMAGE_NAMES[i] + ".png")));
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public static Map<String, BufferedImage> getCache() {
        return images;
    }

    public static BufferedImage getImage(String needle) {
        if (images.get(needle) == null)
            throw new IllegalArgumentException(needle);
        return images.get(needle);
    }
}