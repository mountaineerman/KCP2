#ifndef CONTROL_PANEL_h
#define CONTROL_PANEL_h

#include <Arduino.h>
#include <MuxShield.h>
#include <Adafruit_TLC5947.h>
#include <ModuleA.h>
//#include <ModuleB.h>
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
class ControlPanel
{
public:
	ControlPanel();
	void refreshInputs();
	void diagnosticMode();
	void stepperCalibrationMode();
	
	MuxShield& mux;
	Adafruit_TLC5947& ledDriverBoards;
	ModuleA& moduleA;
	//ModuleB& moduleB;
	//ModuleC& moduleC;
	//ModuleD& moduleD;
	//ModuleE& moduleE;
	//ModuleF& moduleF;
	//ModuleG& moduleG;
	//ModuleH& moduleH;
	//ModuleI& moduleI;
	//ModuleGT& moduleGT;
};

#endif