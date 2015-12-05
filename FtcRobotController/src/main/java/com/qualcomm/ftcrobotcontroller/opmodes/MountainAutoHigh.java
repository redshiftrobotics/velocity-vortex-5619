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


//        if (TimeElapsed> SET THIS FUCKING TIME)
//        {
//            state = mountainStates.extendArms;
//        }

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
        //SET ARMS TO GOT SLIGHTLY ABOVE CHURRO (TEST VALUES)
        lift1.setPosition(.5);
        lift2.setPosition(.5);

        moveLeftArmBlahInches(24);
        moveRightArmBlahInches(24);

        telemetry.addData("State: ", "Extend Arms");
        state = mountainStates.catchArmOnBar;
    }

    void DoCatchArmOnBar()
    {
        //SET ARMS SLOWIY DOWN TILL THE CATCH CHURROS (TEST VALUES)
        hit1.setPosition(.5);
        hit2.setPosition(.5);
        telemetry.addData("State: ", "Catch on arm bar");
        state =  mountainStates.pullUp;

    }

    void DoPullUp()
    {
        //pull up to (midzone??)
        moveRightArmBlahInches(-20);
        moveLeftArmBlahInches(-20);
    }

    void DoStop()
    {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        extendMotor1.setPower(0);
        extendMotor2.setPower(0);
    }
}
