#include <Arduino.h>
#include <PacketUnpacker.h>
#include "../../configuration.h"


PacketUnpacker::PacketUnpacker(AltitudeGauge& altitudeGauge)
	: altitudeGauge(altitudeGauge)
{
	this->altitudePacket = NULL;
}

void PacketUnpacker::setAltitudePacket(const byte * altitudePacket) {
	this->altitudePacket = altitudePacket;
}

void PacketUnpacker::unpackAltitudePacketIntoModel() {
	altitudeGauge.setAltitude(this->convertFourBytesInAltitudePacketIntoFloat(10,13));
	this->clearAltitudePacket();
}

float PacketUnpacker::convertFourBytesInAltitudePacketIntoFloat(int firstByteNum, int lastByteNum) {
	
	union {
		float theFloat;
		unsigned char theByteArray[4];
	} byteArrayToFloat;
	
	int start = firstByteNum - 1;
	int end = lastByteNum - 1;
	int j = 0;
	
	for (int i = start; i < end; i++) {
		byteArrayToFloat.theByteArray[j++] = this->altitudePacket[i];
	}
	
	return byteArrayToFloat.theFloat;
}

void PacketUnpacker::clearOutputRefreshPacket() {
	for (int i = 0; i < ALTITUDE_PACKET_LENGTH_IN_BYTES; i++) {
		this->altitudePacket[i] = 0x00;
	}
}



















