#include <Arduino.h>
#include <string.h>
//#include <stdlib.h> //TODO remove?
#include <ModuleB.h>
#include "..\..\configuration.h"



ModuleB::ModuleB(MuxShield& mux)
	: analogInput_Joystick_Pitch(PIN_JOYSTICK_PITCH)
	, analogInput_Joystick_Yaw	(PIN_JOYSTICK_YAW)
	, analogInput_Joystick_Roll	(PIN_JOYSTICK_ROLL)
	, switch_Joystick		(PIN_MUX_JOYSTICK_BUTTON,	true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_TimeWarpDown	(PIN_TIME_WARP_DOWN, 		true)
	, switch_TimeWarpUp		(PIN_TIME_WARP_UP, 			true)
	, switch_AbortButton	(PIN_MUX_ABORT_BUTTON, 		false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_PitchTrim		(PIN_MUX_PITCH_TRIM_SWITCH, false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_YawTrim		(PIN_MUX_YAW_TRIM_SWITCH, 	false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_RollTrim		(PIN_MUX_ROLL_TRIM_SWITCH, 	false,	MULTIPLEXER_IO_ROW_2, mux)
{
	//Serial.println("ModuleB Constructor");
}

void ModuleB::refreshInputStatus() {
	this->analogInput_Joystick_Pitch.refreshInputStatus();
	this->analogInput_Joystick_Yaw.refreshInputStatus();
	this->analogInput_Joystick_Roll.refreshInputStatus();
	this->switch_Joystick.refreshInputStatus();
	this->switch_TimeWarpDown.refreshInputStatus();
	this->switch_TimeWarpUp.refreshInputStatus();
	this->switch_AbortButton.refreshInputStatus();
	this->switch_PitchTrim.refreshInputStatus();
	this->switch_YawTrim.refreshInputStatus();
	this->switch_RollTrim.refreshInputStatus();
}

String ModuleB::getInputStatus() {
	return String("Module B ============================================================================") +
		   "\nanalogInput_Joystick_Pitch:" + this->analogInput_Joystick_Pitch.getInputStatus() +
		   "\nanalogInput_Joystick_Yaw:" + this->analogInput_Joystick_Yaw.getInputStatus() +
		   "\nanalogInput_Joystick_Roll:" + this->analogInput_Joystick_Roll.getInputStatus() +
		   "\nswitch_Joystick:" + this->switch_Joystick.getInputStatus() +
		   "\nswitch_TimeWarpDown:" + this->switch_TimeWarpDown.getInputStatus() +
		   "\nswitch_TimeWarpUp:" + this->switch_TimeWarpUp.getInputStatus() +
		   "\nswitch_AbortButton:" + this->switch_AbortButton.getInputStatus() +
		   "\nswitch_PitchTrim:" + this->switch_PitchTrim.getInputStatus() +
		   "\nswitch_YawTrim:" + this->switch_YawTrim.getInputStatus() +
		   "\nswitch_RollTrim:" + this->switch_RollTrim.getInputStatus() + "\n";
}