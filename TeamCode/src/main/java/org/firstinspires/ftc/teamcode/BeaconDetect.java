package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.HashMap;

import for_camera_opmodes.OpModeCamera;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */

@TeleOp(name = "Beacon Detect", group = "Dev")
public class BeaconDetect extends OpModeCamera {

  int ds2 = 4;  // additional downsampling of the image
  private BeaconDetector detector;
  // set to 1 to disable further downsampling

  /*
   * Code to run when the op mode is first enabled goes here
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
   */
  @Override
  public void init() {
    HashMap<String, Integer> options = new HashMap<String, Integer>();
    options.put("red:r"         , 210);
    options.put("red:g"         , 150);
    options.put("red:b"         , 240);
    options.put("blue:r"        ,  85);
    options.put("blue:g"        , 255);
    options.put("blue:b"        , 200);
    options.put("classify:main" , 210);
    options.put("classify:other", 210);

    detector = new BeaconDetector(options);

    setCameraDownsampling(2);
    // parameter determines how downsampled you want your images
    // 8, 4, 2, or 1.
    // higher number is more downsampled, so less resolution but faster
    // 1 is original resolution, which is detailed but slow
    // must be called before super.init sets up the camera

    super.init(); // inits camera functions, starts preview callback
  }

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void loop() {

    if (gamepad1.x) {
      telemetry.addData("Status", "Processing");
      while (!imageReady()) {} // wait for an image from the camera

      Bitmap image = convertYuvImageToRgb(yuvImage, width, height, ds2);

      // https://eventuallyconsistent.net/2011/07/21/rotating-a-bitmap-in-android/
      // create a matrix object
      Matrix matrix = new Matrix();
      matrix.postRotate(-90); // clockwise by 90 degrees

      // create a new bitmap from the original using the matrix to transform the result
      image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);

      telemetry.addData("State", detector.detect(image).toString());
      telemetry.addData("Status", "Complete");

      while (gamepad1.x) {} // Wait for the button to be released
    }


  }

  @Override
  public void stop() {
    super.stop(); // stops camera functions
  }
}
