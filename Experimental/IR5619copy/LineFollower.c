#pragma config(Sensor, S1, IROne, sensorI2CCustom)
#pragma config(Sensor, S2, IRTwo, sensorI2CCustom)
#pragma config(Sensor, S3, Motor, sensorI2CCustom)

#include "IR.c"
#include "Motors.h"

const int Threashold = 25;

void MoveRight()
{
	Motors_SetSpeed(S3, 1, 2, -10);
	Motors_SetSpeed(S3, 1, 1, 5);
}

void MoveLeft()
{
	Motors_SetSpeed(S3, 1, 2, -5);
	Motors_SetSpeed(S3, 1, 1, 10);
}

void MoveStraight()
{
	Motors_SetSpeed(S3, 1, 2, -10);
	Motors_SetSpeed(S3, 1, 1, 10);
}

task main()
{
	int Distance = 3;

	while(true)
	{
		//update IR
		IR_Update();

		//stops the program if we are short
		if(IR_LeftValue.C > Threashold && IR_LeftValue.D > Threashold && IR_RightValue.B > Threashold && IR_RightValue.C > Threashold)
		{
			nxtDisplayString(1, "Medium");
			writeDebugStreamLine("Medium");
			Distance = 2;
		}
		else if(IR_LeftValue.C > Threashold && IR_RightValue.C > Threashold && IR_LeftValue.D < Threashold && IR_RightValue.B < Threashold)
		{
			nxtDisplayString(1, "Long");
			writeDebugStreamLine("Long");
			Distance = 3;
		}
		else if(IR_LeftValue.D > Threashold && IR_RightValue.B > Threashold && IR_LeftValue.C < Threashold && IR_RightValue.C < Threashold)
		{
			nxtDisplayString(1, "Short");
			writeDebugStreamLine("Short");

			Distance = 1;
		}

		//does the line following
		if(Distance == 1)
		{
			while(true)
			{
				Motors_SetSpeed(S3, 1, 2, 0);
				Motors_SetSpeed(S3, 1, 1, 0);
			}
		}
		else if(IR_LeftValue.C > Threashold && IR_LeftValue.D > Threashold)
		{
			//move straight
			writeDebugStreamLine("Straight");
			if(Distance == 3)
			{
				MoveRight();
			}
			else
			{
				MoveStraight();
			}
		}
		else if(IR_LeftValue.C > Threashold)
		{
			//go left
			writeDebugStreamLine("Left");
			MoveLeft();
		}
		else if(IR_LeftValue.D > Threashold)
		{
			//go right
			writeDebugStreamLine("Right");
			MoveRight();
		}
	}
}
