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

void SetPower(int Left, int Right)
{
	Motors_SetSpeed(S3, 1, 2, -Right);
	Motors_SetSpeed(S3, 1, 1, Left);
}

void MoveStraight()
{
	Motors_SetSpeed(S3, 1, 2, -10);
	Motors_SetSpeed(S3, 1, 1, 10);
}

void FollowLine() //line follower
{
	while(true)
	{
		//updates IR
		IR_Update();

		//does the line following
		if(IR_LeftValue.C > Threashold && IR_LeftValue.D > Threashold)
		{
			//move straight
			writeDebugStreamLine("Straight");
			MoveStraight();
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

		//quits if we're are close
		if(Distance() == 1)
		{
			Stop();
			break;
		}
	}
}
