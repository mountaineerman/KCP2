#include <Arduino.h>
#include <PacketAssembler.h>
#include "../../configuration.h"


PacketAssembler::PacketAssembler(ControlPanel& controlPanel)
	: controlPanel(controlPanel)
{
	this->inputRefreshPacket = NULL;
}

void PacketAssembler::setInputRefreshPacket(byte * inputRefreshPacket) {
	this->inputRefreshPacket = inputRefreshPacket;
}

void PacketAssembler::displayInputRefreshPacketInDecimal() {
	
	if (!this->inputRefreshPacket) {
		//TODO throw exception
		Serial.println("Exception: PacketAssembler's inputRefreshPacket is not initialized");
	}
	
	Serial.println("PacketAssembler: inputRefreshPacket: (Decimal Format)");
	Serial.print("Position: ");
	for (int i = 0; i < INPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
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

void PacketAssembler::assembleInputRefreshPacket() {
	
	if (!this->inputRefreshPacket) {
		//TODO throw exception
		Serial.println("Exception: PacketAssembler's inputRefreshPacket is not initialized");
	}
	
	byte tempByte = 0;
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleA.switch_StagingButton.getInputStatus(),
										   controlPanel.moduleA.switch_BrakeButton.getInputStatus(),
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false);//TODO
	this->saveByteToInputRefreshPacket(tempByte, 11);
	
	tempByte = this->compressBoolsIntoByte(false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   controlPanel.moduleD.switch_Brake.getInputStatus(),
										   false,//TODO
										   false);//TODO
	this->saveByteToInputRefreshPacket(tempByte, 12);	
	//TODO Add remaining outputs
	
	//this->displayInputRefreshPacketInDecimal();
}

byte PacketAssembler::compressBoolsIntoByte(bool bool1, bool bool2, bool bool3, bool bool4,
											bool bool5, bool bool6, bool bool7, bool bool8) {
	byte result = 0;
	if(bool8) { result |= (1 << 0); }
	if(bool7) { result |= (1 << 1); }
	if(bool6) { result |= (1 << 2); }
	if(bool5) { result |= (1 << 3); }
	if(bool4) { result |= (1 << 4); }
	if(bool3) { result |= (1 << 5); }
	if(bool2) { result |= (1 << 6); }
	if(bool1) { result |= (1 << 7); }
	//Serial.println(result, BIN);
	return result;
}

void PacketAssembler::saveByteToInputRefreshPacket(byte theByte, int byteNumber) {
	this->inputRefreshPacket[byteNumber-1] = theByte;
}




















