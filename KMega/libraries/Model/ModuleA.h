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
	ModuleA(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards);
	void refreshInputStatus();
	String getInputStatus();
	//TODO: Get input status in packet-like form?
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	//Parts:
	AnalogInput analogInput_Throttle;
	SwitchSP2T switch_BrakeButton;
	SwitchSP2T switch_StagingButton;
	LED_PWM ledPWM_BrakeModA;
};

#endif