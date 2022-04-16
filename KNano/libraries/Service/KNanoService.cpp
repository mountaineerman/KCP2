#include <KNanoService.h>
#include <AltitudeGauge.h>
#include <SerialCommunicator.h>
#include <PacketUnpacker.h>

KNanoService::KNanoService()
	: altitudeGauge()
	, serialCommunicator()
	, packetUnpacker(altitudeGauge)
{	
	this->altitudePacket[ALTITUDE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(altitudePacket, ALTITUDE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setAltitudePacket(altitudePacket);
	this->packetUnpacker.setAltitudePacket(altitudePacket);
	
	//this->inputRefreshPacketLastSendTimeInMilliseconds = millis();//TODO delete?
	
	this->startupMode(); //TODO move out of constructor
	
	while (true) {//TODO move out of constructor
		this->standardOperatingMode();//TODO add diagnosticMode, shutdownMode, idleMode...
	}
}

void KNanoService::startupMode() {

	altitudeGauge.setAllLEDsOn();
	delay(1000);//FIXME refresh sevensegmentdisplay
	altitudeGauge.setAllLEDsOff();
	delay(100);//FIXME refresh sevensegmentdisplay
	
	this->serialCommunicator.establishKMegaSerialLink();
}

void KNanoService::standardOperatingMode() {
	
	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	if ( this->serialCommunicator.getAltitudePacket() ) {
		this->packetUnpacker.unpackAltitudePacketIntoModel();
	}
	
	this->altitudeGauge.refreshSevenSegmentDisplay();
	
	delay(CYCLICAL_SLEEP_TIME_IN_MILLISECONDS);
}

void KNanoService::shutdownMode() {
	
	serialCommunicator.teardownKMegaSerialLink();
	altitudeGauge.setAllLEDsOff();
}

void KNanoService::clearPacket(const byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}













