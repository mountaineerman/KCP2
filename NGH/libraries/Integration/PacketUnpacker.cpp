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

void PacketUnpacker::unpackGaugePacketIntoModel() {
	
	// [GaugePacketA]
	controlPanel.moduleC.stepper_HeatLife.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(10,11) );
	controlPanel.moduleC.stepper_Gforce.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(12,13) );
	controlPanel.moduleG.stepper_Mach.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(14,15) );
	controlPanel.moduleG.stepper_Pitch.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(16,17) );
	controlPanel.moduleG.stepper_Heading.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(18,19) );
	controlPanel.moduleI.stepper_Fuel.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(20,21) );

	// [GaugePacketB]
	// controlPanel.moduleI.stepper_Charge.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(10,11) );
	// controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(12,13) );
	// controlPanel.moduleGT.stepper_Density.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(14,15) );
	// controlPanel.moduleGT.stepper_Speed.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(16,17) );
	// controlPanel.moduleGT.stepper_VertSpeed.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(18,19) );
	// controlPanel.moduleGT.stepper_RadarAlt.setDesiredPosition( this->convertTwoBytesInGaugePacketIntoInteger(20,21) );

	this->clearGaugePacket();
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