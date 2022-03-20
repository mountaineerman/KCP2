#include <Arduino.h>
#include <string.h>
#include <ModuleD.h>
#include "..\..\configuration.h"



ModuleD::ModuleD(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards)
	: ledPWM_BrakeModuleD				(PIN_LEDDB_BRAKE_LED_MODULE_D, 				ledDriverBoards)
	, ledPWM_AutopilotHold				(PIN_LEDDB_AUTOPILOT_LED_HOLD, 				ledDriverBoards)
	, ledPWM_AutopilotPrograde			(PIN_LEDDB_AUTOPILOT_LED_PROGRADE, 			ledDriverBoards)
	, ledPWM_AutopilotRetrograde		(PIN_LEDDB_AUTOPILOT_LED_RETROGRADE, 		ledDriverBoards)
	, ledPWM_AutopilotNormal_Red		(PIN_LEDDB_AUTOPILOT_LED_NORMAL_RED, 		ledDriverBoards)
	, ledPWM_AutopilotNormal_Blue		(PIN_LEDDB_AUTOPILOT_LED_NORMAL_BLU, 		ledDriverBoards)
	, ledPWM_AutopilotAntiNormal_Red	(PIN_LEDDB_AUTOPILOT_LED_ANTINORMAL_RED, 	ledDriverBoards)
	, ledPWM_AutopilotAntiNormal_Blue	(PIN_LEDDB_AUTOPILOT_LED_ANTINORMAL_BLUE, 	ledDriverBoards)
	, ledPWM_AutopilotRadialIn_Green	(PIN_LEDDB_AUTOPILOT_LED_RADIALIN_GRN, 		ledDriverBoards)
	, ledPWM_AutopilotRadialIn_Blue		(PIN_LEDDB_AUTOPILOT_LED_RADIALIN_BLU, 		ledDriverBoards)
	, ledPWM_AutopilotRadialOut_Green	(PIN_LEDDB_AUTOPILOT_LED_RADIALOUT_GRN, 	ledDriverBoards)
	, ledPWM_AutopilotRadialOut_Blue	(PIN_LEDDB_AUTOPILOT_LED_RADIALOUT_BLU, 	ledDriverBoards)
	, ledPWM_AutopilotTarget_Red		(PIN_LEDDB_AUTOPILOT_LED_TARGET_RED, 		ledDriverBoards)
	, ledPWM_AutopilotTarget_Blue		(PIN_LEDDB_AUTOPILOT_LED_TARGET_BLU, 		ledDriverBoards)
	, ledPWM_AutopilotAntiTarget_Red	(PIN_LEDDB_AUTOPILOT_LED_ANTITARGET_RED, 	ledDriverBoards)
	, ledPWM_AutopilotAntiTarget_Blue	(PIN_LEDDB_AUTOPILOT_LED_ANTITARGET_BLU, 	ledDriverBoards)
	, ledPWM_AutopilotManeuver			(PIN_LEDDB_AUTOPILOT_LED_MANEUVER, 			ledDriverBoards)
	, switch_AutopilotHold		(PIN_MUX_AUTOPILOT_HOLD_BUTTON, 		true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotPrograde	(PIN_MUX_AUTOPILOT_PROGRADE_BUTTON, 	true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotRetrograde(PIN_MUX_AUTOPILOT_RETROGRADE_BUTTON, 	true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotNormal	(PIN_MUX_AUTOPILOT_NORMAL_BUTTON, 		true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotAntiNormal(PIN_MUX_AUTOPILOT_ANTINORMAL_BUTTON, 	true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotRadialIn	(PIN_MUX_AUTOPILOT_RADIALIN_BUTTON, 	true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotRadialOut	(PIN_MUX_AUTOPILOT_RADIALOUT_BUTTON, 	true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotTarget	(PIN_MUX_AUTOPILOT_TARGET_BUTTON, 		true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotAntiTarget(PIN_MUX_AUTOPILOT_ANTITARGET_BUTTON, 	true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_AutopilotManeuver	(PIN_MUX_AUTOPILOT_MANEUVER, 			true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_SAS				(PIN_MUX_SAS_SWITCH, 					false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_RCS				(PIN_MUX_RCS_SWITCH, 					false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_Lights				(PIN_MUX_LIGHTS_SWITCH, 				false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_Gear				(PIN_MUX_GEAR_SWITCH, 					false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_Brake				(PIN_MUX_BRAKE_SWITCH, 					true,	MULTIPLEXER_IO_ROW_1, mux)
	, switch_Map				(PIN_MUX_MAP_SWITCH, 					false,	MULTIPLEXER_IO_ROW_2, mux)
	, switch_Mute				(PIN_MUX_MUTE_SWITCH, 					false,	MULTIPLEXER_IO_ROW_2, mux)
{
	
}

void ModuleD::refreshInputStatus() {
	this->switch_AutopilotHold.refreshInputStatus();
	this->switch_AutopilotPrograde.refreshInputStatus();
	this->switch_AutopilotRetrograde.refreshInputStatus();
	this->switch_AutopilotNormal.refreshInputStatus();
	this->switch_AutopilotAntiNormal.refreshInputStatus();
	this->switch_AutopilotRadialIn.refreshInputStatus();
	this->switch_AutopilotRadialOut.refreshInputStatus();
	this->switch_AutopilotTarget.refreshInputStatus();
	this->switch_AutopilotAntiTarget.refreshInputStatus();
	this->switch_AutopilotManeuver.refreshInputStatus();
	this->switch_SAS.refreshInputStatus();
	this->switch_RCS.refreshInputStatus();
	this->switch_Lights.refreshInputStatus();
	this->switch_Gear.refreshInputStatus();
	this->switch_Brake.refreshInputStatus();
	this->switch_Map.refreshInputStatus();
	this->switch_Mute.refreshInputStatus();
}

String ModuleD::getInputStatusAsString() {
	return String("Module D ============================================================================") +
		   "\nswitch_AutopilotHold:" + this->switch_AutopilotHold.getInputStatusAsString() +
		   "\nswitch_AutopilotPrograde:" + this->switch_AutopilotPrograde.getInputStatusAsString() +
		   "\nswitch_AutopilotRetrograde:" + this->switch_AutopilotRetrograde.getInputStatusAsString() +
		   "\nswitch_AutopilotNormal:" + this->switch_AutopilotNormal.getInputStatusAsString() +
		   "\nswitch_AutopilotAntiNormal:" + this->switch_AutopilotAntiNormal.getInputStatusAsString() +
		   "\nswitch_AutopilotRadialIn:" + this->switch_AutopilotRadialIn.getInputStatusAsString() +
		   "\nswitch_AutopilotRadialOut:" + this->switch_AutopilotRadialOut.getInputStatusAsString() +
		   "\nswitch_AutopilotTarget:" + this->switch_AutopilotTarget.getInputStatusAsString() +
		   "\nswitch_AutopilotAntiTarget:" + this->switch_AutopilotAntiTarget.getInputStatusAsString() +
		   "\nswitch_AutopilotManeuver:" + this->switch_AutopilotManeuver.getInputStatusAsString() +
		   "\nswitch_SAS:" + this->switch_SAS.getInputStatusAsString() +
		   "\nswitch_RCS:" + this->switch_RCS.getInputStatusAsString() +
		   "\nswitch_Lights:" + this->switch_Lights.getInputStatusAsString() +
		   "\nswitch_Gear:" + this->switch_Gear.getInputStatusAsString() +
		   "\nswitch_Brake:" + this->switch_Brake.getInputStatusAsString() +
		   "\nswitch_Map:" + this->switch_Map.getInputStatusAsString() +
		   "\nswitch_Mute:" + this->switch_Mute.getInputStatusAsString() + "\n";
}

void ModuleD::setAllLEDsOff() {
	this->ledPWM_BrakeModuleD.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotHold.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotPrograde.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotRetrograde.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotNormal_Red.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotNormal_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotAntiNormal_Red.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotAntiNormal_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotRadialIn_Green.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotRadialIn_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotRadialOut_Green.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotRadialOut_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotTarget_Red.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotTarget_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotAntiTarget_Red.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotAntiTarget_Blue.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	this->ledPWM_AutopilotManeuver.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
}

void ModuleD::setAllLEDsOn() {
	this->ledPWM_BrakeModuleD.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotHold.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotPrograde.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotRetrograde.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotNormal_Red.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotNormal_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotAntiNormal_Red.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotAntiNormal_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotRadialIn_Green.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotRadialIn_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotRadialOut_Green.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotRadialOut_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotTarget_Red.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotTarget_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotAntiTarget_Red.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotAntiTarget_Blue.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
	this->ledPWM_AutopilotManeuver.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
}

void ModuleD::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) { 
		led.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_AutopilotHold);
	blinkLED(this->ledPWM_AutopilotPrograde);
	blinkLED(this->ledPWM_AutopilotRetrograde);
	blinkLED(this->ledPWM_AutopilotNormal_Red);
	blinkLED(this->ledPWM_AutopilotNormal_Blue);
	blinkLED(this->ledPWM_AutopilotAntiNormal_Red);
	blinkLED(this->ledPWM_AutopilotAntiNormal_Blue);
	blinkLED(this->ledPWM_AutopilotRadialIn_Green);
	blinkLED(this->ledPWM_AutopilotRadialIn_Blue);
	blinkLED(this->ledPWM_AutopilotRadialOut_Green);
	blinkLED(this->ledPWM_AutopilotRadialOut_Blue);
	blinkLED(this->ledPWM_AutopilotTarget_Red);
	blinkLED(this->ledPWM_AutopilotTarget_Blue);
	blinkLED(this->ledPWM_AutopilotAntiTarget_Red);
	blinkLED(this->ledPWM_AutopilotAntiTarget_Blue);
	blinkLED(this->ledPWM_AutopilotManeuver);
	blinkLED(this->ledPWM_BrakeModuleD);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}