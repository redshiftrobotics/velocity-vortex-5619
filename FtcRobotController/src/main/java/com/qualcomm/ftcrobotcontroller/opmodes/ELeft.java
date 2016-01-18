package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Eric Golde on 1/16/2016.
 */
public class ELeft extends EOpModeBaseTank {
    final double leftAndRightMotorPower = 0.2; //DO NOT EDIT
    final double amountToTurnTheMotorsWhenTurningToGetToTheBottomOfTheMountian = 0.3; //DO NOT EDIT
    @Override
    public void init()
    {
        super.init();
        dt("Left");
    }
    @Override
    public void loop()
    {
        left.setPower(leftAndRightMotorPower * amountToTurnTheMotorsWhenTurningToGetToTheBottomOfTheMountian);
        right.setPower(leftAndRightMotorPower);
    }
}
