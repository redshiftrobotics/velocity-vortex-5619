package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.hardware.adafruit.BNO055IMU;

import java.util.ArrayList;

public class robot {

    public robotData Data;

    /*
     * Public Methods
     */

    /*
     * Robot Constructor.
     * @param imu the IMU
     * @param LeftDrive the left motor
     * @param RightDrive the right motor
     */
    public robot(I2cDeviceSynch imu, DcMotor LeftDrive, DcMotor RightDrive) {
        Data = new robotData();

        Data.imuParameters = new BNO055IMU.Parameters();
        Data.imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        Data.imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
		Data.imu = new AdafruitBNO055IMU(imu);
		Data.imu.initialize(Data.imuParameters);

        Data.Drive.leftDrive = LeftDrive;
        Data.Drive.rightDrive = RightDrive;
        Data.Drive.encoderCount = 1400;

        // Start the program clock
        Data.time = new robotTime();

        // We need two points of data from the IMU to do our calculations. So lets take the first one
        // and put it into our "current" headings slot.
        Data.PID.headings[1] = (float) Data.imu.getAngularOrientation().firstAngle;

    }

    /*
     * Sets the power multiplier of our robot in order to inhibit speeds.
     * @param power the power multiplier
     */
    public void setPowerMultiplier(float power){
        Data.Drive.powerMultiplier = power;
    }
    /*
     * Drive straight and correct rotational error with PID.
     * @param Rotations the number of rotations to move the robot
     * @param Timeout the time in seconds the robot should attempt this action
     */
    public void Straight(float Rotations, int Timeout){

        float StartTime = Data.time.currentTime();
        float LoopTime = Data.time.currentTime();
        float StartPosition = Data.Drive.leftDrive.getCurrentPosition();

        // Clear old data
        Data.PID.integralData.clear();
        Data.PID.derivativeData.clear();

        // Manually calculate our first target
        Data.PID.target = Data.PID.headings[1] * (Rotations * 360);

        // We use encoders to form a loop that corrects rotation until we reach our target.
        while(Math.abs(StartPosition - Data.Drive.leftDrive.getCurrentPosition()) < Math.abs(Rotations) * Data.Drive.encoderCount){

            if(StartTime + Timeout < Data.time.currentTime()){
                break;
            }

            // Record the time since the previous loop.
            LoopTime = Data.time.timeFrom(LoopTime);

            // Calculate our angles. This method may modify the input Rotations.
            Rotations = CalculateTarget(Rotations);

            // Calculate our PID values
            CalculatePID(LoopTime);

            // Calculate the Direction to travel to correct any rotational errors.
            float Direction = (Data.PID.i * Data.PID.iTuning) + (Data.PID.p * Data.PID.pTuning) + (Data.PID.d * Data.PID.dTuning);

            // Constrain our direction from being too intense.
            if(Direction > 50){ Direction = 50; }
            if(Direction < -50){ Direction = -50; }

            // Before we set the power of our motors, we need to adjust for forwards or backwards
            // movement. We can use the sign of Rotations to determine this
            if(Rotations > 0) {
                // We are moving forwards.
                Data.Drive.leftDrive.setPower(Data.Drive.powerMultiplier + (Direction / 200));
                Data.Drive.rightDrive.setPower(Data.Drive.powerMultiplier - (Direction / 200));
            } else {
                // We are moving backwards
                Data.Drive.leftDrive.setPower(Data.Drive.powerMultiplier + (Direction / 200));
                Data.Drive.rightDrive.setPower(Data.Drive.powerMultiplier - (Direction / 200));
            }
        }
        // Our Drive loop has completed! Stop the motors.
        Data.Drive.leftDrive.setPower(0);
        Data.Drive.rightDrive.setPower(0);
    }

    /*
     * Private Methods
     */

    /*
     * Calculate Target Rotation from the IMU data.
     */
    private float CalculateTarget(float Rotations){

        // `Push` the heading down the array and calculate a new one
        Data.PID.headings[0] = Data.PID.headings[1];
        Data.PID.headings[1] = Data.imu.getAngularOrientation().firstAngle;

        Data.PID.computedTarget = (Data.PID.headings[1] + (Rotations * 360));

        // Now we determine if we need to re-calculate the angles.
        if(Data.PID.headings[0] > 300 && Data.PID.headings[1] < 60) {
            Rotations++;
            return CalculateTarget(Rotations);
        } else if(Data.PID.headings[0] < 60 && Data.PID.headings[1] > 300) {
            Rotations--;
            return CalculateTarget(Rotations);
        }
    return Rotations;
    }

    /*
     * Calculate our PID Values
     * @param LoopTime the time from the previous CalculatePID
     */
    private void CalculatePID(float LoopTime){
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

        // Set our p, i, and d values.

        // `p` will be the computedTarget - target
        Data.PID.p = Data.PID.computedTarget - Data.PID.target;
        // `i` will be the average of the integralData (Cries softly at the lack of Java8 streams)

        float IntegralAverage = 0;
        for(float value : Data.PID.integralData){
            IntegralAverage += value;
        }
        Data.PID.i = IntegralAverage / Data.PID.integralData.size();

        // `d` will be the difference of the computedTarget and the Derivative average divided by
        // the time since the last loop in seconds multiplied by one plus half of the size of
        // the Derivative data set size.

        float DerivativeAverage = 0;
        for(float value : Data.PID.derivativeData){
            DerivativeAverage += value;
        }
        DerivativeAverage /= Data.PID.derivativeData.size();

        Data.PID.d = (Data.PID.computedTarget - DerivativeAverage) / ((LoopTime/1000) * (1 + (Data.PID.derivativeData.size() / 2)));

    }
}

/*
 * Data Containers
 */

/*
 * Main data container
 */
class robotData {
    BNO055IMU imu;
    BNO055IMU.Parameters imuParameters;
    PID PID;
    robotTime time;
    drive Drive;
    // Data constructor
    robotData(){
        PID = new PID();
        time = new robotTime();
        Drive = new drive();
    }
}

/*
 * PID data container
 */
class PID {
    float computedTarget;
    float target;
    float p, i, d;
    float pTuning, iTuning, dTuning;
    float[] headings = new float[2];
    ArrayList<Float> derivativeData;
    ArrayList<Float> integralData;
    // Constructor
    PID(){
        // Init non-primitives
        derivativeData = new ArrayList<>();
        integralData = new ArrayList<>();
    }
}

/*
 * Time-related functions.
 */
class robotTime {
    private ElapsedTime ProgramTime;

    public robotTime(){
        ProgramTime = new ElapsedTime();
    }

    public float currentTime(){
        return (float) ProgramTime.seconds();
    }

    public float timeFrom(float PreviousTime){
        return (float) (ProgramTime.seconds() - PreviousTime);
    }

}

/*
 * Drive data container
 */
class drive {
    DcMotor leftDrive;
    DcMotor rightDrive;
    int encoderCount;
    float powerMultiplier = (3/8f);
}
