#include "Arduino.h"

#include "configuration.h"
//#include <GaugeTower.h>
//#include <SevenSeg.h>
//#include "LocalArduinoLibraries/..."



#include "Parts/LED.h"
//#include "Parts/ThreeDigitSevenSegmentDisplay.h"
//#include "Parts/AltitudeGauge.h"

void setup() {
	Serial.begin(BAUD_RATE);
	Serial.println("Hello World! (from Arduino Nano)");
}

void loop() {

}

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