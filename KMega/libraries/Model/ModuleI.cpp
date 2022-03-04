#include <Arduino.h>
#include <ModuleI.h>
#include "..\..\configuration.h"



ModuleI::ModuleI(Adafruit_TLC5947& ledDriverBoards)	
	: switch_MonopropellantIntake	(PIN_MONOPROPELLANT_INTAKEAIR_SWITCH,	true)
	, ledPWM_FUEL_Red				(PIN_LEDDB_FUEL_RGBLED_RED, 			ledDriverBoards)
	, ledPWM_FUEL_Green				(PIN_LEDDB_FUEL_RGBLED_GRN, 			ledDriverBoards)
	, ledPWM_FUEL_Blue				(PIN_LEDDB_FUEL_RGBLED_BLU, 			ledDriverBoards)
	, ledPWM_CHARGE_Red				(PIN_LEDDB_CHARGE_RGBLED_RED, 			ledDriverBoards)
	, ledPWM_CHARGE_Green			(PIN_LEDDB_CHARGE_RGBLED_GRN, 			ledDriverBoards)
	, ledPWM_CHARGE_Blue			(PIN_LEDDB_CHARGE_RGBLED_BLU, 			ledDriverBoards)
	, ledPWM_DeltaCHARGE_Red		(PIN_LEDDB_DELTA_CHARGE_RGBLED_RED, 	ledDriverBoards)
	, ledPWM_DeltaCHARGE_Green		(PIN_LEDDB_DELTA_CHARGE_RGBLED_GRN, 	ledDriverBoards)
	, ledPWM_DeltaCHARGE_Blue		(PIN_LEDDB_DELTA_CHARGE_RGBLED_BLU, 	ledDriverBoards)
	, ledPWM_MONOPROPELLANT_Red		(PIN_LEDDB_MONOPROPELLANT_RGBLED_RED, 	ledDriverBoards)
	, ledPWM_MONOPROPELLANT_Green	(PIN_LEDDB_MONOPROPELLANT_RGBLED_GRN, 	ledDriverBoards)
	, ledPWM_MONOPROPELLANT_Blue	(PIN_LEDDB_MONOPROPELLANT_RGBLED_BLU, 	ledDriverBoards)
	, ledPWM_INTAKE_Red				(PIN_LEDDB_INTAKE_RGBLED_RED, 			ledDriverBoards)
	, ledPWM_INTAKE_Green			(PIN_LEDDB_INTAKE_RGBLED_GRN, 			ledDriverBoards)
	, ledPWM_INTAKE_Blue			(PIN_LEDDB_INTAKE_RGBLED_BLU, 			ledDriverBoards)
	, stepper_Fuel					(PIN_VID6606_2_FREQUENCY_FUEL,		PIN_VID6606_2_DIRECTION_FUEL,	true)
	, stepper_Charge				(PIN_VID6606_2_FREQUENCY_CHARGE,	PIN_VID6606_2_DIRECTION_CHARGE,	true)
	, stepper_MonopropellantIntake	(PIN_VID6606_2_FREQUENCY_MNPINT,	PIN_VID6606_2_DIRECTION_MNPINT,	true)
{
	//Serial.println("ModuleI Constructor");
}

void ModuleI::refreshInputStatus() {
	this->switch_MonopropellantIntake.refreshInputStatus();
}

String ModuleI::getInputStatusAsString() {
	return String("Module I ============================================================================") +
		   "\nswitch_MonopropellantIntake:" + this->switch_MonopropellantIntake.getInputStatusAsString() + "\n";
}

void ModuleI::setAllLEDsOff() {
	this->ledPWM_FUEL_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_FUEL_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_FUEL_Blue.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_DeltaCHARGE_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_DeltaCHARGE_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_DeltaCHARGE_Blue.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_CHARGE_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_CHARGE_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_CHARGE_Blue.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_MONOPROPELLANT_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_MONOPROPELLANT_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_MONOPROPELLANT_Blue.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_INTAKE_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_INTAKE_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_INTAKE_Blue.setPWM(PWM_LED_MINIMUM);
}

void ModuleI::setAllLEDsOn() {
	this->ledPWM_FUEL_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_FUEL_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_FUEL_Blue.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_DeltaCHARGE_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_DeltaCHARGE_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_DeltaCHARGE_Blue.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_CHARGE_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_CHARGE_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_CHARGE_Blue.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_MONOPROPELLANT_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_MONOPROPELLANT_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_MONOPROPELLANT_Blue.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_INTAKE_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_INTAKE_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_INTAKE_Blue.setPWM(PWM_LED_MAXIMUM);
}

void ModuleI::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) { 
		led.setPWM(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWM(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_FUEL_Red);
	blinkLED(this->ledPWM_FUEL_Green);
	blinkLED(this->ledPWM_FUEL_Blue);
	blinkLED(this->ledPWM_DeltaCHARGE_Red);
	blinkLED(this->ledPWM_DeltaCHARGE_Green);
	blinkLED(this->ledPWM_DeltaCHARGE_Blue);
	blinkLED(this->ledPWM_CHARGE_Red);
	blinkLED(this->ledPWM_CHARGE_Green);
	blinkLED(this->ledPWM_CHARGE_Blue);
	blinkLED(this->ledPWM_MONOPROPELLANT_Red);
	blinkLED(this->ledPWM_MONOPROPELLANT_Green);
	blinkLED(this->ledPWM_MONOPROPELLANT_Blue);
	blinkLED(this->ledPWM_INTAKE_Red);
	blinkLED(this->ledPWM_INTAKE_Green);
	blinkLED(this->ledPWM_INTAKE_Blue);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}

void ModuleI::resetStepperToStartingPosition() {
	this->stepper_Fuel.resetStepperToStartingPosition();
	this->stepper_Charge.resetStepperToStartingPosition();
	this->stepper_MonopropellantIntake.resetStepperToStartingPosition();
}


	
	



































