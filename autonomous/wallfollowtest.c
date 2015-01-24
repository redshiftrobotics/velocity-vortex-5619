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
	followWall(sonarSensor);
	sleep(2000);

}
