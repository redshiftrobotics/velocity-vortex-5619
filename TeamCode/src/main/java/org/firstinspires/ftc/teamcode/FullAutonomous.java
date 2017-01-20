package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.LightSensor;

import org.redshiftrobotics.Alliance;
import org.redshiftrobotics.beacons.BeaconState;
import org.redshiftrobotics.beacons.BeaconDetector;

abstract public class FullAutonomous extends AutonomousOpMode {
	private BeaconDetector detector;
	private I2cDeviceSynch imu;

	protected abstract Alliance getAlliance();

	protected boolean isAlternatePosition() {
		return false;
	}


	private BeaconButton computeBeaconButton(BeaconState beaconState) {
		if (getAlliance() == Alliance.RED) {
			switch (beaconState) {
				case RED:
					Log.v("v", "Beacon already red, noop");
					return BeaconButton.NONE; // If it's already correct, then noop.
				case BLUE:
					Log.v("v", "Beacon blue, pressing right");
					return BeaconButton.RIGHT; // It doesn't actually matter which one we press.
				case RED_BLUE:
					Log.v("v", "Beacon RB, L");
					return BeaconButton.LEFT;
				case BLUE_RED:
					Log.v("v", "Beacon BR, R");
					return BeaconButton.RIGHT;
				default:
					Log.v("v", "IDFK");
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
		rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
		leftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
		imu = hardwareMap.i2cDeviceSynch.get("imu");

		/*
		robot = new Robot(imu, rightDrive, leftDrive, telemetry);

		// Set our p, i, and d tuning
		robot.Data.PID.pTuning = 2f;
		robot.Data.PID.iTuning = 1f;
		robot.Data.PID.dTuning = .3f;
		robot.Data.Drive.POWER_CONSTANT = 0.575f;
		robot.Data.PID.magicNumber = 30;
		*/

		detector = new BeaconDetector(hardwareMap);
		detector.start();

		telemetry.addLine("Ready");
		telemetry.update();

		waitForStart();

		if (isAlternatePosition()) { // In Pos 2
			forward(0); // Move away from wall. (This should be 18" less than the primary position.)
			left(); // Face Corner Vortex
			forward(0); // Till we're 9" behind where the primary position would be.
			right(); // Now we're where we would be if we had been in the primary position.
		} else {
			//forward(0); // Move away from wall
		}

		// Note: we do the near beacon first just to make sure we don't cross the line in the first 10 seconds.
		forward(4200); //pendicular to the white tape of the CLOSEST beacon.

		pressBeacon();

		forward(4200);

		pressBeacon();
	}

	private void pressBeacon() throws InterruptedException {
		BeaconState beaconState = detector.detect();
		BeaconButton buttonToPress = computeBeaconButton(beaconState);

		telemetry.addData("beaconState", beaconState.toString());
		telemetry.addData("button", buttonToPress.toString());
		telemetry.update();

		Thread.sleep(1000);

		int ticksToAlignWithBeacon = 0;

		if (buttonToPress == BeaconButton.LEFT) ticksToAlignWithBeacon = 320;
		else if (buttonToPress == BeaconButton.RIGHT) ticksToAlignWithBeacon = 1450;

		backward(ticksToAlignWithBeacon);

		if (ticksToAlignWithBeacon != 0) {
			if (getAlliance() == Alliance.RED) {
				move(3250, 0);
			} else {
				move(3250, 0);
			}
			forward(3500);
			backward(2100);

			if (getAlliance() == Alliance.RED) {
				move(0, -3500);
			} else {
				move(-3500, 0);
			}

			forward(ticksToAlignWithBeacon);
		}
	}
}
