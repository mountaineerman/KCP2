#include <stdlib.h>
#include <Arduino.h>

#include "configuration.h"
#include <ControlPanel.h>

//INITIALIZE COMMON PARTS
ControlPanel controlPanel;

void setup() {
	Serial.begin(COMPUTER_BAUD_RATE);
	Serial.println("Hello world!");
}

void loop() {
	//controlPanel.diagnosticMode();
	//Serial.println("");
	//delay(1000);
	exit(EXIT_SUCCESS);
}


