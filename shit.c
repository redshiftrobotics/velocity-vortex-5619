#pragma config(Sensor, S1, Motor, sensorI2CCustom)
#pragma config(Sensor, S4,     HTPB,           sensorI2CCustom9V)

#include "teleop/5619Drive.h"

task main()
{

	Drive_scissorLiftInit();

}
