#include <KMegaService.h>
#include <SerialCommunicator.h>

KMegaService::KMegaService()
	: controlPanel()
	, serialCommunicator()
	, packetUnpacker(controlPanel)
	, packetAssembler(controlPanel)
{
	this->outputRefreshPacket[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearOutputRefreshPacket();
	this->serialCommunicator.setOutputRefreshPacket(outputRefreshPacket);
	this->packetUnpacker.setOutputRefreshPacket(outputRefreshPacket);
	
	this->inputRefreshPacket[INPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearInputRefreshPacket();
	this->serialCommunicator.setInputRefreshPacket(inputRefreshPacket);
	this->packetAssembler.setInputRefreshPacket(inputRefreshPacket);
	
	
	this->startupMode(); //TODO move out of constructor
	
	////Test Diagnostic Mode:
	//this->controlPanel.runDiagnosticMode();
	
	while(true) { //TODO move out of constructor
		this->standardOperatingMode(); //TODO move out of constructor
	}
}

void KMegaService::startupMode() {
	//Control Panel Startup Test:
	controlPanel.setAllLEDsOn();
	delay(2000);
	controlPanel.setAllLEDsOff();
	delay(500);
	//TODO Add startup test for Stepper Motors
	
	//Establish KKIM Serial Communication Channel:
	this->controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Start of Establishing KKIM Serial Communication Channel
	this->serialCommunicator.establishSerialLink();
	this->controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate End of Establishing KKIM Serial Communication Channel
	
	//TODO Establish KNano Serial Communication Channel. Indicate Start and Finish.
}

void KMegaService::standardOperatingMode() {

	this->controlPanel.refreshInputStatus();
	this->packetAssembler.assembleInputRefreshPacket();
	//this->displayInputRefreshPacket();
	//this->serialCommunicator.sendInputRefreshPacket();
	
	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	if ( this->serialCommunicator.getOutputRefreshPacket() ) {
		//this->displayOutputRefreshPacket();
		this->packetUnpacker.unpackOutputRefreshPacketIntoModel();
		this->controlPanel.writeLEDStatusToLEDDriverBoards();
		//TODO Send toKNano packet?
	}
	
	this->serialCommunicator.tallyDiagnosticData();
	this->controlPanel.runStepperIfNecessary(); //TODO deal with Heading stepper hibernation...
	
	//Idle if necessary
	delay(REFRESH_PERIOD_IN_MILLISECONDS); //TODO remove
}

void KMegaService::clearOutputRefreshPacket() {//TODO combine with clearInputRefreshPacket into generic method
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		this->outputRefreshPacket[i] = 0x00;
	}
}

void KMegaService::clearInputRefreshPacket() {
	for (int i = 0; i < INPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		this->inputRefreshPacket[i] = 0x00;
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
//	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
//		Serial.print(i+1);
//		Serial.print("\t");
//	}
//	Serial.println();
//	
//	Serial.print("Value:    ");
//	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
//		Serial.print(this->outputRefreshPacket[i]);
//		Serial.print("\t");
//	}
//	Serial.println();
//}

void KMegaService::displayInputRefreshPacket() { //TODO remove
	Serial.println("KMegaService: inputRefreshPacket: (decimal format)");
	Serial.print("Position: N/A\tN/A\tN/A\t");
	for (int i = 0; i < (INPUT_REFRESH_PACKET_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES); i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	
	Serial.print("Value:    ");
	for (int i = 0; i < INPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(this->inputRefreshPacket[i]);
		Serial.print("\t");
	}
	Serial.println();
}