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
	
	this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	
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
	delay(1000);
	controlPanel.setAllLEDsOff();
	delay(100);
	//TODO Add startup test for Stepper Motors
	
	//Establish KKIM Serial Communication Channel:
	this->controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Start of Establishing KKIM Serial Communication Channel
	this->serialCommunicator.establishSerialLink();
	this->controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate End of Establishing KKIM Serial Communication Channel
	
	//TODO Establish KNano Serial Communication Channel. Indicate Start and Finish.
}

void KMegaService::standardOperatingMode() {

	long time1 = 0;
	long time2 = 0;
	long time3 = 0;
	long time4 = 0;
	long time5 = 0;
	long time6 = 0;
	long time7 = 0;
	long time8 = 0;
	long time9 = 0;
	long time10 = 0;
	bool sentInputRefreshPacket = false;
	bool gotOutputRefreshPacket = false;


	if ( (millis() - this->inputRefreshPacketLastSendTimeInMilliseconds) > INPUT_REFRESH_PACKET_SEND_RATE_IN_MILLISECONDS ) {
		sentInputRefreshPacket = true;
		time1 = millis();
		this->controlPanel.refreshInputStatus();
		time2 = millis();
		this->packetAssembler.assembleInputRefreshPacket();
		time3 = millis();
		//this->displayInputRefreshPacket();
		this->serialCommunicator.sendInputRefreshPacket();
		time4 = millis();
		this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	}
	
	time5 = millis();
	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	time6 = millis();
	if ( this->serialCommunicator.getOutputRefreshPacket() ) {
		gotOutputRefreshPacket = true;
		//this->displayOutputRefreshPacket();
		time7 = millis();
		this->packetUnpacker.unpackOutputRefreshPacketIntoModel();
		time8 = millis();
		this->controlPanel.writeLEDStatusToLEDDriverBoards();
		time9 = millis();
		//TODO Send toKNano packet?
	}
	
	
	
	this->serialCommunicator.tallyCommunicationsDiagnosticData();
	//this->serialCommunicator.displayCommunicationsDiagnosticData();
	time10 = millis();
	
//	this->controlPanel.runStepperIfNecessary(); //TODO deal with Heading stepper hibernation...
	
	if (sentInputRefreshPacket && gotOutputRefreshPacket) {
		Serial.println();
		Serial.print("controlPanel.refreshInputStatus: "); Serial.println(time2 - time1);
		Serial.print("packetAssembler.assembleInputRefreshPacket: "); Serial.println(time3 - time2);
		Serial.print("serialCommunicator.sendInputRefreshPacket: "); Serial.println("? (omitted)");
		Serial.print("serialCommunicator.ingestDataFromSerialBufferToPacketBuffer: "); Serial.println(time6 - time5);
		Serial.print("serialCommunicator.getOutputRefreshPacket: "); Serial.println(time7 - time6);
		Serial.print("packetUnpacker.unpackOutputRefreshPacketIntoModel: "); Serial.println(time8 - time7);
		Serial.print("controlPanel.writeLEDStatusToLEDDriverBoards: "); Serial.println(time9 - time8);
		Serial.print("Total: "); Serial.println(time10 - time1);
	}
	
	//TODO Idle if necessary
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

void KMegaService::displayOutputRefreshPacket() { //TODO remove
	Serial.println("KMegaService.displayOutputRefreshPacket(): (decimal format)");
	Serial.print("Position: ");
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	
	Serial.print("Value:    ");
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(this->outputRefreshPacket[i]);
		Serial.print("\t");
	}
	Serial.println();
}

void KMegaService::displayInputRefreshPacket() { //TODO remove
	Serial.println("KMegaService.displayInputRefreshPacket(): (decimal format)");
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