#ifndef MODULE_F_h
#define MODULE_F_h

#include <Arduino.h>
#include <string.h>
#include <Interface_Input.h>
#include <Interface_LEDAggregator.h>
#include <SwitchSP2T.h>
#include <AnalogInput.h>
#include <LED_PWM.h>


/* Module F
 *
 * Main Power, Reset, Trim Master, Multi-Purpose Potentiometer, 4-position precision switch */
class ModuleF : public Interface_Input, public Interface_LEDAggregator
{
public:
	ModuleF(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards);
	void refreshInputStatus();
	String getInputStatusAsString();
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	//Parts:
	SwitchSP2T switch_trim;
	SwitchSP2T switch_4pos_AB;
	SwitchSP2T switch_4pos_CD;
	AnalogInput analogInput_Current;
	AnalogInput analogInput_MultiPot;
	LED_PWM ledPWM_twistSwitch25;
	LED_PWM ledPWM_twistSwitch50;
	LED_PWM ledPWM_twistSwitch75;
	LED_PWM ledPWM_twistSwitch100;
};

#endif