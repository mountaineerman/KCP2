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
	
	//this->displayOutputRefreshPacketInDecimal();
	
	if (this->outputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println("Exception: PacketUnpacker.unpackOutputRefreshPacketIntoModel(): OutputRefreshPacket is not initialized");
	}
	
	//LEDs
	controlPanel.moduleA.ledPWM_BrakeModuleA.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(10,11) );
	controlPanel.moduleD.ledPWM_BrakeModuleD.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(12,13) );
	//TODO Add remaining LEDs
	controlPanel.moduleI.ledPWM_FUEL_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(116,117) );
	//TODO Add remaining LEDs
	
	//Stepper Motors
	controlPanel.moduleC.stepper_Gforce.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(170,171) );
	//TODO Add remaining Stepper Motors
	
	controlPanel.altitude = this->convertFourBytesInOutputRefreshPacketIntoFloat(10, 13);
	
	this->clearOutputRefreshPacket();
}

void PacketUnpacker::clearOutputRefreshPacket() {
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		this->outputRefreshPacket[i] = 0x00;
	}
}

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


float PacketUnpacker::convertFourBytesInOutputRefreshPacketIntoFloat(int firstByteNum, int lastByteNum) {
	
	union {
		float theFloat;
		unsigned char theByteArray[4];
	} byteArrayToFloat;
	
	int start = firstByteNum - 1;
	int end = lastByteNum - 1;
	int j = 0;
	
	for (int i = start; i < end; i++) {
		byteArrayToFloat.theByteArray[j++] = this->outputRefreshPacket[i];
	}
	
	return byteArrayToFloat.theFloat;
}





















