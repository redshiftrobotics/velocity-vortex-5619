#pragma config(Sensor, S1,     ,               sensorI2CCustom)
#pragma config(Sensor, S2,     ,               sensorI2CCustom)
#pragma config(Sensor, S3,     IRsensor,       sensorI2CCustom)

#include "5619Drive.h"

task main()
{
	Drive_arbiterDispense();


}
