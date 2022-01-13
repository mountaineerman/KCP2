#ifndef MODULE_A_h
#define MODULE_A_h

#include <Arduino.h>
#include <string.h>
#include "C:\dev\KCP2\KMega\Model\PartTypes\SwitchSP2T.h"
#include "C:\dev\KCP2\KMega\Model\PartTypes\AnalogInput.h"
#include "C:\dev\KCP2\KMega\Model\PartTypes\LED_PWM.h"


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