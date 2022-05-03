#include <Arduino.h>
#include <SerialCommunicator.h>
#include "../../configuration.h"

//Note: the Arduino Serial buffer is 64 Bytes long by default. See:
//https://www.hobbytronics.co.uk/arduino-serial-buffer-size#:~:text=The%20Arduino%20core%20code%20contains,only%2064%20bytes%20in%20size.

SerialCommunicator::SerialCommunicator()
{
	this->altitudePacket = NULL;
	this->receivedByte = 0x00;
	this->packetBuffer[ALTITUDE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacketBuffer();
}

void SerialCommunicator::setAltitudePacket(const byte * altitudePacket) {
	this->altitudePacket = altitudePacket;
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
		}
		
		if (this->packetBufferCursor == ALTITUDE_PACKET_LENGTH_IN_BYTES) { //A full packet is in the Packet Buffer
			if (true /*TODO:packet is valid*/) {
				this->isValidPacketInPacketBuffer = true;
				return;				
			} else { //Packet is invalid
				this->clearPacketBuffer();
			}
		}
	}
}

bool SerialCommunicator::getAltitudePacket() {
	
	if (this->isValidPacketInPacketBuffer) {
		for (int i = 0; i < ALTITUDE_PACKET_LENGTH_IN_BYTES; i++) {
			this->altitudePacket[i] = this->packetBuffer[i];
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

	for (int i = 0; i < ALTITUDE_PACKET_LENGTH_IN_BYTES; i++) {
		this->packetBuffer[i] = 0x00;
	}
}













