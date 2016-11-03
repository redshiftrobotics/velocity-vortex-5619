package org.redshiftrobotics.linefollower;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LightSensor;

public class LineFollower {
	private LightSensor lightSensor;
	private DcMotor leftMotor;
	private DcMotor rightMotor;

	private double LINE_LIGHT_THRESHOLD;
	private double ON_LINE_LEFT_POWER;
	private double ON_LINE_RIGHT_POWER;
	private double OFF_LINE_LEFT_POWER;
	private double OFF_LINE_RIGHT_POWER;

	/**
	 * Create a line follower with the default options.
	 *
	 * @param lightSensor the light sensor to use for detection.
	 * @param leftMotor the motor on the left side of the robot.
	 * @param rightMotor the motor on the right side of the robot.
	 */
	public LineFollower(LightSensor lightSensor, DcMotor leftMotor, DcMotor rightMotor) {
		new LineFollower(lightSensor, leftMotor, rightMotor, 0.1, 1, 0.9, 0.5, 1);
	}

	/**
	 * Create a Line Follower.
	 *
	 * @param lightSensor the hardware LightSensor to use.
	 * @param leftMotor the motor on the left side of the robot.
	 * @param rightMotor the motor on the right side of the robot.
	 * @param lineLightThreshold the minimum amount of brightness detected to be considered "on the line".
	 * @param onLineLeftPower the amount of power to drive from the left motor if the robot is on the line.
	 * @param onLineRightPower the amount of power to drive from the right motor if the robot is on the line.
	 * @param offLineLeftPower the amount of power to drive from the left motor if the robot is off the line.
	 * @param offLineRightPower the amount of power to drive from the right motor if the robot is off the line.
	 */
	public LineFollower(
		LightSensor lightSensor,
		DcMotor leftMotor,
		DcMotor rightMotor,
		double lineLightThreshold,
		double onLineLeftPower,
		double onLineRightPower,
		double offLineLeftPower,
		double offLineRightPower
	) {
		this.lightSensor = lightSensor;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		LINE_LIGHT_THRESHOLD = lineLightThreshold;
		ON_LINE_LEFT_POWER = onLineLeftPower;
		ON_LINE_RIGHT_POWER = onLineRightPower;
		OFF_LINE_LEFT_POWER = offLineLeftPower;
		OFF_LINE_RIGHT_POWER = offLineRightPower;
	}

	/**
	 * Update the motor power based off of the light sensor.
	 */
	public void tick() {
		double lightValue = lightSensor.getLightDetected();
		boolean isLine = lightValue > LINE_LIGHT_THRESHOLD;

		if (isLine) {
			leftMotor.setPower(ON_LINE_LEFT_POWER);
			rightMotor.setPower(ON_LINE_RIGHT_POWER);
		} else {
			leftMotor.setPower(OFF_LINE_LEFT_POWER);
			rightMotor.setPower(OFF_LINE_RIGHT_POWER);
		}
	}
}
