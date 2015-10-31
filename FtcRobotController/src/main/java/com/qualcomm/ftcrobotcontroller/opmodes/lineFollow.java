package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

/**
 * Created by Noah Rose-Ledesma on 10/8/2015.
 */
public class lineFollow extends OpMode{
    private LegacyModule lModule;
    private LightSensor lightSensor;
    private DcMotorController motorController;
    private DcMotor motor1, motor2;

    private double white = 0.50;
    private double black = 0.66;
    private int Kp = 20;
    private double offset = (white+black)/2;
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

        double Lval = lightSensor.getLightDetected();
        telemetry.addData("1 Dogshit", "Light @ " + Lval);
        double error = Lval - offset;
        derivative = error - lastError;
        integral += error;
        double turn = (Kp * error);
        //turn /= 100;
       double powL = (Tp - turn) /80;
        double powR = -(Tp + turn) /80;
        motor1.setPower(powL);
        motor2.setPower(powR);

        telemetry.addData("2 Horseshit", "R: " + String.valueOf(round(powR, 2)) + ". L: " + String.valueOf(round(powL, 2)));
        telemetry.addData("3 Bullshit", "(" + String.valueOf(Tp) + " +- " + String.valueOf(turn) + ")/80");
    }
    @Override
    public void start(){
    }
    public lineFollow(){

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
