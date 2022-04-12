#ifndef MODULE_G_h
#define MODULE_G_h

#include <Arduino.h>
#include <Interface_Input.h>
#include <Interface_LEDAggregator.h>
#include <Interface_StepperMotorAggregator.h>
#include <SwitchSP2T.h>
#include <LED_PWM.h>
#include <StepperMotor.h>
#include <StepperMotorNEMA17.h>


/* Module G
 *
 * Stepper motors: Mach, Pitch, Heading 
 * Communications LED
 * Heat/Life Support Switch */
class ModuleG : public Interface_Input, public Interface_LEDAggregator, public Interface_StepperMotorAggregator
{
public:
	ModuleG(Adafruit_TLC5947& ledDriverBoards);
	
	void refreshInputStatus();
	String getInputStatusAsString();
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	
	//Parts:
	SwitchSP2T switch_HeatLife; // 0 = Heat,  1 = Life
	LED_PWM ledPWM_MACH_Red;
	LED_PWM ledPWM_MACH_Green;
	LED_PWM ledPWM_MACH_Blue;
	LED_PWM ledPWM_PITCH_Red;
	LED_PWM ledPWM_PITCH_Green;
	LED_PWM ledPWM_PITCH_Blue;
	LED_PWM ledPWM_HEADING_Red;
	LED_PWM ledPWM_HEADING_Green;
	LED_PWM ledPWM_HEADING_Blue;
	LED_PWM ledPWM_Comms;
	StepperMotor stepper_Mach;
	StepperMotor stepper_Pitch;
	StepperMotorNEMA17 stepper_Heading;
};

#endif