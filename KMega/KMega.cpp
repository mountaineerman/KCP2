#include <stdlib.h>
#include "Arduino.h"

#include "configuration.h"
#include "Model/ControlPanel.h"


//INITIALIZE COMMON PARTS
ControlPanel controlPanel;

void setup() {
	Serial.begin(COMPUTER_BAUD_RATE);
}

void loop() {
	controlPanel.diagnosticMode();
	exit(EXIT_SUCCESS);
}


