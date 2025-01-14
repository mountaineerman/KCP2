#ifndef CONTROL_PANEL_h
#define CONTROL_PANEL_h

#include <Arduino.h>

#include <Interface_StepperMotorAggregator.h>

#include <ModuleC.h>
#include <ModuleG.h>
#include <ModuleI.h>
#include <ModuleGT.h>



/* MkII Control Panel
 */
class ControlPanel : public Interface_StepperMotorAggregator //TODO rename to: Interface_StepperMotor
{
private:
	//Step "stepper", up to the "maxNumberOfSteps"
	void stepMotorUpTo(StepperMotor2& stepper, int maxNumberOfSteps);

public:
	ControlPanel();
	
	//Run stepper motors to their desiredPosition, up to a configurable number of steps.
	void burstRunSteppers();
	//Check if any stepper in the Panel needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	//Run all steppers to position. Blocks until all steppers have arrived at the position.
	void blockRunAllSteppersToPosition(int position, unsigned long stepTimeInMicroseconds);
	//Run all geared steppers to STEPPER_CW_LIMIT, then STEPPER_CCW_LIMIT. TODO Heading Gauge... Blocks until all steppers have arrived at the position.
	void sweepStepperMotorsThroughMaxMin();
	
	ModuleC moduleC;
	ModuleG moduleG;
	ModuleI moduleI;
	ModuleGT moduleGT;
};

#endif