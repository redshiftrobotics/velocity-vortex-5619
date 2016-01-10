package com.qualcomm.ftcrobotcontroller.opmodes;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Eric Golde on 10/17/2015.
 */
public class ETHigh extends EOpModeBase {

    //
    double lift1Pos = 0.6;
    double lift2Pos = 0.35;


    boolean leftTriggerPushed = false;
    boolean rightTriggerPushed = false;

    @Override
    public void init() {
        dt("HIGH Tank Drive Selected!");
        tts("Ready to drive!");




        super.init(); //calls the init funtion in EOpModeBase.class

        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        resetArmLeft();
        resetArmRight();
        resetArmHeightLeft();
        resetArmHeightRight();
        resetHitLeft();
        resetHitRight();

        lift1.setPosition(lift1Pos);
        lift2.setPosition(lift2Pos);
        //hit1.setPosition(1);
        //hit2.setPosition(0.2);






    }

    @Override
    public void start() {

        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);


        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        extendMotor1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        extendMotor2.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);



    }


    boolean lastBttnStateHitServoLeft = false;
    boolean toggleStateHitServoLeft = false;

    public boolean toggleHitServoLeft() {
        if (gamepad1.left_bumper && !lastBttnStateHitServoLeft) {
            toggleStateHitServoLeft = !toggleStateHitServoLeft;
        }
        lastBttnStateHitServoLeft = gamepad1.left_bumper;
        return toggleStateHitServoLeft;
    }

    boolean lastBttnStateHitServoRight = false;
    boolean toggleStateHitServoRight = false;

    public boolean toggleHitServoRight() {
        if (gamepad1.right_bumper && !lastBttnStateHitServoRight) {
            toggleStateHitServoRight = !toggleStateHitServoRight;
        }
        lastBttnStateHitServoRight = gamepad1.right_bumper;
        return toggleStateHitServoRight;
    }

    boolean lastBttnStateToggleSync = false;
    boolean toggleStateToggleSync = false;

    public boolean toggleSyncMotor() {
        if (gamepad2.right_bumper && !lastBttnStateToggleSync) {
            toggleStateToggleSync = !toggleStateToggleSync;
        }
        lastBttnStateToggleSync = rightTriggerPushed;
        return toggleStateToggleSync;
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


        double amountToSlowDownTheDrivingSpeed = 0.5;
        frontLeftMotor.setPower(xValue * amountToSlowDownTheDrivingSpeed); //
        frontRightMotor.setPower(yValue * amountToSlowDownTheDrivingSpeed); //

        backLeftMotor.setPower(xValue * amountToSlowDownTheDrivingSpeed);
        backRightMotor.setPower(yValue * amountToSlowDownTheDrivingSpeed);


        //this is for making the controller #2 beable to do the sliders
        float extendValueLeft = -gamepad2.left_stick_y;
        float extendValueRight = -gamepad2.right_stick_y;


        extendValueLeft = Range.clip(extendValueLeft, -1, 1);
        extendValueRight = Range.clip(extendValueRight, -1, 1);

        double amountToSlowDownTheArms = 0.5;






        if (toggleHitServoLeft() == false) {
            ct("Hit1", "Closed");
            hit1.setPosition(1);
        } else {
            ct("Hit1", "Open");
            hit1.setPosition(0.50);
        }

        if (toggleHitServoRight() == false) {
            ct("Hit2", "Closed");
            hit2.setPosition(0);
        } else {
            ct("Hit2", "Open");
            hit2.setPosition(0.50);

        }

        if (toggleSyncMotor() == false) {
            ct("Sync", "false");
            extendMotor1.setPower(extendValueLeft * amountToSlowDownTheArms);
            extendMotor2.setPower(extendValueRight * amountToSlowDownTheArms);

        } else {
            ct("Sync", "true");
            extendMotor1.setPower(extendValueLeft * amountToSlowDownTheArms);
            extendMotor2.setPower(extendValueLeft * amountToSlowDownTheArms);
        }


        if (gamepad2.dpad_up) {
            //left up
            if (lift1Pos >= 1) {
                lift1Pos = 1;
                lift1.setPosition(lift1Pos);

            } else {

                lift1.setPosition(lift1Pos);
                lift1Pos = lift1Pos + 0.01;
            }

        }

        if (gamepad2.dpad_down) {
            //left down
            if (lift1Pos <= 0.6) {
                lift1Pos = 0.6;
                lift1.setPosition(lift1Pos);

            } else {

                lift1.setPosition(lift1Pos);
                lift1Pos = lift1Pos - 0.01;
            }
        }

        if (gamepad2.y) {
            //right up
            if (lift2Pos <= 0) {
                lift2Pos = 0;
                lift2.setPosition(lift2Pos);

            } else {

                lift2.setPosition(lift2Pos);
                lift2Pos = lift2Pos - 0.01;
            }
        }

        if (gamepad2.a) {
            //right down
            if (lift2Pos >= 0.35) {
                lift2Pos = 0.35;
                lift2.setPosition(lift2Pos);

            } else {

                lift2.setPosition(lift2Pos);
                lift2Pos = lift2Pos + 0.01;
            }
        }


        ct("Left Arm", extendMotor1.getCurrentPosition());
        ct("Right Arm", extendMotor2.getCurrentPosition());

        ct("Front Left", frontLeftMotor.getCurrentPosition());
        ct("Front Right", frontRightMotor.getCurrentPosition());
        ct("Back Left", backLeftMotor.getCurrentPosition());
        ct("Back Right", backRightMotor.getCurrentPosition());

        ct("lift1Pos", lift1Pos);
        ct("lift2Pos", lift2Pos);

        if(gamepad2.left_trigger > 0)
        {
            leftTriggerPushed = true;
            ct("leftTriggerPushed", "true");
        }
        else
        {
            leftTriggerPushed = false;
            ct("leftTriggerPushed", "false");
        }

        if(gamepad2.right_trigger > 0)
        {
            //pushed
            rightTriggerPushed = true;
            ct("rightTriggerPushed", "true");
        }
        else
        {
            //not
            rightTriggerPushed = false;
            ct("rightTriggerPushed", "false");
        }
    }
}

