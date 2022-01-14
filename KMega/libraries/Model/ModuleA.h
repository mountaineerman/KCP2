#ifndef MODULE_A_h
#define MODULE_A_h

#include <Arduino.h>
#include <string.h>
#include <SwitchSP2T.h>
#include <AnalogInput.h>
#include <LED_PWM.h>


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