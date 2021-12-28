#ifndef MODULE_A_h
#define MODULE_A_h

#include "Arduino.h"
#include <string>
#include "Model/PartTypes/SwitchSP2T.h"
#include "Model/PartTypes/AnalogInput.h"
#include "Model/PartTypes/LED_PWM.h"


/* Module A
 *
 * Throttle, Staging, and Brake Button */
class ModuleA
{
public:
	ModuleA();
	void refreshInputs();
	String getInputStatus();
//protected:
//private:
};

#endif