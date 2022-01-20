#ifndef CONTROL_PANEL_h
#define CONTROL_PANEL_h

#include <Arduino.h>

#include <MuxShield.h>
#include <Adafruit_TLC5947.h>

#include <Interface_Input.h>
//#include <Interface_LEDAggregator.h>

#include <ModuleA.h>
#include <ModuleB.h>
//#include <ModuleC.h>
//#include <ModuleD.h>
//#include <ModuleE.h>
//#include <ModuleF.h>
//#include <ModuleG.h>
//#include <ModuleH.h>
//#include <ModuleI.h>
//#include <ModuleGT.h>



/* MkII Control Panel
 */
class ControlPanel : public Interface_Input
{
private:
	void diagnosticMode_testAllInputs();
	void diagnosticMode_testAllLEDs();
	void diagnosticMode_testLEDsSequentially();
	void diagnosticMode_testStepperMotors();

	MuxShield mux; //TODO: const? Does it need to be public?
	Adafruit_TLC5947 ledDriverBoards; //TODO: const? Does it need to be public?

public:
	ModuleA moduleA;
	ModuleB moduleB;
	//ModuleC moduleC;
	//ModuleD moduleD;
	//ModuleE moduleE;
	//ModuleF moduleF;
	//ModuleG moduleG;
	//ModuleH moduleH;
	//ModuleI moduleI;
	//ModuleGT moduleGT;
	
	ControlPanel();
	void refreshInputStatus();
	String getInputStatusAsString();
	//TODO: getInputStatusAsPacket
	
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	void runDiagnosticMode();
	
	void activateLEDOverride();
	void disableLEDOverride();
};

#endif