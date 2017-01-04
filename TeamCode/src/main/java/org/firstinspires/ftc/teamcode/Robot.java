package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

/**
 * Created by Noah Rose Ledesma on 10/1/16.
 * Re-implementation of the 2856 PID Driving System without the spaghetti.
 * Class name is not finalized. Method names are eh right now, looking to revise them.
 */
public class Robot {

	public robotData Data = new robotData();

	public float IMURotations = 0;

	//changed from I2cDevice
	public Robot(I2cDeviceSynch imu, DcMotor m0, DcMotor m1, Telemetry tm) {


		tm.addData("IMU ", "Innitializing");
		tm.update();
		// Initialize the IMU & its parameters. We will always be using Degrees for angle
		// measurement and Meters per sec per sec for acceleration.
		Data.imuParameters = new BNO055IMU.Parameters();
		Data.imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
		Data.imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

		Data.imu = new AdafruitBNO055IMU(imu);
		Data.imu.initialize(Data.imuParameters);

		Data.PID.target = ((Data.imu.getAngularOrientation().firstAngle*-1) + 180) % 360;

		// Store the Robot Hardware
		Data.Drive.m0 = m0;
		Data.Drive.m1 = m1;
		Data.Drive.encoderCount = 1400;


		// Start the program clock
		Data.Time = new robotTime();
	}

	// Public Interface Methods:
	// Method that moves the robot forward variable number of Rotations. Orientation is verified and
	// corrected by PID control.
	public void straight(float rotations, int timeout, Telemetry tm){
		// We need two points of data from the IMU to do our calculation. So lets take the first one
		// and put it into our "current" headings slot.

		Data.PID.headings[0] = Data.PID.headings[1];
		// Then, we assign the new angle heading.
		Data.PID.headings[1] = ((Data.imu.getAngularOrientation().firstAngle*-1) + 180) % 360;

		Data.PID.headings[0] = Data.PID.headings[1];
		// Then, we assign the new angle heading.
		Data.PID.headings[1] = ((Data.imu.getAngularOrientation().firstAngle*-1) + 180) % 360;

		calculateAngles(tm);

		// Get the current program time and starting encoder position before we start our drive loop
		float startTime = Data.Time.currentTime();
		float startPosition = Data.Drive.m0.getCurrentPosition();

		// Reset our Integral and Derivative data.
		Data.PID.integralData.clear();
		Data.PID.derivativeData.clear();


		//Calculate PIDS again because Isaac Zinda only knows


		// We need to keep track of how much time passes between a loop.
		float loopTime = Data.Time.currentTime();

		// This is the main loop of our straight drive.
		// We use encoders to form a loop that corrects rotation until we reach our target.
		while(Math.abs(startPosition - Data.Drive.m0.getCurrentPosition()) < Math.abs(rotations) * Data.Drive.encoderCount){
			// First we check if we have exceeded our timeout and...
			if(startTime + timeout < Data.Time.currentTime()){
				// ... stop our loop if we have.
				break;
			}

			// Record the time since the previous loop.
			loopTime = Data.Time.timeFrom(loopTime);
			// Calculate our angles. This method may modify the input Rotations.
			//IMURotations =
			calculateAngles(tm);
			// Calculate our PID
			calculatePID(loopTime, tm);

			// Calculate the Direction to travel to correct any rotational errors.
			float direction = ((Data.PID.I * Data.PID.iTuning) / Data.PID.magicNumber) + ((Data.PID.P * Data.PID.pTuning) / Data.PID.magicNumber) + ((Data.PID.D * Data.PID.dTuning) / Data.PID.magicNumber);
			// Constrain our direction from being too intense.
			Data.Drive.m0.setPower((Data.Drive.POWER_CONSTANT + (direction)));
			Data.Drive.m1.setPower((Data.Drive.POWER_CONSTANT - (direction)));
		}
		// Our drive loop has completed! Stop the motors.
		Data.Drive.m0.setPower(0);
		Data.Drive.m1.setPower(0);
	}
	// Private Methods

	// Method that grabs the IMU data and calculates a new computedTarget.
	private float calculateAngles(Telemetry tm){
		// First we will move the current angle heading into the previous angle heading slot.
		Data.PID.headings[0] = Data.PID.headings[1];
		// Then, we assign the new angle heading.

		Data.PID.headings[1] = ((Data.imu.getAngularOrientation().firstAngle*-1) + 180) % 360;

		// Finally we calculate a computedTarget from the current angle heading.
		Data.PID.computedTarget = Data.PID.headings[1] + (IMURotations * 360);
		// Now we determine if we need to re-calculate the angles.
		if(Data.PID.headings[0] > 300 && Data.PID.headings[1] < 60) {
			IMURotations++; //rotations of 360 degrees
			calculateAngles(tm);
		} else if(Data.PID.headings[0] < 60 && Data.PID.headings[1] > 300) {
			IMURotations--;
			calculateAngles(tm);
		}
		return Data.PID.headings[1];
	}


	// Method that calculates P, I, and D. Requires the time
	private void calculatePID(float LoopTime, Telemetry tm){


		// Append to our data sets.
		Data.PID.integralData.add(Data.PID.computedTarget - Data.PID.target);
		Data.PID.derivativeData.add(Data.PID.computedTarget);

		// Keep integralData and derivativeData from having an exceeding number of entries.
		if (Data.PID.integralData.size() > 500){
			Data.PID.integralData.remove(0);
		}

		if(Data.PID.derivativeData.size() > 5){
			Data.PID.derivativeData.remove(0);
		}

		// Set our P, I, and D values.
		// `P` will be the computedTarget - target
		Data.PID.P = Data.PID.computedTarget - Data.PID.target;

		// `I` will be the average of the integralData (Cries softly at the lack of Java8 streams)

		float IntegralAverage = 0;
		for(float value : Data.PID.integralData){
			IntegralAverage += value;
		}
		Data.PID.I = IntegralAverage / Data.PID.integralData.size();

		// `D` will be the difference of the computedTarget and the Derivative average divided by
		// the time since the last loop in seconds multiplied by one plus half of the size of
		// the Derivative data set size.

		float DerivativeAverage = 0;
		for(float value : Data.PID.derivativeData){
			DerivativeAverage += value;
		}
		DerivativeAverage /= Data.PID.derivativeData.size();

		Data.PID.D = (Data.PID.computedTarget - DerivativeAverage) / ((LoopTime/1000) * (1 + (Data.PID.derivativeData.size() / 2)));
	}
	public void setTuning(float P, float I, float D){
		Data.PID.P = P;
		Data.PID.I = I;
		Data.PID.D = D;
	}
}
// Data container classes
// robotData acts as the main container for Data.
// The PID, robotTime, and Drive Classes act as child data containers for neater organization.

class robotData {
	BNO055IMU imu;
	BNO055IMU.Parameters imuParameters;
	PID PID;
	robotTime Time;
	Drive Drive;
	robotData(){
		PID = new PID();
		Time = new robotTime();
		Drive = new Drive();
	}
}

// PID data
class PID {
	float computedTarget;
	float target;
	float P, I, D;
	float pTuning, iTuning, dTuning;
	float[] headings = new float[2];
	int magicNumber = 30;
	ArrayList<Float> derivativeData;
	ArrayList<Float> integralData;
	// Constructor
	PID(){
		// Init non-primitives
		derivativeData = new ArrayList<>();
		integralData = new ArrayList<>();
	}
}
// Time data
class robotTime {
	private ElapsedTime programTime;

	public robotTime(){
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
	//motors indexing around the robot like the quadrants in a graph or like the motors on a drone
	// for example
    /*
    Front
    ________
    |0    1|
    |      |
    |3    2|
    --------
    Rear
    */
	DcMotor m0;
	DcMotor m1;
	int encoderCount;
	float POWER_CONSTANT = (3/8f); // I believe this value does not change. 0.5*(3/4
}
