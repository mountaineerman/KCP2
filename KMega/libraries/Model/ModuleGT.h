#ifndef MODULE_GT_h
#define MODULE_GT_h

#include <Arduino.h>
#include <Interface_LEDAggregator.h>
#include <Interface_StepperMotorAggregator.h>
#include <LED_PWM.h>
#include <StepperMotor2.h>


/* Module GT (Gauge Tower)
 *
 * Stepper Motors: Air Density, Speed, Vertical Speed, Radar Altitude
 * Altitude Gauge */
class ModuleGT : public Interface_LEDAggregator, public Interface_StepperMotorAggregator
{
public:
	ModuleGT(Adafruit_TLC5947& ledDriverBoards);
	
	void setAllLEDsTo(int pwm_level);
	void testLEDsSequentially();
	
	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	
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
	
	StepperMotor2 stepper_Density;
	StepperMotor2 stepper_Speed;
	StepperMotor2 stepper_VertSpeed;
	StepperMotor2 stepper_RadarAlt;
	
	float altitude;
};

#endif