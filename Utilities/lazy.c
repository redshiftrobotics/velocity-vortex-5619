#pragma config(Sensor, S1,     ,               sensorI2CCustom)
#pragma config(Sensor, S2,     ,               sensorI2CCustom)

#include "../teleop/5619Drive.h"

task main()
{
	Drive_scissorLift(30);

}
