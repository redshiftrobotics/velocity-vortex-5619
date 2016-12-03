package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.LightSensor;

import org.redshiftrobotics.Alliance;
import org.redshiftrobotics.beacons.BeaconState;
import org.redshiftrobotics.beacons.BeaconDetector;
import org.redshiftrobotics.linefollower.LineFollower;

abstract public class FullAutonomous extends AutonomousOpMode {
	private BeaconDetector detector;
	private LightSensor leftLightSensor;
	private LightSensor rightLightSensor;

	protected double ON_LINE_LIGHT_VAL = 0.1;
	protected boolean ALTERNATE_POSITION = false;

	protected abstract Alliance getAlliance();

	protected boolean isAlternatePosition() {
		return false;
	}


	private BeaconButton computeBeaconButton(BeaconState beaconState) {
		if (getAlliance() == Alliance.RED) {
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
		} else {
			switch (beaconState) {
				case RED:
					return BeaconButton.RIGHT; // It doesn't actually matter which one we press.
				case BLUE:
					return BeaconButton.NONE; // If it's already correct, then noop.
				case RED_BLUE:
					return BeaconButton.RIGHT;
				case BLUE_RED:
					return BeaconButton.LEFT;
				default:
					return BeaconButton.NONE;
			}

		}
	}

	/**
	 *
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
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
		leftLightSensor = hardwareMap.lightSensor.get("left_light");
		rightLightSensor = hardwareMap.lightSensor.get("right_light");

		detector = new BeaconDetector(hardwareMap);

		waitForStart();

		if (ALTERNATE_POSITION) { // In Pos 2
			forward(0); // Move away from wall. (This should be 18" less than the primary position.)
			left(); // Face Corner Vortex
			forward(0); // Till we're 9" behind where the primary position would be.
			right(); // Now we're where we would be if we had been in the primary position.
		} else {
			forward(0); // Move away from wall
		}

		// Note: we do the near beacon first just to make sure we don't cross the line in the first 10 seconds.
		forward(0); // Till we're perpendicular to the white tape of the CLOSEST beacon.
		forward(0); // 24"ish
		left(); // Facing wall with beacons on it.
		forward(0); // The end of the white tape should be in line with the front of the robot.
		left(); // Now we're facing the white tape. If we drive forward, both light sensors will be on the white line.

		approachBeacon(); // Drive till we're on the line, then back 9"
		right(); // Now we're facing the beacon head-on.

		pressBeacon(); // BEACON! (No-op if it's fully lit for us.) This will move us back.

		right(); // Now face the white line of the second beacon.
		forward(0); // Most of the way to the next white line. (NOT all the way.)

		approachBeacon();
		left(); // Face Beacon #2 (FAR Beacon)

		pressBeacon(); // GO!

		// FIN.
	}

	private void approachBeacon() {
		while (leftLightSensor.getLightDetected() < ON_LINE_LIGHT_VAL) { // FIXME: use both sensors
			// FIXME: Mess with these
			leftDrive.setPower(0.75);
			rightDrive.setPower(0.75);
		}

		leftDrive.setPower(0);
		rightDrive.setPower(0);

		backward(0); // 9"
	}

	private void pressBeacon() throws InterruptedException {
		BeaconState beaconState = detector.detect();
		BeaconButton buttonToPress = computeBeaconButton(beaconState);

		if (buttonToPress != BeaconButton.NONE) {
			LineFollower lf = new LineFollower(
					buttonToPress == BeaconButton.LEFT ? rightLightSensor : leftLightSensor,
					leftDrive,
					rightDrive
			);

			double lStart = leftDrive.getCurrentPosition();
			double rStart = rightDrive.getCurrentPosition();

			while (computeLineFollowDistance(
					leftDrive.getCurrentPosition() - lStart,
					rightDrive.getCurrentPosition() - rStart) < 0
					) {
				lf.tick();
				idle(); // Let's be good people
			}

			leftDrive.setPower(0);
			rightDrive.setPower(0);
			backward(0); // Note, this needs to be the length of the white tape + 9"
		}
	}
}