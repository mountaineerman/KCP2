#include <Arduino.h>
#include <AltitudeGauge.h>
#include "..\..\configuration.h"


AltitudeGauge::AltitudeGauge()
	: sevenSegmentDisplay()
	, led_Altitude_m	(PIN_ALTITUDE_m)
	, led_Altitude_km	(PIN_ALTITUDE_km)
	, led_Altitude_Mm	(PIN_ALTITUDE_Mm)
	, led_Altitude_Gm	(PIN_ALTITUDE_Gm)
{
	this->setAllLEDsOff();
	this->setAltitude(STARTING_ALTITUDE);

	byte numDigits = 3;
	byte digitPins[] = {PIN_SEVENSEGMENTDISPLAY_digit1, PIN_SEVENSEGMENTDISPLAY_digit2, PIN_SEVENSEGMENTDISPLAY_digit3};
	byte segmentPins[] = {PIN_SEVENSEGMENTDISPLAY_a,
						  PIN_SEVENSEGMENTDISPLAY_b,
						  PIN_SEVENSEGMENTDISPLAY_c,
						  PIN_SEVENSEGMENTDISPLAY_d,
						  PIN_SEVENSEGMENTDISPLAY_e,
						  PIN_SEVENSEGMENTDISPLAY_f,
						  PIN_SEVENSEGMENTDISPLAY_g,
						  PIN_SEVENSEGMENTDISPLAY_decimalPoint};
	bool resistorsOnSegments = true; 	//'false' means resistors are on digit pins
	byte hardwareConfig = COMMON_ANODE; //See README.md for options
	bool updateWithDelays = false;		//Default 'false' is Recommended
	bool disableDecPoint = false; 		//Use 'true' if your decimal point doesn't exist or isn't connected. Then, you only need to specify 7 segmentPins[]
	
	sevenSegmentDisplay.begin(hardwareConfig, numDigits, digitPins, segmentPins, resistorsOnSegments, updateWithDelays, SEVEN_SEGMENT_DISPLAY__SHOW_LEADING_ZEROS, disableDecPoint);
	sevenSegmentDisplay.setBrightness(SEVEN_SEGMENT_DISPLAY_BRIGHTNESS);
}

void AltitudeGauge::setAltitude(float altitude) {
	this->altitude = altitude;
	
	sevenSegmentDisplay.setNumber(this->altitude);//TODO verify
	
	if (this->altitude < 0) {//TODO verify
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(true);
	} else if (this->altitude < 1000) {
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 1000000) {
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 1000000000) {
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 1000000000000) {
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(true);
	} else
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(true);
	}
}

void AltitudeGauge::refreshSevenSegmentDisplay() {
	sevenSegmentDisplay.refreshDisplay();
}

void AltitudeGauge::setAllLEDsOff() {
	this->sevenSegmentDisplay.blank();
	this->led_Altitude_m.setState(false);
	this->led_Altitude_km.setState(false);
	this->led_Altitude_Mm.setState(false);
	this->led_Altitude_Gm.setState(false);
}

void AltitudeGauge::setAllLEDsOn() {
	//Note: sevenSegmentDisplay.setNumber(number, decimal_point): decimal point position:
	// 0 = right
	// 1 = center
	// 2 = left
	this->sevenSegmentDisplay.setNumber(888,0);//Note: for simplicity, does not light up all 3 decimal LEDs at the same time, just the right one.
	this->led_Altitude_m.setState(true);
	this->led_Altitude_km.setState(true);
	this->led_Altitude_Mm.setState(true);
	this->led_Altitude_Gm.setState(true);
}

void AltitudeGauge::testLEDsSequentially() {
	
	auto blinkLED = [](const LED& led) { 
		led.setState(true);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setState(false);
	};
	
	blinkLED(this->led_Altitude_Gm);
	blinkLED(this->led_Altitude_Mm);
	blinkLED(this->led_Altitude_m);
	blinkLED(this->led_Altitude_km);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	