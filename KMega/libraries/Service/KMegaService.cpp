#include <KMegaService.h>
#include <SerialCommunicator.h>

KMegaService::KMegaService()
	: controlPanel()
	, serialCommunicator()
	, packetUnpacker(controlPanel)
	, packetAssembler(controlPanel)
{	
	this->outputRefreshPacket[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(outputRefreshPacket, OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setOutputRefreshPacket(outputRefreshPacket);
	this->packetUnpacker.setOutputRefreshPacket(outputRefreshPacket);
	
	this->altitudePacket[ALTITUDE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(altitudePacket, ALTITUDE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setAltitudePacket(altitudePacket);
	this->packetAssembler.setAltitudePacket(altitudePacket);
	
	this->inputRefreshPacket[INPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setInputRefreshPacket(inputRefreshPacket);
	this->packetAssembler.setInputRefreshPacket(inputRefreshPacket);
	
	this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	
	this->startupMode(); //TODO move out of constructor
	
	
	this->controlPanel.moduleH.ledPWM_GlassCockpit_CL.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Button for DiagnosticMode
	this->controlPanel.moduleH.ledPWM_GlassCockpit_CR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Button for graceful shutdown of Control Panel
	while (true) {//TODO move out of constructor
		
		if (this->controlPanel.moduleH.switch_GlassCockpit_CL.getInputStatus()) {
			this->controlPanel.runDiagnosticMode();
			this->shutdownMode();
			break;
		} else if (this->controlPanel.moduleH.switch_GlassCockpit_CR.getInputStatus()) {
			this->shutdownMode();
			break;
		} else {
			this->standardOperatingMode();
		}
	}
}

void KMegaService::startupMode() {

	controlPanel.setAllLEDsOn();
	delay(1000);
	controlPanel.setAllLEDsOff();
	delay(100);
	
	controlPanel.sweepStepperMotorsThroughMaxMinToCalibrate();
	
	this->serialCommunicator.establishKKIMSerialLink();
	this->controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //TODO replace with COMMS LED...
	
	this->serialCommunicator.establishKNanoSerialLink();
	this->controlPanel.moduleH.ledPWM_GlassCockpit_TL.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //TODO replace with COMMS LED...
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
		//this->displayPacket(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "inputRefreshPacket");//TODO verify. Old: //this->displayInputRefreshPacket();
		this->serialCommunicator.sendInputRefreshPacket();
		time4 = millis();
		this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	}
	
	time5 = millis();
	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	time6 = millis();
	if ( this->serialCommunicator.getOutputRefreshPacket() ) {
		gotOutputRefreshPacket = true;
		//this->displayPacket(outputRefreshPacket, OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "outputRefreshPacket");//TODO verify. Old: //this->displayOutputRefreshPacket();
		time7 = millis();
		this->packetUnpacker.unpackOutputRefreshPacketIntoModel();
		time8 = millis();
		this->controlPanel.writeLEDStatusToLEDDriverBoards();
		time9 = millis();
		this->packetAssembler.assembleAltitudePacket();
		time10 = millis();
		this->serialCommunicator.sendAltitudePacket();
		time11 = millis();
	}
	
	//this->serialCommunicator.tallyCommunicationsDiagnosticData();
	//this->serialCommunicator.displayCommunicationsDiagnosticData();
	time12 = millis();
	
	this->controlPanel.runStepperIfNecessary();
	
//	if (sentInputRefreshPacket && gotOutputRefreshPacket) {
//		Serial.println();
//		Serial.print("controlPanel.refreshInputStatus: "); Serial.println(time2 - time1);
//		Serial.print("packetAssembler.assembleInputRefreshPacket: "); Serial.println(time3 - time2);
//		Serial.print("serialCommunicator.sendInputRefreshPacket: "); Serial.println(time4 - time3);
//		Serial.print("serialCommunicator.ingestDataFromSerialBufferToPacketBuffer: "); Serial.println(time6 - time5);
//		Serial.print("serialCommunicator.getOutputRefreshPacket: "); Serial.println(time7 - time6);
//		Serial.print("packetUnpacker.unpackOutputRefreshPacketIntoModel: "); Serial.println(time8 - time7);
//		Serial.print("controlPanel.writeLEDStatusToLEDDriverBoards: "); Serial.println(time9 - time8);
//		Serial.print("packetAssembler.assembleAltitudePacket: "); Serial.println(time10 - time9);
//		Serial.print("serialCommunicator.sendAltitudePacket: "); Serial.println(time11 - time10);
//		Serial.print("Total: "); Serial.println(time12 - time1);
//	}
	
	//TODO Idle if necessary
	delay(REFRESH_PERIOD_IN_MILLISECONDS); //TODO remove
}

//void KMegaService::diagnosticMode() {
//	//controlPanel.runDiagnosticMode();
//}

void KMegaService::shutdownMode() {
	
	serialCommunicator.teardownSerialLinks();

	controlPanel.blockRunAllSteppersToPosition(STEPPER_CCW_LIMIT);
	
	controlPanel.setAllLEDsOff();
}

void KMegaService::clearPacket(const byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}

void KMegaService::displayPacket(const byte * packet, int packetLength, String packetName) {//TODO verify
	Serial.println("KMegaService.displayPacket(): (decimal format)");
	Serial.print("Packet: "); Serial.println(packetName);
	Serial.print("Byte Num: ");
	for (int i = 0; i < packetLength; i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	
	Serial.print("Value:    ");
	for (int i = 0; i < packetLength; i++) {
		Serial.print(packet[i]);
		Serial.print("\t");
	}
	Serial.println();
}