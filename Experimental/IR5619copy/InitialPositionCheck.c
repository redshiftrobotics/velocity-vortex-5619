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

void Stop()
{
	Motors_SetSpeed(S3, 1, 2, 0);
	Motors_SetSpeed(S3, 1, 1, 0);
}

int Distance()
{
	//updates IR
	IR_Update();

	if(IR_LeftValue.C > Threashold && IR_LeftValue.D > Threashold && IR_RightValue.B > Threashold && IR_RightValue.C > Threashold)
	{
		writeDebugStreamLine("Medium");
		return 2;
	}
	else if(IR_LeftValue.C > Threashold && IR_RightValue.C > Threashold && IR_LeftValue.D < Threashold && IR_RightValue.B < Threashold)
	{
		writeDebugStreamLine("Long");
		return 3;
	}
	else if(IR_LeftValue.D > Threashold && IR_RightValue.B > Threashold && IR_LeftValue.C < Threashold && IR_RightValue.C < Threashold)
	{
		writeDebugStreamLine("Short");
		return 1;
	}
	else
	{
		return -1;
	}
}

void FollowLine()
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

		//quits if wer are close
		if(Distance() == 1)
		{
			Stop();
			break;
		}
	}
}

int CheckConfiguration()
{
	//update the sensor values
	IR_Update();

	//write the raw data to the debug stream
	writeDebugStreamLine("Amplitude: %i", IR_LeftValue.C + IR_RightValue.C);

	if(IR_LeftValue.C + IR_RightValue.C < 25)
	{
		//position 3
		writeDebugStreamLine("Position 3");
		return 3;
	}
	else if(IR_LeftValue.C + IR_RightValue.C >= 25 && IR_LeftValue.C + IR_RightValue.C < 100)
	{
		//position 2
		writeDebugStreamLine("Position 2");
		return 2;
	}
	else if(IR_LeftValue.C + IR_RightValue.C >= 100)
	{
		//position 1
		writeDebugStreamLine("Position 1");
		return 1;
	}

	return -1;
}

task main()
{
	//globals
	int Configuration = -1;

	//get up to the line position
	MoveStraight();
	sleep(1500);
	Stop();

	Configuration = CheckConfiguration();

	if(Configuration == 1)
	{
		//do nothing; the finder starts here
	}
	if(Configuration == 2)
	{
		//turn right
		SetPower(-20, 20);
		sleep(1800);
		Stop();

		//move forward
		SetPower(30, 30);
		sleep(3000);
		Stop();

		//turn left
		SetPower(20, -20);
		sleep(2800);
		Stop();
	}
	if(Configuration == 3)
	{
		//turn right
		SetPower(-20, 20);
		sleep(1000);
		Stop();

		//move forward
		SetPower(30, 30);
		sleep(5600);
		Stop();

		//turn left
		SetPower(20, -20);
		sleep(3700);
		Stop();
	}

	//now follow the line
	FollowLine();
}
