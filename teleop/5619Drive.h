#pragma config(Sensor, S1, Motor, sensorI2CCustom)

#include "../Libraries/Motors.h"

/*

 SPEED PARAMETERS

*/

const int scissorSpeed = 10; // TODO tune
const int sweeperSpeed = 10; // TODO tune

/*

 BUS ADDRESS DATA

*/

tSensors MotorController = S1;
const int leftMotorDaisyChainLevel=1;
const int rightMotorDaisyChainLevel=1;
const int leftMotorNumber=1;
const int rightMotorNumber=2;
//Notice that motor (S3,1,2) is wired backwards so a positive value is back
// (S3,1,1) = left motors
// (S3,1,2) = right motors (backward)

/*

 DEBUG PARAMETERS

*/

bool TESTBOT=false;
bool DEBUG=false;


//**********************************************
//Wrapper functions for left/right/center motors
void leftMotor(int spd){
	if (TESTBOT){
		writeDebugStreamLine("LEFT POWER %i",spd);
		Sleep(1000);
	}
	else {
		Motors_SetSpeed(MotorController, leftMotorDaisyChainLevel, leftMotorNumber, spd);
	}
}

void rightMotor(int spd){
	if (TESTBOT){
		writeDebugStreamLine("RIGHT POWER %i",spd);
		Sleep(1000);
	}
	else {
		Motors_SetSpeed(MotorController, rightMotorDaisyChainLevel, rightMotorNumber, spd);
	}
}

void centerMotor(int spd){
	if (TESTBOT){
		writeDebugStreamLine("CENTER POWER %i",spd);
		Sleep(1000);
	}
	else {
		Motors_SetSpeed(MotorController,2,2,spd);
	}
}
//end wrapper functions
//****************************************************************

//****************************************************************
//Spin Functions: Gives opposite speeds to left vs right
void Drive_spinRight(int speed)
{
	if (DEBUG){
		writeDebugStreamLine("in Drive_spinRight");
	}
	leftMotor(speed);
	rightMotor(speed);
}

void Drive_spinLeft(int speed)
{
	if (DEBUG){
		writeDebugStreamLine("in Drive_spinLeft");
	}
	leftMotor(-speed);
	rightMotor(-speed);
}
//*****************End Spin Functions******************************

//turn is a catch all turn function that will act like a
//tank drive.  The input are the speeds of the two sides of the
//robot.  The calling function has to manage their power difference
//Values can be + or - but the right side will be inverted per the wiring of the motors
void Drive_turn(int leftSpeed, int rightSpeed){
	if (DEBUG){
		writeDebugStreamLine("in Drive_turn");
	}
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
	if (DEBUG){
		writeDebugStreamLine("in Drive_forward");
	}
	speed=abs(speed);  //positive value check
	leftMotor(speed);
	rightMotor(-speed);
}
// Backward takes ONLY A POSITIVE VALUE assuming that
//we want to go backward
void Drive_backward (int speed)
{
	if (DEBUG){
		writeDebugStreamLine("in Drive_backward");
	}
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

/*

 SWEEPER CONTROL

*/

void Drive_sweeper(int speed)
{
	Motors_SetSpeed(S1, 1, 1, speed); // TODO check these addresses
}

void Drive_sweeperIn()
{
	Drive_sweeper(sweeperSpeed); // TODO check sign
}

void Drive_sweeperOut()
{
	Drive_sweeper(-sweeperSpeed); // TODO check sign
}

/*

 SCISSOR LIFT CONTROL

*/

void Drive_scissorLift(int speed)
{
	// TODO: limit switches
	Motors_SetSpeed(S1, 3, 1, speed);
	Motors_SetSpeed(S1, 3, 2, -speed);
}

void Drive_scissorLiftUp()
{
	Drive_scissorLift(-scissorSpeed); // TODO check whether this should be negative
}

void Drive_scissorLiftDown()
{
	Drive_scissorLift(scissorSpeed); // TODO check whether this should be negative
}

/*

 MISCELLANEOUS

*/

//Kills all motors
void Drive_allStop(){
	centerMotor(0);
	leftMotor(0);
	rightMotor(0);
}

//Get encoder position
long GetEncValue(int side) {
	long pos = 0;
	//left
	if(side == leftMotorNumber) {
		pos = I2C_GetEncoderPosition(MotorController,leftMotorDaisyChainLevel, leftMotorNumber);
	}
	//right
	else if(side == rightMotorNumber) {
		pos = I2C_GetEncoderPosition(MotorController,rightMotorDaisyChainLevel, rightMotorNumber);
	}
	return pos;
}

//Set encoder position and move to that position
void SetEncValue(int side, long value, byte speed) {
	//left
	if(side == leftMotorNumber) {
		I2C_SetEncoderPosition(MotorController, leftMotorDaisyChainLevel, leftMotorNumber, value, speed);
	}
	//right
	else if(side == rightMotorNumber) {
		I2C_SetEncoderPosition(MotorController, rightMotorDaisyChainLevel, rightMotorNumber, value, speed);
	}
}

//Random number in range
int randRange(int min, int max) {
	return (rand() % (max-min)) + min;
}

void move77(int x) {
		//Move fwd
		if(x == 1) {

		}
		//Move bckwd
		else if(x == -1) {

		}
		//turn fwd right
		else if(x == 2) {

		}
		//turn fwd left
		else if(x == 3) {

		}
		//turn bckwd right
		else if(x == -3) {

		}
		//turn bckwd left
		else if(x == -2) {

		}

}

//Erroneous movement everywhere
void randomMotion() {
	int lastCommand = 0;
	int currentCommand = 0;
	const int thisSpd = 50;
	while(true) {
		lastCommand = currentCommand;
		currentCommand = randRange(0, 5);
		if(Motors_IsMoving(MotorController, rightMotorDaisyChainLevel, rightMotorNumber) != true || Motors_IsMoving(MotorController, leftMotorDaisyChainLevel, leftMotorNumber) != true) {
				move77(-lastCommand);
		}
		else {
				move77(currentCommand);
		}
	}
}
