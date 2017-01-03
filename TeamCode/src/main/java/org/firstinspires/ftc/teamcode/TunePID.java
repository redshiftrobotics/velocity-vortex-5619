package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

@TeleOp(name = "Tune PID", group = "Teleop")
public class TunePID extends LinearOpMode {

	Robot myRobot;
	I2cDeviceSynch imu;
	DcMotor leftDrive;
	DcMotor rightDrive;
	TuneState state;
	DcMotorSimple.Direction direction;
	DcMotorSimple.Direction direction2;
	boolean driving = false;

	@Override
	public void runOpMode() throws InterruptedException {
		// Map hardware
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
		imu = hardwareMap.i2cDeviceSynch.get("imu");

		// Create our driver
		myRobot = new Robot(imu, leftDrive, rightDrive, telemetry);
		// Give it default tunings
		myRobot.Data.PID.PTuning = 1.8f;
		myRobot.Data.PID.ITuning = 1f;
		myRobot.Data.PID.DTuning = .1f;
		myRobot.Data.Drive.POWER_CONSTANT = 0.575f;
		myRobot.Data.PID.MagicNumber = 30;
		rightDrive.setDirection(DcMotor.Direction.REVERSE);
		// Give a default state
		state = TuneState.P;

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
				} else if(gamepad1.y){
					telemetry.addData("Tuning", "POW");
					state = TuneState.POW;
				} else if(gamepad1.right_stick_button) {
					telemetry.addData("Tuning", "MAG");
					state = TuneState.MAG;
				} else if(gamepad1.dpad_up){
					telemetry.addData("Right Drive Direction", "Forwards");
					rightDrive.setDirection(DcMotor.Direction.FORWARD);
				} else if(gamepad1.dpad_down){
					telemetry.addData("Right Drive Direction", "Backwards");
					rightDrive.setDirection(DcMotor.Direction.REVERSE);
				} else if(gamepad1.dpad_left){
					telemetry.addData("Left Drive Direction", "Backwards");
					leftDrive.setDirection(DcMotor.Direction.REVERSE);
				} else if(gamepad1.dpad_right){
					telemetry.addData("Left Drive Direction", "Forwards");
					leftDrive.setDirection(DcMotor.Direction.FORWARD);
				} if (gamepad1.start) {
					telemetry.addData("Driving", "Driving");
					myRobot.Straight(50f, 3, telemetry);
				}

				// Allow tuning of the values
				switch (state) {
					case P:
						if (gamepad1.right_bumper) {
							myRobot.Data.PID.PTuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.PTuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.PTuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.PTuning -= 1;
						}
						break;

					case I:
						if (gamepad1.right_bumper) {
							myRobot.Data.PID.ITuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.ITuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.ITuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.ITuning -= 1;
						}
						break;
					case D:
						if (gamepad1.right_bumper) {
							myRobot.Data.PID.DTuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.DTuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.DTuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.DTuning -= 1;
						}
						break;
					case POW:
						if (gamepad1.right_bumper) {
							myRobot.Data.Drive.POWER_CONSTANT += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.Drive.POWER_CONSTANT += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.Drive.POWER_CONSTANT -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.Drive.POWER_CONSTANT -= 1;
						}
						break;
					case MAG:
						if (gamepad1.right_bumper) {
							myRobot.Data.PID.MagicNumber += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.MagicNumber += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.MagicNumber -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.MagicNumber -= 1;
						}
						break;
				}
				telemetry.addData("P", myRobot.Data.PID.PTuning);
				telemetry.addData("I", myRobot.Data.PID.ITuning);
				telemetry.addData("D", myRobot.Data.PID.DTuning);
				telemetry.addData("POW", myRobot.Data.Drive.POWER_CONSTANT);
				telemetry.addData("MAG", myRobot.Data.PID.MagicNumber);
				telemetry.addData("IMU", myRobot.Data.imu.getAngularOrientation() + ". TAR: " + myRobot.Data.PID.Target);
				telemetry.update();
				Thread.sleep(100);
			}
		}
	}

	enum TuneState {
		P, I, D, POW, MAG
	}
}
