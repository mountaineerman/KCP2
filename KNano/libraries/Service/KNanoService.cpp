#include <KNanoService.h>
#include <AltitudeGauge.h>
#include <SerialCommunicator.h>
#include <PacketUnpacker.h>
#include "../../configuration.h"

KNanoService::KNanoService()
	: altitudeGauge()
	, serialCommunicator()
	, packetUnpacker(altitudeGauge)
{
	this->altitudePacket[ALTITUDE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(altitudePacket, ALTITUDE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setAltitudePacket(altitudePacket);
	this->packetUnpacker.setAltitudePacket(altitudePacket);
	
	lastByteArrivalTime = millis();
	lastAltitudePacketArrivalTime = millis();
	
	this->startupMode();
	
	while (true) {
		this->standardOperatingMode();
	}
}

void KNanoService::startupMode() {

	this->altitudeGauge.setAllLEDsOn();
	for (int i = 0; i < 2000; i++) {
		this->altitudeGauge.refreshSevenSegmentDisplay();
		delay(1);
	}
	this->altitudeGauge.setAllLEDsOff();
	
	this->altitudeGauge.displayString(NO_BYTES_RECEIVED);
	//this->altitudeGauge.setAllUnitLEDsOn();
	
	this->serialCommunicator.establishKMegaSerialLink();
}

void KNanoService::standardOperatingMode() {
	
	if (this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer()) {
		this->lastByteArrivalTime = millis();
		if (this->serialCommunicator.getAltitudePacket()) {
			this->lastAltitudePacketArrivalTime = millis();
			this->packetUnpacker.unpackAltitudePacketIntoModel();
		}
	}
	
	if ((millis() - this->lastByteArrivalTime) > MINIMUM_QUIET_TIME_PRIOR_TO_ERROR_MODE_IN_MILLISECONDS) {
		this->altitudeGauge.displayString(NO_BYTES_RECEIVED);
		this->altitudeGauge.setAllUnitLEDsOn();
	} else if ((millis() - this->lastAltitudePacketArrivalTime) > MINIMUM_QUIET_TIME_PRIOR_TO_ERROR_MODE_IN_MILLISECONDS) {
		this->altitudeGauge.displayString(NO_PACKET_RECEIVED);
		this->altitudeGauge.setAllUnitLEDsOn();
	}
	
	this->altitudeGauge.refreshSevenSegmentDisplay();
	
	delay(CYCLICAL_SLEEP_TIME_IN_MILLISECONDS);
}

void KNanoService::clearPacket(byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}













