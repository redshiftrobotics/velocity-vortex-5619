package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Map;

public class BeaconDetector {
    private Map<String, Integer> options;

    private Bitmap image;
    private int[] reds;
    private int[] blues;

    public BeaconDetector(Map<String, Integer> options) {
        this.options = options;
    }

    public BeaconState detect(Bitmap image) {
        this.image = image;

        int height = image.getHeight();
        int width = image.getWidth();

        reds  = new int[width];
        blues = new int[width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelClass = classifyPixel(x, y);
                if (pixelClass == 0) {
                    blues[x]++;
                } else if (pixelClass == 1) {
                    reds[x]++;
                }
            }
        }

        BeaconState ret = classify();

        this.image = null;
        reds = null;
        blues = null;

        return ret;
    }


    /**
     * Classify a pixel as blue, red or neither.
     * @param x the x coordinate of the pixel to classify.
     * @param y the y coordinate of the pixel to classify.
     * @return 0 if pixel is blue, 1 if it's red, and -1 if it's neither (black).
     */
    private int classifyPixel(int x, int y) {
        // This ignores the alpha channel, which could be bad.
        int pixel = image.getPixel(x, y);
        int R = (pixel >> 16) & 0xff;
        int G = (pixel >>  8) & 0xff;
        int B =  pixel        & 0xff;

        if (R < options.get("blue:r") && G < options.get("blue:g") && B > options.get("blue:b")) {
            return 0;
        } else if (R > options.get("red:r") && G < options.get("red:g") && B < options.get("red:b")) {
            return 1;
        }
        return -1;
    }

    /**
     * "Use Java!", they said.
     * "It's a mature language," they said.
     * I had to tell it how to sum an array.
     *
     * @param arr the values to add.
     * @return the sum of all the values in arr.
     */
    private int sum(int[] arr) {
        int total = 0;
        for (int x: arr) {
            total += x;
        }
        return total;
    }

    /**
     * Find the starting index of the longest streak of non-zero values in an array.
     * @param vals the array to look through.
     * @return the starting index of the longest streak of non-zero values.
     */
    private int findStreakStart(int[] vals) {
        int biggestStart = -1;
        int biggestLength = 0;
        int currentStart = -1;
        int currentLength = 0;

        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == 0) {
                if (biggestLength < currentLength) {
                    biggestStart = currentStart;
                    biggestLength = currentLength;
                }
                currentStart = -1;
                currentLength = 0;
            } else {
                if (currentStart == -1) {
                    currentStart = i;
                }
                currentLength++;
            }
        }

        return biggestStart;
    }

    /**
     * Detect if a beacon is bluered or redblue.
     * @return the state of the beacon.
     */
    private BeaconState findBlueRedOrder() {
//        for (int i = 0; i < reds.length; ++i) {
//            if (reds[i] <= blues[i]) {
//                reds[i]  = 0;
//            } else {
//                blues[i] = 0;
//            }
//        }

        Log.d("log", "findBlueRedOrder");

        int redStart  = findStreakStart(reds);
        int blueStart = findStreakStart(blues);


        if (redStart < blueStart) {
            return BeaconState.RED_BLUE;
        } else {
            return BeaconState.BLUE_RED;
        }
    }

    /**
     * Classify if the beacon is blue, red, bluered or redblue.
     * @return The state of the beacon.
     */
    private BeaconState classify() {
        int numReds  = sum(reds);
        int numBlues = sum(blues);

        if (numBlues <= options.get("classify:other") && numReds > options.get("classify:main")) {
            return BeaconState.RED;
        }
        if (numReds <= options.get("classify:other") && numBlues > options.get("classify:main")) {
            return BeaconState.BLUE;
        }
        return findBlueRedOrder();
    }

}
