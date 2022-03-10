#include <Arduino.h>

#include "configuration.h"
#include <ControlPanel.h>

/* REMINDER: 
When programming KMega, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\KMega
	2. Tools > Board = Arduino Mega or Mega 2560
	3. Tools > Processor = ATmega2560 (Mega 2560)
	4. Tools > Port = COM4
*/

//int main(void) { //TODO replace setup() and loop() with main()
void setup() {
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
	delay(1000);
	
	//Startup Mode////////////////////////////////////////////////////////////////////////////////
	ControlPanel controlPanel;
	//TODO Run startup test (LEDs, Stepper Motors)
	//TODO Establish KKIM  Serial Communication Channel. Indicate Start and Finish.
	//TODO Establish KNano Serial Communication Channel. Indicate Start and Finish.
	
	//Standard Operating Mode/////////////////////////////////////////////////////////////////////
	//Loop:
		//Read inputs
		//Send toFlightComputer Packet
		//Receive toKMega packet
		//Refresh outputs
			//Send toKNano packet
		//Idle if necessary
	
	//Diagnostic Mode/////////////////////////////////////////////////////////////////////////////
	//controlPanel.runDiagnosticMode();
	//controlPanel.resetStepperToStartingPosition();
	
	exit(EXIT_SUCCESS);
}