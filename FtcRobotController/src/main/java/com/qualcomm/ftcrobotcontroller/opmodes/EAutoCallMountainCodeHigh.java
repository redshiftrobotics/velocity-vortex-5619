package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Eric Golde on 12/10/2015.
 */
public class EAutoCallMountainCodeHigh {

    String teleConvert;
    int teleInt = 0;


    protected DcMotor frontLeftMotor; //FRONT LEFT
    protected DcMotor frontRightMotor; //FRONT RIGHT
    protected DcMotor backLeftMotor; //BACK LEFT
    protected DcMotor backRightMotor; //BACK RIGHT
    protected DcMotor extendMotor1; // arm1
    protected DcMotor extendMotor2; // arm2
    protected Servo lift1;
    protected Servo lift2;
    protected Telemetry telemetry;


    public EAutoCallMountainCodeHigh(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, DcMotor extendMotor1, DcMotor extendMotor2, Telemetry telemetry, Servo lift1, Servo lift2) {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;
        this.extendMotor1 = extendMotor1;
        this.extendMotor2 = extendMotor2;
        this.telemetry = telemetry;
        this.lift1 = lift1;
        this.lift2 = lift2;
    }

    public void dt(String text)
    {
        teleInt++;
        teleConvert = Integer.toString(teleInt);
        telemetry.addData(teleConvert, "[M] " + text);
    }

    public void ct(String what, String text)
    {
        telemetry.addData(what, text);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void init()
    {
        dt("Started!");

        //maddy's code goes here
    }

    public void loop()
    {

        //maddy's code goes here
    }
}
