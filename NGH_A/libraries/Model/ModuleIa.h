//[GaugePacketA] (does not apply to [GaugePacketB])
#ifndef MODULE_Ia_h
#define MODULE_Ia_h

#include <Arduino.h>
#include <Interface_StepperMotorAggregator.h>
#include <StepperMotor2.h>


/* Module Ia
 * 	NGH_A Stepper motors: Fuel
 * 	NGH_B Stepper motors: Charge, Monopropellant/Intake Air
 */
class ModuleIa : public Interface_StepperMotorAggregator
{
public:
	ModuleIa();
	
	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	
	//Parts:
	StepperMotor2 stepper_Fuel;
};

#endif