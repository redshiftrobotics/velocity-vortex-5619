package org.firstinspires.ftc.teamcode.debug;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Rotation Counter", group = "debug")
public class RotationCounter extends OpMode {
	DcMotor leftDrive;
	DcMotor rightDrive;

	double sLeft;
	double sRight;

	void reset() {
		sLeft = leftDrive.getCurrentPosition();
		sRight = rightDrive.getCurrentPosition();
	}

	@Override
	public void init() {
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
	}

	@Override
	public void loop() {
		leftDrive.setPower(gamepad1.left_stick_y);
		rightDrive.setPower(gamepad1.right_stick_y);
		if (gamepad1.back) reset();
		telemetry.addData("Left", leftDrive.getCurrentPosition() - sLeft);
		telemetry.addData("Right", rightDrive.getCurrentPosition() - sRight);
		telemetry.update();
	}
}
