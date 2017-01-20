package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public abstract class AutonomousOpMode extends LinearOpMode {
	protected DcMotor leftDrive;
	protected DcMotor rightDrive;

	protected Robot robot;

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

		double startL = leftDrive.getCurrentPosition();
		double startR = rightDrive.getCurrentPosition();

		float lSpeed = left > 0 ?  1.0f : -1.0f;
		float rSpeed = right > 0 ? 1.0f : -1.0f;

		boolean leftDone = false;
		boolean rightDone = false;
		while (!leftDone || !rightDone) {
			leftDone = leftDone || left == 0 || (left < 0 ? leftDrive.getCurrentPosition() < lTarget : leftDrive
					.getCurrentPosition() > lTarget);
			rightDone = rightDone || right == 0 || (right < 0 ? rightDrive.getCurrentPosition() < rTarget : rightDrive
					.getCurrentPosition() > rTarget);
			telemetry.addData("lTarget", lTarget);
			telemetry.addData("rTarget", rTarget);
			telemetry.addData("lSpeed", lSpeed);
			telemetry.addData("rSpeed", rSpeed);
			telemetry.addData("lstart", startL);
			telemetry.addData("rstart", startR);
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
		leftDrive.setPower(0);
		rightDrive.setPower(0);
		Thread.sleep(1000);
	}


	protected void forward(double rotations) throws InterruptedException {
		//rotations = rotations / 1400; // We actually input ticks.
		//robot.straight((float) rotations, (int) rotations, telemetry);
		move(rotations, rotations);
	}

	protected void backward(double rotations) throws InterruptedException {
		//rotations = rotations / 1400; // We actually input ticks.
		//robot.straight((float) -rotations, (int) rotations, telemetry);
		forward(-rotations);
	}

	protected void left() throws InterruptedException {
		// TODO: PID
		move(0, 3500);
	}

	protected void right() throws InterruptedException {
		// TODO: PID
		move(3500, 0);
	}
}
