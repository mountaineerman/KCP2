#include <Arduino.h>
#include <ModuleI.h>
#include "../../configuration.h"



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
	, stepper_Fuel					(GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
	, stepper_Charge				(GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
	, stepper_MonopropellantIntake	(GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
{
	
}

void ModuleI::refreshInputStatus() {
	this->switch_MonopropellantIntake.refreshInputStatus();
}

String ModuleI::getInputStatusAsString() {
	return String("Module I ============================================================================") +
		   "\nswitch_MonopropellantIntake:" + this->switch_MonopropellantIntake.getInputStatusAsString() + "\n";
}

void ModuleI::setAllLEDsTo(int pwm_level) {
	this->ledPWM_FUEL_Red.setPWM(pwm_level);
	this->ledPWM_FUEL_Green.setPWM(pwm_level);
	this->ledPWM_FUEL_Blue.setPWM(pwm_level);
	this->ledPWM_DeltaCHARGE_Red.setPWM(pwm_level);
	this->ledPWM_DeltaCHARGE_Green.setPWM(pwm_level);
	this->ledPWM_DeltaCHARGE_Blue.setPWM(pwm_level);
	this->ledPWM_CHARGE_Red.setPWM(pwm_level);
	this->ledPWM_CHARGE_Green.setPWM(pwm_level);
	this->ledPWM_CHARGE_Blue.setPWM(pwm_level);
	this->ledPWM_MONOPROPELLANT_Red.setPWM(pwm_level);
	this->ledPWM_MONOPROPELLANT_Green.setPWM(pwm_level);
	this->ledPWM_MONOPROPELLANT_Blue.setPWM(pwm_level);
	this->ledPWM_INTAKE_Red.setPWM(pwm_level);
	this->ledPWM_INTAKE_Green.setPWM(pwm_level);
	this->ledPWM_INTAKE_Blue.setPWM(pwm_level);
}

void ModuleI::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) {
		led.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
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