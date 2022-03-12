#ifndef KMEGA_SERVICE_h
#define KMEGA_SERVICE_h

//Serial Communication Test///////////////////////////////////////////////////////////////////
	Serial.println("<Arduino is ready>");
	
	// Example 1 - Receiving single characters	
	//while(true) {
	//	serialReadOneChar();
	//	showNewData();
	//}
	
	// Example 6 - Receiving binary data
	while(true) {
		recvBytesWithStartEndMarkers();
		showNewData();
		delay(1);
	}
	////ToKKIM:
	//readale: Switch1:OFF, Switch2:OFF, Switch3:OFF, Switch4:OFF
	//hex:0x0
	//bin:0000
	

//Startup Mode////////////////////////////////////////////////////////////////////////////////

//Control Panel Startup Test
//controlPanel.setAllLEDsOn();
//controlPanel.setAllLEDsOff();
//TODO Add startup test for Stepper Motors

//Establish KKIM Serial Communication Channel. Indicate Start and Finish.
controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWM(PWM_LED_MAXIMUM); //Indicate Start of Establishing KKIM  Serial Communication Channel
Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
Serial.begin(COMPUTER_BAUD_RATE);
controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWM(PWM_LED_MAXIMUM); //Indicate End of Establishing KKIM  Serial Communication Channel
	
//TODO Establish KNano Serial Communication Channel. Indicate Start and Finish.


//Standard Operating Mode/////////////////////////////////////////////////////////////////////
//Loop:
	//controlPanel.refreshInputStatus();
	////Assemble toFlightComputer Packet
	////Send toFlightComputer Packet
	////Receive toKMega packet
	////Refresh outputs
	////Send toKNano packet
	////Idle if necessary

//Diagnostic Mode/////////////////////////////////////////////////////////////////////////////
//controlPanel.runDiagnosticMode();

//Shutdown Mode///////////////////////////////////////////////////////////////////////////////
//controlPanel.resetStepperToStartingPosition();
Serial.end();

#endif