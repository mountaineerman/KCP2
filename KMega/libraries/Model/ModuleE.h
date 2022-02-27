#ifndef MODULE_E_h
#define MODULE_E_h

#include <Arduino.h>
#include <string.h>
#include <Interface_Input.h>
#include <Interface_LEDAggregator.h>
#include <SwitchSP2T.h>
#include <LED_PWM.h>


/* Module E
 *
 * Action Group switches/buttons:
		Science
		Reset
		Solar Panels
		Ladder
		Autonavigation
		Action Group 1
		Action Group 2
		Action Group 3
		Fairing
		Parachute
		
   3-position switches:
		Surface/Orbit/Target Speed
		Rocket/Plane/Rover Control Mode
		90/30/9 Precision
		 */
class ModuleE : public Interface_Input, public Interface_LEDAggregator
{
public:
	ModuleE(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards);
	void refreshInputStatus();
	String getInputStatusAsString();
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	//Parts:
	LED_PWM ledPWM_ORB;
	LED_PWM ledPWM_PLN;
	LED_PWM ledPWM_30deg;
	LED_PWM ledPWM_Fairing;
	LED_PWM ledPWM_Chute;
	SwitchSP2T switch_FairingButton;
	SwitchSP2T switch_ChuteButton;
	SwitchSP2T switch_SFC;
	SwitchSP2T switch_TGT;
	SwitchSP2T switch_RKT;
	SwitchSP2T switch_RVR;
	SwitchSP2T switch_90deg;
	SwitchSP2T switch_9deg;
	SwitchSP2T switch_ActionGroup1;
	SwitchSP2T switch_ActionGroup2;
	SwitchSP2T switch_ActionGroup3;
	SwitchSP2T switch_Science;
	SwitchSP2T switch_Reset;
	SwitchSP2T switch_Solar;
	SwitchSP2T switch_Ladder;
	SwitchSP2T switch_AutoNavigation;

};

#endif