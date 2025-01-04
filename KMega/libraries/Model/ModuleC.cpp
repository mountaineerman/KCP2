#include <Arduino.h>
#include <ModuleC.h>
#include "..\..\configuration.h"



ModuleC::ModuleC(Adafruit_TLC5947& ledDriverBoards)
	: ledPWM_HEAT_Red		(PIN_LEDDB_HEAT_RGBLED_RED, 		ledDriverBoards)
	, ledPWM_HEAT_Green		(PIN_LEDDB_HEAT_RGBLED_GRN, 		ledDriverBoards)
	, ledPWM_HEAT_Blue		(PIN_LEDDB_HEAT_RGBLED_BLU, 		ledDriverBoards)
	, ledPWM_LIFE_Red		(PIN_LEDDB_LIFE_SUPPORT_RGBLED_RED, ledDriverBoards)
	, ledPWM_LIFE_Green		(PIN_LEDDB_LIFE_SUPPORT_RGBLED_GRN, ledDriverBoards)
	, ledPWM_LIFE_Blue		(PIN_LEDDB_LIFE_SUPPORT_RGBLED_BLU, ledDriverBoards)
	, ledPWM_GFORCE_Red		(PIN_LEDDB_GFORCE_RGBLED_RED, 		ledDriverBoards)
	, ledPWM_GFORCE_Green	(PIN_LEDDB_GFORCE_RGBLED_GRN, 		ledDriverBoards)
	, ledPWM_GFORCE_Blue	(PIN_LEDDB_GFORCE_RGBLED_BLU, 		ledDriverBoards)
	, stepper_HeatLife		(PIN_VID6606_1_FREQUENCY_HEATLIFE,	PIN_VID6606_1_DIRECTION_HEATLIFE,	false,	GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
	, stepper_Gforce		(PIN_VID6606_1_FREQUENCY_GFORCE,	PIN_VID6606_1_DIRECTION_GFORCE, 	false,	GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
{
	
}

void ModuleC::setAllLEDsTo(int pwm_level) {
	this->ledPWM_HEAT_Red.setPWM(pwm_level);
	this->ledPWM_HEAT_Green.setPWM(pwm_level);
	this->ledPWM_HEAT_Blue.setPWM(pwm_level);
	this->ledPWM_LIFE_Red.setPWM(pwm_level);
	this->ledPWM_LIFE_Green.setPWM(pwm_level);
	this->ledPWM_LIFE_Blue.setPWM(pwm_level);
	this->ledPWM_GFORCE_Red.setPWM(pwm_level);
	this->ledPWM_GFORCE_Green.setPWM(pwm_level);
	this->ledPWM_GFORCE_Blue.setPWM(pwm_level);
}

void ModuleC::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) {
		led.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_HEAT_Red);
	blinkLED(this->ledPWM_HEAT_Green);
	blinkLED(this->ledPWM_HEAT_Blue);
	blinkLED(this->ledPWM_LIFE_Red);
	blinkLED(this->ledPWM_LIFE_Green);
	blinkLED(this->ledPWM_LIFE_Blue);
	blinkLED(this->ledPWM_GFORCE_Red);
	blinkLED(this->ledPWM_GFORCE_Green);
	blinkLED(this->ledPWM_GFORCE_Blue);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}

bool ModuleC::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	isAMotorStillInMotion = this->stepper_HeatLife.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->stepper_Gforce.runStepperIfNecessary() || isAMotorStillInMotion;
	return isAMotorStillInMotion;
}