package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class ETDrive extends OpMode {

    String teleConvert;
    int teleInt = 3;

    int snapshot;

    DcMotor frontLeftMotor; //FRONT LEFT
    DcMotor frontRightMotor; //FRONT RIGHT
    DcMotor backLeftMotor; //BACK LEFT
    DcMotor backRightMotor; //BACK RIGHT
    DcMotor extendMotor; // arm


    public void dt(String text)
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }
    public void init() {


        dt ("init loaded");
        dt ("Tank Drive Loaded:");

        frontLeftMotor = hardwareMap.dcMotor.get("left1");
        frontRightMotor = hardwareMap.dcMotor.get("right1");
        backLeftMotor = hardwareMap.dcMotor.get("left2");
        backRightMotor = hardwareMap.dcMotor.get("right2");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
       // backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        // frontRightMotor.setDirection(DcMotor.Direction.REVERSE); //CHANGED

        extendMotor = hardwareMap.dcMotor.get("extend");



    }



    @Override
    public void loop() {


//get the values from the gamepads
        //note: pushing the stick all the way up returns -1,
        //so we need to reverse the y values
        float xValue = -gamepad1.left_stick_y;
        float yValue = -gamepad1.right_stick_y;


        //calculate the power needed for each motor


        //clip the power values so that it only goes from -1 to 1
        xValue = Range.clip(xValue, -1, 1);
       yValue = Range.clip(yValue, -1, 1);

        //set the power of the motors with the gamepad values
        frontLeftMotor.setPower(-xValue); //
        frontRightMotor.setPower(-yValue); //

        backLeftMotor.setPower(xValue);
        backRightMotor.setPower(yValue);


        if(gamepad1.right_bumper)
        {
            extendMotor.setPower(1);
        }
        if(gamepad1.left_bumper)
        {
           extendMotor.setPower(-1);
        }

    }




}
