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
	
	this->setAllUnitLEDsOff();
	this->displayString(NO_BYTES_RECEIVED);
}

void AltitudeGauge::setAltitude(float altitude) {
	this->altitude = altitude;
		
	//sevenSegmentDisplay.setNumber(this->altitude); //Note: does not work for values over 999m (displays "---")
	
																					//e.g., -3.2175 x 10 ^ 5;
	int exponent = floor(log10(abs(this->altitude)));								//e.g., 5
	float three_digit_unrounded_mantissa = this->altitude / pow(10,(exponent-2));	//e.g., -321.75
	int three_digit_rounded_mantissa = round(three_digit_unrounded_mantissa);		//e.g., -322
	float two_digit_unrounded_mantissa = this->altitude / pow(10,(exponent-1));		//e.g., -32.175
	int two_digit_rounded_mantissa = round(two_digit_unrounded_mantissa);			//e.g., -32
	float one_digit_unrounded_mantissa = this->altitude / pow(10,exponent);			//e.g., -3.2175
	int one_digit_rounded_mantissa = round(one_digit_unrounded_mantissa);			//e.g., -3
	
	
	if (exponent <= -2) {//TODO test
	//Range: Absolute Value < 0.1m
		this->displayString("  0");
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -99500) {
	//Range: "negative infinity" to -99,500m
		this->displayString(NUMBER_TOO_SMALL_TO_DISPLAY__NEGATIVE_NUMBER_ERROR);
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(true);
	} else if (this->altitude <= -10000) {
	//Range: -99km to -10km
		//this->displayString(String(two_digit_rounded_mantissa));
		sevenSegmentDisplay.setNumber(two_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -9950) {
	//Range: -10km
		this->displayString("-10");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -1000) {
	//Range: -9.9km to -1.0km
		this->displayString("-" + String(abs(two_digit_rounded_mantissa/10)) + "." + String(abs(two_digit_rounded_mantissa%10)));
		//sevenSegmentDisplay.setNumber(one_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -950) {
	//Range: -1.0km
		this->displayString("-1.0");
		//sevenSegmentDisplay.setNumber(one_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -99.5) {
	//Range: -0.9km to -0.1km
		this->displayString("-0." + String(abs(one_digit_rounded_mantissa)));
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -10) {
	//Range: -99m to -10m
		//this->displayString(String(two_digit_rounded_mantissa));
		sevenSegmentDisplay.setNumber(two_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -9.5) {
	//Range: -10m
		this->displayString("-10");
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -0.95) {
	//Range: -9m to -1m
		this->displayString("- " + String(abs(one_digit_rounded_mantissa)));
		//sevenSegmentDisplay.setNumber(one_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude <= -0.095) {
	//Range: -0.9m to -0.1m
		this->displayString("-0." + String(abs(one_digit_rounded_mantissa)));
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 0.95) {
	//Range: 0.1m to 0.9m
		this->displayString(" 0." + String(one_digit_rounded_mantissa));
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 1.0) {
	//Range: 1.0m
		this->displayString(" 1.0");
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 9.95) {//TODO right-justify
	//Range: 1.0m to 9.9m
		//sevenSegmentDisplay.setNumber(two_digit_rounded_mantissa, DECIMAL_CENTER);
		this->displayString(" " + String(two_digit_rounded_mantissa/10) + "." + String(two_digit_rounded_mantissa%10));
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 10.0) {
	//Range: 10.0m
		this->displayString("10.0");
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 99.95) {
	//Range: 10.0m to 99.9m
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_CENTER);
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 100) {
	//Range: 100m
		this->displayString("100");
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 999.5) {
	//Range: 100m to 999m
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 1000) {
	//Range: 1.00km
		this->displayString("1.00");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 9995) {
	//Range: 1.00km to 9.99km
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_LEFT);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 10000) {
	//Range: 10.0km
		this->displayString("10.0");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 99950) {
	//Range: 10.0km to 99.9km
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_CENTER);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 100000) {
	//Range: 100km
		this->displayString("100");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 999500) {
	//Range: 100km to 999km
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 1000000) {
	//Range: 1.00Mm
		this->displayString("1.00");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 9995000) {
	//Range: 1.00Mm to 9.99Mm
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_LEFT);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 10000000) {
	//Range: 10.0Mm
		this->displayString("10.0");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 99950000) {
	//Range: 10.0Mm to 99.9Mm
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_CENTER);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 100000000) {
	//Range: 100Mm
		this->displayString("100");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 999500000) {
	//Range: 100Mm to 999Mm
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(false);
	} else if (this->altitude < 1000000000) {
	//Range: 1.00Gm
		this->displayString("1.00");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(true);
	} else if (this->altitude < 9995000000) {
	//Range: 1.00Gm to 9.99Gm
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_LEFT);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(true);
	} else if (this->altitude < 10000000000) {
	//Range: 10.0Gm
		this->displayString("10.0");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(true);
	} else if (this->altitude < 99950000000) {
	//Range: 10.0Gm to 99.9Gm
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_CENTER);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(true);
	} else if (this->altitude < 100000000000) {
	//Range: 100Gm
		this->displayString("100");
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(true);
	} else if (this->altitude < 999500000000) {
	//Range: 100Gm to 999Gm
		sevenSegmentDisplay.setNumber(three_digit_rounded_mantissa, DECIMAL_OFF);
		this->led_Altitude_m.setState(false);
		this->led_Altitude_km.setState(false);
		this->led_Altitude_Mm.setState(false);
		this->led_Altitude_Gm.setState(true);
	} else {
	//Range: 999.5Gm to "infinity"
		this->displayString(NUMBER_TOO_BIG_TO_DISPLAY__LARGE_NUMBER_ERROR);
		this->led_Altitude_m.setState(true);
		this->led_Altitude_km.setState(true);
		this->led_Altitude_Mm.setState(true);
		this->led_Altitude_Gm.setState(true);
	}
}

void AltitudeGauge::displayString(String stringToDisplay) {
	this->sevenSegmentDisplay.setChars(stringToDisplay.c_str());
}

void AltitudeGauge::refreshSevenSegmentDisplay() {
	sevenSegmentDisplay.refreshDisplay();
}

void AltitudeGauge::setAllLEDsOn() {
	this->displayString(ALL_LEDs_ON);
	this->setAllUnitLEDsOn();
}

void AltitudeGauge::setAllLEDsOff() {
	this->sevenSegmentDisplay.blank();
	this->setAllUnitLEDsOff();
}

void AltitudeGauge::setAllUnitLEDsOff() {
	this->led_Altitude_m.setState(false);
	this->led_Altitude_km.setState(false);
	this->led_Altitude_Mm.setState(false);
	this->led_Altitude_Gm.setState(false);
}

void AltitudeGauge::setAllUnitLEDsOn() {
	this->led_Altitude_m.setState(true);
	this->led_Altitude_km.setState(true);
	this->led_Altitude_Mm.setState(true);
	this->led_Altitude_Gm.setState(true);
}

void AltitudeGauge::testLEDsSequentially() {
	
	auto blinkLED = [](const LED& led) { 
		led.setState(true);
		delay(SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setState(false);
	};
	
	blinkLED(this->led_Altitude_Gm);
	blinkLED(this->led_Altitude_Mm);
	blinkLED(this->led_Altitude_m);
	blinkLED(this->led_Altitude_km);
	delay(SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	