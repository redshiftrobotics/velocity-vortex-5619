package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

@TeleOp(name = "Tune PID", group = "Util")
public class TunePID extends LinearOpMode {

	Robot myRobot;
	I2cDeviceSynch imu;
	DcMotor leftDrive;
	DcMotor rightDrive;
	TuneState state;
	DcMotorSimple.Direction direction;
	boolean driving = false;

	@Override
	public void runOpMode() throws InterruptedException {
		// Map hardware
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
		imu = hardwareMap.i2cDeviceSynch.get("imu");

		// Create our driver
		myRobot = new Robot(imu, leftDrive, rightDrive);
		// Give it default tunings
		myRobot.data.PID.PTuning = 20;
		myRobot.data.PID.ITuning = 20;
		myRobot.data.PID.DTuning = 20;

		// Give a default state
		state = TuneState.P;
		direction = DcMotor.Direction.FORWARD;

		while (true) {
			if (!driving) {
				// Declare what we are tuning based on the
				if (gamepad1.a) {
					telemetry.addData("Tuning", "P");
					state = TuneState.P;
				} else if (gamepad1.b) {
					telemetry.addData("Tuning", "I");
					state = TuneState.I;
				} else if (gamepad1.x) {
					telemetry.addData("Tuning", "D");
					state = TuneState.D;
				}  else if(gamepad1.dpad_up){
					telemetry.addData("drive Direction", "Forwards");
					direction = DcMotor.Direction.FORWARD;
				} else if(gamepad1.dpad_down){
					telemetry.addData("drive Direction", "Backwards");
					direction = DcMotor.Direction.REVERSE;
				} else if (gamepad1.back) {
					// Start the auto drive. Functionality should halt until the drive stops
					leftDrive.setDirection(direction);
					rightDrive.setDirection(direction);
					myRobot.straight(50f, 3);
				}

				// Allow tuning of the values
				switch (state) {
					case P:
						if (gamepad1.right_bumper) {
							myRobot.data.PID.PTuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.data.PID.PTuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.data.PID.PTuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.data.PID.PTuning -= 1;
						}
						break;

					case I:
						if (gamepad1.right_bumper) {
							myRobot.data.PID.ITuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.data.PID.ITuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.data.PID.ITuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.data.PID.ITuning -= 1;
						}
						break;
					case D:
						if (gamepad1.right_bumper) {
							myRobot.data.PID.DTuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.data.PID.DTuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.data.PID.DTuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.data.PID.DTuning -= 1;
						}
						break;
				}
				telemetry.addData("P", myRobot.data.PID.PTuning);
				telemetry.addData("I", myRobot.data.PID.ITuning);
				telemetry.addData("D", myRobot.data.PID.DTuning);
				telemetry.update();
				Thread.sleep(100);
			}
		}
	}

	enum TuneState {
		P, I, D
	}
}
