package org.firstinspires.ftc.teamcode.debug;

import com.google.blocks.ftcrobotcontroller.BlocksActivity;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Rotation Counter", group = "debug")
public class RotationCounter extends OpMode {
	DcMotor leftDrive;
	DcMotor rightDrive;

	double sLeft;
	double sRight;

	boolean lastX = false;
	boolean lastB = false;

	protected void move(double leftTicks, double rightTicks) {
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
		leftDrive.setPower(1.0);
		rightDrive.setPower(1.0);
		while (leftDrive.isBusy() || rightDrive.isBusy()) {}
		leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		reset();
	}

	void reset() {
		sLeft = leftDrive.getCurrentPosition();
		sRight = rightDrive.getCurrentPosition();
	}

	@Override
	public void init() {
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
		rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
	}

	@Override
	public void loop() {
		leftDrive.setPower(gamepad1.left_stick_y);
		rightDrive.setPower(gamepad1.right_stick_y);
		if (gamepad1.back || gamepad1.a) reset();
		if (gamepad1.x != lastX && gamepad1.x) move(-4000, 0);
		if (gamepad1.b != lastB && gamepad1.b) move(0, -4000);
		telemetry.addData("Left", leftDrive.getCurrentPosition() - sLeft);
		telemetry.addData("Right", rightDrive.getCurrentPosition() - sRight);
		telemetry.update();
		lastX = gamepad1.x;
		lastB = gamepad1.b;
	}
}
