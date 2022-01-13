#include <stdlib.h>
#include <Arduino.h>

#include "C:\dev\KCP2\KMega\configuration.h"
#include "C:\dev\KCP2\KMega\Model\ControlPanel.h"


//INITIALIZE COMMON PARTS
//ControlPanel controlPanel;

void setup() {
	Serial.begin(COMPUTER_BAUD_RATE);
}

void loop() {
	//controlPanel.diagnosticMode();
	exit(EXIT_SUCCESS);
}


