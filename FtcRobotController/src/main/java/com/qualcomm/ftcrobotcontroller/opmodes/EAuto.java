package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Eric Golde on 10/5/2015.
 */
public class EAuto extends OpMode {
    String teleConvert;
    int teleInt = 3;

    DcMotor frontLeftMotor; //FRONT LEFT
    DcMotor frontRightMotor; //FRONT RIGHT
    DcMotor backLeftMotor; //BACK LEFT
    DcMotor backRightMotor; //BACK RIGHT
    DcMotor extendMotor1; // arm
    DcMotor extendMotor2; // arm
    Servo clamp1;
    Servo clamp2;
    Servo hit1;
    Servo hit2;


    public void dt(String text)
    {
        //make a new line
        teleInt++;
        //convert to string
        teleConvert = Integer.toString(teleInt);
        //print to console new line
        telemetry.addData(teleConvert, text);
    }
    public void ct(String what, String text)
    {
        telemetry.addData(what, text);
    }





    public void init() {
        dt("**DOES NOTHING**");
        dt("Autonomous Selected!");
        dt("Init Loading...");

        frontLeftMotor = hardwareMap.dcMotor.get("left1");
        frontRightMotor = hardwareMap.dcMotor.get("right1");
        backLeftMotor = hardwareMap.dcMotor.get("left2");
        backRightMotor = hardwareMap.dcMotor.get("right2");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        // backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        // frontRightMotor.setDirection(DcMotor.Direction.REVERSE); //CHANGED

        extendMotor1 = hardwareMap.dcMotor.get("extend1");
        extendMotor2 = hardwareMap.dcMotor.get("extend2");

        clamp1 = hardwareMap.servo.get("clamp1");
        clamp2 = hardwareMap.servo.get("clamp2");

        hit1 = hardwareMap.servo.get("hit1");
        hit2 = hardwareMap.servo.get("hit2");

        dt ("Init Loaded!");

        dt("Ready?");
        dt("Push the play button to start!");

    }

    @Override
    public void loop() {

        /*
       Use encoders here.
       Follow pictures

         */



    }




}
