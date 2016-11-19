package org.redshiftrobotics.pid;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class RobotData {
	BNO055IMU imu;
	BNO055IMU.Parameters imuParameters;
	public PID PID;
	RobotTime time;
	public Drive drive;
	RobotData(){
		PID = new PID();
		time = new RobotTime();
		drive = new Drive();
	}

	// PID Calculation data
	public class PID {
		float computedTarget;
		float target;
		float P, I, D;
		public float PTuning, ITuning, DTuning;
		float[] headings = new float[2];
		int IMURotations;
		ArrayList<Float> derivativeData;
		ArrayList<Float> integralData;
		final int INTEGRAL_DATA_MAX_SIZE = 500;
		final int DERIVATIVE_DATA_MAX_SIZE = 5;
		// Constructor
		PID(){
			// Init non-primitives
			derivativeData = new ArrayList<>();
			integralData = new ArrayList<>();
			IMURotations = 0;
		}
	}

	// time data
	class RobotTime {
		private ElapsedTime programTime;

		public RobotTime(){
			programTime = new ElapsedTime();
		}

		public float currentTime(){
			return (float) programTime.seconds();
		}

		public float timeFrom(float previousTime){
			return (float) (programTime.seconds() - previousTime);
		}
	}

	// Robot hardware data.
	public class Drive {
		//motors indexing around the Robot like the quadrants in a graph or like the motors on a drone
		// for example
		DcMotor rightDrive;
		DcMotor leftDrive;

		int encoderCount;
		public float POWER_CONSTANT = (3/8f); // I believe this value does not change. 0.5*(3/4)
	}

}
