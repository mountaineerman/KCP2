#include <Arduino.h>
#include <ModuleGT.h>
#include "..\..\configuration.h"



ModuleGT::ModuleGT(Adafruit_TLC5947& ledDriverBoards)
: ledPWM_DENSITY_Red	(PIN_LEDDB_DENSITY_RGBLED_RED, 		ledDriverBoards)
, ledPWM_DENSITY_Green	(PIN_LEDDB_DENSITY_RGBLED_GRN, 		ledDriverBoards)
, ledPWM_DENSITY_Blue	(PIN_LEDDB_DENSITY_RGBLED_BLU, 		ledDriverBoards)
, ledPWM_SPEED_Red		(PIN_LEDDB_SPEED_RGBLED_RED, 		ledDriverBoards)
, ledPWM_SPEED_Green	(PIN_LEDDB_SPEED_RGBLED_GRN, 		ledDriverBoards)
, ledPWM_SPEED_Blue		(PIN_LEDDB_SPEED_RGBLED_BLU, 		ledDriverBoards)
, ledPWM_VSPEED_Red		(PIN_LEDDB_VERTSPEED_RGBLED_RED, 	ledDriverBoards)
, ledPWM_VSPEED_Green	(PIN_LEDDB_VERTSPEED_RGBLED_GRN, 	ledDriverBoards)
, ledPWM_VSPEED_Blue	(PIN_LEDDB_VERTSPEED_RGBLED_BLU, 	ledDriverBoards)
, ledPWM_RADARALT_Red	(PIN_LEDDB_RADARALT_RGBLED_RED, 	ledDriverBoards)
, ledPWM_RADARALT_Green	(PIN_LEDDB_RADARALT_RGBLED_GRN, 	ledDriverBoards)
, ledPWM_RADARALT_Blue	(PIN_LEDDB_RADARALT_RGBLED_BLU, 	ledDriverBoards)
, stepper_Density	(PIN_VID6606_3_FREQUENCY_DENSITY,		PIN_VID6606_3_DIRECTION_DENSITY,		true)
, stepper_Speed		(PIN_VID6606_3_FREQUENCY_SPEED,			PIN_VID6606_3_DIRECTION_SPEED,			true)
, stepper_VertSpeed	(PIN_VID6606_3_FREQUENCY_VERTICALSPEED,	PIN_VID6606_3_DIRECTION_VERTICALSPEED,	true)
, stepper_RadarAlt	(PIN_VID6606_3_FREQUENCY_RADARALTITUDE,	PIN_VID6606_3_DIRECTION_RADARALTITUDE,	true)
{
	
}

void ModuleGT::setAllLEDsOff() {	
	this->ledPWM_DENSITY_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_DENSITY_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_DENSITY_Blue.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_SPEED_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_SPEED_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_SPEED_Blue.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_VSPEED_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_VSPEED_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_VSPEED_Blue.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_RADARALT_Red.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_RADARALT_Green.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_RADARALT_Blue.setPWM(PWM_LED_MINIMUM);
}

void ModuleGT::setAllLEDsOn() {
	this->ledPWM_DENSITY_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_DENSITY_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_DENSITY_Blue.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_SPEED_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_SPEED_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_SPEED_Blue.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_VSPEED_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_VSPEED_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_VSPEED_Blue.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_RADARALT_Red.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_RADARALT_Green.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_RADARALT_Blue.setPWM(PWM_LED_MAXIMUM);
}

void ModuleGT::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) { 
		led.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_DENSITY_Red);
	blinkLED(this->ledPWM_DENSITY_Green);
	blinkLED(this->ledPWM_DENSITY_Blue);
	blinkLED(this->ledPWM_SPEED_Red);
	blinkLED(this->ledPWM_SPEED_Green);
	blinkLED(this->ledPWM_SPEED_Blue);
	blinkLED(this->ledPWM_VSPEED_Red);
	blinkLED(this->ledPWM_VSPEED_Green);
	blinkLED(this->ledPWM_VSPEED_Blue);
	blinkLED(this->ledPWM_RADARALT_Red);
	blinkLED(this->ledPWM_RADARALT_Green);
	blinkLED(this->ledPWM_RADARALT_Blue);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}

bool ModuleGT::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	isAMotorStillInMotion = this->stepper_Density.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->stepper_Speed.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->stepper_VertSpeed.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->stepper_RadarAlt.runStepperIfNecessary() || isAMotorStillInMotion;
	return isAMotorStillInMotion;	
}

void ModuleGT::resetStepperToStartingPosition() {
	this->stepper_Density.resetStepperToStartingPosition();
	this->stepper_Speed.resetStepperToStartingPosition();
	this->stepper_VertSpeed.resetStepperToStartingPosition();
	this->stepper_RadarAlt.resetStepperToStartingPosition();
}







































