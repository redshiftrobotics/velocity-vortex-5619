package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Madeline Byrne on 11/15/2015.
 */
public class MountainAutoState extends EOpModeBase
{
    final static double errorMarginWheels = 300;

    enum mountainStates {begining, stalledWheels, forwardDrive, climbing, badState}
    mountainStates state;

    int previousFrontLeftMotorPosition;
    int currentFrontLeftMotorPosition;

    int previousFrontRightMotorPosition;
    int currentFrontRightMotorPosition;


    //static final int errorMarginWheels = GET VALUES BASE ON RANGE
    boolean WheelsStalled(int currentPosition, int lastPosition)
    {

        double UpperWheelErrorMargin = lastPosition + errorMarginWheels;
        double LowerWheelErrorMargin = lastPosition - errorMarginWheels;


        if (currentPosition>LowerWheelErrorMargin && currentPosition<UpperWheelErrorMargin )
        {
            return true;
        }
        else return false;
    }


    mountainStates getState()
    {
        //checking if motors and arms don't have power
        if (frontRightMotor.getPower() == 0 && frontLeftMotor.getPower() ==0 && backRightMotor.getPower() == 0 && backLeftMotor.getPower() == 0 && extendMotor1.getPower() == 0 && extendMotor2.getPower() == 0)
        {
            //if neither have power set Estate to beginning
            return mountainStates.begining;
        }
        //if motors have forward power
        else if (frontLeftMotor.isBusy() && frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy())
        {

            //if motors are stalling
            if (WheelsStalled(currentFrontLeftMotorPosition, previousFrontLeftMotorPosition ) && WheelsStalled(currentFrontRightMotorPosition, previousFrontRightMotorPosition))
            {
                //if arms have power
                if( extendMotor1.isBusy() && extendMotor2.isBusy())
                {
                    return mountainStates.climbing;
                }
                //if arms don't have power
                else
                {
                    return mountainStates.stalledWheels;
                }
            }
            //if motors aren't stalling
            else
            {
                //if arms have power
                if( extendMotor1.isBusy() && extendMotor2.isBusy())
                {
                    return mountainStates.climbing;
                }
                //if the arms dont have power
                else return mountainStates.forwardDrive;
            }
        }

        //check if motors have power and arms dont
        else if(frontLeftMotor.isBusy()&& frontRightMotor.isBusy() && backLeftMotor.isBusy() && backRightMotor.isBusy() && extendMotor1.getPower() == 0 && extendMotor2.getPower() == 0)
        {
            return mountainStates.forwardDrive;
        }
        //something is fucked up, basically an error  message
        else
        {
            telemetry.addData("front left motor power: ", Double.toString(frontLeftMotor.getPower()));
            telemetry.addData("front right motor power: ", Double.toString(frontRightMotor.getPower()));
            telemetry.addData("back left motor power: ", Double.toString(backLeftMotor.getPower()));
            telemetry.addData("back right motor power: ", Double.toString(backRightMotor.getPower()));
            return mountainStates.badState;
        }

    }

    @Override
    public void init()
    {
        super.init();
        frontLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backLeftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        backRightMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        telemetry.addData("State: ", "Begining");
        //TEST THESE VALUES
        frontLeftMotor.setPower(.1);
        //telemetry.addData("front left motor power: ", frontLeftMotor.getPower());

        frontRightMotor.setPower(.1);
        //telemetry.addData("front right motor power: ", frontRightMotor.getPower());

        backLeftMotor.setPower(.1);
        //telemetry.addData("back left motor power: ", backLeftMotor.getPower());

        backRightMotor.setPower(.1);
        //telemetry.addData("back right motor power: ", backRightMotor.getPower());
        try
        {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public void loop()
    {
//        currentFrontLeftMotorPosition = frontLeftMotor.getCurrentPosition();
//        currentFrontRightMotorPosition = frontRightMotor.getCurrentPosition();

//        mountainStates Estate = getState();
//        switch (Estate)
//        {
//            case begining:
//                DoBeginning();
//                break;
//            case forwardDrive:
//                DoForwardDrive();
//                break;
//            case stalledWheels:
//                DoStalledWheels();
//                break;
//            case climbing:
//                DoClimbing();
//                break;
//            case badState:
//                DoBadState();
//                break;
//        }

    }

    void DoBeginning()
    {
        telemetry.addData("State: ", "Begining");
        //TEST THESE VALUES
        frontLeftMotor.setPower(.1);
        //telemetry.addData("front left motor power: ", frontLeftMotor.getPower());

        frontRightMotor.setPower(.1);
        //telemetry.addData("front right motor power: ", frontRightMotor.getPower());

        backLeftMotor.setPower(.1);
        //telemetry.addData("back left motor power: ", backLeftMotor.getPower());

        backRightMotor.setPower(.1);
        //telemetry.addData("back right motor power: ", backRightMotor.getPower());
        try
        {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    void DoForwardDrive()
    {
        telemetry.addData("State: ", "Forward Drive");
    }

    void DoStalledWheels()
    {
        telemetry.addData("State: ", "Wheels Stalled");
        //TEST THESES VALUSES
        extendMotor1.setPower(.8);
        extendMotor2.setPower(.8);
        telemetry.addData("Arm Power: ", "80%");
        frontRightMotor.setPower(.3);
        frontLeftMotor.setPower(.8);
        backRightMotor.setPower(.3);
        backLeftMotor.setPower(.3);
        telemetry.addData("Wheel Power: ", "30%");
    }

    void DoClimbing()
    {
        telemetry.addData("State: ", "Climbing");
        //figure out stragegy for second bar
    }

    void DoBadState()
    {
        telemetry.addData("State: ", "Bad State - ERROR");
        //WTF IS MY RECOVERY STRATEGY - TALK TO TEAM
    }
}
