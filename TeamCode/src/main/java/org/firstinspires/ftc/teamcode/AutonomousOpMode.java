package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public abstract class AutonomousOpMode extends LinearOpMode {
	protected DcMotor leftDrive;
	protected DcMotor rightDrive;

	protected float MAX_POWER = 1.0f;
	protected int TICKS_PER_ROTATION = 1120;

	protected void move(double leftRotations, double rightRotations) {
		leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		leftDrive.setTargetPosition(
				leftDrive.getCurrentPosition() + (int) Math.round(leftRotations * TICKS_PER_ROTATION)
		);
		rightDrive.setTargetPosition(
				rightDrive.getCurrentPosition() + (int) Math.round(rightRotations * TICKS_PER_ROTATION)
		);
		leftDrive.setPower(MAX_POWER);
		rightDrive.setPower(MAX_POWER);
	}

	protected void forward(double rotations) {
		// TODO: PID
		move(rotations, rotations);
	}

	protected void backward(double rotations) {
		forward(-rotations);
	}

	protected void left() {
		// TODO: PID
		move(0, 0);
	}

	protected void right() {
		// TODO: PID
		move(0, 0);
	}
}
