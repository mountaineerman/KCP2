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
	void clearOutputRefreshPacket();//TODO remove?
	void displayOutputRefreshPacket(); //TODO remove
	
	ControlPanel controlPanel;
	SerialCommunicator serialCommunicator;
	PacketUnpacker packetUnpacker;
	//PacketAssembler packetAssembler;
	
	byte outputRefreshPacket[INCOMING_PACKET_LENGTH_IN_BYTES]; //READ-ONLY after initialization
};

#endif