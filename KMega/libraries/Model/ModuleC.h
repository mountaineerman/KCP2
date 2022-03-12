#ifndef MODULE_C_h
#define MODULE_C_h

#include <Arduino.h>
#include <Interface_LEDAggregator.h>
#include <Interface_StepperMotorAggregator.h>
#include <LED_PWM.h>
#include <StepperMotor.h>


/* Module C
 *
 * Heat, Life Support, G-Force */
class ModuleC : public Interface_LEDAggregator, public Interface_StepperMotorAggregator
{
public:
	ModuleC(Adafruit_TLC5947& ledDriverBoards);
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	void resetStepperToStartingPosition();
	
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
	StepperMotor stepper_HeatLife;
	StepperMotor stepper_Gforce;
};

#endif