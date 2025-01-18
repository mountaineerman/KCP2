#include <Arduino.h>
#include <SerialCommunicator.h>
#include "../../configuration.h"

SerialCommunicator::SerialCommunicator()
{
	this->gaugePacket = NULL;
	this->receivedByte = 0x00;
	this->packetBuffer[GAUGE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacketBuffer();
}

void SerialCommunicator::setGaugePacket(const byte * gaugePacket) {
	this->gaugePacket = gaugePacket;
}

void SerialCommunicator::establishKMegaSerialLink() {
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(BAUD_RATE);
}

void SerialCommunicator::ingestDataFromSerialBufferToPacketBuffer() {
	
	while (Serial.available()) { // There are bytes available in the Arduino Serial Buffer
		
		this->receivedByte = Serial.read();
		
		if (0 <= this->delimiterByteCounter && this->delimiterByteCounter < NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet has not started yet
			if (this->receivedByte == PACKET_DELIMITER_BYTE) {
				this->delimiterByteCounter++;
			} else {
				this->delimiterByteCounter = 0;
			}
		
		} else if (this->delimiterByteCounter == NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet read in progress
			this->packetBuffer[this->packetBufferCursor] = this->receivedByte;
			this->packetBufferCursor++;
		} else {
			//Serial.println(F("Exception: SerialCommunicator.ingestDataFromSerialBufferToPacketBuffer(): delimiterByteCounter is out of range"));
		}
		
		
		if (this->packetBufferCursor == GAUGE_PACKET_LENGTH_IN_BYTES) { //A full packet is in the Packet Buffer
			if (true /*TODO:packet is valid*/) {
				this->isValidPacketInPacketBuffer = true;
				return;				
			} else { //Packet is invalid
				this->clearPacketBuffer();
			}
		}
	}
}

bool SerialCommunicator::getGaugePacket() {
	
	if (this->isValidPacketInPacketBuffer) {
		for (int i = 0; i < GAUGE_PACKET_LENGTH_IN_BYTES; i++) {
			this->gaugePacket[i] = this->packetBuffer[i];
		}
		this->clearPacketBuffer();
		return true;
	} else {
		return false;
	}
}



void SerialCommunicator::teardownKMegaSerialLink() {
	Serial.end();
}

void SerialCommunicator::clearPacketBuffer() {

	this->delimiterByteCounter = 0;
	this->packetBufferCursor = 0;	
	this->isValidPacketInPacketBuffer = false;	

	for (int i = 0; i < GAUGE_PACKET_LENGTH_IN_BYTES; i++) {
		this->packetBuffer[i] = 0x00;
	}
}