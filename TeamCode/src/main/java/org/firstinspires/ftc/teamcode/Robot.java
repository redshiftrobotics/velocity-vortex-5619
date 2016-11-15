package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;

import java.util.ArrayList;

/**
 * Created by Noah Rose Ledesma on 10/1/16. Thanks to Matt Kelsey, Adam Perlin, and Samin Zachariah
 * Re-implementation of the 2856 PID Driving System without the spaghetti.
 * Class name is not finalized. Method names are eh right now, looking to revise them.
 */
public class Robot {

	public RobotData data = new RobotData();


	//changed from I2cDevice
	public Robot(I2cDeviceSynch imu, DcMotor leftDrive, DcMotor rightDrive) {
		// Initialize the IMU & its parameters. We will always be using Degrees for angle
		// measurement and Meters per sec per sec for acceleration.
		data.imuParameters = new BNO055IMU.Parameters();
		data.imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		data.imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

		data.imu = new AdafruitBNO055IMU(imu);
		data.imu.initialize(data.imuParameters);

		// Store the Robot Hardware
		data.drive.rightDrive = rightDrive;
		data.drive.leftDrive = leftDrive;

		data.drive.encoderCount = 1400;
	}

	// Public Interface Methods:

	// Method that moves the Robot forward variable number of Rotations. Orientation is verified and
	// corrected by PID control.
	public void straight(float rotations){
		// Assume 1 sec per rotation.
		straight(rotations, (int)rotations);
	}
	public void straight(float rotations, int timeout){
		// We need two points of data from the IMU to do our calculation. So lets take the first one
		// and put it into our "current" headings slot.
		calculateAngles();

		// Get the current program time and starting encoder position before we start our drive loop
		float startTime = data.time.currentTime();
		float startPosition = data.drive.leftDrive.getCurrentPosition();

		// Reset our Integral and Derivative data.
		data.PID.integralData.clear();
		data.PID.derivativeData.clear();



		// Manually calculate our first target
		data.PID.target = calculateAngles() + (data.PID.IMURotations * 360);

		// We need to keep track of how much time passes between a loop.
		float loopTime = data.time.currentTime();

		// This is the main loop of our straight drive.
		// We use encoders to form a loop that corrects rotation until we reach our target.
		while(Math.abs(startPosition - data.drive.leftDrive.getCurrentPosition()) < Math.abs(rotations) * data.drive.encoderCount){
			// First we check if we have exceeded our timeout and...
			if(startTime + timeout < data.time.currentTime()){
				// ... stop our loop if we have.
				break;
			}

			// Record the time since the previous loop.
			loopTime = data.time.timeFrom(loopTime);
			// Calculate our angles. This method may modify the input Rotations.
			//IMURotations =
			calculateAngles();
			// Calculate our PID
			calculatePID(loopTime);

			// Calculate the Direction to travel to correct any rotational errors.
			float Direction = ((data.PID.I * data.PID.ITuning) / 2000) + ((data.PID.P * data.PID.PTuning) / 2000) + ((data.PID.D * data.PID.DTuning) / 2000);
			// Constrain our direction from being too intense.

			// Define our motor power multiplier

			// Before we set the power of our motors, we need to adjust for forwards or backwards
			// movement. We can use the sign of Rotations to determine this
			// We are moving forwards
			data.drive.rightDrive.setPower(data.drive.POWER_CONSTANT - (Direction));
			data.drive.leftDrive.setPower(data.drive.POWER_CONSTANT + (Direction));
		}
		// Our drive loop has completed! Stop the motors.
		data.drive.rightDrive.setPower(0);
		data.drive.leftDrive.setPower(0);
	}

	public void angleTurn(float angle, int timeout){
		// We need two points of data from the IMU to do our calculation. So lets take the first one
		// and put it into our "current" headings slot.
		calculateAngles();

		// Get the current program time and starting encoder position before we start our drive loop
		float StartTime = data.time.currentTime();
		float StartPosition = data.drive.leftDrive.getCurrentPosition();

		// Reset our Integral and Derivative data.
		data.PID.integralData.clear();
		data.PID.derivativeData.clear();


		// Manually calculate our first target
		data.PID.target = (calculateAngles() + (data.PID.IMURotations * 360)) + angle;

		// We need to keep track of how much time passes between a loop.
		float LoopTime = data.time.currentTime();

		// This is the main loop of our straight drive.
		// We use encoders to form a loop that corrects rotation until we reach our target.
		while(StartTime + timeout > data.time.currentTime()){

			// Record the time since the previous loop.
			LoopTime = data.time.timeFrom(LoopTime);
			// Calculate our angles. This method may modify the input Rotations.
			//IMURotations =
			calculateAngles();
			// Calculate our PID
			calculatePID(LoopTime);

			// Calculate the Direction to travel to correct any rotational errors.
			float Direction = ((data.PID.I * data.PID.ITuning) / 2000) + ((data.PID.P * data.PID.PTuning) / 2000) + ((data.PID.D * data.PID.DTuning) / 2000);;

			if(Math.abs(Direction) <= 0.03f) {
				break;
			}

			data.drive.rightDrive.setPower(data.drive.POWER_CONSTANT - (Direction));
			data.drive.leftDrive.setPower(data.drive.POWER_CONSTANT  + (Direction));
		}
		// Our drive loop has completed! Stop the motors.
		data.drive.rightDrive.setPower(0);
		data.drive.leftDrive.setPower(0);
	}

	// Private Methods

	// Method that grabs the IMU data and calculates a new computedTarget.
	private float calculateAngles(){
		// First we will move the current angle heading into the previous angle heading slot.
		data.PID.headings[0] = data.PID.headings[1];
		// Then, we assign the new angle heading.
		data.PID.headings[1] = data.imu.getAngularOrientation().firstAngle;

		// Finally we calculate a computedTarget from the current angle heading.
		data.PID.computedTarget = data.PID.headings[1] + (data.PID.IMURotations * 360);

		// Now we determine if we need to re-calculate the angles.
		if(data.PID.headings[0] > 300 && data.PID.headings[1] < 60) {
			data.PID.IMURotations++; //rotations of 360 degrees
			calculateAngles();
		} else if(data.PID.headings[0] < 60 && data.PID.headings[1] > 300) {
			data.PID.IMURotations--;
			calculateAngles();
		}
		return data.PID.headings[1];
	}

	// Method that calculates P, I, and D. Requires the time
	private void calculatePID(float LoopTime){


		// Append to our data sets.
		data.PID.integralData.add(data.PID.computedTarget - data.PID.target);
		data.PID.derivativeData.add(data.PID.computedTarget);

		// Keep integralData and derivativeData from having an exceeding number of entries.
		if (data.PID.integralData.size() > data.PID.INTEGRAL_DATA_MAX_SIZE){
			data.PID.integralData.remove(0);
		}

		if(data.PID.derivativeData.size() > data.PID.DERIVATIVE_DATA_MAX_SIZE){
			data.PID.derivativeData.remove(0);
		}

		// Set our P, I, and D values.
		// `P` will be the computedTarget - target
		data.PID.P = data.PID.computedTarget - data.PID.target;

		// `I` will be the average of the integralData (Cries softly at the lack of Java8 streams)
		float IntegralAverage = 0;
		for(float value : data.PID.integralData){
			IntegralAverage += value;
		}
		data.PID.I = IntegralAverage / data.PID.integralData.size();

		// `D` will be the difference of the computedTarget and the Derivative average divided by
		// the time since the last loop in seconds multiplied by one plus half of the size of
		// the Derivative data set size.
		float DerivativeAverage = 0;
		for(float value : data.PID.derivativeData){
			DerivativeAverage += value;
		}
		DerivativeAverage /= data.PID.derivativeData.size();

		data.PID.D = (data.PID.computedTarget - DerivativeAverage) / ((LoopTime/1000) * (1 + (data.PID.derivativeData.size() / 2)));
	}
}


// data container classes
// RobotData acts as the main container for data.
// The PID, RobotTime, and drive Classes act as child data containers for neater organization.

class RobotData {
	BNO055IMU imu;
	BNO055IMU.Parameters imuParameters;
	PID PID;
	RobotTime time;
	Drive drive;
	RobotData(){
		PID = new PID();
		time = new RobotTime();
		drive = new Drive();
	}

	// PID Calculation data
	class PID {
		float computedTarget;
		float target;
		float P, I, D;
		float PTuning, ITuning, DTuning;
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
	class Drive {
		//motors indexing around the Robot like the quadrants in a graph or like the motors on a drone
		// for example
		DcMotor rightDrive;
		DcMotor leftDrive;

		int encoderCount;
		final float POWER_CONSTANT = (3/8f); // I believe this value does not change. 0.5*(3/4)
	}

}
