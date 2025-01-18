#include <NGHService.h>
#include <SerialCommunicator.h>

NGHService::NGHService()
	: controlPanel()
	, serialCommunicator()
	, packetUnpacker(controlPanel)
{	
	this->gaugePacket[GAUGE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(gaugePacket, GAUGE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setGaugePacket(gaugePacket);
	this->packetUnpacker.setGaugePacket(gaugePacket);
	
	this->gaugePacketLastReceiveTimeInMilliseconds = millis();
	this->startupMode(); //TODO move out of constructor
	
	while (true) {//TODO move out of constructor
		this->standardOperatingMode();
	}
}

void NGHService::startupMode() {

	//TODO: Stepper Logic disabled until performance is fixed (do not modify):
	this->controlPanel.blockRunAllSteppersToPosition(STEPPER_CW_LIMIT, 500);
	delay(200);
	this->controlPanel.blockRunAllSteppersToPosition(STEPPER_CCW_LIMIT, 500);
	
	this->serialCommunicator.establishKMegaSerialLink();
}

void NGHService::standardOperatingMode() {

	bool gotGaugePacket = false;

	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	if ( this->serialCommunicator.getGaugePacket() ) {
		gotGaugePacket = true;
		this->gaugePacketLastReceiveTimeInMilliseconds = millis();
		//this->displayPacket(gaugePacket, GAUGE_PACKET_LENGTH_IN_BYTES, "gaugePacket");//TODO verify. Old: //this->displayOutputRefreshPacket();
		this->packetUnpacker.unpackGaugePacketIntoModel();
	}
		
	//TODO: Stepper Logic disabled until performance is fixed (do not modify):
	this->controlPanel.runStepperIfNecessary();//TODO remove
	this->controlPanel.runStepperIfNecessary();//TODO remove
	this->controlPanel.runStepperIfNecessary();//TODO remove
	this->controlPanel.runStepperIfNecessary();//TODO remove
	this->controlPanel.runStepperIfNecessary();//TODO remove
	//this->controlPanel.burstRunSteppers();
	
	//TODO Idle if necessary
	//delay(REFRESH_PERIOD_IN_MILLISECONDS); //TODO remove
	delay(1);
}

void NGHService::shutdownMode() {
	
	serialCommunicator.teardownKMegaSerialLink();

	//TODO: Stepper Logic disabled until performance is fixed (do not modify):
	controlPanel.blockRunAllSteppersToPosition(STEPPER_CCW_LIMIT, 500);
}

void NGHService::clearPacket(byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}

void NGHService::displayPacket(const byte * packet, int packetLength, String packetName) {//TODO verify
	Serial.println(F("NGHService.displayPacket(): (decimal format)"));
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