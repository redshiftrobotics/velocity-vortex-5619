package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;


@Autonomous(name = "exampleAutonomous", group = "Autonomous")
public class exampleAutonomous extends LinearOpMode {

    robot myRobot;
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
        myRobot = new robot(imu, leftDrive, rightDrive);

        // Set our p, i, and d tuning
        myRobot.Data.PID.PTuning = 37f;
        myRobot.Data.PID.ITuning = 1f;
        myRobot.Data.PID.DTuning = 4f;

        // Lets Drive straight ten rotations or 10 seconds.
        myRobot.Straight(10f, 10);
    }
}

