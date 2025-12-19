//[GaugePacketA] (does not apply to [GaugePacketB])
#ifndef MODULE_G_h
#define MODULE_G_h

#include <Arduino.h>
#include <Interface_StepperMotorAggregator.h>
#include <StepperMotor2.h>
#include <NEMA17StepperMotor.h>

/* Module G
 * Stepper motors: Mach, Pitch, Heading 
 */
class ModuleG : public Interface_StepperMotorAggregator
{
public:
	ModuleG();
	
	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	
	//Parts:
	StepperMotor2 stepper_Mach;
	StepperMotor2 stepper_Pitch;
	NEMA17StepperMotor stepper_Heading;
};

#endif