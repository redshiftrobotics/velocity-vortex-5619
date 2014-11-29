
// Tankdrive.c

/*

	(C) Copyright 2014 Samin Zach

	This file is part of the FTC team 6157 application code.

	FTC team 6157 application code is free software: you can
	redistribute it and/or modify it under the terms of the GNU
	General Public License as published by the Free Software
	Foundation, either version 2 of the License, or (at your
	option) any later version.

	FTC team 6157 application code is distributed in the hope that
	it will be useful, but WITHOUT ANY WARRANTY; without even the
	implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
	PURPOSE.  See the GNU General Public License for more details.

	You should have received a copy of the GNU General Public
	License along with FTC team 6157 application code. If not, see
	<http://www.gnu.org/licenses/>.

*/
#pragma config(Sensor, S1, , sensorI2CCustom)

#include "../Libraries/Motors.h"
#include "../Libraries/Servos.h"

#include "JoystickDriver.c"

int constrain(int x, int min, int max){
	// constraining movment to a set of numbers between min and max
	if (x > max)
		return max;
	if (x < min)
		return min;
	return x;
}

int joymotor (int joy)
{
	// deadzone
	if(joy > 7 || joy < -7)
	{
		int motor = joy * 100 / 127;
	}
	else
	{
		return 0;
	}
	return constrain(motor,-100,100);// constrain function
}

task main()
{
	int SpeedLeft;
	int SpeedRight;
	int servoangle;

	while(true)
	{
		getJoystickSettings(joystick);

		SpeedLeft = joymotor(joystick.joy1_y1);
		SpeedRight = joymotor(joystick.joy1_y2);

		if(joy1Btn(1) == 1)
		{
				servoangle = 180;
		}
		else if (joy1Btn(2) == 1)
		{
				servoangle = 0;
		}

		Servos_SetPosition(S4, 2, 1, servoangle);
		Motors_SetSpeed(S4, 1, 1, SpeedLeft);
		Motors_SetSpeed(S4, 1, 2, SpeedRight);
	}

}
