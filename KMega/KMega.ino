#include <Arduino.h>

#include "configuration.h"
#include <ControlPanel.h>

/* REMINDER: 
When programming KMega, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\KMega
	2. Tools > Board = //TBD
	3. Tools > Processor = //TBD
	4. Tools > Port = COM4 //TODO confirm
*/

void setup() {
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
	delay(1000);
}

void loop() {
	delay(1000);
	
	ControlPanel controlPanel;
	controlPanel.runDiagnosticMode();
	controlPanel.resetStepperToStartingPosition();
	
	Serial.println("Main Menu. Terminating meow...");
	
	delay(500);
	exit(EXIT_SUCCESS);
	
	//27 rows per half-screen
	//59 rows per full-screen
}


