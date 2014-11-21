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

#include "../teleop/5619Drive.h"

const int regSpd = -5;
const int turn = 2;
const int threshold = 10;
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

bool IRLineFollow(tSensors IRport, tSensors Motorport) {
	updateIR(IRport);
	// Is the beacon front left or front right?
	//sensor 2 and sensor 4
	if (IRvalues[1] > threshold || IRvalues[3] > threshold)
	{
			// Is it front left?
			if (IRvalues[1] > IRvalues[3])
			{
				Drive_spinLeft(-regSpd);

			}
			// Is it front right?
			else if (IRvalues[1] < IRvalues[3]) {

				Drive_spinRight(-regSpd);
			}
			// Is the beacon close and to the left?
			else if (abs(IRvalues[2] - IRvalues[1]) <= threshold) {
				Drive_forward(regSpd);
				//strafeLeft(Motorport, regSpd / (IRvalues[2] == IRvalues[1]));
				strafeLeft(Motorport, regSpd / 4);
			}
			// Is the becon close and to the right?
			else if (abs(IRvalues[2] - IRvalues[3]) < threshold) {
				Drive_forward(regSpd);
				//strafeRight(Motorport, regSpd * (1 - (1 / (11 - IRvalues[2] - IRvalues[3]))));
				strafeRight(Motorport, regSpd / 4);
			}
			else {
				// TODO: pass back whether or not we're at the center goal
				return false;
			}
	}	else {
		// TODO: pass back whether or not we're at the center goal
		return false;
	}
}

void moveDownRamp(tSensors port) {
		Drive_forward(39);
		Sleep(1500);
}
