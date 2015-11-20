package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Eric Golde on 11/18/2015.
 */
public class EAutoCallMountainCode {


    /*
   THIS IS A FILE TO HAVE MY 8 AUTONOMIS CODE CALL MountainAutoStateTest11_15
   THIS IS A PARTICAL OP MODE
    */

    protected DcMotor frontLeftMotor; //FRONT LEFT
    protected DcMotor frontRightMotor; //FRONT RIGHT
    protected DcMotor backLeftMotor; //BACK LEFT
    protected DcMotor backRightMotor; //BACK RIGHT
    protected DcMotor extendMotor1; // arm1
    protected DcMotor extendMotor2; // arm2
    protected Telemetry telemetry;

    String teleConvert;
    int teleInt = 0;

    public EAutoCallMountainCode(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, DcMotor extendMotor1, DcMotor extendMotor2, Telemetry telemetry)
    {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;
        this.extendMotor1 = extendMotor1;
        this.extendMotor2 = extendMotor2;
        this.telemetry = telemetry;
    }
    public void dt(String text) //Debug text multiline. Usefull for a lot of output debugging
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
        //toastShort(text); //this makes it so DT (Debug text Multiline) also uses toast. ONLY USE IF NESSASARRY
    }

    public void ct(String what, String text) //Debug text single line. Usefull for printing state changes
    {
        telemetry.addData(what, text);
    }


    public void init()
    {
        dt("Driving Up Mountain!");
        extendMotor1.setPower(0);
        extendMotor2.setPower(0);
    }


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

        try {
            Thread.sleep(100);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    enum mountainStates {begining, stalledWheels, forwardDrive, climbing, badState}
    mountainStates state;

    int previousBackLeftMotorPosition;
    int currentBackLeftMotorPosition;

    int previousBackRightMotorPosition;
    int currentBackRightMotorPosition;

    static final double errorMarginWheels = 300;

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
            //if neither have power set state to beginning
            return mountainStates.begining;
        }
        //if motors have forward power
        else if (frontLeftMotor.getPower() > 0 && frontRightMotor.getPower() >0 && backLeftMotor.getPower() > 0 && backRightMotor.getPower() > 0)
        {

            //if motors are stalling
            if (WheelsStalled(currentBackLeftMotorPosition, previousBackLeftMotorPosition ) && WheelsStalled(currentBackRightMotorPosition, previousBackRightMotorPosition))
            {
//                //if arms have power
//                if( extendMotor1.getPower() >0 && extendMotor2.getPower() >0)
//                {
//                    return mountainStates.climbing;
//                }
                //if arms don't have power
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
            try {
                Thread.sleep(500);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            backRightMotor.setPower(0);
            frontRightMotor.setPower(0);
            frontLeftMotor.setPower(.1);
            backLeftMotor.setPower(.1);
            try {
                Thread.sleep(500);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return mountainStates.forwardDrive;
        }

        else if (frontRightMotor.getPower() >0 && WheelsStalled(currentBackRightMotorPosition, previousBackRightMotorPosition))
        {
            frontLeftMotor.setPower(-.1);
            frontRightMotor.setPower(-.1);
            backLeftMotor.setPower(-.1);
            backRightMotor.setPower(-.1);
            try {
                Thread.sleep(500);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            backRightMotor.setPower(.1);
            frontRightMotor.setPower(.1);
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            try {
                Thread.sleep(500);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
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
        frontLeftMotor.setPower(.3);
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
        telemetry.addData("State: ", "Bad State - ERROR (we are fucked)");
        //WTF IS MY RECOVERY STRATEGY - TALK TO TEAM
    }

}
