#include <Arduino.h>
#include <string.h>

#include <MuxShield.h>

#include <ControlPanel.h>
#include <CommonUtilities.h>
#include "../../configuration.h"


ControlPanel::ControlPanel()
	: mux()
	, ledDriverBoards(NUMBER_OF_LED_DRIVER_BOARDS, PIN_LED_DRIVER_BOARDS_CLOCK, PIN_LED_DRIVER_BOARDS_DATA_IN, PIN_LED_DRIVER_BOARDS_LATCH)
	, moduleA(mux, ledDriverBoards)
	, moduleB(mux)
	, moduleC(ledDriverBoards)
	, moduleD(mux, ledDriverBoards)
	, moduleE(mux, ledDriverBoards)
	, moduleF(mux, ledDriverBoards)
	, moduleG(ledDriverBoards)
	, moduleH(ledDriverBoards)
	, moduleI(ledDriverBoards)
	, moduleGT(ledDriverBoards)
{
	this->mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP); //VERIFIED
	this->mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);		   //VERIFIED
	this->mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);		   //VERIFIED
	
	//TODO: Remove://this->ledDriverBoards = new Adafruit_TLC5947(NUMBER_OF_LED_DRIVER_BOARDS, PIN_LED_DRIVER_BOARDS_CLOCK, PIN_LED_DRIVER_BOARDS_DATA_IN, PIN_LED_DRIVER_BOARDS_LATCH);
	this->ledDriverBoards.begin();
	//TODO: replace the following 2 lines with an LED OVERRIDE function... And add a bit to kkim>kmega interface...
	pinMode(PIN_LED_DRIVER_BOARDS_OVERRIDE, OUTPUT);
	this->disableLEDOverride();
	
	this->setAllLEDsTo(PWM_LED_MINIMUM);
}

void ControlPanel::refreshInputStatus() {
	this->moduleA.refreshInputStatus();
	this->moduleB.refreshInputStatus();
	this->moduleD.refreshInputStatus();
	this->moduleE.refreshInputStatus();
	this->moduleF.refreshInputStatus();
	this->moduleG.refreshInputStatus();
	this->moduleH.refreshInputStatus();
	this->moduleI.refreshInputStatus();
}

String ControlPanel::getInputStatusAsString() { //TODO: Fix (figure out overflow error (empty after adding Module H)???)
	//return this->moduleA.getInputStatusAsString() +
	//	   this->moduleB.getInputStatusAsString() +
	//	   this->moduleD.getInputStatusAsString() +
	//	   this->moduleE.getInputStatusAsString() +
	//	   this->moduleF.getInputStatusAsString() +
	//	   //this->moduleG.getInputStatusAsString() +
	//	   this->moduleH.getInputStatusAsString();// +
	//	   //this->moduleI.getInputStatusAsString();
	
	Serial.print(this->moduleA.getInputStatusAsString());
	Serial.print(this->moduleB.getInputStatusAsString());
	Serial.print(this->moduleD.getInputStatusAsString());
	Serial.print(this->moduleE.getInputStatusAsString());
	Serial.print(this->moduleF.getInputStatusAsString());
	Serial.print(this->moduleG.getInputStatusAsString());
	Serial.print(this->moduleH.getInputStatusAsString());
	Serial.print(this->moduleI.getInputStatusAsString());
	return(String(""));
}

void ControlPanel::setAllLEDsTo(int pwm_level) {
	this->moduleA.setAllLEDsTo(pwm_level);
	this->moduleC.setAllLEDsTo(pwm_level);
	this->moduleD.setAllLEDsTo(pwm_level);
	this->moduleE.setAllLEDsTo(pwm_level);
	this->moduleF.setAllLEDsTo(pwm_level);
	this->moduleG.setAllLEDsTo(pwm_level);
	this->moduleH.setAllLEDsTo(pwm_level);
	this->moduleI.setAllLEDsTo(pwm_level);
	this->moduleGT.setAllLEDsTo(pwm_level);
	this->writeLEDStatusToLEDDriverBoards();
}

void ControlPanel::writeLEDStatusToLEDDriverBoards() {
	this->ledDriverBoards.write();
}

void ControlPanel::testLEDsSequentially() {
	this->moduleA.testLEDsSequentially();
	this->moduleC.testLEDsSequentially();
	this->moduleD.testLEDsSequentially();
	this->moduleE.testLEDsSequentially();
	this->moduleF.testLEDsSequentially();
	this->moduleG.testLEDsSequentially();
	this->moduleH.testLEDsSequentially();
	this->moduleI.testLEDsSequentially();
	this->moduleGT.testLEDsSequentially();
}

void ControlPanel::runDiagnosticMode() {
	
	String userInput;
	
	while(true) {
	
		clearScreen();
		Serial.println(F("Diagnostic Mode activated. Select one of the following:"));
		Serial.println(F("[0] Exit"));
		Serial.println(F("[1] Test all inputs"));
		Serial.println(F("[2] Test all LEDs (simultaneously)"));
		Serial.println(F("[3] Test LEDs sequentially"));
		
		userInput = Serial.readStringUntil('\n');
		//Serial.println(userInput);
		delay(100);
		
		if(userInput == "0") {
			Serial.println("Exiting...");
			return;
		} else if(userInput == "1") {
			this->diagnosticMode_testAllInputs();
		} else if(userInput == "2") {
			this->diagnosticMode_testAllLEDs();
		} else if(userInput == "3") {
			this->diagnosticMode_testLEDsSequentially();
		}
	}
}

void ControlPanel::diagnosticMode_testAllInputs() {
	
	Serial.setTimeout(2000);//Lower delay
	String userInput;
	
	while(true) {
		
		clearScreen();
		Serial.println(F("Testing All Inputs. Enter '0' at any time to return to the main menu."));
		this->refreshInputStatus();
		this->getInputStatusAsString();//TODO: Fix: //Serial.println(this->getInputStatusAsString());
		
		userInput = Serial.readStringUntil('\n');
		//Serial.println(userInput);
		
		if(userInput == "0") {
			Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);//Reset delay
			return;
		}
	}
}

void ControlPanel::diagnosticMode_testAllLEDs() {
	
	clearScreen();
	Serial.println(F("Testing All LEDs. Enter '0' at any time to return to the main menu."));
	this->setAllLEDsTo(PWM_LED_MAXIMUM);
	
	String userInput;
	
	while(true) {
		
		userInput = Serial.readStringUntil('\n');
		//Serial.println(userInput);
		//delay(3000);
		
		if(userInput == "0") {
			this->setAllLEDsTo(PWM_LED_MINIMUM);
			return;
		}
	}
}

void ControlPanel::diagnosticMode_testLEDsSequentially() {

	String userInput;
	
	while(true) {
	
		clearScreen();
		Serial.println(F("Testing LEDs Sequentially. LEDs will be lit top to bottom, left to right."));
		Serial.println(F("RGB LEDs will be lit in R>G>B order. Select which module(s) to test:"));
		Serial.println(F("[0] Return to main menu"));
		Serial.println(F("[all] All"));
		Serial.println(F("[a] Module A"));
		Serial.println(F("[c] Module C"));
		Serial.println(F("[d] Module D"));
		Serial.println(F("[e] Module E"));
		Serial.println(F("[f] Module F"));
		Serial.println(F("[g] Module G"));
		Serial.println(F("[h] Module H"));
		Serial.println(F("[i] Module I"));
		Serial.println(F("[gt] Gauge Tower"));
		
		userInput = Serial.readStringUntil('\n');
		
		if(userInput == "0") {
			return;
		} else if(userInput == "all") {
			this->testLEDsSequentially();
		} else if(userInput == "a") {
			this->moduleA.testLEDsSequentially();
		} else if(userInput == "c") {
			this-> moduleC.testLEDsSequentially();
		} else if(userInput == "d") {
			this->moduleD.testLEDsSequentially();
		} else if(userInput == "e") {
			this->moduleE.testLEDsSequentially();
		} else if(userInput == "f") {
			this->moduleF.testLEDsSequentially();
		} else if(userInput == "g") {
			this->moduleG.testLEDsSequentially();
		} else if(userInput == "h") {
			this->moduleH.testLEDsSequentially();
		} else if(userInput == "i") {
			this->moduleI.testLEDsSequentially();
		} else if(userInput == "gt") {
			this->moduleGT.testLEDsSequentially();
		}
	}
}

//Regardless of what status the LEDs driven by the LED Driver Boards are, turn them off
void ControlPanel::activateLEDOverride() {
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, HIGH);
}

//Allow the LEDs driven by the LED Driver Boards to be ON
void ControlPanel::disableLEDOverride() {
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, LOW);
}