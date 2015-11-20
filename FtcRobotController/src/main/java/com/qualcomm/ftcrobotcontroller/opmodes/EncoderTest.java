package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Madeline Byrne on 11/16/2015.
 */
public class EncoderTest extends EOpModeBase
{
    double currentPosRight;
    double currentPosLeft;
    @Override
    public void init()
    {

        super.init();

    }

    @Override
    public void loop()
    {
        currentPosRight=backRightMotor.getCurrentPosition();
        currentPosLeft = backLeftMotor.getCurrentPosition();
        telemetry.addData("Current Pos Left:", currentPosLeft);
        telemetry.addData("Current Pos Right: ", currentPosRight);
    }

    void DoBeginning()
    {
        telemetry.addData("State: ", "Begining");
        //TEST THESE VALUES
        frontLeftMotor.setPower(.06);
        frontRightMotor.setPower(.06);
        backLeftMotor.setPower(.06);
        backRightMotor.setPower(.06);
        telemetry.addData("Wheel Power: ", "4%");

    }

}
