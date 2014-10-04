#include "../Libraries/I2C.h"
#include "JoystickDriver.c"

const int Deadband = 10;
const int MiddleSpeed = 40;

void HandleSides()
{
	if (abs(joystick.joy1_y2) > deadband)
	{
		I2C_SetMotorSpeed(S1, 1, 2, -joystick.joy1_y2);
	}
	
	if (abs(joystick.joy1_y1) > deadband)
	{
		I2C_SetMotorSpeed(S1, 1, 1, joystick.joy1_y1);
	}
}

void HandleMiddle()
{
	if (joy1Btn(8))
	{
		I2C_SetMotorSpeed(S1, 2, 2, MiddleSpeed);
	}
	
	if (joy1Btn(7))
	{
		I2C_SetMotorSpeed(S1, 2, 2, -MiddleSpeed);
	}
}

task main()
{
	while(true)
	{
		getJoystickSetting(joystick);
		
		HandleSides();
		HandleMiddle();
		
		Sleep(10);
	}
}
