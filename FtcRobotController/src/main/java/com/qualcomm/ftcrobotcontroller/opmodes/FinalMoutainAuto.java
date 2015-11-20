package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Madeline Byrne on 11/19/2015.
 */
public class FinalMoutainAuto extends EOpModeBase
{
    Long StartTime;
    long TimeElapsed;
    enum mountainStates {beginning, forwardDrive, climbing}
    mountainStates state;

    @Override
    public void init()
    {
        super.init();
        StartTime = System.currentTimeMillis();
        state = mountainStates.beginning;
    }


    @Override
    public void loop()
    {
        TimeElapsed = System.currentTimeMillis()-StartTime;

        if(TimeElapsed> 3000)
        {
            state = mountainStates.climbing;
        }

        switch (state)
        {
            case beginning:
                DoBeginning();
                break;
            case forwardDrive:
                DoForwardDrive();
                break;
            case climbing:
                DoClimbing();
        }
    }

    void DoBeginning() {
        telemetry.addData("State: ", "Begining");
        //TEST THESE VALUES
        frontLeftMotor.setPower(.2);
        frontRightMotor.setPower(.2);
        backLeftMotor.setPower(.2);
        backRightMotor.setPower(.2);
        telemetry.addData("Wheel Power: ", "20%");
        state = mountainStates.forwardDrive;

    }

    void DoForwardDrive()
    {
        telemetry.addData("State: ", "Forward Drive");
    }

    void DoClimbing()
    {
        extendMotor2.setDirection(DcMotor.Direction.FORWARD);
        extendMotor1.setDirection(DcMotor.Direction.REVERSE);
        extendMotor2.setPower(.3);
        extendMotor1.setPower(.3);
        telemetry.addData("State: ", "Climbing");
    }
}
