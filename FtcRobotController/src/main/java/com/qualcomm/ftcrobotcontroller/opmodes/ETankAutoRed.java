package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 1/9/2016.
 */
public class ETankAutoRed extends EOpModeBaseTank { //red team autonomous mode //left
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final int encoderPosToDriveToBottomOnTheMountian = 5800; //edit //maddy edit this number to change turning
    private final int encoderPosToDriveUpToTheMidZone = encoderPosToDriveToBottomOnTheMountian + 6000; //edit


    private final double leftAndRightMotorPower = 0.5; //DO NOT EDIT
    private final double amountToTurnTheMotorsWhenTurningToGetToTheBottomOfTheMountian = 0.2; //DO NOT EDIT
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private int state;
    private final int STATE_TURN_ONTO_MOUNTIAN = 1;
    private final int STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR = 2;

    public void updateTelementryMotorOutput() {
        ct("Left Encoder", left.getCurrentPosition());
        ct("Right Encoder", right.getCurrentPosition());
        ct("Arm Encoder", arm.getCurrentPosition());
        if (state == STATE_TURN_ONTO_MOUNTIAN) {
            ct("ToGetTo", encoderPosToDriveToBottomOnTheMountian);
        } else if (state == STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR) {
            ct("ToGetTo", encoderPosToDriveToBottomOnTheMountian + encoderPosToDriveUpToTheMidZone);
        }
    }

    public void stopAll() {
        left.setPower(0);
        right.setPower(0);
        arm.setPower(0);
        ct("State", "stopAll");
    }

    public void stopDriveMotors() {
        left.setPower(0);
        right.setPower(0);
        ct("State", "stopDriveMotors");
    }

    @Override
    public void init() {
        dt("Red Team Autonomous Selected!");
        super.init();
        setupEncoderStuffAndThings();
    }

    @Override
    public void start() {


        startTurnOntoMountian();
    }

    @Override
    public void loop() {
        updateTelementryMotorOutput();
        if (state == STATE_TURN_ONTO_MOUNTIAN) {
            loopTurnOntoMountian();
        } else if (state == STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR) {
            //run loop for getting onto first bar
            loopTryToGetToTheFirstBar();
        }
    }

    public void setupEncoderStuffAndThings() {
        //set up all encoders before startung up the robot
        //this needs to be in the start
        //it will not work in the init for some reason
        //this makes all the motors work properly

        dt("Setting up encoders...");
        left.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        right.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        left.setTargetPosition(encoderPosToDriveToBottomOnTheMountian);
        right.setTargetPosition(encoderPosToDriveToBottomOnTheMountian);
        //arm.setTargetPosition(/*Encoder pos for autonoums arm. I dont think we need this but it is here*/);

        left.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        right.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        arm.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        left.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        right.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        dt("Done!");

    }

    public void startTurnOntoMountian() {
        state = STATE_TURN_ONTO_MOUNTIAN;
        ct("State", "STATE_DO_CURVE");

        setupEncoderStuffAndThings();// before we do anything else set up the encoders

        left.setPower(leftAndRightMotorPower * amountToTurnTheMotorsWhenTurningToGetToTheBottomOfTheMountian);
        right.setPower(leftAndRightMotorPower);

    }

    public void loopTurnOntoMountian() {
        updateTelementryMotorOutput();

        if (right.getCurrentPosition() >= encoderPosToDriveToBottomOnTheMountian) {
            //at bottom of the mountian!
            stopDriveMotors();
            startTryToGetOntoTheFirstBar();
        }
    }

    public void startTryToGetOntoTheFirstBar() {
        state = STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR;
        ct("State", "STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR");
        //drive motors slowly

        left.setPower(.4);
        right.setPower(.4);
    }

    public void loopTryToGetToTheFirstBar() {
        updateTelementryMotorOutput();

        if (right.getCurrentPosition() >= encoderPosToDriveUpToTheMidZone) {
            //In the mid zone
            //stop everything
            stopAll(); //stop motors
            ct("State", "IN_THE_MID_ZONE");
            //stop(); //"ftc stop" THIS MAY NOT WORK. USE OWN STOP FUNCTION
        }

    }

}
