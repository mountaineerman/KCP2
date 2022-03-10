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
	
	//Startup Mode////////////////////////////////////////////////////////////////////////////////
	ControlPanel controlPanel;
	
	//Control Panel Startup Test:
	controlPanel.setAllLEDsOn();
	controlPanel.setAllLEDsOff();
	//TODO Add startup test for Stepper Motors
	
	controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWM(PWM_LED_MAXIMUM); //Indicate Start of Establishing KKIM  Serial Communication Channel
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
	controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWM(PWM_LED_MAXIMUM); //Indicate End of Establishing KKIM  Serial Communication Channel
	//delay(1000);
		
	//TODO Establish KNano Serial Communication Channel. Indicate Start and Finish.
	
	
	//Standard Operating Mode/////////////////////////////////////////////////////////////////////
	//Loop:
	int n = 0;
	while(true){
		Serial.println(n++);
		delay(1000);
		//controlPanel.refreshInputStatus();
		//Assemble toFlightComputer Packet
		
		//Send toFlightComputer Packet
		
		//Receive toKMega packet
		//Refresh outputs
			//Send toKNano packet
		//Idle if necessary
	}
	
	//Diagnostic Mode/////////////////////////////////////////////////////////////////////////////
	//controlPanel.runDiagnosticMode();
	
	//Shutdown Mode///////////////////////////////////////////////////////////////////////////////
	//controlPanel.resetStepperToStartingPosition();
	Serial.end();
	
	exit(EXIT_SUCCESS);
}