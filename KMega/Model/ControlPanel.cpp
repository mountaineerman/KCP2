#include "Arduino.h"
#include "ControlPanel.h"
#include "configuration.h"

ControlPanel::ControlPanel() {
	this->mux = new MuxShield();
	this->mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP); //TODO is this right?
	this->mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);		   //TODO is this right?
	this->mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);		   //TODO is this right?
	
	this->ledDriverBoards = new Adafruit_TLC5947(NUMBER_OF_LED_DRIVER_BOARDS, PIN_LED_DRIVER_BOARDS_CLOCK, PIN_LED_DRIVER_BOARDS_DATA_IN, PIN_LED_DRIVER_BOARDS_LATCH);
	this->ledDriverBoards.begin();
	//TODO replace the following 2 lines with an LED OVERRIDE function... And add a bit to kkim>kmega interface...
	pinMode(PIN_LED_DRIVER_BOARDS_OVERRIDE, OUTPUT);
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, LOW); //Set to LOW to enable the outputs.
	
	this->moduleA = new ModuleA();
	this->moduleB = new ModuleB();
	this->moduleC = new ModuleC();
	this->moduleD = new ModuleD();
	this->moduleE = new ModuleE();
	this->moduleF = new ModuleF();
	this->moduleG = new ModuleG();
	this->moduleH = new ModuleH();
	this->moduleI = new ModuleI();
	this->moduleGT = new ModuleGT();
}

void ControlPanel::refreshInputs() {
	this->moduleA.refreshInputs();
	this->moduleB.refreshInputs();
	this->moduleD.refreshInputs();
	this->moduleE.refreshInputs();
	this->moduleF.refreshInputs();
	this->moduleG.refreshInputs();
	this->moduleH.refreshInputs();
	this->moduleI.refreshInputs();
}

void ControlPanel::diagnosticMode() {

	Serial.println("Activating Diagnostic Mode...");
	delay(3000);
	
	do {
		this->refreshInputs();
		
		if(this->moduleH.<TR Switch>.getStatus()) { // Test All Inputs
			for(int i=1; i<=30; i++){ Serial.println(); }
			Serial.print(this->moduleA.getInputStatus());
			Serial.print(this->moduleB.getInputStatus());
			Serial.print(this->moduleD.getInputStatus());
			Serial.print(this->moduleE.getInputStatus());
			Serial.print(this->moduleF.getInputStatus());
			Serial.print(this->moduleG.getInputStatus());
			Serial.print(this->moduleH.getInputStatus());
			Serial.print(this->moduleI.getInputStatus());
			delay(10);
		} else if (this->moduleH.<CR Switch>.getStatus()) { // Test All Outputs Simultaneously
			//TODO:Turn on all LEDs
			//TODO:Move steppers to MAX CW.
			//TODO:Move steppers to MAX CCW.
			//TODO:Turn off all LEDs
		} else if (this->moduleH.<BR Switch>.getStatus()) { // Test All Outputs Sequentially
			//TODO:Per module:
			//TODO:Sequentially turn on all LEDs
			//TODO:Sequentially move steppers to MAX CW, MAX CCW.
			//TODO:Turn off all LEDs
		} else {
			delay(1000);
		}
	}while(true); //TODO: Add a way to break out of this diagnostic mode
}