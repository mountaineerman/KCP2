#include <Arduino.h>
#include <SerialCommunicator.h>
#include "../../configuration.h"

//Note: the Arduino Serial buffer is 64 Bytes long by default. See:
//https://www.hobbytronics.co.uk/arduino-serial-buffer-size#:~:text=The%20Arduino%20core%20code%20contains,only%2064%20bytes%20in%20size.

SerialCommunicator::SerialCommunicator()
{
	this->outputRefreshPacket = NULL;
	this->receivedByte = 0x00;
	this->packetBuffer[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacketBuffer();
	
	this->inputRefreshPacket = NULL;
	this->numberOfBytesWritten = 0;
	
	this->timer = millis();
	this->running_numberOfRejectedIncomingBytes = 0;
	this->running_numberOfRejectedOutputRefreshPackets = 0;
	this->running_numberOfAcceptedOutputRefreshPackets = 0;
	this->running_numberOfSentInputRefreshPackets = 0;
	this->running_numberOfInputRefreshPacketsNotSent = 0;
	this->numberOfRejectedIncomingBytes = 0;
	this->numberOfRejectedOutputRefreshPackets = 0;
	this->numberOfAcceptedOutputRefreshPackets = 0;
	this->numberOfSentInputRefreshPackets = 0;
	this->numberOfInputRefreshPacketsNotSent = 0;
}

void SerialCommunicator::setOutputRefreshPacket(byte * outputRefreshPacket) {
	this->outputRefreshPacket = outputRefreshPacket;
}

void SerialCommunicator::setInputRefreshPacket(byte * inputRefreshPacket) {
	this->inputRefreshPacket = inputRefreshPacket;
}

void SerialCommunicator::establishSerialLink() {
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
}

void SerialCommunicator::ingestDataFromSerialBufferToPacketBuffer() {
	
	while (Serial.available()) { // There are bytes available in the Arduino Serial Buffer
		
		this->receivedByte = Serial.read();
		
		//Serial.println(this->delimiterByteCounter);
		if (0 <= this->delimiterByteCounter && this->delimiterByteCounter < NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet has not started yet
			if (this->receivedByte == PACKET_DELIMITER_BYTE) {
				this->delimiterByteCounter++;
			} else {
				this->delimiterByteCounter = 0;
				this->running_numberOfRejectedIncomingBytes++;
			}
		
		} else if (this->delimiterByteCounter == NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet read in progress
			//Serial.println(this->receivedByte);
			this->packetBuffer[this->packetBufferCursor] = this->receivedByte;
			this->packetBufferCursor++;
			
		} else {
			//TODO throw exception
			Serial.println("Exception: SerialCommunicator's delimiterByteCounter is out of range");
		}
		
		
		if (this->packetBufferCursor == OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES) { //A full packet is in the Packet Buffer
			if (true /*TODO:packet is valid*/) {
				this->isValidPacketInPacketBuffer = true;
				this->running_numberOfAcceptedOutputRefreshPackets++;
				return;				
			} else { //Packet is invalid
				this->clearPacketBuffer();
				this->running_numberOfRejectedOutputRefreshPackets++;
			}
		}
	}
}

bool SerialCommunicator::getOutputRefreshPacket() {
	
	if (!this->outputRefreshPacket) {
		//TODO throw exception
		Serial.println("Exception: SerialCommunicator's outputRefreshPacket is not initialized");
	}
	
	if (this->isValidPacketInPacketBuffer) {
		for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
			this->outputRefreshPacket[i] = this->packetBuffer[i];
		}
		this->clearPacketBuffer();
		return true;
	} else {
		return false;
	}
}

void SerialCommunicator::sendInputRefreshPacket() {
	
	if ( Serial.availableForWrite() >= INPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
		numberOfBytesWritten = Serial.write(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
		
		if (numberOfBytesWritten == INPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
			this->running_numberOfSentInputRefreshPackets++;
		} else {
			this->running_numberOfInputRefreshPacketsNotSent++;
		}
	} else {
		this->running_numberOfInputRefreshPacketsNotSent++;
	}
}

void SerialCommunicator::tallyDiagnosticData() {
	if ( (millis() - this->timer) > MAX_TALLY_TIME_FOR_DIAGNOSTICS_IN_MILLISECONDS ) {
		this->numberOfRejectedIncomingBytes 		= this->running_numberOfRejectedIncomingBytes;
		this->numberOfRejectedOutputRefreshPackets 	= this->running_numberOfRejectedOutputRefreshPackets;
		this->numberOfAcceptedOutputRefreshPackets 	= this->running_numberOfAcceptedOutputRefreshPackets;
		this->numberOfSentInputRefreshPackets 		= this->running_numberOfSentInputRefreshPackets;
		this->numberOfInputRefreshPacketsNotSent 	= this->running_numberOfInputRefreshPacketsNotSent;
		
		this->running_numberOfRejectedIncomingBytes = 0;
		this->running_numberOfRejectedOutputRefreshPackets = 0;
		this->running_numberOfAcceptedOutputRefreshPackets = 0;
		this->running_numberOfSentInputRefreshPackets = 0;
		this->running_numberOfInputRefreshPacketsNotSent = 0;
		
		this->timer = millis();
	}
}

void SerialCommunicator::teardownSerialLink() {
	Serial.end();
}

void SerialCommunicator::clearPacketBuffer() {

	this->delimiterByteCounter = 0;
	this->packetBufferCursor = 0;	
	this->isValidPacketInPacketBuffer = false;	

	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		this->packetBuffer[i] = 0x00;
	}
}

//void SerialCommunicator::displayPacketBufferInDecimal() { //TODO remove
//	Serial.println("SerialCommunicator: packetBuffer: (decimal format)");
//	Serial.print("Position: ");
//	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
//		Serial.print(i+1);
//		Serial.print("\t");
//	}
//	Serial.println();
//	Serial.print("Value:    ");
//	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
//		Serial.print(this->packetBuffer[i]);
//		Serial.print("\t");
//	}
//	Serial.println();
//}