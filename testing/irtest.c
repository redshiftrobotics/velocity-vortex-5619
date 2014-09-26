#pragma config(Sensor, S1,     IR,             sensorI2CCustom)
#include "../Libraries/drivers/hitechnic-irseeker-v2.h"
#include "../Libraries/I2C.h"

int acS1, acS2, acS3, acS4, acS5 = 0;

task main()
{
	clearDebugStream();
	
	writeDebugStream("#S1:S2:S3:S4:S5:tick");
	
	while(true)
	{
		// get values
		
		HTIRS2readAllACStrength(IR, acS1, acS2, acS3, acS4, acS5);
		
		// debug stream debugging
		
		writeDebugStream("%i:%i:%i:%i:%i:%i", acS1, acS2, acS3, acS4, acS5, nPgmTime);
		
		// onboard debugging
		
		eraseDisplay();
		
		nxtDisplayString(1, "nPgmTime: %i", nPgmTime);
		nxtDisplayString(2, "acS1: %i", acS1);
		nxtDisplayString(2, "acS2: %i", acS2);
		nxtDisplayString(2, "acS3: %i", acS3);
		nxtDisplayString(2, "acS4: %i", acS4);
		nxtDisplayString(2, "acS5: %i", acS5);
		
		// movement
		
		// FIXME: I'm not sure exactly what chassis we're going to use
		/*I2C_SetMotorSpeed(S1, 1, 1, 10);
		I2C_SetMotorSpeed(S1, 1, 1, 10);
		I2C_SetMotorSpeed(S1, 1, 1, 10);
		I2C_SetMotorSpeed(S1, 1, 1, 10);*/
	}
}
