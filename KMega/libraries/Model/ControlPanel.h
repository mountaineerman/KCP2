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
	//void diagnosticMode_testGearedStepperMotor(StepperMotor& stepperMotorUnderTest); //TODO remove
	void diagnosticMode_testNEMA17StepperMotor(StepperMotorNEMA17& stepperMotorUnderTest);
	void diagnosticMode_testStepperMotor2(StepperMotor2& stepperMotorUnderTest);
	void diagnosticMode_sweepSingleStepperMotor(StepperMotor2& stepperMotorUnderTest);
	void diagnosticMode_sweepAllStepperMotors();
	void diagnosticMode_fuelTest();
	//TODO: Add diagnostic mode for MUX only (see commented code in ControlPanel.cpp)
	
	//Step "stepper", up to the "maxNumberOfSteps"
	void stepMotorUpTo(StepperMotor2& stepper, int maxNumberOfSteps);

	MuxShield mux;
	Adafruit_TLC5947 ledDriverBoards;

public:
	ControlPanel();
	//~ControlPanel();
	
	void refreshInputStatus();
	String getInputStatusAsString();
	
	void setAllLEDsTo(int pwm_level); //See PWM_LED_MINIMUM / PWM_LED_DIM / PWM_LED_MAXIMUM
	void writeLEDStatusToLEDDriverBoards();
	void testLEDsSequentially();
	void activateLEDOverride();
	void disableLEDOverride();
	
	//Run stepper motors to their desiredPosition, up to a configurable number of steps.
	void burstRunSteppers();
	//Check if any stepper in the Panel needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
	bool runStepperIfNecessary();
	//Run all steppers to position. Blocks until all steppers have arrived at the position.
	void blockRunAllSteppersToPosition(int position, unsigned long stepTimeInMicroseconds);
	//Run all geared steppers to GEARED_STEPPER_CW_LIMIT, then STEPPER_CCW_LIMIT. TODO Heading Gauge... Blocks until all steppers have arrived at the position.
	void sweepStepperMotorsThroughMaxMin();
	
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