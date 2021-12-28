#ifndef CONTROL_PANEL_h
#define CONTROL_PANEL_h

#include "Arduino.h"
#include "LocalArduinoLibraries/MuxShield.h" //Local Arduino Library (downloaded manually and imported into Eclipse)
#include "Adafruit_TLC5947.h" //Library Managed by Eclipse
#include "Model/Modules/ModuleA.h"
#include "Model/Modules/ModuleB.h"
#include "Model/Modules/ModuleC.h"
#include "Model/Modules/ModuleD.h"
#include "Model/Modules/ModuleE.h"
#include "Model/Modules/ModuleF.h"
#include "Model/Modules/ModuleG.h"
#include "Model/Modules/ModuleH.h"
#include "Model/Modules/ModuleI.h"
#include "Model/Modules/ModuleGT.h"

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
	ModuleB& moduleB;
	ModuleC& moduleC;
	ModuleD& moduleD;
	ModuleE& moduleE;
	ModuleF& moduleF;
	ModuleG& moduleG;
	ModuleH& moduleH;
	ModuleI& moduleI;
	ModuleGT& moduleGT;
};

#endif