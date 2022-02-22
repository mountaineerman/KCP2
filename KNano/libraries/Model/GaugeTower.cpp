#include <Arduino.h>

#include <GaugeTower.h>
#include "../../configuration.h"


GaugeTower::GaugeTower()
	: led_Altitude_m	(PIN_ALTITUDE_m)
	, led_Altitude_km	(PIN_ALTITUDE_km)
	, led_Altitude_Mm	(PIN_ALTITUDE_Mm)
	, led_Altitude_Gm	(PIN_ALTITUDE_Gm)
	, altitudeGauge()
{
	this->setAllLEDsOff();
}

void GaugeTower::setAllLEDsOff() {
	this->led_Altitude_Gm.setState(true);
	this->led_Altitude_Mm.setState(true);
	this->led_Altitude_m.setState(true);
	this->led_Altitude_km.setState(true);
	this->altitudeGauge.setAllLEDsOff();
}

void GaugeTower::setAllLEDsOn() {
	this->led_Altitude_Gm.setState(false);
	this->led_Altitude_Mm.setState(false);
	this->led_Altitude_m.setState(false);
	this->led_Altitude_km.setState(false);
	this->altitudeGauge.setAllLEDsOn();
}

void GaugeTower::testLEDsSequentially() {
	auto blinkLED = [](const LED& led) { 
		led.setState(false);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setState(true);
	};
	
	this->altitudeGauge.testLEDsSequentially();
	blinkLED(this->led_Altitude_Gm);
	blinkLED(this->led_Altitude_Mm);
	blinkLED(this->led_Altitude_m);
	blinkLED(this->led_Altitude_km);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}