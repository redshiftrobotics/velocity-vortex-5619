package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.redshiftrobotics.beacons.BeaconState;
import org.redshiftrobotics.beacons.BeaconDetector;
import org.redshiftrobotics.linefollower.LineFollower;

enum BeaconButton {
	LEFT, RIGHT, NONE
}

public class Autonomous1 extends LinearOpMode {

	private BeaconDetector detector;
	private LineFollower lineFollower;
	private DcMotor leftMotor;
	private DcMotor rightMotor;
	private LightSensor leftLightSensor;
	private LightSensor rightLightSensor;
	private TouchSensor wallDetector;
	private Robot robot;

	private static final double ON_LINE_LIGHT_VAL = 0.1;

	// Helpers
	private void forward(double rotations) {
		robot.straight((float) rotations, (int) rotations);
	}

	private void backward(double rotations) {
		forward(-rotations);
	}

	private void left() {
		robot.AngleTurn(-90, 30);
	}

	private void right() {
		robot.AngleTurn(90, 30);
	}

	private BeaconButton computeBeaconButton(BeaconState beaconState) {
		// This assumes that we're red.
		switch (beaconState) {
			case RED:
				return BeaconButton.NONE; // If it's already correct, then noop.
			case BLUE:
				return BeaconButton.RIGHT; // It doesn't actually matter which one we press.
			case RED_BLUE:
				return BeaconButton.LEFT;
			case BLUE_RED:
				return BeaconButton.RIGHT;
			default:
				return BeaconButton.NONE;
		}
	}

	private void note() {

		/**
		  1. Forward
		  2. If in POS 2:
		    1. Left 90
		    2. Forward
		    3. Right 90
		  3. Forward to Perp(B2)
		  4. Forward 20"
		  5. Left 90
		  6. Forward
		  7. Left 90
		  8. Forward Till Line
		  9. Back 1/2 robot width
		 10. Right 90
		 11. Beacon Detect
		 12. Switch Target Button:
		 	1. LEFT
		 		1. Follow with RIGHT sensor on RIGHT of line.
		 	2. RIGHT
		 		1. Follow with LEFT sensor on LEFT of line.
		 13. Line Follow till wall detected/beacon hit.
		 14. Reverse
		 15. Left 90
		 16. Forward 12" // To account for current beacon's line.
		 17. Forward till line
		 18. Back 1/2 robot width
		 19. Right 90
		 20. Beacon Detect
		 21. Switch Target Button:
		 	1. LEFT
		 		1. Follow with RIGHT sensor on RIGHT of line.
		 	2. RIGHT
		 		1. Follow with LEFT sensor on LEFT of line.
		 22. Line Follow till wall detected/beacon hit.
		 23. Reverse
		 --- IF GOING TO RAMP --
		 24. Left 90
		 25. Drive forward till ramp (fixed distance)

		 */
	}

	/**
	 * Given delta encoder values for the left and right side drive motors, compute the total distance forward (in
	 * encoder ticks. a.k.a. ignore sideways movement.)
	 *
	 * The algorithm is:
	 * 1. Figure out which one is the biggest, and which is the smallest. (If they're the same, then just return one.)
	 * 2. Compute the difference between them.
	 *     - This value is one quarter of the circumference of a circle, who's radius is equal to the amount the robot
	 *       moved forward. This was determined only through lots of horrible sketches and diagrams, and lots of
	 *       WolframAlpha.
	 * 3. Return the smallest value + the radius of said circle
	 *     - For the lazy: this is the difference * 4 * (1/(2π))
	 *
	 * I think that this is right. Regardless, it's almost certainly close enough, because we just aren't that accurate.
	 *
	 * @param dLeft the change in encoder values for the left motor.
	 * @param dRight the change in encoder values for the right motor.
	 * @return the distance moved forward (ie. ignoring sideways) that the robot moved, in encoder ticks.
	 */
	private double computeLineFollowDistance(double dLeft, double dRight) {
		if (dLeft == dRight) return dLeft;
		double dBig = dLeft > dRight ? dLeft : dRight;
		double dSmall = dLeft > dRight ? dRight : dLeft;
		double diff = dBig - dSmall;
		return dSmall + diff * 4.0 * (1.0 / (2.0 * Math.PI));
	}

	public void runOpMode() throws InterruptedException {
		leftMotor = hardwareMap.dcMotor.get("left_drive");
		rightMotor = hardwareMap.dcMotor.get("right_drive");
		leftLightSensor = hardwareMap.lightSensor.get("left_light");
		leftLightSensor = hardwareMap.lightSensor.get("right_light");

		detector = new BeaconDetector(hardwareMap);

		waitForStart();

		forward(0);

		if (false) { // In Pos 2
			left();
			forward(0);
			right();
		}

		forward(0); // Till perp to beacon
		forward(0); // 1 robot width * 110%
		left();
		forward(0);
		left();

		while (leftLightSensor.getLightDetected() < ON_LINE_LIGHT_VAL) { // FIXME: use both sensors
			// FIXME: Mess with these
			leftMotor.setPower(0.75);
			rightMotor.setPower(0.75);
		}

		leftMotor.setPower(0);
		rightMotor.setPower(0);

		backward(0); // 9"
		right();

		BeaconState beaconState = detector.detect();
		BeaconButton buttonToPress = computeBeaconButton(beaconState);

		if (buttonToPress != BeaconButton.NONE) {
			LineFollower lf = new LineFollower(
					buttonToPress == BeaconButton.LEFT ? rightLightSensor : leftLightSensor,
					leftMotor,
					rightMotor
			);

			double lStart = leftMotor.getCurrentPosition();
			double rStart = rightMotor.getCurrentPosition();

			while (computeLineFollowDistance(
					leftMotor.getCurrentPosition() - lStart,
					rightMotor.getCurrentPosition() - rStart) < 0
					) {
				lineFollower.tick();
				idle(); // Let's be good people
			}

			leftMotor.setPower(0);
			rightMotor.setPower(0);
			backward(0);
		}

		right();
		forward(0);

		while (leftLightSensor.getLightDetected() < ON_LINE_LIGHT_VAL) { // FIXME: use both sensors
			// FIXME: Mess with these
			leftMotor.setPower(0.75);
			rightMotor.setPower(0.75);
		}

		leftMotor.setPower(0);
		rightMotor.setPower(0);

		backward(0); // 9"
		left();

		if (buttonToPress != BeaconButton.NONE) {
			LineFollower lf = new LineFollower(
					buttonToPress == BeaconButton.LEFT ? rightLightSensor : leftLightSensor,
					leftMotor,
					rightMotor
			);

			double lStart = leftMotor.getCurrentPosition();
			double rStart = rightMotor.getCurrentPosition();

			while (computeLineFollowDistance(
					leftMotor.getCurrentPosition() - lStart,
					rightMotor.getCurrentPosition() - rStart) < 0
					) {
				lineFollower.tick();
				idle(); // Let's be good people
			}

			leftMotor.setPower(0);
			rightMotor.setPower(0);
			backward(0);
		}
	}
}
