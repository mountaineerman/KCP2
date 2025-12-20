#ifndef CONTROL_PANEL_h
#define CONTROL_PANEL_h

#include <Arduino.h>

#include <Interface_StepperMotorAggregator.h>

//[GaugePacketA]
#include <ModuleC.h>
#include <ModuleG.h>
#include <ModuleI.h>
// //[GaugePacketB]
// #include <ModuleI.h>
// #include <ModuleGT.h>



/* MkII Control Panel
 */
class ControlPanel : public Interface_StepperMotorAggregator //TODO rename to: Interface_StepperMotor
{
private:

public:
	ControlPanel();
	
	//Check if any stepper in the Panel needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	//Run all steppers to position. Blocks until all steppers have arrived at the position. Does not affect the Heading gauge.
	void blockRunAllGearedSteppersToPosition(int position, unsigned long stepTimeInMicroseconds);
	
	//[GaugePacketA]
	ModuleC moduleC;
	ModuleG moduleG;
	ModuleI moduleI;

	// //[GaugePacketB]
	// ModuleI moduleI;
	// ModuleGT moduleGT;
};

#endif