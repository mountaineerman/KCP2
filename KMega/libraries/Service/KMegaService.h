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
	//void shutdownMode();
	void clearOutputRefreshPacket();
	void clearInputRefreshPacket();
	void displayOutputRefreshPacket(); //TODO remove
	void displayInputRefreshPacket(); //TODO remove
	
	ControlPanel controlPanel;
	SerialCommunicator serialCommunicator;
	PacketUnpacker packetUnpacker;
	PacketAssembler packetAssembler;
	
	byte outputRefreshPacket[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES]; //READ-ONLY after initialization
	byte inputRefreshPacket[INPUT_REFRESH_PACKET_LENGTH_IN_BYTES];   //READ-ONLY after initialization
	
	long inputRefreshPacketLastSendTimeInMilliseconds;
};

#endif