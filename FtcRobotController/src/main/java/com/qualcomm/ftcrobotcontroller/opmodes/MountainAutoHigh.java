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

<<<<<<< HEAD
//        if (TimeElapsed> SET THIS FUCKING TIME)
//        {
//            state = mountainStates.extendArms;
//        }
=======
        if (TimeElapsed> /*SET THIS FUCKING TIME*/0)
        {
            state = mountainStates.extendArms;
        }
>>>>>>> e0dad5072e6530c8bcdb1bd57660ca9cd4295b70

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
<<<<<<< HEAD
        //TEST THIS SHIT^^^^
        //EXTEND ARMS -- ASK ERIC ABOUT VALUES AND SHIT
=======
        /*
        TEST THIS SHIT^^^^
        EXTEND ARMS -- ASK ERIC ABOUT VALUES AND SHIT
        */
>>>>>>> e0dad5072e6530c8bcdb1bd57660ca9cd4295b70
    }

    void DoCatchArmOnBar()
    {
    //lower arms
<<<<<<< HEAD
=======
        /*
        FIGURE OUT HOW TO SENSE IF WE HIT THE BAR
        */
>>>>>>> e0dad5072e6530c8bcdb1bd57660ca9cd4295b70
    }

    void DoPullUp()
    {

    }

    void DoStop()
    {

    }
}
