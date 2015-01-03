#include "../Libraries/I2C.h"
#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"
#include "../Libraries/Motors.h"
#include "../teleop/5619Drive.h"


/*

	(C) Copyright 2014 Aidan Wood

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
	Drive_scissorLiftUp();
	// TODO: this doesn't actually reach the center goal, but it requires physical changes to fix
	Sleep(1000);
	Drive_scissorLift(0);
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
		writeDebugStreamLine("Couldn't determine center column position!")
		return 1;
	}

	return IRdirection-4;
}

void MoveToIRPosition(int direction)
{
	switch(direction)
	{
		case 1:
			//Drive_forward(50);
			//Sleep(20);
			PrepareToDispense();
			break;
		case 2:
			Drive_turn(50, 25);
			// TODO
		case 3:
			// IR is in FTC position 3
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
	Drive_forward(regspd);
	centerMotor(50*direction);

	writeDebugStreamLine("Read IR: %i, %i", direction, strength);

	return (strength > strengthThreshold);
}

void MoveDownRamp() {
	Drive_forward(50);
	Sleep(2700);
}

void MoveToKickstand() {
	Drive_driveOmni(70, 0);
	sleep(1500);
	Drive_driveOmni(70, 0);
	Sleep(900);
	Drive_allStop();
}
