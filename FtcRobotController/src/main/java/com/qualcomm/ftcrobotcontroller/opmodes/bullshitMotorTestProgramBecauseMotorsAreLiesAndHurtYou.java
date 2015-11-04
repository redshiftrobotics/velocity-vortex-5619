package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;

/**
 * Created by Noah Rose-Ledesma on 10/22/2015.
 */
public class bullshitMotorTestProgramBecauseMotorsAreLiesAndHurtYou extends OpMode{
    private LegacyModule lModule;
    private LightSensor lightSensor;
    private DcMotorController motorController;
    private DcMotor motor1, motor2;

    private int white = 25;
    private int black = 60;
    private int Kp = 20;
    private int offset = (white+black)/2;
    private int lastError = 0;
    private double derivative = 0;
    private int Tp = 10;
    private double integral = 0;

    @Override
    public void init(){
        lModule = hardwareMap.legacyModule.get("Legacy Module 1");
        lightSensor = hardwareMap.lightSensor.get("light");
        motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
        motor1 = hardwareMap.dcMotor.get("left");
        motor2 = hardwareMap.dcMotor.get("right");
    }
    @Override
    public void loop(){

    }
    @Override
    public void start(){
    }
    public bullshitMotorTestProgramBecauseMotorsAreLiesAndHurtYou(){

    }
}
