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
//#include <ModuleE.h>
#include <ModuleF.h>
//#include <ModuleG.h>
#include <ModuleH.h>
//#include <ModuleI.h>
//#include <ModuleGT.h>



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
	//TODO: Add diagnostic mode for MUX only (see commented code in ControlPanel.cpp)

	MuxShield mux; //TODO: const? Does it need to be public?
	Adafruit_TLC5947 ledDriverBoards; //TODO: const? Does it need to be public?

public:
	ControlPanel();
	//~ControlPanel();
	
	ModuleA moduleA;
	ModuleB moduleB;
	ModuleC moduleC;
	ModuleD moduleD;
	//ModuleE moduleE;
	ModuleF moduleF;
	//ModuleG moduleG;
	ModuleH moduleH;
	//ModuleI moduleI;
	//ModuleGT moduleGT;
	
	void refreshInputStatus();
	String getInputStatusAsString();
	//TODO: getInputStatusAsPacket
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	void resetStepperToStartingPosition();
	
	void runDiagnosticMode();
	
	void activateLEDOverride();
	void disableLEDOverride();
};

#endif