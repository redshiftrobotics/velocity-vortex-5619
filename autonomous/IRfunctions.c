#include "../Libraries/Drivers/hitechnic-irseeker-v2.h"

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

float readIR(tSensors link) {
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
	return chdeg(n);
	//return (n*72)-36;
}
