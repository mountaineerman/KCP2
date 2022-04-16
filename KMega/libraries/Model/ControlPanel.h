#ifndef CONTROL_PANEL_h
#define CONTROL_PANEL_h

#include <Arduino.h>

#include <MuxShield.h>
#include <Adafruit_TLC5947.h>

#include <Interface_Input.h>
#include <Interface_LEDAggregator.h>
#include <Interface_StepperMotorAggregator.h>

#include <ModuleA.h>
#include <ModuleB.h>
#include <ModuleC.h>
#include <ModuleD.h>
#include <ModuleE.h>
#include <ModuleF.h>
#include <ModuleG.h>
#include <ModuleH.h>
#include <ModuleI.h>
#include <ModuleGT.h>



/* MkII Control Panel
 */
class ControlPanel
	: public Interface_Input
	, public Interface_LEDAggregator
	, public Interface_StepperMotorAggregator //TODO rename to: Interface_StepperMotor
{
private:
	void diagnosticMode_testAllInputs();
	void diagnosticMode_testAllLEDs();
	void diagnosticMode_testLEDsSequentially();
	void diagnosticMode_testStepperMotors();
	void diagnosticMode_testGearedStepperMotor(StepperMotor& stepperMotorUnderTest);
	void diagnosticMode_testNEMA17StepperMotor(StepperMotorNEMA17& stepperMotorUnderTest); //TODO remove
	//TODO: Add diagnostic mode for MUX only (see commented code in ControlPanel.cpp)

	MuxShield mux;
	Adafruit_TLC5947 ledDriverBoards;

public:
	ControlPanel();
	//~ControlPanel();
	
	void refreshInputStatus();
	String getInputStatusAsString();
	//TODO: getInputStatusAsPacket
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void writeLEDStatusToLEDDriverBoards();
	void testLEDsSequentially();
	void activateLEDOverride();
	void disableLEDOverride();
	
	//Check if any stepper in the Panel needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	//Run all steppers to position. Blocks until all steppers have arrived at the position.
	void blockRunAllSteppersToPosition(long position);//TODO switch to int?
	//Run all geared steppers to STEPPER_CW_LIMIT, then STEPPER_CCW_LIMIT. TODO Heading Gauge... Blocks until all steppers have arrived at the position.
	void sweepStepperMotorsThroughMaxMinToCalibrate();
	
	void runDiagnosticMode();
	
	ModuleA moduleA;
	ModuleB moduleB;
	ModuleC moduleC;
	ModuleD moduleD;
	ModuleE moduleE;
	ModuleF moduleF;
	ModuleG moduleG;
	ModuleH moduleH;
	ModuleI moduleI;
	ModuleGT moduleGT;
};

#endif