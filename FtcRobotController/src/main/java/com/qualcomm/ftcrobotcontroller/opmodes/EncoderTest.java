package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Madeline Byrne on 11/16/2015.
 */
public class EncoderTest extends EOpModeBase
{
    double currentPos;
    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void loop()
    {
        currentPos=frontRightMotor.getCurrentPosition();
        telemetry.addData("Current position: ", currentPos);
    }



}
