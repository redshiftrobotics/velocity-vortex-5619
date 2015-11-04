package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
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
	protected DcMotor extendMotor1; // arm1
	protected DcMotor extendMotor2; // arm2
	protected Servo clamp1; //clamp1
	protected Servo clamp2; //clamp2
	protected Servo hit1; //hit1
	protected Servo hit2; //hit2
	protected LightSensor lightSensor; //line follower light sensor

	protected LegacyModule legacyModule1; //legacy module
	protected ServoController servoController1; //servo controller
	protected DcMotorController dcMotorController1; //motor controller 1
	protected DcMotorController dcMotorController2; //motor controller 2
	protected DcMotorController dcMotorController3; //motor controller 3

	public void toastShort(String message) //makes the toast on screen widget work in a simple way. SHORT LASTING WIDGET. Good for debug messages
	{
		Toast.makeText(FtcRobotControllerActivity.mainActivity.getBaseContext(), message, Toast.LENGTH_SHORT).show();
	}

	public void toastLong(String message) //makes the toast on screen widget work in a simple way. LONG LASTING WIDGET. Good for simple error messages
	{
		Toast.makeText(FtcRobotControllerActivity.mainActivity.getBaseContext(), message, Toast.LENGTH_LONG).show();
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
		dt("Init Loading...");//start

		//prepare for a s**t load of try catches :/
		//this is all for error checking
		//guess this wasnt needed :/


		try
		{
			legacyModule1 = hardwareMap.legacyModule.get("Legacy Module 1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("Couldn't find Legacy Module 1");
			throw e;
		}

		try
		{
			servoController1 = hardwareMap.servoController.get("Servo Controller 1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("Couldn't find Servo Controller 1");
			throw e;
		}

		try
		{
			dcMotorController1 = hardwareMap.dcMotorController.get("Motor Controller 1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("Couldn't find Motor Controller 1");
			throw e;
		}

		try
		{
			dcMotorController2 = hardwareMap.dcMotorController.get("Motor Controller 2");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("Couldn't find Motor Controller 2");
			throw e;
		}

		try
		{
			dcMotorController3 = hardwareMap.dcMotorController.get("Motor Controller 3");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("Couldn't find Motor Controller 3");
			throw e;
		}

		try
		{
			frontLeftMotor = hardwareMap.dcMotor.get("left1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: left1");
			throw e;
		}

		try
		{
			frontRightMotor = hardwareMap.dcMotor.get("right1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: right1");
			throw e;
		}

		try
		{
			backLeftMotor = hardwareMap.dcMotor.get("left2");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: left2");
			throw e;
		}

		try
		{
			backRightMotor = hardwareMap.dcMotor.get("right2");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: right2");
			throw e;
		}

		try
		{
			extendMotor1 = hardwareMap.dcMotor.get("extend1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: extend1");
			throw e;
		}

		try
		{
			extendMotor2 = hardwareMap.dcMotor.get("extend2");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: extend2");
			throw e;
		}

		try
		{
			clamp1 = hardwareMap.servo.get("clamp1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: clamp1");
			throw e;
		}

		try
		{
			clamp2 = hardwareMap.servo.get("clamp2");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: clamp2");
			throw e;
		}

		try
		{
			hit1 = hardwareMap.servo.get("hit1");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: hit1");
			throw e;
		}

		try
		{
			hit2 = hardwareMap.servo.get("hit2");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: hit2");
			throw e;
		}

		try
		{
			lightSensor = hardwareMap.lightSensor.get("light");
		}
		catch(RuntimeException e)
		{
			dt("An Error Occurred!!!");
			dt("You need to add in your config: light");
			throw e;
		}

		//god that was a LOT of try catches
		//turns out they were not needed
		//well, that was some nice good time wasted i guess :/

		frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
		backRightMotor.setDirection(DcMotor.Direction.REVERSE);

		dt("Init Loaded!"); //end



	}


}
