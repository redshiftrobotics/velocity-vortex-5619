package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;


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
	protected DcMotor extendMotor1; // arm1
	protected DcMotor extendMotor2; // arm2
	protected Servo lift1; //
	protected Servo lift2; //
	protected Servo hit1; //hit1
	protected Servo hit2; //hit2
	//protected LightSensor lightSensor; //line follower light sensor

	//protected LegacyModule legacyModule1; //legacy module
	protected ServoController servoController1; //servo controller
	protected DcMotorController dcMotorController1; //motor controller 1
	protected DcMotorController dcMotorController2; //motor controller 2
	protected DcMotorController dcMotorController3; //motor controller 3
	protected EAutoCallMountainCode mountainCode;


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
		dt("Init Loading...");//start




		//legacyModule1 = hardwareMap.legacyModule.get("Legacy Module 1");
		servoController1 = hardwareMap.servoController.get("Servo Controller 1");
		dcMotorController1 = hardwareMap.dcMotorController.get("Motor Controller Arm");
		dcMotorController2 = hardwareMap.dcMotorController.get("Motor Controller Back");
		dcMotorController3 = hardwareMap.dcMotorController.get("Motor Controller Front");
		frontLeftMotor = hardwareMap.dcMotor.get("left1");
		frontRightMotor = hardwareMap.dcMotor.get("right1");
		backLeftMotor = hardwareMap.dcMotor.get("left2");
		backRightMotor = hardwareMap.dcMotor.get("right2");
		extendMotor1 = hardwareMap.dcMotor.get("extend1");
		extendMotor2 = hardwareMap.dcMotor.get("extend2");
		lift1 = hardwareMap.servo.get("lift1");
		lift2 = hardwareMap.servo.get("lift2");
		hit1 = hardwareMap.servo.get("hit1");
		hit2 = hardwareMap.servo.get("hit2");

		//lightSensor = hardwareMap.lightSensor.get("light");



		frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
		frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
		backRightMotor.setDirection(DcMotor.Direction.REVERSE);
		backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
		extendMotor2.setDirection(DcMotor.Direction.REVERSE);
		extendMotor1.setDirection(DcMotor.Direction.FORWARD);

		mountainCode = new EAutoCallMountainCode(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, extendMotor1, extendMotor2, telemetry);
		dt("Init Loaded!"); //end



	}


}
