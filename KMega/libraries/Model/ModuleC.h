#ifndef MODULE_C_h
#define MODULE_C_h

#include <Arduino.h>
#include <Interface_LEDAggregator.h>
#include <LED_PWM.h>
//TODO: Steppers


/* Module C
 *
 * Heat, Life Support, G-Force */
class ModuleC : public Interface_LEDAggregator
{
public:
	ModuleC(Adafruit_TLC5947& ledDriverBoards);
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	//TODO: Steppers
	
	//Parts:
	LED_PWM ledPWM_HEAT_Red;
	LED_PWM ledPWM_HEAT_Green;
	LED_PWM ledPWM_HEAT_Blue;
	LED_PWM ledPWM_LIFE_Red;
	LED_PWM ledPWM_LIFE_Green;
	LED_PWM ledPWM_LIFE_Blue;
	LED_PWM ledPWM_GFORCE_Red;
	LED_PWM ledPWM_GFORCE_Green;
	LED_PWM ledPWM_GFORCE_Blue;
	//TODO: Steppers
};

#endif