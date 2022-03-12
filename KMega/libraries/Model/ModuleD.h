#ifndef MODULE_D_h
#define MODULE_D_h

#include <Arduino.h>
#include <string.h>
#include <Interface_Input.h>
#include <Interface_LEDAggregator.h>
#include <SwitchSP2T.h>
#include <LED_PWM.h>


/* Module D
 *
 * SAS, RCS, Lights, Gear, Brake, Map, Mute, and Autopilot Commands */
class ModuleD : public Interface_Input, public Interface_LEDAggregator
{
public:
	ModuleD(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards);
	void refreshInputStatus();
	String getInputStatusAsString();
	void setAllLEDsOff();
	void setAllLEDsOn();
	void testLEDsSequentially();
	
	//Parts:
	LED_PWM ledPWM_BrakeModuleD;
	LED_PWM ledPWM_AutopilotHold;
	LED_PWM ledPWM_AutopilotPrograde;
	LED_PWM ledPWM_AutopilotRetrograde;
	LED_PWM ledPWM_AutopilotNormal_Red;
	LED_PWM ledPWM_AutopilotNormal_Blue;
	LED_PWM ledPWM_AutopilotAntiNormal_Red;
	LED_PWM ledPWM_AutopilotAntiNormal_Blue;
	LED_PWM ledPWM_AutopilotRadialIn_Green;
	LED_PWM ledPWM_AutopilotRadialIn_Blue;
	LED_PWM ledPWM_AutopilotRadialOut_Green;
	LED_PWM ledPWM_AutopilotRadialOut_Blue;
	LED_PWM ledPWM_AutopilotTarget_Red;
	LED_PWM ledPWM_AutopilotTarget_Blue;
	LED_PWM ledPWM_AutopilotAntiTarget_Red;
	LED_PWM ledPWM_AutopilotAntiTarget_Blue;
	LED_PWM ledPWM_AutopilotManeuver;
	SwitchSP2T switch_AutopilotHold;
	SwitchSP2T switch_AutopilotPrograde;
	SwitchSP2T switch_AutopilotRetrograde;
	SwitchSP2T switch_AutopilotNormal;
	SwitchSP2T switch_AutopilotAntiNormal;
	SwitchSP2T switch_AutopilotRadialIn;
	SwitchSP2T switch_AutopilotRadialOut;
	SwitchSP2T switch_AutopilotTarget;
	SwitchSP2T switch_AutopilotAntiTarget;
	SwitchSP2T switch_AutopilotManeuver;
	SwitchSP2T switch_SAS;
	SwitchSP2T switch_RCS;
	SwitchSP2T switch_Lights;
	SwitchSP2T switch_Gear;
	SwitchSP2T switch_Brake;
	SwitchSP2T switch_Map;
	SwitchSP2T switch_Mute;
};

#endif