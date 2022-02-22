#ifndef GAUGE_TOWER_h
#define GAUGE_TOWER_h

#include <Arduino.h>

#include <Interface_LEDAggregator.h>

#include <LED.h>
#include <AltitudeGauge.h>


class GaugeTower : public Interface_LEDAggregator
{
private:
	//Parts:
	LED led_Altitude_m;
	LED led_Altitude_km;
	LED led_Altitude_Mm;
	LED led_Altitude_Gm;
	AltitudeGauge altitudeGauge;

public:
	GaugeTower();
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
};

#endif