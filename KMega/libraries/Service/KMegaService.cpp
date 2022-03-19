#include <KMegaService.h>
#include <SerialCommunicator.h>

KMegaService::KMegaService()
	: controlPanel()
	, serialCommunicator()
	, packetUnpacker(controlPanel)
	//, packetAssembler()
{
	this->outputRefreshPacket[INCOMING_PACKET_LENGTH_IN_BYTES] = {};
	this->clearOutputRefreshPacket();
	this->serialCommunicator.setOutputRefreshPacket(outputRefreshPacket);
	this->packetUnpacker.setOutputRefreshPacket(outputRefreshPacket);
	
	
	this->startupMode(); //TODO move out of constructor
	
	while(true) { //TODO move out of constructor
		this->standardOperatingMode(); //TODO move out of constructor
	}
}

void KMegaService::startupMode() {
	//Control Panel Startup Test:
	//controlPanel.setAllLEDsOn();
	//controlPanel.setAllLEDsOff();
	//TODO Add startup test for Stepper Motors
	
	//Establish KKIM Serial Communication Channel:
	this->controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWM(PWM_LED_MAXIMUM); //Indicate Start of Establishing KKIM Serial Communication Channel
	this->serialCommunicator.establishSerialLink();
	this->controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWM(PWM_LED_MAXIMUM); //Indicate End of Establishing KKIM Serial Communication Channel
	
	//TODO Establish KNano Serial Communication Channel. Indicate Start and Finish.
}

void KMegaService::standardOperatingMode() {
	//controlPanel.refreshInputStatus();
	//Assemble toFlightComputer Packet
	//Send toFlightComputer Packet
	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	
	if ( this->serialCommunicator.getOutputRefreshPacket() ) {
		this->packetUnpacker.unpackOutputRefreshPacketIntoModel();
		//Refresh outputs?
		//Send toKNano packet?
	}
	//Idle if necessary
	delay(REFRESH_PERIOD_IN_MILLISECONDS); //TODO remove
}

void KMegaService::clearOutputRefreshPacket() {
	for (int i = 0; i < INCOMING_PACKET_LENGTH_IN_BYTES; i++) {
		this->outputRefreshPacket[i] = 0x00;
	}
}

//void KMegaService::diagnosticMode() {
//	//controlPanel.runDiagnosticMode();
//}
//
//void KMegaService::shutdownMode() {
//	//controlPanel.resetStepperToStartingPosition();
//	serialCommunicator.teardownSerialLink();
//}

//void KMegaService::displayOutputRefreshPacket() { //TODO remove
//	Serial.println("KMegaService: outputRefreshPacket: (decimal format)");
//	Serial.print("Position: ");
//	for (int i = 0; i < INCOMING_PACKET_LENGTH_IN_BYTES; i++) {
//		Serial.print(i+1);
//		Serial.print("\t");
//	}
//	Serial.println();
//	
//	Serial.print("Value:    ");
//	for (int i = 0; i < INCOMING_PACKET_LENGTH_IN_BYTES; i++) {
//		Serial.print(this->outputRefreshPacket[i]);
//		Serial.print("\t");
//	}
//	Serial.println();
//}