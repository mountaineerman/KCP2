#include <Arduino.h>
#include <string.h>
//#include <stdlib.h> //TODO remove?
#include <ModuleA.h>
#include "..\..\configuration.h"



ModuleA::ModuleA(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards)
	: analogInput_Throttle	(PIN_THROTTLE, true)
	, switch_BrakeButton	(PIN_MUX_BRAKE_BUTTON,	true,  MULTIPLEXER_IO_ROW_1, mux)
	, switch_StagingButton	(PIN_MUX_STAGING_BUTTON,false, MULTIPLEXER_IO_ROW_2, mux)
	, ledPWM_BrakeModuleA	(PIN_LEDDB_BRAKE_LED_MODULE_A, ledDriverBoards)
{
	
}

void ModuleA::refreshInputStatus() {
	this->analogInput_Throttle.refreshInputStatus();
	this->switch_BrakeButton.refreshInputStatus();
	this->switch_StagingButton.refreshInputStatus();
}

String ModuleA::getInputStatusAsString() {
	return String("Module A ============================================================================") +
		   "\nanalogInput_Throttle:" + this->analogInput_Throttle.getInputStatusAsString() +
		   "\nswitch_BrakeButton:" + this->switch_BrakeButton.getInputStatusAsString() +
		   "\nswitch_StagingButton:" + this->switch_StagingButton.getInputStatusAsString() + "\n";
}

void ModuleA::setAllLEDsOff() {
	this->ledPWM_BrakeModuleA.setPWM(PWM_LED_MINIMUM);
}

void ModuleA::setAllLEDsOn() {
	this->ledPWM_BrakeModuleA.setPWM(PWM_LED_MAXIMUM);
}

void ModuleA::testLEDsSequentially() {
	this->ledPWM_BrakeModuleA.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
	this->ledPWM_BrakeModuleA.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}