package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class ETHigh extends EOpModeBase {

    double lift1Pos = 0.6;
    double lift2Pos = 0.2;

    public void init() {
        dt("HIGH Tank Drive Selected!");
        tts("Ready to drive!");
        super.init(); //calls the init funtion in EOpModeBase.class
/*
        extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
*/

        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);




        //extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
       // extendMotor2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        //frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        //frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
       // backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
       // backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        extendMotor2.setDirection(DcMotor.Direction.FORWARD); //not needed
        extendMotor1.setDirection(DcMotor.Direction.REVERSE); //already in opmodebase


        //extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        // extendMotor2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        //frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        //frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        // backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        // backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

            lift1.setPosition(lift1Pos);
            lift2.setPosition(lift2Pos);


    }



    boolean lastBttnStateHitServoLeft = false;
    boolean toggleStateHitServoLeft = false;

    public boolean toggleHitServoLeft() {
        if (gamepad2.left_bumper && !lastBttnStateHitServoLeft) {
            toggleStateHitServoLeft = !toggleStateHitServoLeft;
        }
        lastBttnStateHitServoLeft = gamepad2.left_bumper;
        return toggleStateHitServoLeft;
    }

    boolean lastBttnStateHitServoRight = false;
    boolean toggleStateHitServoRight = false;

    public boolean toggleHitServoRight() {
        if (gamepad2.right_bumper && !lastBttnStateHitServoRight) {
            toggleStateHitServoRight = !toggleStateHitServoRight;
        }
        lastBttnStateHitServoRight = gamepad2.right_bumper;
        return toggleStateHitServoRight;
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
        frontLeftMotor.setPower(xValue); //
        frontRightMotor.setPower(yValue); //

        backLeftMotor.setPower(xValue);
        backRightMotor.setPower(yValue);


        //this is for making the controller #2 beable to do the sliders
        float extendValueLeft = -gamepad2.left_stick_y;
        float extendValueRight = -gamepad2.right_stick_y;


        extendValueLeft = Range.clip(extendValueLeft, -1, 1);
        extendValueRight = Range.clip(extendValueRight, -1, 1);


        extendMotor1.setPower(extendValueLeft * 0.2);
        extendMotor2.setPower(extendValueRight * 0.2);


        if (toggleHitServoLeft() == true) {
            ct("Hit1", "Open");
            hit1.setPosition(1);
        } else {
            ct("Hit1", "Closed");
            hit1.setPosition(0.50);
        }

        if (toggleHitServoRight() == true) {
            ct("Hit2", "Open");
            hit2.setPosition(0);
        } else {
            ct("Hit2", "Closed");
            hit2.setPosition(0.50);

        }



        if (gamepad2.dpad_up) {
            //left up
            if(lift1Pos >= 1)
            {
                lift1Pos = 1;
                lift1.setPosition(lift1Pos);

            }
            else{

                lift1.setPosition(lift1Pos);
                lift1Pos = lift1Pos + 0.1;
            }

        }

        if (gamepad2.dpad_down) {
            //left down
            if(lift1Pos <= 0.6)
            {
                lift1Pos = 0.6;
                lift1.setPosition(lift1Pos);

            }
            else{

                lift1.setPosition(lift1Pos);
                lift1Pos = lift1Pos - 0.1;
            }
        }

        if (gamepad2.y) {
            //right up
            if(lift2Pos >= 0)
            {
                lift2Pos = 0;
                lift2.setPosition(lift2Pos);

            }
            else{

                lift2.setPosition(lift2Pos);
                lift2Pos = lift2Pos - 0.05;
            }
        }

        if (gamepad2.a) {
            //right down
            if(lift2Pos <= 0.2)
            {
                lift2Pos = 0.2;
                lift2.setPosition(lift2Pos);

            }
            else{

                lift2.setPosition(lift2Pos);
                lift2Pos = lift2Pos + 0.05;
            }
        }


        ct("Left Arm", extendMotor1.getCurrentPosition());
        ct("Right Drive", extendMotor2.getCurrentPosition());

        ct("Front Left", frontLeftMotor.getCurrentPosition()); //no incoder yet
        ct("Front Right", frontRightMotor.getCurrentPosition()); //no incoder yet
        ct("Back Left", backLeftMotor.getCurrentPosition());
        ct("Back Right", backRightMotor.getCurrentPosition()); //no incoder nyet

        ct("lift1Pos", lift1Pos);
        ct("lift2Pos", lift2Pos);
    }
}

