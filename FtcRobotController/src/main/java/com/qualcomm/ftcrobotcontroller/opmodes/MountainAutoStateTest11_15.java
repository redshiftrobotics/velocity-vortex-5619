package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Madeline Byrne on 11/16/2015.
 */
public class MountainAutoStateTest11_15 extends EOpModeBase
{
    enum mountainStates {begining, stalledWheels, forwardDrive, climbing, badState}
    mountainStates state;

    int previousBackLeftMotorPosition;
    int currentBackLeftMotorPosition;

    int previousBackRightMotorPosition;
    int currentBackRightMotorPosition;

    static final double errorMarginWheels = 1;

    boolean WheelsStalled(int currentPosition, int lastPosition)
    {
//        double UpperWheelErrorMargin = lastPosition + errorMarginWheels;
//        double LowerWheelErrorMargin = lastPosition - errorMarginWheels;

            return false;
//        if (currentPosition>LowerWheelErrorMargin && currentPosition<UpperWheelErrorMargin )
//        {
//            return true;
//        }
//        else return false;
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
        else if (frontLeftMotor.getPower() > 0 && frontRightMotor.getPower() >0 && backLeftMotor.getPower() > 0 && backRightMotor.getPower() > 0)
        {

            //if motors are stalling
            if (WheelsStalled(currentBackLeftMotorPosition, previousBackLeftMotorPosition ) && WheelsStalled(currentBackRightMotorPosition, previousBackRightMotorPosition))
            {
                return mountainStates.stalledWheels;
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

        else if (backLeftMotor.getPower() >0 && WheelsStalled(currentBackLeftMotorPosition,previousBackLeftMotorPosition))
        {
            frontLeftMotor.setPower(-.1);
            frontRightMotor.setPower(-.1);
            backLeftMotor.setPower(-.1);
            backRightMotor.setPower(-.1);

            backRightMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(.1);
            backLeftMotor.setPower(0);

            return mountainStates.forwardDrive;
        }

        else if (frontRightMotor.getPower() >0 && WheelsStalled(currentBackRightMotorPosition, previousBackRightMotorPosition))
        {
            frontLeftMotor.setPower(-.1);
            frontRightMotor.setPower(-.1);
            backLeftMotor.setPower(-.1);
            backRightMotor.setPower(-.1);

            backRightMotor.setPower(.1);
            frontRightMotor.setPower(.1);
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            return mountainStates.forwardDrive;
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
        state = mountainStates.begining;
    }

    @Override
    public void loop()
    {
        previousBackLeftMotorPosition=currentBackLeftMotorPosition;
        previousBackRightMotorPosition=currentBackRightMotorPosition;

        currentBackLeftMotorPosition = backLeftMotor.getCurrentPosition();
        currentBackRightMotorPosition = backRightMotor.getCurrentPosition();

        mountainStates state = getState();
        switch (state)
        {
            case begining:
                DoBeginning();
                telemetry.addData("State: ", "Begining");
                break;
            case forwardDrive:
                DoForwardDrive();
                telemetry.addData("State: ", "Forward Drive");
                break;
            case stalledWheels:
                telemetry.addData("State: ", "Wheels Stalled");
                DoStalledWheels();
                break;
            case climbing:
                DoClimbing();
                telemetry.addData("State: ", "Climbing");
                break;
            case badState:
                telemetry.addData("State: ", "Bad State - ERROR");
                DoBadState();
                break;
        }


    }

    void DoBeginning()
    {
        telemetry.addData("State: ", "Begining");
        //TEST THESE VALUES
        frontLeftMotor.setPower(.2);
        frontRightMotor.setPower(.2);
        backLeftMotor.setPower(.2);
        backRightMotor.setPower(.2);
        telemetry.addData("Wheel Power: ", "4%");

    }

    void DoForwardDrive()
    {
        telemetry.addData("State: ", "Forward Drive");
    }

    void DoStalledWheels()
    {
        telemetry.addData("State: ", "Wheels Stalled");
        telemetry.addData("Wheels Stalled back left: current ", currentBackLeftMotorPosition);
        telemetry.addData(" wheel stalled back left previous: ", previousBackLeftMotorPosition);
        telemetry.addData("wheel stalled back right: current", currentBackRightMotorPosition );
        telemetry.addData("wheel stalled back right: previous", previousBackRightMotorPosition);
        //TEST THESES VALUSES
        extendMotor1.setPower(-.5);
        extendMotor2.setPower(-.5);
    }

    void DoClimbing()
    {
        telemetry.addData("State: ", "Climbing");
        //figure out stragegy for second bar
    }

    void DoBadState()
    {
        telemetry.addData("State: ", "Bad State - ERROR (we are fucked)");
        //WTF IS MY RECOVERY STRATEGY - TALK TO TEAM
    }
}
