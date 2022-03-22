#ifndef MODULE_I_h
#define MODULE_I_h

#include <Arduino.h>
#include <Interface_Input.h>
#include <Interface_LEDAggregator.h>
#include <Interface_StepperMotorAggregator.h>
#include <SwitchSP2T.h>
#include <LED_PWM.h>
#include <StepperMotor.h>


/* Module I
 *
 * Stepper motors: Fuel, Charge, Monopropellant/Intake Air
 * Delta Fuel LED
 * Monopropellant/Intake Air Switch */
class ModuleI : public Interface_Input, public Interface_LEDAggregator, public Interface_StepperMotorAggregator
{
public:
	ModuleI(Adafruit_TLC5947& ledDriverBoards);
	
	void refreshInputStatus();
	String getInputStatusAsString();
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	void runStepperIfNecessary();
	void resetStepperToStartingPosition();
	
	//Parts:
	SwitchSP2T switch_MonopropellantIntake; // 0 = Monopropellant,  1 = Intake Air
	LED_PWM ledPWM_FUEL_Red;
	LED_PWM ledPWM_FUEL_Green;
	LED_PWM ledPWM_FUEL_Blue;
	LED_PWM ledPWM_CHARGE_Red;
	LED_PWM ledPWM_CHARGE_Green;
	LED_PWM ledPWM_CHARGE_Blue;
	LED_PWM ledPWM_DeltaCHARGE_Red;
	LED_PWM ledPWM_DeltaCHARGE_Green;
	LED_PWM ledPWM_DeltaCHARGE_Blue	;
	LED_PWM ledPWM_MONOPROPELLANT_Red;
	LED_PWM ledPWM_MONOPROPELLANT_Green;
	LED_PWM ledPWM_MONOPROPELLANT_Blue;
	LED_PWM ledPWM_INTAKE_Red;
	LED_PWM ledPWM_INTAKE_Green;
	LED_PWM ledPWM_INTAKE_Blue;
	StepperMotor stepper_Fuel;
	StepperMotor stepper_Charge;
	StepperMotor stepper_MonopropellantIntake;
};

#endif