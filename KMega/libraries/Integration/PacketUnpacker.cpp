#include <Arduino.h>
#include <PacketUnpacker.h>
#include "../../configuration.h"


PacketUnpacker::PacketUnpacker(ControlPanel& controlPanel)
	: controlPanel(controlPanel)
{
	this->outputRefreshPacket = NULL;
}

void PacketUnpacker::setOutputRefreshPacket(const byte * outputRefreshPacket) {
	this->outputRefreshPacket = outputRefreshPacket;
}

void PacketUnpacker::displayOutputRefreshPacketInDecimal() {
	
	if (this->outputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println("Exception: PacketUnpacker.displayOutputRefreshPacketInDecimal(): OutputRefreshPacket is not initialized");
	}
	
	Serial.println("PacketUnpacker: outputRefreshPacket: (Decimal Format)");
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

void PacketUnpacker::unpackOutputRefreshPacketIntoModel() {
	
	//Serial.println("KMega: PacketUnpacker: received OutputRefreshPacket:");
	//this->displayOutputRefreshPacketInDecimal();
	
	if (this->outputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println("Exception: PacketUnpacker.unpackOutputRefreshPacketIntoModel(): OutputRefreshPacket is not initialized");
	}
	
	//Serial.println("The Module A Brake number is: ");                  //TODO remove
	//int temp = convertTwoBytesInOutputRefreshPacketIntoInteger(11,10); //TODO remove
	//Serial.println(temp);                                              //TODO remove
	//controlPanel.moduleA.ledPWM_BrakeModuleA.setPWM(temp);             //TODO remove
	
	controlPanel.moduleA.ledPWM_BrakeModuleA.setPWM( convertTwoBytesInOutputRefreshPacketIntoInteger(10,11) );
	controlPanel.moduleD.ledPWM_BrakeModuleD.setPWM( convertTwoBytesInOutputRefreshPacketIntoInteger(12,13) );
	//TODO Add remaining outputs
	
	controlPanel.writeLEDStatusToLEDDriverBoards();
	
	this->clearOutputRefreshPacket();
}

void PacketUnpacker::clearOutputRefreshPacket() {
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		this->outputRefreshPacket[i] = 0x00;
	}
}

// byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote)
int PacketUnpacker::convertTwoBytesInOutputRefreshPacketIntoInteger(int byteNum1, int byteNum2) {
	
	if (byteNum1 > OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
		//TODO throw exception
		Serial.println("Exception: PacketUnpacker.convertTwoByteArrayIntoInteger(): byteNum1 greater than length of OutputRefreshPacket");
	}
	
	if (byteNum2 > OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
		//TODO throw exception
		Serial.println("Exception: PacketUnpacker.convertTwoByteArrayIntoInteger(): byteNum2 greater than length of OutputRefreshPacket");
	}
	
	int largestByteNum = 0;
	int smallestByteNum = 0;
	
	if (byteNum1 > byteNum2) {
		largestByteNum = byteNum1 - 1;
		smallestByteNum = byteNum2 - 1;
	} else {
		largestByteNum = byteNum2 - 1;
		smallestByteNum = byteNum1 - 1;
	}
	
	char tempTwoByteArray[2];
	tempTwoByteArray[0] = this->outputRefreshPacket[smallestByteNum];
	tempTwoByteArray[1] = this->outputRefreshPacket[largestByteNum];
	
	return (*((int *)tempTwoByteArray));
}


