#ifndef AltitudeGauge_h
#define AltitudeGauge_h

#include "Arduino.h"

//Purpose: forward the altitude information from KKIM to KNano
class AltitudeGauge
{
public:
	AltitudeGauge();
	void setAltitude(float altitude);
protected:
	//Used for debugging only:
	float getAltitude();
private:
	//Approximate expected range: -5,000m to 150,000,000,000m (1.5E+11)
	float altitude;
};

#endif
