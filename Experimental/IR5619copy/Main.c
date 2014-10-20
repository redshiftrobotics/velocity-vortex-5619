#pragma config(Sensor, S1, IROne, sensorI2CCustom)
#pragma config(Sensor, S2, IRTwo, sensorI2CCustom)
#pragma config(Sensor, S3, Motor, sensorI2CCustom)

#include "IR.c"
#include "Motors.h"


const int Threashold = 25;

void MoveStraight()
{
	nxtDisplayString(2, "Straight");
	Motors_SetSpeed(S3, 1, 2, -10);
	Motors_SetSpeed(S3, 1, 1, 10);
}

void MoveLeft()
{
	nxtDisplayString(2, "Left");
	Motors_SetSpeed(S3, 1, 2, 10);
	Motors_SetSpeed(S3, 1, 1, -5);
}

void MoveRight()
{
	nxtDisplayString(2, "Right");
	Motors_SetSpeed(S3, 1, 2, 5);
	Motors_SetSpeed(S3, 1, 1, -10);
}

task main()
{

	while(true)
	{
		//wait
		wait1Msec(100);

		//call the update (stores data in varaibles)
		IR_Update();

		//write updates varaibles to the display
		eraseDisplay();
		//nxtDisplayString(1, "1B: %i", IR_LeftValue.B);
		//nxtDisplayString(2, "1C: %i", IR_LeftValue.C);
		//nxtDisplayString(3, "1D: %i", IR_LeftValue.D);
		//nxtDisplayString(4, "2B: %i", IR_RightValue.B);
		//nxtDisplayString(5, "2C: %i", IR_RightValue.C);
		//nxtDisplayString(6, "2D: %i", IR_RightValue.D);

		//write to debug stream
		writeDebugStream("%i, ", IR_LeftValue.B);
		writeDebugStream("%i, ", IR_LeftValue.C);
		writeDebugStream("%i, ", IR_LeftValue.D);
		writeDebugStream("%i, ", IR_RightValue.B);
		writeDebugStream("%i, ", IR_RightValue.C);
		writeDebugStreamLine("%i", IR_RightValue.D);

		//does distance computation


		//go straight
		if(IR_LeftValue.C > Threashold && IR_RightValue.C > Threashold && IR_LeftValue.B < Threashold && IR_LeftValue.D < Threashold && IR_RightValue.B < Threashold && IR_RightValue.D < Threashold)
		{
			MoveStraight();
		}
		else
		{
			int Right = 0;
			int Left = 0;

			//go left
			if(IR_LeftValue.B > Threashold)
			{
				Left++;
			}

			if(IR_LeftValue.C < Threashold && IR_RightValue > Threashold)
			{
				Left++;
			}

			if(IR_RightValue.B > Threashold)
			{
				Left++;
			}

			//go right
			if(IR_RightValue.D > Threashold)
			{
				Right++;
			}

			if(IR_LeftValue.D > Threashold)
			{
				Right++;
			}

			if(IR_RightValue.C < Threashold && IR_LeftValue > Threashold)
			{
				Right++;
			}

			nxtDisplayString(5, "%i", Left);
			nxtDisplayString(6, "%i", Right);

			if(Left > Right)
			{
				MoveLeft();
			}
			else if(Right > Left)
			{
				MoveRight();
			}
			else
			{
				MoveStraight();
			}
		}
	}
}
