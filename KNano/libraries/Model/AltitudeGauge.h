#ifndef ALTITUDE_GAUGE_h
#define ALTITUDE_GAUGE_h

#include <Arduino.h>
#include <SevSeg.h>
#include <Interface_LEDAggregator.h>
#include <LED.h>

/* 3 digit seven segment display (with decimal points) and 4 LEDs specifying altitude units (m, km, Mm, Gm).
 * 
 * Note: The seven segment display requires frequent refreshes in order to work.
 */
class AltitudeGauge : public Interface_LEDAggregator
{
public:
	AltitudeGauge();
	void setAltitude(float altitude);
	void refreshSevenSegmentDisplay();
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();//Note: does not test sevenSegmentDisplay
	
private:
	SevSeg sevenSegmentDisplay;
	float altitude; //Approximate expected range: -5,000m to 150,000,000,000m (1.5E+11)
	LED led_Altitude_m;
	LED led_Altitude_km;
	LED led_Altitude_Mm;
	LED led_Altitude_Gm;
};

#endif
