package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Eric Golde on 1/9/2016.
 */
public class EDebug extends EOpModeBaseTank { //blue team autonomous mode //right
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    final int encoderPosToDriveToBottomOnTheMountian = 8400; //edit
    final int encoderPosToDriveUpToTheMidZone = encoderPosToDriveToBottomOnTheMountian + 6000; //edit
    final double leftAndRightMotorPowerDivider = 1; //edit

    final double leftAndRightMotorPower = 0.2; //DO NOT EDIT
    final double amountToTurnTheMotorsWhenTurningToGetToTheBottomOfTheMountian = 0.3; //DO NOT EDIT
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    int state;
    final int STATE_TURN_ONTO_MOUNTIAN = 1;
    final int STATE_TRY_TOO_GET_OVER_THE_FIRST_BAR = 2;

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
        dt("DEBUG --turn right");

        super.init();

    }

    @Override
    public void start() {

        left.setPower(1);
        right.setPower(0);

    }

    @Override
    public void loop() {
        updateTelementryMotorOutput();

    }
}


