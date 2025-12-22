#ifndef MODULE_Ib_h
#define MODULE_Ib_h

#include <Arduino.h>
#include <Interface_StepperMotorAggregator.h>
#include <StepperMotor2.h>


/* Module Ib
 * 	NGH_A Stepper motors: Fuel
 * 	NGH_B Stepper motors: Charge, Monopropellant/Intake Air
 */
class ModuleIb : public Interface_StepperMotorAggregator
{
public:
	ModuleIb();
	
	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	
	//Parts:
	StepperMotor2 stepper_Charge;				//[GaugePacketB]
	StepperMotor2 stepper_MonopropellantIntake;	//[GaugePacketB]
};

#endif