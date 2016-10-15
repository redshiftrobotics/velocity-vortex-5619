package org.redshiftrobotics.beacons;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Recognize a beacon from a Bitmap
 */
public class BeaconRecognizer {
    private Map<String, Integer> options;

    private Bitmap image;
    private int[] reds;
    private int[] blues;

	/**
	 * Create a BeaconRecognizer with the default options.
	 */
    public BeaconRecognizer() {
        HashMap<String, Integer> options = new HashMap<String, Integer>();
        options.put("red:r"         , 210);
        options.put("red:g"         , 150);
        options.put("red:b"         , 240);
        options.put("blue:r"        ,  85);
        options.put("blue:g"        , 255);
        options.put("blue:b"        , 200);
        options.put("classify:main" , 210);
        options.put("classify:other", 210);

        this.options = options;
    }

	/**
	 * Create a BeaconRecognizer with custom options.
	 *
	 * Options:
	 * - `red:r`: the minimum red threshold for a pixel to be classified as red.
	 * - `red:g`: the maximum green threshold for a pixel to be classified as red.
	 * - `red:b`: the maximum blue threshold for a pixel to be classified as red.
	 * - `blue:r`: the maximum blue threshold for a pixel to be classified as blue.
	 * - `blue:g`: the maximum green threshold for a pixel to be classified as blue.
	 * - `blue:b`: the minimum blue threshold for a pixel to be classified as blue.
	 * - `classify:main": The number of pixels of the dominant color for a beacon to be considered of that color.
	 * - `classify:other`: The maximum number of pixels of the alternate color.
	 *
	 * @param options the options
	 * @TODO Make classify:main/other %'s
	 */
    public BeaconRecognizer(Map<String, Integer> options) {
        this.options = options;
    }

	/**
	 * Recognize a beacon from an image.
	 *
	 * @see BeaconRecognizer for a simple solution that handles getting the image for you.
	 * @param image the image to recognize.
	 * @return the state of the beacon, or null if there isn't one.
	 * @TODO We never actually return null here, so if you give us a picture of a cat you'll get a BeaconState. We might
	 * 		 want to try and fix that, by detecting if it's actually a beacon.
	 */
    public BeaconState recognize(Bitmap image) {
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
        for (int i = 0; i < reds.length; ++i) {
            if (reds[i] <= blues[i]) {
                reds[i]  = 0;
            } else {
                blues[i] = 0;
            }
        }

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
