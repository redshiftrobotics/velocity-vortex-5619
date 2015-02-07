#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"

int turn = 0;

void calcPower(int angle, int goal, float* power) {
		float angle2 = angle * PI / 180;
		power[0] = -goal * cos(angle2);
		power[1] = -goal * sin(angle2);
}

int convertRadians(int x) {
	return x * PI / 180;
}

int chdeg(int x) {
	return (x*72)-144;
}

int readIR(tSensors link) {
	int IRvalues[5];
	HTIRS2readAllDCStrength(link, IRvalues[0], IRvalues[1], IRvalues[2], IRvalues[3], IRvalues[4]);
	int n = -1;
	int greatest = 0;
	for(int i = 0; i <= 4; i++)
	{
		if(IRvalues[i] > greatest)
		{
			greatest = IRvalues[i];
			n = i;
		}
		else if(IRvalues[i] == greatest) {
			//take values again
		}
		else {
		  //do nothing
		}
	}
	return chdeg(n+1);
	//return (n*72)-36;
}

int readIR2(tSensors link) {
	int IRvalue = 0;
	IRvalue = HTIRS2readDCDir(link);
	IRvalue = 30*(10 - IRvalue);
	return IRvalue;
}

int getSonarVal(tSensors link) {
	int val = -1;
	//ADD STUFF HERE
	return val;
}

bool PIDController() {
	int Kp = 60; //placeholder
	int Ki = 60; //placeholder
	int Kd = 60; //placeholder

	int goal = 50; //placeholder

	int lastError = 0;

	int derivative = 0;
	int Tp = 25;
	int integral = 0;

	int sonarVal = 0;
	while(true) {
		sonarVal = getSonarVal(S1); //placeholder
    int error = sonarVal - goal;
		derivative = error - lastError;
		int turn = (Kp * error) + (Ki * integral) + (Kd * derivative);
		integral += error;
		turn /= 100;
		int powRight = Tp - turn;
		int powLeft = Tp + turn;
		//Run right motor
		//Run left motor
		lastError = error;
	}
	return true;
}
