#include <Arduino.h>
#include <ModuleG.h>
#include "..\..\configuration.h"



ModuleG::ModuleG(Adafruit_TLC5947& ledDriverBoards)
	: switch_HeatLife	(PIN_HEAT_LIFESUPPORT_SWITCH, true)
	, ledPWM_MACH_Red		(PIN_LEDDB_MACH_RGBLED_RED, 	ledDriverBoards)
	, ledPWM_MACH_Green		(PIN_LEDDB_MACH_RGBLED_GRN, 	ledDriverBoards)
	, ledPWM_MACH_Blue		(PIN_LEDDB_MACH_RGBLED_BLU, 	ledDriverBoards)
	, ledPWM_PITCH_Red		(PIN_LEDDB_PITCH_RGBLED_RED, 	ledDriverBoards)
	, ledPWM_PITCH_Green	(PIN_LEDDB_PITCH_RGBLED_GRN, 	ledDriverBoards)
	, ledPWM_PITCH_Blue		(PIN_LEDDB_PITCH_RGBLED_BLU, 	ledDriverBoards)
	, ledPWM_HEADING_Red	(PIN_LEDDB_HEADING_RGBLED_RED, 	ledDriverBoards)
	, ledPWM_HEADING_Green	(PIN_LEDDB_HEADING_RGBLED_GRN, 	ledDriverBoards)
	, ledPWM_HEADING_Blue	(PIN_LEDDB_HEADING_RGBLED_BLU, 	ledDriverBoards)
	, ledPWM_Comms			(PIN_LEDDB_COMMS_LED, 			ledDriverBoards)
	, stepper_Mach	(PIN_VID6606_1_FREQUENCY_MACH, 	PIN_VID6606_1_DIRECTION_MACH, false)
	, stepper_Pitch	(PIN_VID6606_1_FREQUENCY_PITCH, PIN_VID6606_1_DIRECTION_PITCH, false)
	, stepper_Heading (PIN_EASYDRIVER_STEP, PIN_EASYDRIVER_DIR, PIN_EASYDRIVER_SLP, PIN_EASYDRIVER_MS1, PIN_EASYDRIVER_MS2)
{
	
}

void ModuleG::refreshInputStatus() {
	this->switch_HeatLife.refreshInputStatus();
}

String ModuleG::getInputStatusAsString() {
	return String("Module G ============================================================================") +
		   "\nswitch_HeatLife:" + this->switch_HeatLife.getInputStatusAsString() + "\n";
}

void ModuleG::setAllLEDsOff() {
	this->ledPWM_MACH_Red.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_MACH_Green.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_MACH_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_PITCH_Red.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_PITCH_Green.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_PITCH_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_HEADING_Red.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_HEADING_Green.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_HEADING_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);

}

void ModuleG::setAllLEDsOn() {
	this->ledPWM_MACH_Red.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_MACH_Green.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_MACH_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_PITCH_Red.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_PITCH_Green.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_PITCH_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_HEADING_Red.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_HEADING_Green.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_HEADING_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
}

void ModuleG::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) { 
		led.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_MACH_Red);
	blinkLED(this->ledPWM_MACH_Green);
	blinkLED(this->ledPWM_MACH_Blue);
	blinkLED(this->ledPWM_PITCH_Red);
	blinkLED(this->ledPWM_PITCH_Green);
	blinkLED(this->ledPWM_PITCH_Blue);
	blinkLED(this->ledPWM_Comms);
	blinkLED(this->ledPWM_HEADING_Red);
	blinkLED(this->ledPWM_HEADING_Green);
	blinkLED(this->ledPWM_HEADING_Blue);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}

void ModuleG::resetStepperToStartingPosition() {
	this->stepper_Mach.resetStepperToStartingPosition();
	this->stepper_Pitch.resetStepperToStartingPosition();
	this->stepper_Heading.resetStepperToStartingPosition();
}


	
	



































