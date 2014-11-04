#include "../Libraries/I2C.h"
#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"
#include "../Libraries/Motors.h"

const int regSpd = 50;
const int turn = 2;
const int threshold = 10;
int IRvalues[5];

void moveForward(tSensors port, int spd) {
		I2C_SetMotorSpeed(port, 1, 1, (sbyte)spd);
		I2C_SetMotorSpeed(port, 1, 2, (sbyte)spd);
}

void moveLeft(tSensors port, int spd) {
		I2C_SetMotorSpeed(port, 1, 1, (sbyte)-spd/turn);
		I2C_SetMotorSpeed(port, 1, 2, (sbyte)spd/turn);
}

void moveRight(tSensors port, int spd) {
		I2C_SetMotorSpeed(port, 1, 1, (sbyte)spd/turn);
		I2C_SetMotorSpeed(port, 1, 2, (sbyte)-spd/turn);
}
void stopMoving(tSensors port) {
		I2C_SetMotorSpeed(port, 1, 1, (sbyte)0);
		I2C_SetMotorSpeed(port, 1, 2, (sbyte)0);
}

void strafeRight(tSensors port, int spd) {
	I2C_SetMotorSpeed(port, 2, 2, (sbyte)spd);
}

void strafeLeft(tSensors port, int spd) {
	I2C_SetMotorSpeed(port, 2, 2, (sbyte)-spd);
}

void updateIR(tSensors port) {
	HTIRS2readAllDCStrength(port, IRvalues[0], IRvalues[1], IRvalues[2], IRvalues[3], IRvalues[4]);
	for(int i = 0; i <= 4; i++)
	{
		writeDebugStreamLine("%i", IRvalues[i]);
	}
}

void IRLineFollow(tSensors IRport, tSensors Motorport) {
	updateIR(IRport);
	if(IRvalues[1] > threshold || IRvalues[3] > threshold)
	{
			if(IRvalues[1] > IRvalues[3])
				moveRight(Motorport, regSpd);
			else if(IRvalues[1] < IRvalues[3])
				moveLeft(Motorport, regSpd);
			else if(abs(IRvalues[2] == IRvalues[1]) <= threshold) {
				moveForward(Motorport, regSpd);
				//strafeLeft(Motorport, regSpd / (IRvalues[2] == IRvalues[1]));
				strafeLeft(Motorport, regSpd / 4);
			}
			else if(abs(IRvalues[2] - IRvalues[3]) < threshold) {
				moveForward(Motorport, regSpd);
				//strafeRight(Motorport, regSpd * (1 - (1 / (11 - IRvalues[2] - IRvalues[3]))));
				strafeRight(Motorport, regSpd / 4);
			}
			else
				break;
	}
	else
		break;

}

void moveDownRamp(tSensors port) {
		strafeRight(port, regSpd / 2);
}
