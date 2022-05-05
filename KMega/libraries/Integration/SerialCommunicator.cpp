#include <Arduino.h>
#include <SerialCommunicator.h>
#include "../../configuration.h"

SerialCommunicator::SerialCommunicator()
{
	this->outputRefreshPacket = NULL;
	this->receivedByte = 0x00;
	this->packetBuffer[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacketBuffer();
	
	this->altitudePacket = NULL;
	
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

void SerialCommunicator::setOutputRefreshPacket(const byte * outputRefreshPacket) {
	this->outputRefreshPacket = outputRefreshPacket;
}

void SerialCommunicator::setAltitudePacket(const byte * altitudePacket) {
	this->altitudePacket = altitudePacket;
}

void SerialCommunicator::setInputRefreshPacket(const byte * inputRefreshPacket) {
	this->inputRefreshPacket = inputRefreshPacket;
}

void SerialCommunicator::establishKKIMSerialLink() {
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
}

void SerialCommunicator::establishKNanoSerialLink() {
	Serial1.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);//TODO Confirm Serial1
	Serial1.begin(KNANO_BAUD_RATE);
}

void SerialCommunicator::ingestDataFromSerialBufferToPacketBuffer() {
	
	
//	if (Serial.available() > 0) {//TODO remove
//		Serial.print("Bytes in Serial Buffer: "); Serial.println(Serial.available());//TODO remove
//	}
	
	while (Serial.available()) { // There are bytes available in the Arduino Serial Buffer
		
		this->receivedByte = Serial.read();
		
		//Serial.print("delimiterByteCounter: "); Serial.println(this->delimiterByteCounter);
		if (0 <= this->delimiterByteCounter && this->delimiterByteCounter < NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet has not started yet
			if (this->receivedByte == PACKET_DELIMITER_BYTE) {
				this->delimiterByteCounter++;
			} else {
				this->delimiterByteCounter = 0;
				this->running_numberOfRejectedIncomingBytes++;
			}
		
		} else if (this->delimiterByteCounter == NUMBER_OF_PACKET_DELIMITER_BYTES) { //Packet read in progress
			//Serial.print("Received byte: "); Serial.println(this->receivedByte);
			this->packetBuffer[this->packetBufferCursor] = this->receivedByte;
			this->packetBufferCursor++;
			
		} else {
			//TODO throw exception
			Serial.println(F("Exception: SerialCommunicator.ingestDataFromSerialBufferToPacketBuffer(): delimiterByteCounter is out of range"));
		}
		
		
		if (this->packetBufferCursor == OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES) { //A full packet is in the Packet Buffer
			//Serial.println("A full packet is in the Packet Buffer");
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
	
	if (this->outputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println(F("Exception: SerialCommunicator.getOutputRefreshPacket(): outputRefreshPacket is not initialized"));
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

void SerialCommunicator::sendAltitudePacket() {
	
	if ( Serial1.availableForWrite() >= ALTITUDE_PACKET_LENGTH_IN_BYTES) {
		Serial1.write(altitudePacket, ALTITUDE_PACKET_LENGTH_IN_BYTES);
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

void SerialCommunicator::sendKKIMTerminalDisplayPacket(char * charArrayToDisplay, int charArrayLength) {//TODO add support for KKIMTerminalDisplayPackets longer than the ARDUINO_SERIAL_WRITE_BUFFER_LENGTH_IN_BYTES
	
	//Serial.print("charArrayLength: "); Serial.println(charArrayLength);
	int packetLength = NUMBER_OF_PACKET_DELIMITER_BYTES + PACKET_HEADER_LENGTH_IN_BYTES + charArrayLength;
	//Serial.print("packetLength: "); Serial.println(packetLength);
	
	if (packetLength > ARDUINO_SERIAL_WRITE_BUFFER_LENGTH_IN_BYTES) {
		//Serial.print("charArrayLength too long ("); Serial.print(charArrayLength); Serial.print("). Maximum length is "); Serial.print(ARDUINO_SERIAL_WRITE_BUFFER_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES - PACKET_HEADER_LENGTH_IN_BYTES); Serial.println(". Cropping array at max limit.");
		packetLength = ARDUINO_SERIAL_WRITE_BUFFER_LENGTH_IN_BYTES;
		charArrayLength = ARDUINO_SERIAL_WRITE_BUFFER_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES - PACKET_HEADER_LENGTH_IN_BYTES;
	}
	byte KKIMTerminalDisplayPacketBuffer[packetLength] = {};
	
	// (1) Populate Delimiter:
	KKIMTerminalDisplayPacketBuffer[0] = PACKET_DELIMITER_BYTE;
	KKIMTerminalDisplayPacketBuffer[1] = PACKET_DELIMITER_BYTE;
	KKIMTerminalDisplayPacketBuffer[2] = PACKET_DELIMITER_BYTE;
	
	// (2) Populate Header:
	/* Originator */	KKIMTerminalDisplayPacketBuffer[3] = 1;
	/* Packet Type */	KKIMTerminalDisplayPacketBuffer[4] = 4;
	/* Packet Length */	KKIMTerminalDisplayPacketBuffer[5] = (packetLength - NUMBER_OF_PACKET_DELIMITER_BYTES);
	/* Current Mode */	KKIMTerminalDisplayPacketBuffer[6] = 0;//TODO
	/* Command */		KKIMTerminalDisplayPacketBuffer[7] = 0;//TODO
	/* Parity Byte */	KKIMTerminalDisplayPacketBuffer[8] = 0;//TODO
	/* Empty */			KKIMTerminalDisplayPacketBuffer[9] = 0;
	/* Empty */			KKIMTerminalDisplayPacketBuffer[10] = 0;
	/* Empty */			KKIMTerminalDisplayPacketBuffer[11] = 0;
	
	// (3) Populate Payload:
	int j = NUMBER_OF_PACKET_DELIMITER_BYTES + PACKET_HEADER_LENGTH_IN_BYTES;
	for (int i = 0; i < charArrayLength; i++) {
		KKIMTerminalDisplayPacketBuffer[j++] = charArrayToDisplay[i];
	}
	
	while (Serial.availableForWrite() < packetLength) {
		delay(1);
	}
	
	Serial.write(KKIMTerminalDisplayPacketBuffer, packetLength);
}

void SerialCommunicator::tallyCommunicationsDiagnosticData() {
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

void SerialCommunicator::displayCommunicationsDiagnosticData() {
	Serial.print("numberOfRejectedIncomingBytes: "); Serial.println(this->numberOfRejectedIncomingBytes);
	Serial.print("numberOfRejectedOutputRefreshPackets: "); Serial.println(this->numberOfRejectedOutputRefreshPackets);
	Serial.print("numberOfAcceptedOutputRefreshPackets: "); Serial.println(this->numberOfAcceptedOutputRefreshPackets);
	Serial.print("numberOfSentInputRefreshPackets: "); Serial.println(this->numberOfSentInputRefreshPackets);
	Serial.print("numberOfInputRefreshPacketsNotSent: "); Serial.println(this->numberOfInputRefreshPacketsNotSent);
	Serial.println();
}

void SerialCommunicator::teardownSerialLinks() {
	Serial.end();
	Serial1.end();//TODO verify
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