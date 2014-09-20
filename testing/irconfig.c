#pragma config(Sensor, S1,     IR,             sensorI2CCustom)
#include "../Libraries/drivers/hitechnic-irseeker-v2.h"

int acS1, acS2, acS3, acS4, acS5 = 0;

task main()
{
	clearDebugStream();

	while(true)
	{
		readIR();

	}

}
void readIR() {
			HTIRS2readAllACStrength(IR, acS1, acS2, acS3, acS4, acS5);
	}
