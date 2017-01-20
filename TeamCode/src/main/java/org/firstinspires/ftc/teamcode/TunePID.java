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
		myRobot.Data.PID.pTuning = 1.8f;
		myRobot.Data.PID.iTuning = 1f;
		myRobot.Data.PID.dTuning = .1f;
		myRobot.Data.Drive.POWER_CONSTANT = 0.575f;
		myRobot.Data.PID.magicNumber = 30;
		leftDrive.setDirection(DcMotor.Direction.FORWARD);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);
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
				} if (gamepad1.start) {
					telemetry.addData("Driving", "Driving");
					myRobot.straight(50f, 3, telemetry);
				}

				// Allow tuning of the values
				switch (state) {
					case P:
						if (gamepad1.right_bumper) {
							myRobot.Data.PID.pTuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.pTuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.pTuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.pTuning -= 1;

						}
						break;

					case I:
						if (gamepad1.right_bumper) {
							myRobot.Data.PID.iTuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.iTuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.iTuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.iTuning -= 1;

						}
						break;
					case D:
						if (gamepad1.right_bumper) {
							myRobot.Data.PID.dTuning += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.dTuning += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.dTuning -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.dTuning -= 1;
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
							myRobot.Data.PID.magicNumber += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.PID.magicNumber += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.PID.magicNumber -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.PID.magicNumber -= 1;
						}
						break;
				}
				telemetry.addData("P", myRobot.Data.PID.pTuning);
				telemetry.addData("I", myRobot.Data.PID.iTuning);
				telemetry.addData("D", myRobot.Data.PID.dTuning);
				telemetry.addData("POW", myRobot.Data.Drive.POWER_CONSTANT);
				telemetry.addData("MAG", myRobot.Data.PID.magicNumber);
				telemetry.addData("IMU", myRobot.Data.imu.getAngularOrientation() + ". TAR: " + myRobot.Data.PID.target);

				telemetry.update();
				Thread.sleep(100);
			}
		}
	}

	enum TuneState {
		P, I, D, POW, MAG
	}
}
