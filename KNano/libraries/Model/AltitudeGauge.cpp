#include <Arduino.h>
#include "..\..\configuration.h"
#include <AltitudeGauge.h>


AltitudeGauge::AltitudeGauge()
	: sevenSegmentDisplay()
{
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
	sevenSegmentDisplay.setNumber(this->altitude);
	sevenSegmentDisplay.refreshDisplay();
	//TODO
}

void AltitudeGauge::setAllLEDsOff() {
	this->sevenSegmentDisplay.blank();
}

void AltitudeGauge::setAllLEDsOn() {
	this->sevenSegmentDisplay.setNumber(888);
	this->sevenSegmentDisplay.refreshDisplay();
	delay(1);
	//TODO: Understand how all 3 decimal points can be displayed at the same time.
	//Note: decimal point position:
	// 0 = right
	// 1 = center
	// 2 = left
	//this->sevenSegmentDisplay.setNumber(888,0);
	//this->sevenSegmentDisplay.refreshDisplay();
	//delay(1);
	//this->sevenSegmentDisplay.setNumber(888,1);
	//this->sevenSegmentDisplay.refreshDisplay();
	//delay(1);
	//this->sevenSegmentDisplay.setNumber(888,2);
	//this->sevenSegmentDisplay.refreshDisplay();
	//delay(1);
	
	//TODO: The seven segment display requires frequent refreshes in order to work.
}

void AltitudeGauge::testLEDsSequentially() {
	
	for(int i = 0; i < DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS; i++){
		this->setAllLEDsOn();
	}
	this->setAllLEDsOff();
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	