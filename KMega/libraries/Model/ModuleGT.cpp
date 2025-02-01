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
, stepper_Density	(GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
, stepper_Speed		(GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
, stepper_VertSpeed	(GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
, stepper_RadarAlt	(GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, GEARED_STEPPER_CW_LIMIT)
{
	this->altitude = STARTING_ALTITUDE;
}

void ModuleGT::setAllLEDsTo(int pwm_level) {
	this->ledPWM_DENSITY_Red.setPWM(pwm_level);
	this->ledPWM_DENSITY_Green.setPWM(pwm_level);
	this->ledPWM_DENSITY_Blue.setPWM(pwm_level);
	this->ledPWM_SPEED_Red.setPWM(pwm_level);
	this->ledPWM_SPEED_Green.setPWM(pwm_level);
	this->ledPWM_SPEED_Blue.setPWM(pwm_level);
	this->ledPWM_VSPEED_Red.setPWM(pwm_level);
	this->ledPWM_VSPEED_Green.setPWM(pwm_level);
	this->ledPWM_VSPEED_Blue.setPWM(pwm_level);
	this->ledPWM_RADARALT_Red.setPWM(pwm_level);
	this->ledPWM_RADARALT_Green.setPWM(pwm_level);
	this->ledPWM_RADARALT_Blue.setPWM(pwm_level);
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