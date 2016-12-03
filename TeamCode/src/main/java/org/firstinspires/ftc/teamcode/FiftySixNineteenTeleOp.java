package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "5619 TeleOp Main")
public class FiftySixNineteenTeleOp extends OpMode {
	private DcMotor left;
	private DcMotor right;
	private DcMotor collector;
	private DcMotor launcher;

	@Override
	public void init() {
		// Directions are reversed for TeleOP
		left = hardwareMap.dcMotor.get("right_drive");
		right = hardwareMap.dcMotor.get("left_drive");
		collector = hardwareMap.dcMotor.get("collector");
		launcher = hardwareMap.dcMotor.get("shooter");
		left.setDirection(DcMotorSimple.Direction.REVERSE);
		right.setDirection(DcMotorSimple.Direction.FORWARD);
		collector.setDirection(DcMotorSimple.Direction.REVERSE);
		launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
	}

	@Override
	public void loop() {
		telemetry.addLine("Welcome to 5619 Horizon.");
		telemetry.addLine();
		telemetry.addLine("Instructions");
		telemetry.addLine("Press A on either controller to toggle the collector.");
		telemetry.addLine("Press B on either controller to toggle the launcher.");
		telemetry.addLine("Use the joysticks on gamepad1 to drive the robot. (Tank Drive)");
		telemetry.addLine();
		telemetry.addLine("0, 1, 2, FIFTY-SIX NINETEEN!");
		telemetry.update();

		if (gamepad1.right_trigger > 0.75) launcher.setPower(1.0);
		else launcher.setPower(0);
		if (gamepad1.left_trigger > 0.75) collector.setPower(1.0);
		else collector.setPower(0);
		left.setPower(gamepad1.left_stick_y);
		right.setPower(gamepad1.right_stick_y);
	}
}
