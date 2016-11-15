package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public class RotationCounter extends OpMode {
	DcMotor left;
	DcMotor right;

	Robot robot;

	//   Y
	// X   B
	//   A
	boolean lastYState = false; // Forward
	boolean lastAState = false; // Back
	boolean lastXState = false; // Left
	boolean lastBState = false; // Right

	double startPos;

	void reset() {
		startPos = right.getCurrentPosition();
	}

	@Override
	public void init() {
		left = hardwareMap.dcMotor.get("left_drive");
		right = hardwareMap.dcMotor.get("right_drive");
		robot = new Robot(hardwareMap.i2cDeviceSynch.get("imu"), left, right);
		reset();
	}

	@Override
	public void loop() {
		if (gamepad1.y != lastYState && gamepad1.y) robot.straight(+1);
		if (gamepad1.a != lastAState && gamepad1.a) robot.straight(-1);
		if (gamepad1.x != lastXState && gamepad1.x) robot.AngleTurn(-90, 30);
		if (gamepad1.b != lastBState && gamepad1.b) robot.AngleTurn(+90, 30);

		left.setPower(gamepad1.right_stick_y);
		right.setPower(gamepad1.right_stick_y);

		telemetry.addLine("Use the right joystick to move straight.");
		telemetry.addLine("Use Y/A to move forward/backward 1 rotation.");
		telemetry.addLine("Use X/B to turn left/right 90° (not counted in rotation total)");
		telemetry.addLine("Use back to reset the count");
		telemetry.addLine();
		telemetry.addData("Count", right.getCurrentPosition() - startPos);

	}
}
