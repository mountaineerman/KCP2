#ifndef MODULE_I_h
#define MODULE_I_h

#include <Arduino.h>
#include <Interface_StepperMotorAggregator.h>
#include <StepperMotor2.h>


/* Module I
 * Stepper motors: Fuel, Charge, Monopropellant/Intake Air
 */
class ModuleI : public Interface_StepperMotorAggregator
{
public:
	ModuleI();
	
	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	
	//Parts:

	//[GaugePacketA]
	StepperMotor2 stepper_Fuel;

	// //[GaugePacketB]
	// StepperMotor2 stepper_Charge;
	// StepperMotor2 stepper_MonopropellantIntake;
};

#endif