#include <Arduino.h>
#include <string.h>
#include <ModuleF.h>
#include "..\..\configuration.h"



ModuleF::ModuleF(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards)
	: switch_trim		(PIN_MUX_TRIM_SWITCH,	false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_4pos_AB	(PIN_4POS_SWITCH_AB,	true)
	, switch_4pos_CD	(PIN_4POS_SWITCH_CD,	true)
	, analogInput_Current	(PIN_CURRENT_SENSOR)
	, analogInput_MultiPot	(PIN_MULTI_PURPOSE_POTENTIOMETER)
	, ledPWM_twistSwitch25	(PIN_LEDDB_TWIST_SWITCH_25, ledDriverBoards)
	, ledPWM_twistSwitch50	(PIN_LEDDB_TWIST_SWITCH_50, ledDriverBoards)
	, ledPWM_twistSwitch75	(PIN_LEDDB_TWIST_SWITCH_75, ledDriverBoards)
	, ledPWM_twistSwitch100	(PIN_LEDDB_TWIST_SWITCH_100, ledDriverBoards)
{
	
}

void ModuleF::refreshInputStatus() {
	this->switch_trim.refreshInputStatus();
	this->switch_4pos_AB.refreshInputStatus();
	this->switch_4pos_CD.refreshInputStatus();
	this->analogInput_Current.refreshInputStatus();
	this->analogInput_MultiPot.refreshInputStatus();
}

String ModuleF::getInputStatusAsString() {
	return String("Module F ============================================================================") +
		   "\nswitch_trim:" + this->switch_trim.getInputStatusAsString() +
		   "\nswitch_4pos_AB:" + this->switch_4pos_AB.getInputStatusAsString() +
		   "\nswitch_4pos_CD:" + this->switch_4pos_CD.getInputStatusAsString() +
		   "\nanalogInput_Current:" + this->analogInput_Current.getInputStatusAsString() + "(~ x5 for mA)" +
		   "\nanalogInput_MultiPot:" + this->analogInput_MultiPot.getInputStatusAsString() + "\n";
}

void ModuleF::setAllLEDsOff() {
	this->ledPWM_twistSwitch25.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_twistSwitch50.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_twistSwitch75.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_twistSwitch100.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
}

void ModuleF::setAllLEDsOn() {
	this->ledPWM_twistSwitch25.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_twistSwitch50.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_twistSwitch75.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_twistSwitch100.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
}

void ModuleF::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) { 
		led.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_twistSwitch100);
	blinkLED(this->ledPWM_twistSwitch75);
	blinkLED(this->ledPWM_twistSwitch50);
	blinkLED(this->ledPWM_twistSwitch25);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}



