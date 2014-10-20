#pragma config(Sensor, S1, IROne, sensorI2CCustom)
#pragma config(Sensor, S2, IRTwo, sensorI2CCustom)
#pragma config(Sensor, S3, Motor, sensorI2CCustom)

#include "IR.c"
#include "Motors.h"

const int Threashold = 25;


task main()
{
	while(true)
	{
		//update IR
		IR_Update();

		writeDebugStream("Left::: ");
		writeDebugStream("A: %i, ", IR_RightValue.A);
		writeDebugStream("B: %i, ", IR_RightValue.B);
		writeDebugStream("C: %i, ", IR_RightValue.C);
		writeDebugStream("D: %i, ", IR_RightValue.D);
		writeDebugStreamLine("E: %i", IR_RightValue.E);

		writeDebugStream("Right::: ");
		writeDebugStream("A: %i, ", IR_LeftValue.A);
		writeDebugStream("B: %i, ", IR_LeftValue.B);
		writeDebugStream("C: %i, ", IR_LeftValue.C);
		writeDebugStream("D: %i, ", IR_LeftValue.D);
		writeDebugStreamLine("E: %i", IR_LeftValue.E);

		writeDebugStreamLine("Total: %i", IR_LeftValue.C + IR_RightValue.C);
	}
}
