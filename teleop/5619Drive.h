#pragma config(Sensor, S1, Motor, sensorI2CCustom)
#pragma config(Sensor, S4,     HTPB,           sensorI2CCustom9V)

#include "../Libraries/Motors.h"
#include "../Libraries/Servos.h"
#include "../Libraries/drivers/hitechnic-protoboard.h"

/*

SPEED PARAMETERS

*/

const int scissorSpeed = 25;
const int sweeperSpeed = 70;
/*

BUS ADDRESS DATA

*/

tSensors MotorController = S1;
tSensors SweeperMotorController = S2;

const int leftMotorDaisyChainLevel=2;
const int rightMotorDaisyChainLevel=2;
const int centerMotorDaisyChainLevel=3;
const int scissorMotorDaisyChainLevel=3;
const int servoControllerDaisyChainLevel=1;

const int leftMotorNumber=1;
const int rightMotorNumber=2;
const int centerMotorNumber=2;
const int scissorMotorNumber=1;
const int sweeperMotorNumber=2;
const int grabberChannelNumber=5;
const int ballArbiterChannelNumber=6;
//Notice that motor (S3,1,2) is wired backwards so a positive value is back
// (S3,1,1) = left motors
// (S3,1,2) = right motors (backward)

/*

DEBUG PARAMETERS

*/

bool TESTBOT=false;
bool DEBUG=false;

/*

GLOBAL STATE

*/

bool scissorInitializing = false;
bool scissorInitialized = false;
int scissorLowerEncoder;

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
		Motors_SetSpeed(MotorController,centerMotorDaisyChainLevel,centerMotorNumber,spd);
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
	Motors_SetSpeed(SweeperMotorController, 1, sweeperMotorNumber, speed);
}

void Drive_sweeperIn()
{
	Drive_sweeper(sweeperSpeed); // TODO check sign
}

void Drive_sweeperOut()
{
	Drive_sweeper(-sweeperSpeed); // TODO check sign
}

void Drive_sweeperStop()
{
	Drive_sweeper(0);
}

/*

SCISSOR LIFT CONTROL

*/

bool _Drive_scissorLiftGetLimitSwitch()
{
	// Returns true if the limit switch is pressed

	int _chVal = 0;  // analog input

	wait1Msec(2000);
	// Setup all the digital IO ports as inputs (0x00) 000000 .
	if (!HTPBsetupIO(HTPB, 0x00)) {
		nxtDisplayTextLine(4, "ERROR!!");
		wait1Msec(2000);
		StopAllTasks();
	}

	eraseDisplay();

	_chVal = HTPBreadADC(HTPB, 0, 10);  // get the value for ADC channel 0, we want a 10 bit answer
	nxtDisplayTextLine(0, "A0: %d", _chVal);

	wait1Msec(50);

	if (/* TODO */ true)
	{
		return true;
	} else {
		return false;
	}
}

bool _Drive_scissorLiftCheckEncoder()
{
	// Returns true if it's safe to run the scissor lift down any further
	// TODO
	return true;
}

void Drive_scissorLift(int speed)
{
	if (!scissorInitialized || !scissorInitializing)
	{
		writeDebugStreamLine("Program attempted to run the scissor lift before initializing it!");
		writeDebugStreamLine("Cowardly refusing to risk damaging the motors.");
		return;
	}

	if (!_Drive_scissorLiftCheckEncoder())
	{
		return;
	}

	Motors_SetSpeed(S1, scissorMotorDaisyChainLevel, scissorMotorNumber, speed);
}

void Drive_scissorLiftUp()
{
	writeDebugStreamLine("Driving scissor lift up");
	Drive_scissorLift(scissorSpeed);
}

void Drive_scissorLiftDown()
{
	writeDebugStreamLine("Driving scissor lift down");
	Drive_scissorLift(-scissorSpeed);
}

void Drive_scissorLiftInit()
{
	// Initialize the scissor lift with the proper encoder position
	// This basically means that we drive the lift down until it hits the switch, then sample the encoder position
	scissorInitializing = true;

	bool hitLimitSwitch = false;
	if (!hitLimitSwitch)
	{
		Drive_scissorLiftDown();
		hitLimitSwitch = _Drive_scissorLiftGetLimitSwitch();
	}

	Drive_scissorLift(0);
	scissorLowerEncoder = Motors_GetPosition(S1, scissorMotorDaisyChainLevel, scissorMotorNumber);

	scissorInitialized = true;
}

/*

	GRABBER CONTROL

*/

void Drive_grabberDown()
{
	writeDebugStreamLine("Running grabber down");
	Servos_SetPosition(MotorController, servoControllerDaisyChainLevel, grabberChannelNumber, 10);
}

void Drive_grabberUp()
{
	writeDebugStreamLine("Running grabber up");
	Servos_SetPosition(MotorController, servoControllerDaisyChainLevel, grabberChannelNumber, 80);
}

/*

BALL ARBITER CONTROL

*/

void Drive_arbiterDispense()
{
	//writeDebugStreamLine("Running arbiter down");
	Servos_SetPosition(MotorController, servoControllerDaisyChainLevel, ballArbiterChannelNumber, 170);
}

void Drive_arbiterQueue()
{
	//writeDebugStreamLine("Running arbiter up");
	Servos_SetPosition(MotorController, servoControllerDaisyChainLevel, ballArbiterChannelNumber, 80);
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

// TODO: move this to a better place in this file.
//  Currently it needs to be here because of Drive_allStop().

void Drive_spin180()
{
	Drive_spinLeft(50);
	// This value was dead-reckoned
	Sleep(1160);
	Drive_allStop();
}

void Drive_spinLeft90()
{
	Drive_spinLeft(50);
	// This value was dead-reckoned
	Sleep(660);
	Drive_allStop();
}

void Drive_spinRight90()
{
	Drive_spinRight(50);
	// This value was dead-reckoned
	Sleep(620);
	Drive_allStop();
}
