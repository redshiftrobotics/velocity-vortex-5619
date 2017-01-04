package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public abstract class AutonomousOpMode extends LinearOpMode {
	protected DcMotor leftDrive;
	protected DcMotor rightDrive;

	protected float MAX_POWER = 1.0f;
	protected int TICKS_PER_ROTATION = 1120;

	/*
	protected void move(double leftTicks, double rightTicks) throws InterruptedException {
		double leftPower = MAX_POWER * Math.signum(leftTicks);
		double rightPower = MAX_POWER * Math.signum(rightTicks);
		leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		leftDrive.setTargetPosition(
				leftDrive.getCurrentPosition() + (int) Math.round(leftTicks)
		);
		rightDrive.setTargetPosition(
				rightDrive.getCurrentPosition() + (int) Math.round(rightTicks)
		);
		leftDrive.setPower(leftPower);
		rightDrive.setPower(rightPower);
		leftDrive.setMaxSpeed(550);
		rightDrive.setMaxSpeed(550);

		while (leftDrive.isBusy() || rightDrive.isBusy()) idle();
	}
	*/

	protected void move(double left, double right) throws InterruptedException {
		double lTarget = leftDrive.getCurrentPosition() + left;
		double rTarget = rightDrive.getCurrentPosition() + right;

		float lSpeed = left > 0 ? 1.0f : -1.0f;
		float rSpeed = right > 0 ? 1.0f : -1.0f;

		boolean leftDone = false;
		boolean rightDone = false;
		while (!leftDone || !rightDone) {
			leftDone = leftDone || Math.abs(leftDrive.getCurrentPosition() - lTarget) < 100;
			rightDone = rightDone || Math.abs(rightDrive.getCurrentPosition() - rTarget) < 100;
			telemetry.addData("lTarget", lTarget);
			telemetry.addData("rTarget", rTarget);
			telemetry.addData("lSpeed", lSpeed);
			telemetry.addData("rSpeed", rSpeed);
			telemetry.addData("lcp", leftDrive.getCurrentPosition());
			telemetry.addData("rcp", rightDrive.getCurrentPosition());
			telemetry.addData("ldone", leftDone);
			telemetry.addData("rdone", rightDone);
			telemetry.update();
			if (!leftDone) {
				leftDrive.setPower(lSpeed);
			} else {
				leftDrive.setPower(0);
			}
			if (!rightDone) {
				rightDrive.setPower(rSpeed);
			} else {
				rightDrive.setPower(0);
			}
			idle();
		}
		Thread.sleep(500);
	}


	protected void forward(double rotations) throws InterruptedException {
		// TODO: PID
		move(rotations, rotations);
	}

	protected void backward(double rotations) throws InterruptedException {
		forward(-rotations);
	}

	protected void left() throws InterruptedException {
		// TODO: PID
		move(0, 4000);
	}

	protected void right() throws InterruptedException {
		// TODO: PID
		move(4000, 0);
	}
}
