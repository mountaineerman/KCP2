#ifndef ALTITUDE_GAUGE_h
#define ALTITUDE_GAUGE_h

#include <Arduino.h>

#include <SevSeg.h>

#include <Interface_LEDAggregator.h>

class AltitudeGauge : public Interface_LEDAggregator
{
public:
	AltitudeGauge();
	void setAltitude(float altitude);
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
protected:
	
private:
	SevSeg sevenSegmentDisplay;
	float altitude; //Approximate expected range: -5,000m to 150,000,000,000m (1.5E+11)
};

#endif
