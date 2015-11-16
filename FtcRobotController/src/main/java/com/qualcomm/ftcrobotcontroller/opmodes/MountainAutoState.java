package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Madeline Byrne on 11/15/2015.
 */
public class MountainAutoState extends EOpModeBase
{
    enum mountainStates {begining, stalledWheels, forwardDrive, climbing, badState}
    mountainStates state;

    int previousFrontLeftMotorPosition;
    int currentFrontLeftMotorPosition;

    int previousFrontRightMotorPosition;
    int currentFrontRightMotorPosition;
    

    static final int errorMarginWheels = insert value BASED ON motor position value range;
    boolean WheelsStalled(int currentPosition, int lastPosition)
    {

        int UpperWheelErrorMargin = lastPosition + errorMarginWheels;
        int LowerWheelErrorMargin = lastPosition - errorMarginWheels;


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
            //if neither have power set state to beginning
            return mountainStates.begining;
        }
        //if motors have forward power
        else if (frontLeftMotor.getPower() > 0 && frontRightMotor.getPower() >0 && backLeftMotor.getPower() > 0 && backRightMotor.getPower() > 0)
        {

            //if motors are stalling
            if (WheelsStalled(currentFrontLeftMotorPosition, previousFrontLeftMotorPosition ) && WheelsStalled(currentFrontRightMotorPosition, previousFrontRightMotorPosition))
            {
                //if arms have power
                if( extendMotor1.getPower() >0 && extendMotor2.getPower() >0)
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
                if( extendMotor1.getPower() >0 && extendMotor2.getPower() >0)
                {
                    return mountainStates.climbing;
                }
                //if the arms dont have power
                else return mountainStates.forwardDrive;
            }
        }

        //check if motors have power and arms dont
        else if(frontLeftMotor.getPower() > 0 && frontRightMotor.getPower() >0 && backLeftMotor.getPower() > 0 && backRightMotor.getPower() > 0 && extendMotor1.getPower() == 0 && extendMotor2.getPower() == 0)
        {
            return mountainStates.forwardDrive;
        }
        //something is fucked up, basically an error  message
        else return mountainStates.badState;
    }

    @Override
    public void init()
    {
        super.init();
    }


    @Override
    public void loop()
    {
        currentFrontLeftMotorPosition = frontLeftMotor.getCurrentPosition();
        currentFrontRightMotorPosition = frontRightMotor.getCurrentPosition();




        mountainStates state = getState();
        switch (state)
        {
            case begining:
                DoBeginning();
                break;
            case forwardDrive:
                DoForwardDrive();
                break;
            case stalledWheels:
                DoStalledWheels();
                break;
            case climbing:
                DoClimbing();
                break;
            case badState:
                DoBadState();
                break;
        }

    }

    void DoBeginning()
    {
        telemetry.addData("State: ", "Begining");
        TEST THESE VALUES
        frontLeftMotor.setPower(.2);
        frontRightMotor.setPower(.2);
        backLeftMotor.setPower(.2);
        backRightMotor.setPower(.2);
        telemetry.addData("Wheel Power: ", "20%");

    }

    void DoForwardDrive()
    {
        telemetry.addData("State: ", "Forward Drive");
    }

    void DoStalledWheels()
    {
        telemetry.addData("State: ", "Wheels Stalled");
        TEST THESES VALUSES
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
