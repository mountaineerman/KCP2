#ifndef MODULE_GT_h
#define MODULE_GT_h

#include <Arduino.h>
#include <Interface_LEDAggregator.h>
#include <Interface_StepperMotorAggregator.h>
#include <LED_PWM.h>
#include <StepperMotor.h>
//#include <StepperMotorNEMA17.h>


/* Module GT (Gauge Tower)
 *
 * Heat, Life Support, G-Force */
class ModuleGT : public Interface_LEDAggregator, public Interface_StepperMotorAggregator
{
public:
	ModuleGT(Adafruit_TLC5947& ledDriverBoards);
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	void resetStepperToStartingPosition();
	
	//Parts:
	LED_PWM ledPWM_DENSITY_Red;
	LED_PWM ledPWM_DENSITY_Green;
	LED_PWM ledPWM_DENSITY_Blue;
	LED_PWM ledPWM_SPEED_Red;
	LED_PWM ledPWM_SPEED_Green;
	LED_PWM ledPWM_SPEED_Blue;
	LED_PWM ledPWM_VSPEED_Red;
	LED_PWM ledPWM_VSPEED_Green;
	LED_PWM ledPWM_VSPEED_Blue;
	LED_PWM ledPWM_RADARALT_Red;
	LED_PWM ledPWM_RADARALT_Green;
	LED_PWM ledPWM_RADARALT_Blue;
	
	StepperMotor stepper_Density;
	StepperMotor stepper_Speed;
	StepperMotor stepper_VertSpeed;
	StepperMotor stepper_RadarAlt;
};

#endif