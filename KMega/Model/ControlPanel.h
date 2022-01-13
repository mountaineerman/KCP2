#ifndef CONTROL_PANEL_h
#define CONTROL_PANEL_h

#include <Arduino.h>
#include "C:\dev\KCP2\KMega\LocalArduinoLibraries\MuxShield\MuxShield.h"
#include "C:\dev\KCP2\KMega\LocalArduinoLibraries\Adafruit_TLC5947\Adafruit_TLC5947.h"
#include "C:\dev\KCP2\KMega\Model\Modules\ModuleA.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleB.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleC.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleD.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleE.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleF.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleG.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleH.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleI.h"
//#include "C:\dev\KCP2\KMega\Model\Modules\ModuleGT.h"

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