#pragma config(Sensor, S1,     IR,             sensorI2CCustom)
#include "../Libraries/drivers/hitechnic-irseeker-v2.h"

int acS1, acS2, acS3, acS4, acS5 = 0;

task main()
{
	clearDebugStream();

	while(true)
	{
		HTIRS2readAllACStrength(IR, acS1, acS2, acS3, acS4, acS5);

		//writeDebugStream("a");
		writeDebugStream("%i,", acS1);

		//writeDebugStreamLine("b");
		writeDebugStream("%i,", acS2);

		//writeDebugStreamLine("c");
		writeDebugStream("%i,", acS3);

		//writeDebugStreamLine("d");
		writeDebugStream("%i,", acS4);

		//writeDebugStreamLine("e");
		writeDebugStreamLine("%i,", acS5);
	}
}
