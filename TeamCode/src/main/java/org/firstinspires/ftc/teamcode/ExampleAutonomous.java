package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;


@Autonomous(name = "ExampleAutonomous", group = "Autonomous")
public class ExampleAutonomous extends LinearOpMode {

    Robot myRobot;
    I2cDeviceSynch imu;
    DcMotor leftDrive;
    DcMotor rightDrive;

    @Override
    public void runOpMode(){

        // Initialize our hardware
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        rightDrive = hardwareMap.dcMotor.get("right_drive");

        imu = hardwareMap.i2cDeviceSynch.get("imu");

        // Create our driver
        myRobot = new Robot(imu, leftDrive, rightDrive, telemetry);
        // Set our p, i, and d tuning
		myRobot.Data.PID.PTuning = 16;
		myRobot.Data.PID.ITuning = 1;
		myRobot.Data.PID.DTuning = 4;
        // Lets drive straight ten rotations or 10 seconds.
        myRobot.Straight(10f, 10, telemetry);
		// Now do a 90 degree turn
    }
}

