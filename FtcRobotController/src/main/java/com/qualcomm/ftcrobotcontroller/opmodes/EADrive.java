package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class EADrive extends EOpModeBase {

    public void init() {
        dt ("Arcade Drive Selected!");

       	super.init();

    }



    @Override
    public void loop() {

//get the values from the gamepads
        //note: pushing the stick all the way up returns -1,
        //so we need to reverse the y values
        float xValue = gamepad1.left_stick_x;
        float yValue = -gamepad1.left_stick_y;


        //calculate the power needed for each motor
        float leftPower = yValue + xValue;
        float rightPower = yValue - xValue;

        //clip the power values so that it only goes from -1 to 1
        leftPower = Range.clip(leftPower, -1, 1);
        rightPower = Range.clip(rightPower, -1, 1);

        //set the power of the motors with the gamepad values
        frontLeftMotor.setPower(leftPower);
        frontRightMotor.setPower(rightPower);

        backLeftMotor.setPower(leftPower);
        backRightMotor.setPower(rightPower);


    }




}





