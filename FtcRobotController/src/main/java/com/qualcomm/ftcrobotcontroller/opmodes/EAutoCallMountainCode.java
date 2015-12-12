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

    long StartTime;
    long TimeElapsed;

    enum mountainStates {beginning, forwardDrive, climbing, stop}

    mountainStates state;

    public EAutoCallMountainCode(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, DcMotor extendMotor1, DcMotor extendMotor2, Telemetry telemetry) {
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

    public void ct(String what, String text) //Debug text single line. Usefull for printing Estate changes
    {
        telemetry.addData(what, text);
    }



    public void init() {

        extendMotor1.setPower(0);
        extendMotor2.setPower(0);
        StartTime = System.currentTimeMillis();
        state = mountainStates.beginning;

    }




    public void loop() {
        TimeElapsed = System.currentTimeMillis() - StartTime;
        if (TimeElapsed>6300)
        {
            state = mountainStates.stop;
        }
        else if (TimeElapsed>6000)
        {
            extendMotor2.setPower(.55);
            extendMotor1.setPower(.5);
            //frontLeftMotor.setPower(0);
            //frontRightMotor.setPower(0);
            //backLeftMotor.setPower(0);
            //backRightMotor.setPower(0);
        }
        else if (TimeElapsed > 3000) {
            state = mountainStates.climbing;
        }





        switch (state) {
            case beginning:
                DoBeginning();
                break;
            case forwardDrive:
                DoForwardDrive();
                break;
            case climbing:
                DoClimbing();
                break;
            case stop:
                DoStop();
                break;
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

    void DoForwardDrive() {
        telemetry.addData("State: ", "Forward Drive");
    }

    void DoStop()
    {
        telemetry.addData("State: ", "Stop");
        extendMotor1.setPower(0);
        extendMotor2.setPower(0);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    void DoClimbing() {
        extendMotor2.setDirection(DcMotor.Direction.FORWARD);
       extendMotor1.setDirection(DcMotor.Direction.REVERSE);
       extendMotor2.setPower(.5);
        extendMotor1.setPower(.55);


        telemetry.addData("State: ", "Climbing");


    }
}
