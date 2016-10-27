package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Tune PID", group = "Util")
public class tunePID extends LinearOpMode{

	robot myRobot;
	I2cDeviceSynch imu;
	DcMotor leftDrive;
	DcMotor rightDrive;
	TuneState state;
	boolean driving = false;

	@Override
	public void runOpMode() throws InterruptedException{
		// Map hardware
		leftDrive = hardwareMap.dcMotor.get("left_drive");
		rightDrive = hardwareMap.dcMotor.get("right_drive");
		imu = hardwareMap.i2cDeviceSynch.get("imu");

		// Create our driver
		myRobot = new robot(imu, leftDrive, rightDrive);
		// Give it default tunings
		myRobot.Data.PID.pTuning = 20;
		myRobot.Data.PID.iTuning = 20;
		myRobot.Data.PID.dTuning = 20;
		myRobot.Data.Drive.powerMultiplier = 20;

		// Give a default state
		state = TuneState.P;

		while (true){
			if(!driving) {
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
				} else if (gamepad1.y) {
					telemetry.addData("Tuning", "Power");
					state = TuneState.POWER;
				} else if (gamepad1.start) {
					// Start the auto drive. Functionality should halt until the drive stops
					myRobot.Straight(50f, 3);
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
					case POWER:
						if (gamepad1.right_bumper) {
							myRobot.Data.Drive.powerMultiplier += .1;
						} else if (gamepad1.right_trigger == 1) {
							myRobot.Data.Drive.powerMultiplier += 1;
						} else if (gamepad1.left_bumper) {
							myRobot.Data.Drive.powerMultiplier -= .1;
						} else if (gamepad1.left_trigger == 1) {
							myRobot.Data.Drive.powerMultiplier -= 1;
						}
						break;
				}
			}
			telemetry.addData("P", myRobot.Data.PID.pTuning);
			telemetry.addData("I", myRobot.Data.PID.iTuning);
			telemetry.addData("D", myRobot.Data.PID.dTuning);
			telemetry.addData("POW", myRobot.Data.Drive.powerMultiplier);
			telemetry.update();
			Thread.sleep(100);
		}
	}
}

enum TuneState{
	P,I,D,POWER
}
