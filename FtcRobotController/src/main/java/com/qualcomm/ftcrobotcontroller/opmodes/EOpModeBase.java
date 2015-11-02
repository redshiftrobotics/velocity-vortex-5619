package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import android.widget.Toast;


/**
 * Created by Eric Golde on 11/1/2015.
 */
public abstract class EOpModeBase extends OpMode{

	String teleConvert;
	int teleInt = 0;

	protected DcMotor frontLeftMotor; //FRONT LEFT
	protected DcMotor frontRightMotor; //FRONT RIGHT
	protected DcMotor backLeftMotor; //BACK LEFT
	protected DcMotor backRightMotor; //BACK RIGHT
	protected DcMotor extendMotor1; // arm
	protected DcMotor extendMotor2; // arm
	protected Servo clamp1; //clamp
	protected Servo clamp2; //clamp
	protected Servo hit1; //hit
	protected Servo hit2; //hit
	protected LightSensor lightSensor;

	protected LegacyModule legacyModule1;
	protected ServoController servoController1;
	protected DcMotorController dcMotorController1;
	protected DcMotorController dcMotorController2;
	protected DcMotorController dcMotorController3;

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


	public void init()
	{
		dt("Init Loading...");//start

		legacyModule1 = hardwareMap.legacyModule.get("Legacy Module 1");
		servoController1 = hardwareMap.servoController.get("Servo Controller 1");
		dcMotorController1 = hardwareMap.dcMotorController.get("Motor Controller 1");
		dcMotorController2 = hardwareMap.dcMotorController.get("Motor Controller 2");
		dcMotorController3 = hardwareMap.dcMotorController.get("Motor Controller 3");

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


		lightSensor = hardwareMap.lightSensor.get("light");

		dt ("Init Loaded!"); //end
	}


}
