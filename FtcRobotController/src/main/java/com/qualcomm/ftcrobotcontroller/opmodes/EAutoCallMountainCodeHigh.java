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

    final int ENCODER_CPR = 1120; //ANDY MARK MOTOR DONT CHANGE
    final double TAPE_MEASURE_INCH_PER_ROTATION = 5.25;


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

    enum mountainStates {beginning, forwardDrive, extendArms, catchArmOnBar, pullUp, pullingUp, stop, extendingArms}
    mountainStates state;

    boolean armsOut = false;


    long StartTime;
    long TimeElapsed;
//

    public void init()
    {
        dt("STARTING MADDYS OP MODE");
        //StartTime = System.currentTimeMillis();
        state = mountainStates.beginning;

    }

    public void loop()
    {

//        TimeElapsed = System.currentTimeMillis() - StartTime;
//
//
//        if (!armsOut && TimeElapsed> /*SET THIS FUCKING TIME*/0)
//        {
//            Estate = mountainStates.extendArms;
//            armsOut = true;
//        }
//

        switch (state)
        {
            case beginning:
                DoBeginning();
                break;
//            case forwardDrive:
//                DoForwardDrive();
//                break;
            case extendArms:
                DoExtendArms();
                break;
            case extendingArms:
                DoExtendingArms();
                break;
            case catchArmOnBar:
                DoCatchArmOnBar();
                break;
            case pullUp:
                DoPullUp();
                break;
            case pullingUp:
                DoPullingUp();
                break;
            case stop:
                DoStop();
                break;
        }
    }

    void DoBeginning()
    {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        telemetry.addData("State: ", "Beginning");
        state = mountainStates.extendArms;
    }

   // void DoForwardDrive()
//    {
//        telemetry.addData("State: ", "Forward Drive");
//    }

    void DoExtendArms()
    {
        //SET ARMS TO GOT SLIGHTLY ABOVE CHURRO (TEST VALUES)
        //TEST THIS SHIT
        lift1.setPosition(.7);
        lift2.setPosition(.2);

        moveLeftArmBlahInches(48);
        moveRightArmBlahInches(48);

        telemetry.addData("State: ", "Extend Arms");


        state= mountainStates.extendingArms;
    }

    void DoExtendingArms()
    {
        if (getLeftTapePos()>= 48 && getRightTapePos()>= 48)
        {
            state = mountainStates.catchArmOnBar;
        }
        telemetry.addData("State: ", "Extending Arms Currently");
    }

    void DoCatchArmOnBar()
    {
        //SET ARMS SLOWIY DOWN TILL THE CATCH CHURROS (TEST VALUES)
        //TEST THESE FUCKING VALUES
        lift1.setPosition(.6);
        lift2.setPosition(.3);
        //maybe add bool to check the postition???
        telemetry.addData("State: ", "Catch on arm bar");
        state =  mountainStates.pullUp;

    }

    void DoPullUp()
    {
        //pull up to (midzone??)
        //POSSIBLY CHANGE WHEEL SPEED/POWER
        moveRightArmBlahInches(-48);
        moveLeftArmBlahInches(-48);

        telemetry.addData("State:", "Do Pull Up");

        state = mountainStates.pullingUp;
    }

    void DoPullingUp()
    {
        telemetry.addData("State:", "Currently Doing Pull Up");
        if(getLeftTapePos()<= 5 && getRightTapePos()<= 5)
        {
            state = mountainStates.stop;
        }
    }

    void DoStop()
    {

        telemetry.addData("State: ", "Stopped");
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        extendMotor1.setPower(0);
        extendMotor2.setPower(0);
    }


    public void moveLeftArmBlahInches(double inchesToMove) {
        double count = extendMotor1.getCurrentPosition() + inchesToMove / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR;

        if (count > 0) {
            extendMotor1.setPower(1);
            while (extendMotor1.getCurrentPosition() < count) {

            }
            extendMotor1.setPower(0);
        } else {
            extendMotor1.setPower(-1);
            while (extendMotor1.getCurrentPosition() > count) {

            }
            extendMotor1.setPower(0);
        }
    }

    public void moveRightArmBlahInches(double inchesToMove) {
        double count = extendMotor2.getCurrentPosition() + inchesToMove / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR;

        if (count > 0) {
            extendMotor2.setPower(1);
            while (extendMotor2.getCurrentPosition() < count) {

            }
            extendMotor2.setPower(0);
        } else {
            extendMotor2.setPower(-1);
            while (extendMotor2.getCurrentPosition() > count) {

            }
            extendMotor2.setPower(0);
        }


    }



    public void resetArmLeft() {
        double count = 0;
        extendMotor1.setPower(-1);
        while (extendMotor1.getCurrentPosition() > count) {

        }
        extendMotor1.setPower(0);
    }

    public void resetArmRight() {
        double count = 0;
        extendMotor2.setPower(-1);
        while (extendMotor2.getCurrentPosition() > count) {

        }
        extendMotor2.setPower(0);
    }



    public void resetArmHeightLeft() {

        lift1.setPosition(0.6);
    }

    public void resetArmHeightRight() {

        lift2.setPosition(0.5);
    }

    public double getLeftTapePos() {

        return extendMotor1.getCurrentPosition() / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR;
    }

    public double getRightTapePos() {

        return extendMotor2.getCurrentPosition() / TAPE_MEASURE_INCH_PER_ROTATION * ENCODER_CPR;
    }
}
