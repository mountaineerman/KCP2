#include <Arduino.h>
#include <string.h>
#include <ModuleE.h>
#include "..\..\configuration.h"



ModuleE::ModuleE(MuxShield& mux, Adafruit_TLC5947& ledDriverBoards)
	: ledPWM_ORB		(PIN_LEDDB_ORB_LED, 	ledDriverBoards)
	, ledPWM_PLN		(PIN_LEDDB_PLN_LED, 	ledDriverBoards)
	, ledPWM_30deg		(PIN_LEDDB_30_DEG_LED, 	ledDriverBoards)
	, ledPWM_Fairing	(PIN_LEDDB_FAIRING_LED, ledDriverBoards)
	, ledPWM_Chute		(PIN_LEDDB_CHUTE_LED, 	ledDriverBoards)
	, switch_FairingButton	(PIN_MUX_FAIRING_BUTTON, 		true, MULTIPLEXER_IO_ROW_1, mux)
	, switch_ChuteButton	(PIN_MUX_CHUTE_BUTTON, 			true, MULTIPLEXER_IO_ROW_1, mux)
	, switch_SFC			(PIN_MUX_SFC_SWITCH, 			false, MULTIPLEXER_IO_ROW_2, mux)
	, switch_TGT			(PIN_MUX_TGT_SWITCH, 			false, MULTIPLEXER_IO_ROW_2, mux)
	, switch_RKT			(PIN_MUX_RKT_SWITCH, 			false, MULTIPLEXER_IO_ROW_2, mux)
	, switch_RVR			(PIN_MUX_RVR_SWITCH, 			false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_90deg			(PIN_MUX_90_DEG_SWITCH, 		false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_9deg			(PIN_MUX_9_DEG_SWITCH, 			false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_ActionGroup1	(PIN_MUX_ACTION_GROUP_1_SWITCH, false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_ActionGroup2	(PIN_MUX_ACTION_GROUP_2_SWITCH, false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_ActionGroup3	(PIN_MUX_ACTION_GROUP_3_SWITCH, false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_Science		(PIN_MUX_SCIENCE_SWITCH, 		false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_Reset			(PIN_MUX_RESET_SWITCH, 			false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_Solar			(PIN_MUX_SOLAR_SWITCH, 			false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_Ladder			(PIN_MUX_LADDER_SWITCH, 		false, MULTIPLEXER_IO_ROW_3, mux)
	, switch_AutoNavigation	(PIN_MUX_AUTONAVIGATION_SWITCH, false, MULTIPLEXER_IO_ROW_3, mux)

{
	
}

void ModuleE::refreshInputStatus() {
	this->switch_FairingButton.refreshInputStatus();
	this->switch_ChuteButton.refreshInputStatus();
	this->switch_SFC.refreshInputStatus();
	this->switch_TGT.refreshInputStatus();
	this->switch_RKT.refreshInputStatus();
	this->switch_RVR.refreshInputStatus();
	this->switch_90deg.refreshInputStatus();
	this->switch_9deg.refreshInputStatus();
	this->switch_ActionGroup1.refreshInputStatus();
	this->switch_ActionGroup2.refreshInputStatus();
	this->switch_ActionGroup3.refreshInputStatus();
	this->switch_Science.refreshInputStatus();
	this->switch_Reset.refreshInputStatus();
	this->switch_Solar.refreshInputStatus();
	this->switch_Ladder.refreshInputStatus();
	this->switch_AutoNavigation.refreshInputStatus();
}

String ModuleE::getInputStatusAsString() {
	return String("Module E ============================================================================") +
		"\nswitch_FairingButton:" + this->switch_FairingButton.getInputStatusAsString() +
		"\nswitch_ChuteButton:" + this->switch_ChuteButton.getInputStatusAsString() +
		"\nswitch_SFC:" + this->switch_SFC.getInputStatusAsString() +
		"\nswitch_TGT:" + this->switch_TGT.getInputStatusAsString() +
		"\nswitch_RKT:" + this->switch_RKT.getInputStatusAsString() +
		"\nswitch_RVR:" + this->switch_RVR.getInputStatusAsString() +
		"\nswitch_90deg:" + this->switch_90deg.getInputStatusAsString() +
		"\nswitch_9deg:" + this->switch_9deg.getInputStatusAsString() +
		"\nswitch_ActionGroup1:" + this->switch_ActionGroup1.getInputStatusAsString() +
		"\nswitch_ActionGroup2:" + this->switch_ActionGroup2.getInputStatusAsString() +
		"\nswitch_ActionGroup3:" + this->switch_ActionGroup3.getInputStatusAsString() +
		"\nswitch_Science:" + this->switch_Science.getInputStatusAsString() +
		"\nswitch_Reset:" + this->switch_Reset.getInputStatusAsString() +
		"\nswitch_Solar:" + this->switch_Solar.getInputStatusAsString() +
		"\nswitch_Ladder:" + this->switch_Ladder.getInputStatusAsString() +
		"\nswitch_AutoNavigation:" + this->switch_AutoNavigation.getInputStatusAsString() + "\n";
}

void ModuleE::setAllLEDsOff() {
	this->ledPWM_ORB.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_PLN.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_30deg.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_Fairing.setPWM(PWM_LED_MINIMUM);
	this->ledPWM_Chute.setPWM(PWM_LED_MINIMUM);
}

void ModuleE::setAllLEDsOn() {
	this->ledPWM_ORB.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_PLN.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_30deg.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_Fairing.setPWM(PWM_LED_MAXIMUM);
	this->ledPWM_Chute.setPWM(PWM_LED_MAXIMUM);
}

void ModuleE::testLEDsSequentially() {
	
	auto blinkLED = [](const LED_PWM& led) { 
		led.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
		led.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
	};
	
	blinkLED(this->ledPWM_Fairing);
	blinkLED(this->ledPWM_Chute);
	blinkLED(this->ledPWM_ORB);
	blinkLED(this->ledPWM_PLN);
	blinkLED(this->ledPWM_30deg);
	delay(DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS);
}