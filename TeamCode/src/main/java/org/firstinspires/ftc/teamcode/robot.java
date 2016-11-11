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
public class robot {

	public RobotData Data = new RobotData();


	//changed from I2cDevice
	public robot(I2cDeviceSynch imu, DcMotor leftDrive, DcMotor rightDrive) {
		// Initialize the IMU & its parameters. We will always be using Degrees for angle
		// measurement and Meters per sec per sec for acceleration.
		Data.imuParameters = new BNO055IMU.Parameters();
		Data.imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		Data.imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

		Data.imu = new AdafruitBNO055IMU(imu);
		Data.imu.initialize(Data.imuParameters);

		// Store the Robot Hardware
		Data.Drive.rightDrive = rightDrive;
		Data.Drive.leftDrive = leftDrive;

		Data.Drive.EncoderCount = 1400;
	}

	// Public Interface Methods:

	// Method that moves the robot forward variable number of Rotations. Orientation is verified and
	// corrected by PID control.
	public void Straight(float Rotations, int Timeout){
		// We need two points of data from the IMU to do our calculation. So lets take the first one
		// and put it into our "current" headings slot.
		CalculateAngles();

		// Get the current program time and starting encoder position before we start our drive loop
		float StartTime = Data.Time.CurrentTime();
		float StartPosition = Data.Drive.leftDrive.getCurrentPosition();

		// Reset our Integral and Derivative data.
		Data.PID.IntegralData.clear();
		Data.PID.DerivativeData.clear();



		// Manually calculate our first target
		Data.PID.Target = CalculateAngles() + (Data.PID.IMURotations * 360);

		// We need to keep track of how much time passes between a loop.
		float LoopTime = Data.Time.CurrentTime();

		// This is the main loop of our straight drive.
		// We use encoders to form a loop that corrects rotation until we reach our target.
		while(Math.abs(StartPosition - Data.Drive.leftDrive.getCurrentPosition()) < Math.abs(Rotations) * Data.Drive.EncoderCount){
			// First we check if we have exceeded our timeout and...
			if(StartTime + Timeout < Data.Time.CurrentTime()){
				// ... stop our loop if we have.
				break;
			}

			// Record the time since the previous loop.
			LoopTime = Data.Time.TimeFrom(LoopTime);
			// Calculate our angles. This method may modify the input Rotations.
			//IMURotations =
			CalculateAngles();
			// Calculate our PID
			CalculatePID(LoopTime);

			// Calculate the Direction to travel to correct any rotational errors.
			float Direction = ((Data.PID.I * Data.PID.ITuning) / 2000) + ((Data.PID.P * Data.PID.PTuning) / 2000) + ((Data.PID.D * Data.PID.DTuning) / 2000);
			// Constrain our direction from being too intense.

			//if(Direction > 50){ Direction = 50; }
			//if(Direction < -50){ Direction = -50; }

			// Define our motor power multiplier

			// Before we set the power of our motors, we need to adjust for forwards or backwards
			// movement. We can use the sign of Rotations to determine this
			// We are moving forwards
			Data.Drive.rightDrive.setPower(Data.Drive.POWER_CONSTANT - (Direction));
			Data.Drive.leftDrive.setPower(Data.Drive.POWER_CONSTANT + (Direction));
		}
		// Our drive loop has completed! Stop the motors.
		Data.Drive.rightDrive.setPower(0);
		Data.Drive.leftDrive.setPower(0);
	}

	public void AngleTurn(float angle, int Timeout){
		// We need two points of data from the IMU to do our calculation. So lets take the first one
		// and put it into our "current" headings slot.
		CalculateAngles();

		// Get the current program time and starting encoder position before we start our drive loop
		float StartTime = Data.Time.CurrentTime();
		float StartPosition = Data.Drive.leftDrive.getCurrentPosition();

		// Reset our Integral and Derivative data.
		Data.PID.IntegralData.clear();
		Data.PID.DerivativeData.clear();


		// Manually calculate our first target
		Data.PID.Target = (CalculateAngles() + (Data.PID.IMURotations * 360)) + angle;

		// We need to keep track of how much time passes between a loop.
		float LoopTime = Data.Time.CurrentTime();

		// This is the main loop of our straight drive.
		// We use encoders to form a loop that corrects rotation until we reach our target.
		while(StartTime + Timeout > Data.Time.CurrentTime()){

			// Record the time since the previous loop.
			LoopTime = Data.Time.TimeFrom(LoopTime);
			// Calculate our angles. This method may modify the input Rotations.
			//IMURotations =
			CalculateAngles();
			// Calculate our PID
			CalculatePID(LoopTime);

			// Calculate the Direction to travel to correct any rotational errors.
			float Direction = ((Data.PID.I * Data.PID.ITuning) / 2000) + ((Data.PID.P * Data.PID.PTuning) / 2000) + ((Data.PID.D * Data.PID.DTuning) / 2000);;

			if(Math.abs(Direction) <= 0.03f) {
				break;
			}

			Data.Drive.rightDrive.setPower(Data.Drive.POWER_CONSTANT - (Direction));
			Data.Drive.leftDrive.setPower(Data.Drive.POWER_CONSTANT  + (Direction));
		}
		// Our drive loop has completed! Stop the motors.
		Data.Drive.rightDrive.setPower(0);
		Data.Drive.leftDrive.setPower(0);
	}

	// Private Methods

	// Method that grabs the IMU data and calculates a new ComputedTarget.
	private float CalculateAngles(){
		// First we will move the current angle heading into the previous angle heading slot.
		Data.PID.Headings[0] = Data.PID.Headings[1];
		// Then, we assign the new angle heading.
		Data.PID.Headings[1] = Data.imu.getAngularOrientation().firstAngle;

		// Finally we calculate a ComputedTarget from the current angle heading.
		Data.PID.ComputedTarget = Data.PID.Headings[1] + (Data.PID.IMURotations * 360);

		// Now we determine if we need to re-calculate the angles.
		if(Data.PID.Headings[0] > Math.abs(300) && Data.PID.Headings[1] < Math.abs(60)) {
			Data.PID.IMURotations++; //rotations of 360 degrees
			CalculateAngles();
		} else if(Data.PID.Headings[0] < Math.abs(60) && Data.PID.Headings[1] > Math.abs(300)) {
			Data.PID.IMURotations--;
			CalculateAngles();
		}
		return Data.PID.Headings[1];
	}

	// Method that calculates P, I, and D. Requires the time
	private void CalculatePID(float LoopTime){


		// Append to our data sets.
		Data.PID.IntegralData.add(Data.PID.ComputedTarget - Data.PID.Target);
		Data.PID.DerivativeData.add(Data.PID.ComputedTarget);

		// Keep IntegralData and DerivativeData from having an exceeding number of entries.
		if (Data.PID.IntegralData.size() > 500){
			Data.PID.IntegralData.remove(0);
		}

		if(Data.PID.DerivativeData.size() > 5){
			Data.PID.DerivativeData.remove(0);
		}

		// Set our P, I, and D values.
		// `P` will be the ComputedTarget - Target
		Data.PID.P = Data.PID.ComputedTarget - Data.PID.Target;

		// `I` will be the average of the IntegralData (Cries softly at the lack of Java8 streams)
		float IntegralAverage = 0;
		for(float value : Data.PID.IntegralData){
			IntegralAverage += value;
		}
		Data.PID.I = IntegralAverage / Data.PID.IntegralData.size();

		// `D` will be the difference of the ComputedTarget and the Derivative average divided by
		// the time since the last loop in seconds multiplied by one plus half of the size of
		// the Derivative data set size.
		float DerivativeAverage = 0;
		for(float value : Data.PID.DerivativeData){
			DerivativeAverage += value;
		}
		DerivativeAverage /= Data.PID.DerivativeData.size();

		Data.PID.D = (Data.PID.ComputedTarget - DerivativeAverage) / ((LoopTime/1000) * (1 + (Data.PID.DerivativeData.size() / 2)));
	}
}


// Data container classes
// RobotData acts as the main container for Data.
// The PID, RobotTime, and Drive Classes act as child data containers for neater organization.

class RobotData {
	BNO055IMU imu;
	BNO055IMU.Parameters imuParameters;
	PID PID;
	RobotTime Time;
	Drive Drive;
	RobotData(){
		PID = new PID();
		Time = new RobotTime();
		Drive = new Drive();
	}

	// PID Calculation data
	class PID {
		float ComputedTarget;
		float Target;
		float P, I, D;
		float PTuning, ITuning, DTuning;
		float[] Headings = new float[2];
		int IMURotations;
		ArrayList<Float> DerivativeData;
		ArrayList<Float> IntegralData;
		// Constructor
		PID(){
			// Init non-primitives
			DerivativeData = new ArrayList<>();
			IntegralData = new ArrayList<>();
			IMURotations = 0;
		}
	}

	// Time data
	class RobotTime {
		private ElapsedTime ProgramTime;

		public RobotTime(){
			ProgramTime = new ElapsedTime();
		}

		public float CurrentTime(){
			return (float) ProgramTime.seconds();
		}

		public float TimeFrom(float PreviousTime){
			return (float) (ProgramTime.seconds() - PreviousTime);
		}
	}

	// Robot hardware data.
	class Drive {
		//motors indexing around the robot like the quadrants in a graph or like the motors on a drone
		// for example
		DcMotor rightDrive;
		DcMotor leftDrive;

		int EncoderCount;
		final float POWER_CONSTANT = (3/8f); // I believe this value does not change. 0.5*(3/4)
	}

}
