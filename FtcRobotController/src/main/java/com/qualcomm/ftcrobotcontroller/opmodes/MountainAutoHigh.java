package com.qualcomm.ftcrobotcontroller.opmodes;

import android.text.format.Time;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Madeline Byrne on 12/2/2015.
 */
public class MountainAutoHigh extends EOpModeBase
{
    enum mountainStates {beginning, forwardDrive, extendArms, catchArmOnBar, pullUp, stop}
    mountainStates state;

    long StartTime;
    long TimeElapsed;

    @Override
    public void init()
    {
        StartTime = System.currentTimeMillis();
        state = mountainStates.forwardDrive;
    }
    @Override
    public void loop()
    {

        TimeElapsed = System.currentTimeMillis() - StartTime;

        if (TimeElapsed> /*SET THIS FUCKING TIME*/0)
        {
            state = mountainStates.extendArms;
        }

        switch (state)
        {
            case beginning:
                DoBeginning();
                break;
            case forwardDrive:
                DoForwardDrive();
                break;
            case extendArms:
                DoExtendArms();
                break;
            case catchArmOnBar:
                DoCatchArmOnBar();
                break;
            case pullUp:
                DoPullUp();
                break;
            case stop:
                DoStop();
                break;
        }
    }

    void DoBeginning()
    {
        frontLeftMotor.setPower(.2);
        frontRightMotor.setPower(.2);
        backLeftMotor.setPower(.2);
        backRightMotor.setPower(.2);
        telemetry.addData("State: ", "Beginning");
        state = mountainStates.forwardDrive;
    }

    void DoForwardDrive()
    {
        telemetry.addData("State: ", "Forward Drive");
    }

    void DoExtendArms()
    {
        hit1.setPosition(.5);
        hit2.setPosition(.5);
        /*
        TEST THIS SHIT^^^^
        EXTEND ARMS -- ASK ERIC ABOUT VALUES AND SHIT
        */
    }

    void DoCatchArmOnBar()
    {
    //lower arms
        /*
        FIGURE OUT HOW TO SENSE IF WE HIT THE BAR
        */
    }

    void DoPullUp()
    {

    }

    void DoStop()
    {

    }
}
