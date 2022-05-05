#ifndef KNANO_SERVICE_h
#define KNANO_SERVICE_h

#include <AltitudeGauge.h>
#include <SerialCommunicator.h>
#include <PacketUnpacker.h>

/* KNano Service
 * Responsible for orchestrating KNano
 */
class KNanoService
{
public:
	KNanoService();
private:
	void startupMode();
	void standardOperatingMode();
	void clearPacket(byte * packet, int packetLength);
	
	AltitudeGauge altitudeGauge;
	SerialCommunicator serialCommunicator;
	PacketUnpacker packetUnpacker;
	
	byte altitudePacket[ALTITUDE_PACKET_LENGTH_IN_BYTES];//READ-ONLY by KNanoService after initialization
	
	long lastByteArrivalTime;
	long lastAltitudePacketArrivalTime;
};

#endif