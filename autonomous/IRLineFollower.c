#include "../Libraries/I2C.h"
#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"
#include "../Libraries/Motors.h"
#include "../teleop/5619Drive.h"


/*

	(C) Copyright 2014 Aidan Wood, Alex Jordan

	This file is part of the FTC team 5619 application code.

	FTC team 5619 application code is free software: you can
	redistribute it and/or modify it under the terms of the GNU
	General Public License as published by the Free Software
	Foundation, either version 2 of the License, or (at your
	option) any later version.

	FTC team 5619 application code is distributed in the hope that
	it will be useful, but WITHOUT ANY WARRANTY; without even the
	implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
	PURPOSE.  See the GNU General Public License for more details.

	You should have received a copy of the GNU General Public
	License along with FTC team 5619 application code. If not, see
	<http://www.gnu.org/licenses/>.

*/

#include "../teleop/5619Drive.h"

const int regSpd = -5;
const int turn = 2;
const int strengthThreshold = 280;
int IRvalues[5];

void strafeRight(tSensors port, int spd) {
	I2C_SetMotorSpeed(port, 2, 2, (sbyte)spd);
}

void strafeLeft(tSensors port, int spd) {
	I2C_SetMotorSpeed(port, 2, 2, (sbyte)-spd);
}

void updateIR(tSensors port) {
	HTIRS2readAllDCStrength(port, IRvalues[0], IRvalues[1], IRvalues[2], IRvalues[3], IRvalues[4]);
	for(int i = 0; i <= 4; i++)
	{
		writeDebugStreamLine("%i", IRvalues[i]);
	}
}

void PrepareToDispense()
{
	Drive_spin180();
}

int GetIRPosition(tSensors IRport)
{
	int strength;
	int direction;
	HTIRS2readEnhanced(IRport, direction, strength);

	writeDebugStreamLine("Read IR: %i, %i", direction, strength);

	return direction;
}

int GetIRFieldPosition(int IRdirection)
{
	if (IRdirection < 5 || IRdirection > 7)
	{
		// We can't determine the field position
		writeDebugStreamLine("Couldn't determine center column position!");
		return 1;
	}

	return IRdirection-4;
}

void MoveToIRPosition(int direction)
{
	switch(direction)
	{
		case 1:
			Drive_forward(50);
			Sleep(20);
			Drive_forward(0);
			break;
		case 2:
			Drive_turn(100, 30);
			Sleep(75); //aj what did you mean by sleep(75 0)?
			Drive_forward(100);
			Sleep(500);
			Drive_turn(30, 100);
			Sleep(500);
			break;
		case 3:
			// IR is in FTC position 3
			break;
		default:
			// Do nothing
	}
}

bool IRLineFollow(tSensors IRport)
{
	int strength;
	int direction;
	HTIRS2readEnhanced(IRport, direction, strength);
	direction -= 5;
	int rightSpeed = 30;
	int leftSpeed = 30;
	const int speedAddition = 10;

	// TODO: this logic needs to be tested
	if (direction < 0)
	{
		// IR is to the left, increase the right motor speed
		rightSpeed += speedAddition;
	} else if (direction > 0) {
		// IR is to the right, increase the left motor speed
		leftSpeed += speedAddition;
	}

	Drive_turn(leftSpeed, rightSpeed);

	//writeDebugStreamLine("Read IR: %i, %i", direction, strength);
	writeDebugStreamLine("Running motors at %i, %i", leftSpeed, rightSpeed);

	return (strength > strengthThreshold);
}

void MoveDownRamp() {
	Drive_forward(50);
	Sleep(2700);
}

void RampAndTubeServos()
{
	// This needs to be called regularly because the bus times out
	Drive_grabberUp();
	Drive_arbiterQueue();
}

void MoveDownRampAndGetTube() {
	RampAndTubeServos();
	Drive_backward(50);
	RampAndTubeServos();
	Drive_scissorLiftUp();
	Sleep(2200);
	RampAndTubeServos();
	Drive_scissorLift(0);
	// Continue running the chassis motors
	Sleep(2600);
	RampAndTubeServos();
	Drive_forward(0);
	Drive_arbiterDispense();
	Sleep(750);
	Drive_grabberDown();
	Sleep(750);

}

void MoveTubesToParkingZone()
{
	writeDebugStreamLine("Running MoveTubesToParkingZone()");
	Drive_turn(50, 20);
	writeDebugStreamLine("Running initial turn");
	Sleep(600);
	Drive_forward(100);
	writeDebugStreamLine("Running straight line");
	Sleep(500);
	Drive_turn(20, 60);
	writeDebugStreamLine("Running secondary turn");
	Sleep(900);
	Drive_forward(100);
	writeDebugStreamLine("Running second straight line");
	Sleep(1250);
	writeDebugStreamLine("Running final turn");
	Drive_turn(100, 10);
	Sleep(750);
	Drive_forward(0);
}

void MoveToZone() {
	Drive_forward(50);
	Sleep(1900);
}

void MoveToKickstand() {
	// Note: this is all backward because the robot orientation is flipped
	Drive_spinRight90();
	Drive_backward(50);
	Sleep(750);
	Drive_spinLeft90();
	Drive_backward(50);
	Sleep(1100);
	Drive_allStop();
}


void followWall(tSensors sonar) {

		//cVal = SensorValue[sonar];
		//writeDebugStreamLine("%i", SensorValue[sonar]);
		//int dif = cVal - pVal;
		//if(dif > 0) {
		//		leftSpd + (dif*2.3);
		//		rightSpd - (dif*2.3);
		//}
		//else if(dif < 0) {
		//	leftSpd - (dif*2.3);
		//	rightSpd + (dif*2.3);
		//}
		//pVal = cVal;
		//Drive_turn(leftSpd, rightSpd);

}
