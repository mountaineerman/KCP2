#ifndef KMEGA_SERVICE_h
#define KMEGA_SERVICE_h

#include <ControlPanel.h>
#include <SerialCommunicator.h>
#include <PacketUnpacker.h>
#include <PacketAssembler.h>

enum KMegaOperatingMode {STARTUP, STANDARD, SHUTDOWN, DIAGNOSTIC};

/* KMega Service
 * Responsible for orchestrating KMega
 */
class KMegaService
{
public:
	KMegaService();

	//Run through the KMega Operating Modes until termination
	void run();
private:
	void startupMode();
	void standardOperatingMode();
	void shutdownMode();
	
	void updateCommsLEDToIndicateError();
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
	long outputRefreshPacketLastReceiveTimeInMilliseconds;
	long commsLEDErrorStateLastToggleTimeInMilliseconds;
	
	bool outputsHaveBeenSetToIdleState;
	KMegaOperatingMode nextMode;
};

#endif