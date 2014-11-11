#pragma config(Sensor, S1, Motor, sensorI2CCustom)

#include "Motors.h"

tSensors MotorController = S1;
int leftMotorDaisyChainLevel=1;
int rightMotorDaisyChainLevel=2;
int leftMotorNumber=1;
int rightMotorNumber=2;
//Notice that motor (S3,1,2) is wired backwards so a positive value is back
// (S3,1,1) = left motors
// (S3,1,2) = right motors (backward)

//**********************************************
//Wrapper functions for left/right/center motors
void leftMotor(int spd){
	Motors_SetSpeed(MotorController, leftMotorDaisyChainLevel, leftMotorNumber, spd);
}

void rightMotor(int spd){
	Motors_SetSpeed(MotorController, rightMotorDaisyChainLevel, rightMotorNumber, spd);
}

void centerMotor(int spd){
		Motors_SetSpeed(MotorController,2,2,spd);
}
//end wrapper functions
//****************************************************************

//****************************************************************
//Spin Functions: Gives opposite speeds to left vs right
void Drive_spinRight(int speed)
{
	leftMotor(speed);
	rightMotor(speed);
}

void Drive_spinLeft(int speed)
{
	leftMotor(-speed);
	rightMotor(-speed);
}
//*****************End Spin Functions******************************

//turn is a catch all turn function that will act like a 
//tank drive.  The input are the speeds of the two sides of the
//robot.  The calling function has to manage their power difference
//Values can be + or - but the right side will be inverted per the wiring of the motors
void Drive_turn(int leftSpeed, int rightSpeed){
	leftMotor(leftSpeed);
	rightMotor(-rightSpeed);
}

//********************************************************************
// The forward and backward functions are not ncessary but make 
// the code more readible

// Forward takes ONLY A POSITIVE VALUE assuming that 
//we want to go forward
void Drive_forward(int speed)
{
	speed=abs(speed);  //positive value check
	leftMotor(speed);
	rightMotor(-speed);
}
// Backward takes ONLY A POSITIVE VALUE assuming that 
//we want to go backward
void Drive_backward (int speed) {
	speed=abs(speed);  //positive value check
	leftMotor(-speed);
	rightMotor(speed);
}
//**********************End forward/backward**********************

// driveOmni takes EITHER a + or - for either speed and this will 
//manage the direction
//These values should come from the analog control values
//QUESTION: Is the positive value for the center wheel left or right?
//ASSUMPTION: Positive = right
// The complex calculation for the center wheel is done in teleop
void Drive_driveOmni(int inLineSpeed, int centerSpeed){
	centerMotor(centerSpeed);
	if (inLineSpeed>=0){
		Drive_forward(inLineSpeed);
	}
	else {
		Drive_backward(-inLineSpeed); //remember that "backward" takes a positive value
	}
}

//Kills all motors
void Drive_allStop(){	
	centerMotor(0);
	leftMotor(0);
	rightMotor(0);
}
