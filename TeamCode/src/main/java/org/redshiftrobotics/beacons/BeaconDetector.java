package org.redshiftrobotics.beacons;

/**
 * TODO: Better docs
 * To use:
 * Create an instance somewhere (one at a time).
 * Call `startCamera()` in start()
 * Call `detect()` to try and detect a beacon.
 * Call `stopCamera()` in stop()
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicReference;

import for_camera_opmodes.CameraPreview;

/**
 * All the code to take an image from the camera and recognize any beacons in it all in one class!
 */
public class BeaconDetector {
	/**
	 * Public Members
	 */

	/**
	 * The BeaconRecognizer that is fed images from the camera.
	 *
	 * You can change this if you have a different one you want to use, such as one with different options or a
	 * subclass.
	 */
	public BeaconRecognizer recognizer = new BeaconRecognizer();

	/**
	 * The reference to the Android Camera, if you want it.
	 */
	public Camera camera;

	/**
	 * The (Atomic)Reference to the CameraPreview
	 */
	public AtomicReference<CameraPreview> cameraPreview = new AtomicReference<CameraPreview>(null);

	/**
	 * Private Members
	 */
	private FtcRobotControllerActivity appContext;

	private int cameraWidth;
	private int cameraHeight;
	private YuvImage yuvImage = null;

	volatile private boolean imageReady = false;

	private boolean started;
	private int looped = 0;
	private String data;
	private int ds = 4; // downsampling parameter

	/**
	 * Public Methods
	 */

	/**
	 * Create a BeaconDetector with an OpMode. You will probably use this constructor the most.
	 * @param opMode the current OpMode.
	 */
	public BeaconDetector(OpMode opMode) {
		this.appContext = (FtcRobotControllerActivity) opMode.hardwareMap.appContext;
	}

	/**
	 * Create a BeaconDetector with just a hardwareMap.
	 * @param hardwareMap the active hardwareMap
	 */
	public BeaconDetector(HardwareMap hardwareMap) {
		this.appContext = (FtcRobotControllerActivity) hardwareMap.appContext;
	}

	/**
	 * Create a BeaconDetector with just the FtcRobotControllerActivity.
	 * @param appContext the current activity
	 */
	public BeaconDetector(FtcRobotControllerActivity appContext) {
		this.appContext = appContext;
	}

	/**
	 * Detect a beacon (if there is one).
	 * @return BeaconState: The state of the beacon (or null).
	 */
	public BeaconState detect() {
		// FIXME(@ariporad): There must be a better way to wait for the camera.
		while (!imageReady()) {
		} // wait for an image from the camera
		Bitmap image = convertYuvImageToRgb(yuvImage, cameraWidth, cameraHeight, ds);

		return recognizer.recognize(image);
	}

	public void setDownsampling(int downSampling) {
		ds = downSampling;
	}

	/**
	 * Open the camera. You should call this in the start method of your OpMode.
	 */
	public void start() {
		if (started) throw new Error("start() called twice!");
		started = true;
		startCamera();
		// This may do more in the future.
	}

	/**
	 * Close the camera and cleanup resources. You should call this in the stop method of your OpMode.
	 */
	public void stop() {
		if (!started) throw new Error("stop() called twice or before start()!");
		stopCamera();
		// This may do more in the future.
		started = false;
	}

	/**
	 * Private Methods
	 */
	private void startCamera() {
		camera = openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);

		camera.setPreviewCallback(previewCallback);

		Camera.Parameters parameters = camera.getParameters();

		cameraWidth = parameters.getPreviewSize().width / ds;
		cameraHeight = parameters.getPreviewSize().height / ds;
		parameters.setPreviewSize(cameraWidth, cameraHeight);

		// camera.setParameters(parameters);

		data = parameters.flatten();

		if (cameraPreview.get() == null) {
			appContext.initPreview(camera, this.cameraPreview, previewCallback);
		}
	}

	private void stopCamera() {
		if (camera != null) {
			if (cameraPreview.get() != null) {
				appContext.removePreview(this.cameraPreview);
				cameraPreview.set(null);
			}
			camera.stopPreview();
			camera.setPreviewCallback(null);
			if (camera != null) {
				camera.release();
			}
			camera = null;
		}
	}

	private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			try {
				Camera.Parameters parameters = camera.getParameters();
				cameraWidth = parameters.getPreviewSize().width;
				cameraHeight = parameters.getPreviewSize().height;
				yuvImage = new YuvImage(data, ImageFormat.NV21, cameraWidth, cameraHeight, null);
				imageReady = true;
				looped += 1;
			} catch (Exception e) {

			}
		}
	};

	private boolean imageReady() {
		return imageReady;
	}

	private boolean isCameraAvailable() {
		int cameraId = -1;
		Camera cam = null;
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) { // Camera.CameraInfo.CAMERA_FACING_FRONT or BACK
				cameraId = i;
				break;
			}
		}
		try {
			cam = Camera.open(cameraId);
		} catch (Exception e) {
			Log.e("Error", "Camera Not Available!");
			return false;
		}
		if (cam != null) {
			cam.release();
		}
		cam = null;
		return true;
	}

	private Camera openCamera(int cameraInfoType) {
		int cameraId = -1;
		Camera cam = null;
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == cameraInfoType) { // Camera.CameraInfo.CAMERA_FACING_FRONT or BACK
				cameraId = i;
				break;
			}
		}
		try {
			cam = Camera.open(cameraId);
		} catch (Exception e) {
			Log.e("Error", "Can't Open Camera");
		}
		return cam;
	}

	static private int red(int pixel) {
		return (pixel >> 16) & 0xff;
	}

	static private int green(int pixel) {
		return (pixel >> 8) & 0xff;
	}

	static private int blue(int pixel) {
		return pixel & 0xff;
	}

	static private int gray(int pixel) {
		return (red(pixel) + green(pixel) + blue(pixel));
	}

	static private int highestColor(int red, int green, int blue) {
		int[] color = {red, green, blue};
		int value = 0;
		for (int i = 1; i < 3; i++) {
			if (color[value] < color[i]) {
				value = i;
			}
		}
		return value;
	}

	// returns ROTATED image, to match cameraPreview window
	static private Bitmap convertYuvImageToRgb(YuvImage yuvImage, int width, int height, int downSample) {
		Bitmap rgbImage;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		yuvImage.compressToJpeg(new Rect(0, 0, width, height), 0, out);
		byte[] imageBytes = out.toByteArray();

		BitmapFactory.Options opt;
		opt = new BitmapFactory.Options();
		opt.inSampleSize = downSample;

		// get image and rotate it so (0,0) is in the bottom left
		Bitmap tmpImage;
		Matrix matrix = new Matrix();
		tmpImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, opt);
		rgbImage = Bitmap.createBitmap(tmpImage, 0, 0, tmpImage.getWidth(), tmpImage.getHeight(), matrix, true);

		return rgbImage;
	}


}
