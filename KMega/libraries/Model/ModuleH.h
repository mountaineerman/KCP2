#ifndef MODULE_H_h
#define MODULE_H_h

#include <Arduino.h>
#include <string.h>
#include <Interface_Input.h>
#include <Interface_LEDAggregator.h>
#include <SwitchSP2T.h>
#include <LED_PWM.h>


/* Module H
 *
 * Glass Cockpit Buttons */
class ModuleH : public Interface_Input, public Interface_LEDAggregator
{
public:
	ModuleH(Adafruit_TLC5947& ledDriverBoards);
	void refreshInputStatus();
	String getInputStatusAsString();
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	//Parts:
	SwitchSP2T switch_GlassCockpit_TL;
	SwitchSP2T switch_GlassCockpit_CL;
	SwitchSP2T switch_GlassCockpit_BL;
	SwitchSP2T switch_GlassCockpit_TR;
	SwitchSP2T switch_GlassCockpit_CR;
	SwitchSP2T switch_GlassCockpit_BR;
	LED_PWM ledPWM_GlassCockpit_TL;
	LED_PWM ledPWM_GlassCockpit_CL;
	LED_PWM ledPWM_GlassCockpit_BL;
	LED_PWM ledPWM_GlassCockpit_TR;
	LED_PWM ledPWM_GlassCockpit_CR;
	LED_PWM ledPWM_GlassCockpit_BR;
};

#endif