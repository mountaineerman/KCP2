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
	
	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	
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
	StepperMotor2 stepper_Gforce;
};

#endif