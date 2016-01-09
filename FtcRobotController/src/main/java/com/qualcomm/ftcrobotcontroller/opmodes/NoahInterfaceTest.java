package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Eric Golde on 1/5/2016.
 */

//this is test code for noah using interfaces to controll the robot :D
public class NoahInterfaceTest {

    final int ENCODER_CPR = 1120; //ANDY MARK MOTOR DONT CHANGE
    final double TANK_MM_PER_ROTATION = 5.25;

    protected DcMotor leftMotor; //FRONT LEFT
    protected DcMotor rightMotor; //FRONT RIGHT
    protected Telemetry telemetry;
    protected double leftMotorPos;
    protected double rightMotorPos;

    public NoahInterfaceTest(DcMotor LeftMotor, DcMotor rightMotor, Telemetry telemetry) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.telemetry = telemetry;
    }

    public void log(String msg)
    {
        Log.i("Info: ", msg);
    }

    public void update()
    {
        //not working atm
        //fLMPos = fLM.getCurrentPosition() + inchesToMove / TANK_MM_PER_ROTATION * ENCODER_CPR;
        //update motor pos and convert it to MM
        //fLMPos = fLM.getCurrentPosition() / TANK_MM_PER_ROTATION * ENCODER_CPR;
        //fRMPos = fRM.getCurrentPosition() / TANK_MM_PER_ROTATION * ENCODER_CPR;
        //bLMPos = fLM.getCurrentPosition() / TANK_MM_PER_ROTATION * ENCODER_CPR;
        //bRMPos = bRM.getCurrentPosition() / TANK_MM_PER_ROTATION * ENCODER_CPR;

        leftMotorPos = leftMotor.getCurrentPosition();
        rightMotorPos = rightMotor.getCurrentPosition();
    }

    public void setupAllEncodersAndDirectionStuffAndThings()
    {
        log("Starting setting up all motors");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);


        leftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        log("Done.");
    }

    public double getEncoderPosOnLeftSide()
    {
        update();
        return leftMotorPos;
    }

    public double getEncoderPosOnRightSide()
    {
        update();
        return rightMotorPos;
    }


    public void turnLeft(double en)
    {
        stopAll();
        update();
        while(en < getEncoderPosOnLeftSide())
        {
            leftMotor.setPower(1);
            //log
        }
        stopAll();
    }

    public void turnRight(double en)
    {
        stopAll();
        update();
        while(en < getEncoderPosOnRightSide())
        {
            rightMotor.setPower(1);
            //log
        }
        stopAll();
    }

    public void goForward(double en) {
        stopAll();
        update();
        while(en + getEncoderPosOnLeftSide() <= getEncoderPosOnLeftSide() || en + getEncoderPosOnRightSide() <= getEncoderPosOnRightSide())
        {
            leftMotor.setPower(1);
            rightMotor.setPower(1);
            //log
        }
        stopAll();
    }

    public void goBackward(double en)
    {
        stopAll();
        update();
        while(en - getEncoderPosOnLeftSide() >= getEncoderPosOnLeftSide() || en - getEncoderPosOnRightSide() >= getEncoderPosOnRightSide())
        {
            leftMotor.setPower(1);
            rightMotor.setPower(1);
            //log
        }
        stopAll();
    }

    public void stopAll()
    {
        log("STOP ALL CALLED");
        leftMotor.setPower(0);
        rightMotor.setPower(0);

    }

}
