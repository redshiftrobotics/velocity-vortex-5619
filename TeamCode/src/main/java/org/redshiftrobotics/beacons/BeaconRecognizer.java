package org.redshiftrobotics.beacons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.redshiftrobotics.beacons.PixelState.BLUE;

enum PixelState {
	RED, BLUE, BLACK
}

/**
 * Recognize a beacon from a Bitmap
 */
public class BeaconRecognizer {
    private Map<String, Integer> options;


	private Bitmap inputImage;
    private Bitmap image;
    private int[] reds;
    private int[] blues;

	/**
	 * Create a BeaconRecognizer with the default options.
	 */
    public BeaconRecognizer() {
        HashMap<String, Integer> options = new HashMap<String, Integer>();
        options.put("red:r"         , 180);
        options.put("red:g"         , 200);
        options.put("red:b"         , 145);
        options.put("blue:r"        , 145);
        options.put("blue:g"        , 255);
        options.put("blue:b"        , 200);
        options.put("classify:main" , 250);
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
	 * @return the state of the beacon.
	 * @TODO We never actually return null here, so if you give us a picture of a cat you'll get a BeaconState. We might
	 * 		 want to try and fix that, by detecting if it's actually a beacon.
	 */
    public BeaconState recognize(Bitmap im) {
		this.inputImage = im.copy(Bitmap.Config.ARGB_8888, false);
        this.image = im.copy(Bitmap.Config.ARGB_8888, true);

        int height = image.getHeight();
        int width = image.getWidth();

        reds  = new int[width];
        blues = new int[width];

		Log.d("Height", String.valueOf(height));
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PixelState pixelState = classifyPixel(x, y);
				switch (pixelState) {
					case BLUE:
						blues[x]++;
						image.setPixel(x, y, Color.BLUE);
						break;
					case RED:
						reds[x]++;
						image.setPixel(x, y, Color.RED);
						break;
					case BLACK:
						image.setPixel(x, y, Color.BLACK);
						break;
					default:
						image.setPixel(x, y, Color.YELLOW);
						break;

				}
            }
        }

        BeaconState ret = classify();

		this.writeImageReport();

        this.image = null;
		inputImage = null;
        reds = null;
        blues = null;

        return ret;
    }

	private void writeImageReport() {
		Bitmap res;

		int width = this.image.getWidth() + this.image.getWidth();
		int height = this.image.getHeight();

		res = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas comboImage = new Canvas(res);

		comboImage.drawBitmap(this.inputImage, 0f, 0f, null);
		comboImage.drawBitmap(this.image, this.inputImage.getWidth(), 0f, null);

		// this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
		String tmpImg = "RedshiftBeaconDetector-" + String.valueOf(System.currentTimeMillis()) + ".png";

		OutputStream os = null;
		try {
			os = new FileOutputStream("/sdcard/Pictures/" + tmpImg);
			res.compress(Bitmap.CompressFormat.PNG, 100, os);
		} catch(IOException e) {
			Log.e("combineImages", "problem combining images", e);
		}
	}


    /**
     * Classify a pixel as blue, red or neither.
     * @param x the x coordinate of the pixel to classify.
     * @param y the y coordinate of the pixel to classify.
     * @return state of the pixel
     */
    private PixelState classifyPixel(int x, int y) {
        // This ignores the alpha channel, which could be bad.
        int pixel = image.getPixel(x, y);
		int R = Color.red(pixel);
		int G = Color.green(pixel);
		int B = Color.blue(pixel);

        if (R < options.get("blue:r") && G < options.get("blue:g") && B > options.get("blue:b")) {
            return BLUE;
        } else if (R > options.get("red:r") && G < options.get("red:g") && B < options.get("red:b")) {
			return PixelState.RED;
        }
		return PixelState.BLACK;
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
