#ifndef MODULE_B_h
#define MODULE_B_h

#include <Arduino.h>
#include <string.h>
#include <Interface_Input.h>
#include <SwitchSP2T.h>
#include <AnalogInput.h>


/* Module B
 *
 * Joystick, Trim Controls, Abort, Time Warp */
class ModuleB : public Interface_Input
{
public:
	ModuleB(MuxShield& mux);
	void refreshInputStatus();
	String getInputStatusAsString();
	//TODO: Get input status in packet-like form?
	
	//Parts:
	AnalogInput analogInput_Joystick_Pitch;
	AnalogInput analogInput_Joystick_Yaw;
	AnalogInput analogInput_Joystick_Roll;
	SwitchSP2T switch_Joystick;
	SwitchSP2T switch_TimeWarpDown;
	SwitchSP2T switch_TimeWarpUp;
	SwitchSP2T switch_AbortButton;
	SwitchSP2T switch_PitchTrim;
	SwitchSP2T switch_YawTrim;
	SwitchSP2T switch_RollTrim;
};

#endif