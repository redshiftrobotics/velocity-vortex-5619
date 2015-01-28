#pragma config(Sensor, S1,     ,               sensorI2CCustom)
#pragma config(Sensor, S2,     ,               sensorI2CCustom)
#pragma config(Sensor, S3,     IRsensor,       sensorI2CCustom)
#pragma config(Sensor, S4,     sonarSensor,    sensorSONAR)

#include "../Libraries/I2C.h"
#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"
#include "../Libraries/Motors.h"
#include "../teleop/5619Drive.h"
#include "IRLineFollower.c"

task main()
{
	int pVal = 0;
	int cVal = 0;

	int leftSpd = 40;
	int rightSpd = 40;

	cVal = SensorValue[sonarSensor];
	pVal = cVal;
	while(true) {
		cVal = SensorValue[sonarSensor];
		writeDebugStreamLine("%i", SensorValue[sonarSensor]);
		int dif = cVal - pVal;
		if(dif > 0) {
				leftSpd + (dif*2.3);
				rightSpd - (dif*2.3);
		}
		else if(dif < 0) {
			leftSpd - (dif*2.3);
			rightSpd + (dif*2.3);
		}
		pVal = cVal;
		writeDebugStreamLine("%i:%i", leftSpd, rightSpd);
		Drive_turn(leftSpd, rightSpd);
	}
	//while(true) {
	//	writeDebugStreamLine("%i", SensorValue[sonarSensor]);
	//}

}
