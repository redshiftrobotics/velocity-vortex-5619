#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"

void calcPower(int angle, int goal, float* power) {
	if(angle != -1) {
		//float angle2 = angle * PI / 180;
		float angle2 = angle;
		power[0] = -goal * cosDegrees(angle2);
		power[1] = -goal * sinDegrees(angle2);
	}
	else {
		power[0] = 0;
		power[1] = 0;
	}
}

int convertRadians(int x) {
	return x * PI / 180;
}

int chdeg(int x) {
	return (x*72)-144;
}

float readIR(tSensors link) {
	int IRvalues[6];
	HTIRS2readAllDCStrength(link, IRvalues[0], IRvalues[1], IRvalues[2], IRvalues[3], IRvalues[4]);
	int n = 5;
	int total = 0;
	IRvalues[5] = -1;
	//int greatest = 0;
	for(int i = 0; i <= 4; i++)
	{
		if(IRvalues[i] >= IRvalues[n])
		{
			//greatest = IRvalues[i];
			n = i;
			total += IRvalues[i];
		}
		else {
		  //do nothing
		}
	}
	if(total != 0){
		return chdeg(n);
	}
	else
		return -1;
	//return (n*72)-36;
}
