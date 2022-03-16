#include <Arduino.h>
#include <SerialCommunicator.h>
#include "../../configuration.h"

//Note: the Arduino Serial buffer is 64 Bytes long by default. See:
//https://www.hobbytronics.co.uk/arduino-serial-buffer-size#:~:text=The%20Arduino%20core%20code%20contains,only%2064%20bytes%20in%20size.

SerialCommunicator::SerialCommunicator() {
	delimiterByteCounter = 0;
	receivedByte = 0x00;
	packetBuffer[INCOMING_PACKET_LENGTH_IN_BYTES] = {};
	packetBufferCursor = 0;
}

void SerialCommunicator::establishSerialLink() {
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
}

void SerialCommunicator::ingestData() {
	
	while (Serial.available()) { // There are bytes available in the Arduino Serial Buffer
		
		receivedByte = Serial.read();
		
		//Serial.println(delimiterByteCounter);
		if (0 <= delimiterByteCounter && delimiterByteCounter < NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet has not started yet
			if (receivedByte == PACKET_DELIMITER_BYTE) {
				delimiterByteCounter++;
			} else {
				delimiterByteCounter = 0;
			}
		
		} else if (delimiterByteCounter == NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet read in progress
			//Serial.println(receivedByte);
			packetBuffer[packetBufferCursor] = receivedByte;
			packetBufferCursor++;
			
		} else {
			//TODO throw exception
		}
		
		
		if (packetBufferCursor == INCOMING_PACKET_LENGTH_IN_BYTES) { //A full packet is in the Packet Buffer
			if (true /*TODO:packet is valid*/) {
				this->displayPacketBufferInDecimal();
				//TODO send packet to packetUnpacker
			} else { //Packet is invalid
				
			}
			
			delimiterByteCounter = 0;
			packetBufferCursor = 0;
			this->clearPacketBuffer();
		}
	}
}

void SerialCommunicator::teardownSerialLink() {
	Serial.end();
}

void SerialCommunicator::clearPacketBuffer() {
	for (int i = 0; i < INCOMING_PACKET_LENGTH_IN_BYTES; i++) {
		packetBuffer[i] = 0x00;
	}
}

void SerialCommunicator::displayPacketBufferInDecimal() {
	Serial.println("Decimal format:");
	Serial.print("Position: ");
	for (int i = 0; i < INCOMING_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	Serial.print("Value:    ");
	for (int i = 0; i < INCOMING_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(packetBuffer[i]);
		Serial.print(" \t");
	}
	Serial.println();
}