#ifndef KMEGA_SERVICE_h
#define KMEGA_SERVICE_h

#include <ControlPanel.h>
#include <SerialCommunicator.h>
#include <PacketUnpacker.h>
#include <PacketAssembler.h>

/* KMega Service
 * Responsible for orchestrating KMega
 */
class KMegaService
{
public:
	KMegaService();
private:
	void startupMode();
	void standardOperatingMode();
	//void diagnosticMode();
	void shutdownMode();
	void clearPacket(byte * packet, int packetLength);
	void displayPacket(const byte * packet, int packetLength, String packetName);
	void testAltitudeGauge();
	
	ControlPanel controlPanel;
	SerialCommunicator serialCommunicator;
	PacketUnpacker packetUnpacker;
	PacketAssembler packetAssembler;
	
	byte outputRefreshPacket[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES];//READ-ONLY by KMegaService after initialization
	byte altitudePacket[ALTITUDE_PACKET_LENGTH_IN_BYTES];			//READ-ONLY by KMegaService after initialization
	byte inputRefreshPacket[INPUT_REFRESH_PACKET_LENGTH_IN_BYTES];	//READ-ONLY by KMegaService after initialization
	
	long inputRefreshPacketLastSendTimeInMilliseconds;
};

#endif