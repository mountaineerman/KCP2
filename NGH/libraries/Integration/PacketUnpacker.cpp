#include <Arduino.h>
#include <PacketUnpacker.h>
#include "../../configuration.h"


PacketUnpacker::PacketUnpacker(ControlPanel& controlPanel)
	: controlPanel(controlPanel)
{
	this->gaugePacket = NULL;
}

void PacketUnpacker::setGaugePacket(const byte * gaugePacket) {
	this->gaugePacket = gaugePacket;
}

byte PacketUnpacker::unpackGaugePacketIntoModel() {
	
	byte command = this->gaugePacket[4];
	if (command != 0x00) {
		return command;
	}

	if (NGH_A) {
		// [GaugePacketA]
		controlPanel.moduleC.stepper_HeatLife.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(10,11) );
		controlPanel.moduleC.stepper_Gforce.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(12,13) );
		controlPanel.moduleG.stepper_Mach.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(14,15) );
		controlPanel.moduleG.stepper_Pitch.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(16,17) );
		controlPanel.moduleG.stepper_Heading.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(18,19) );
		controlPanel.moduleIa.stepper_Fuel.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(20,21) );
	} else {
		// [GaugePacketB]
		controlPanel.moduleIb.stepper_Charge.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(10,11) );
		controlPanel.moduleIb.stepper_MonopropellantIntake.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(12,13) );
		controlPanel.moduleGT.stepper_Density.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(14,15) );
		controlPanel.moduleGT.stepper_Speed.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(16,17) );
		controlPanel.moduleGT.stepper_VertSpeed.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(18,19) );
		controlPanel.moduleGT.stepper_RadarAlt.setDesiredPositionAndTravelStatus( this->convertTwoBytesInGaugePacketIntoInteger(20,21) );
	}
	
	this->clearGaugePacket();
	return command;
}

void PacketUnpacker::clearGaugePacket() {
	for (int i = 0; i < GAUGE_PACKET_LENGTH_IN_BYTES; i++) {
		this->gaugePacket[i] = 0x00;
	}
}

int PacketUnpacker::convertTwoBytesInGaugePacketIntoInteger(int byteNum1, int byteNum2) {
	
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
	tempTwoByteArray[0] = this->gaugePacket[smallestByteNum];
	tempTwoByteArray[1] = this->gaugePacket[largestByteNum];
	
	return (*((int *)tempTwoByteArray));
}