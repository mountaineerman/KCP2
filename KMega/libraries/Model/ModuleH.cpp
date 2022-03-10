#include <Arduino.h>
#include <string.h>
#include <ModuleH.h>
#include "..\..\configuration.h"



ModuleH::ModuleH(Adafruit_TLC5947& ledDriverBoards)
	: switch_GlassCockpit_TL	(PIN_GLASS_COCKPIT_TL_SWITCH, true)
	, switch_GlassCockpit_CL	(PIN_GLASS_COCKPIT_CL_SWITCH, true)
	, switch_GlassCockpit_BL	(PIN_GLASS_COCKPIT_BL_SWITCH, true)
	, switch_GlassCockpit_TR	(PIN_GLASS_COCKPIT_TR_SWITCH, true)
	, switch_GlassCockpit_CR	(PIN_GLASS_COCKPIT_CR_SWITCH, true)
	, switch_GlassCockpit_BR	(PIN_GLASS_COCKPIT_BR_SWITCH, true)
	, ledPWM_GlassCockpit_TL	(PIN_LEDDB_GLASS_COCKPIT_TL, ledDriverBoards)
	, ledPWM_GlassCockpit_CL	(PIN_LEDDB_GLASS_COCKPIT_CL, ledDriverBoards)
	, ledPWM_GlassCockpit_BL	(PIN_LEDDB_GLASS_COCKPIT_BL, ledDriverBoards)
	, ledPWM_GlassCockpit_TR	(PIN_LEDDB_GLASS_COCKPIT_TR, ledDriverBoards)
	, ledPWM_GlassCockpit_CR	(PIN_LEDDB_GLASS_COCKPIT_CR, ledDriverBoards)
	, ledPWM_GlassCockpit_BR	(PIN_LEDDB_GLASS_COCKPIT_BR, ledDriverBoards)
{
	
}

void ModuleH::refreshInputStatus() {
	this->switch_GlassCockpit_TL.refreshInputStatus();
	this->switch_GlassCockpit_CL.refreshInputStatus();
	this->switch_GlassCockpit_BL.refreshInputStatus();
	this->switch_GlassCockpit_TR.refreshInputStatus();
	this->switch_GlassCockpit_CR.refreshInputStatus();
	this->switch_GlassCockpit_BR.refreshInputStatus();
}

String ModuleH::getInputStatusAsString() {
	return String("Module H ============================================================================") +
		   "\nswitch_GlassCockpit_TL:" + this->switch_GlassCockpit_TL.getInputStatusAsString() +
		   "\nswitch_GlassCockpit_CL:" + this->switch_GlassCockpit_CL.getInputStatusAsString() +
		   "\nswitch_GlassCockpit_BL:" + this->switch_GlassCockpit_BL.getInputStatusAsString() +
		   "\nswitch_GlassCockpit_TR:" + this->switch_GlassCockpit_TR.getInputStatusAsString() +
		   "\nswitch_GlassCockpit_CR:" + this->switch_GlassCockpit_CR.getInputStatusAsString() +
		   "\nswitch_GlassCockpit_BR:" + this->switch_GlassCockpit_BR.getInputStatusAsString() + "\n";
}

void ModuleH::setAllLEDsOff() {
	this->ledPWM_GlassCockpit_TL.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_GlassCockpit_CL.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_GlassCockpit_BL.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_GlassCockpit_TR.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_GlassCockpit_CR.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_GlassCockpit_BR.setPWM(PWM_LED_MINIMUM);
}

void ModuleH::setAllLEDsOn() {
	this->ledPWM_GlassCockpit_TL.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_GlassCockpit_CL.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_GlassCockpit_BL.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_GlassCockpit_TR.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_GlassCockpit_CR.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_GlassCockpit_BR.setPWM(PWM_LED_MAXIMUM);
}

void ModuleH::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) { 
		led.setPWM(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWM(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_GlassCockpit_TL);
	blinkLED(this->ledPWM_GlassCockpit_TR);
	blinkLED(this->ledPWM_GlassCockpit_CL);
	blinkLED(this->ledPWM_GlassCockpit_CR);
	blinkLED(this->ledPWM_GlassCockpit_BL);
	blinkLED(this->ledPWM_GlassCockpit_BR);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}