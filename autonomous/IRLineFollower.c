#include "../Libraries/I2C.h"
#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"
#include "../Libraries/Motors.h"


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

const int regSpd = -25;
const int turn = 2;
const int threshold = 10;
int IRvalues[5];

void moveForward(tSensors port, int spd) {
	// Tested
	I2C_SetMotorSpeed(port, 1, 1, (sbyte)spd/turn);
	I2C_SetMotorSpeed(port, 1, 2, (sbyte)-spd/turn);
}

void moveLeft(tSensors port, int spd) {
		I2C_SetMotorSpeed(port, 1, 1, (sbyte)-spd/turn);
		I2C_SetMotorSpeed(port, 1, 2, (sbyte)-spd/turn);
}

void moveRight(tSensors port, int spd) {
	// Tested
	I2C_SetMotorSpeed(port, 1, 1, (sbyte)spd);
	I2C_SetMotorSpeed(port, 1, 2, (sbyte)spd);
}

void stopMoving(tSensors port) {
		I2C_SetMotorSpeed(port, 1, 1, (sbyte)0);
		I2C_SetMotorSpeed(port, 1, 2, (sbyte)0);
}

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

void IRLineFollow(tSensors IRport, tSensors Motorport) {
	updateIR(IRport);
	// Is the beacon front left or front right?
	if(IRvalues[1] > threshold || IRvalues[3] > threshold)
	{
			// Is it front left?
			if(IRvalues[1] > IRvalues[3])
			{
				moveLeft(Motorport, regSpd);

			}
			// Is it front right?
			else if(IRvalues[1] < IRvalues[3]) {

				moveRight(Motorport, regSpd);
			}
			// Is the beacon close and to the left?
			else if(abs(IRvalues[2] - IRvalues[1]) <= threshold) {
				moveForward(Motorport, regSpd);
				//strafeLeft(Motorport, regSpd / (IRvalues[2] == IRvalues[1]));
				strafeLeft(Motorport, regSpd / 4);
			}
			// Is the becon close and to the right?
			else if(abs(IRvalues[2] - IRvalues[3]) < threshold) {
				moveForward(Motorport, regSpd);
				//strafeRight(Motorport, regSpd * (1 - (1 / (11 - IRvalues[2] - IRvalues[3]))));
				strafeRight(Motorport, regSpd / 4);
			}
			else {
				return;
			}
	}
	else
		return;
}

void moveDownRamp(tSensors port) {
		strafeRight(port, regSpd / 2);
}
