package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 1/9/2016.
 */
public class ETankAutoBlue extends EOpModeBaseTank { //red team autonomous mode

    int state;
    final int encoderPosToDriveToBottomOnTheMountian = 1000; //edit
    final int encoderPosToExtendTapeMeasureToGrabTheBarAtTheBegining = 1000; //edit
    final double leftAndRightMotorPower = 0.5;
    final double amountToTurnTheMotorsWhenTurningToGetToTheBottomOfTheMountian = 0.4;
    final double armExtendPower = 1;
    final int STATE_TURN_ONTO_MOUNTIAN = 1;
    final int STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR = 2;

    public void updateTelementryMotorOutput()
    {
        ct("Left Encoder:", left.getCurrentPosition());
        ct("Right Encoder:", right.getCurrentPosition());
        ct("Arm Encoder:", arm.getCurrentPosition());


    }

    public void stopAll()
    {
        left.setPower(0);
        right.setPower(0);
        arm.setPower(0);
    }

    public void stopDriveMotors()
    {
        left.setPower(0);
        right.setPower(0);
    }

    @Override
    public void init() {
        dt("Red Team Autonomous Selected!");
        super.init();
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


    public void startTurnOntoMountian() {
        state = STATE_TURN_ONTO_MOUNTIAN;
        ct("State", "STATE_DO_CURVE");

        left.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        right.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);

        left.setTargetPosition(encoderPosToDriveToBottomOnTheMountian);
        right.setTargetPosition(encoderPosToDriveToBottomOnTheMountian);
        right.setTargetPosition(encoderPosToExtendTapeMeasureToGrabTheBarAtTheBegining);

        left.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        right.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        arm.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        left.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        right.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        arm.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        left.setPower(leftAndRightMotorPower);
        right.setPower(leftAndRightMotorPower * amountToTurnTheMotorsWhenTurningToGetToTheBottomOfTheMountian);

    }

    public void loopTurnOntoMountian()
    {
        updateTelementryMotorOutput();

        if(right.getCurrentPosition() >= encoderPosToDriveToBottomOnTheMountian )
        {
            //at bottom of the mountian!
            stopDriveMotors();
            startTryToGetOntoTheFirstBar();
        }
    }

    public void startTryToGetOntoTheFirstBar()
    {
        state = STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR;
        ct("State", "STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR");
        dt("Done!");
    }

    public void loopTryToGetToTheFirstBar()
    {
        updateTelementryMotorOutput();;
    }

}
