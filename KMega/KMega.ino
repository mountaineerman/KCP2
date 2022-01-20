#include <Arduino.h>

#include "configuration.h"
#include <ControlPanel.h>




void setup() {
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
	delay(1000);
}

void loop() {
	delay(1000);
	
	ControlPanel controlPanel;
	controlPanel.runDiagnosticMode();
	
	Serial.println("Main Menu. Terminating meow...");
	
	delay(500);
	exit(EXIT_SUCCESS);
	
	//27 rows per half-screen
	//59 rows per full-screen
}


