#ifndef NGH_SERVICE_h
#define NGH_SERVICE_h

#include <ControlPanel.h>
#include <SerialCommunicator.h>
#include <PacketUnpacker.h>

/* NGH Service
 * Responsible for orchestrating NGH
 */
class NGHService
{
public:
	NGHService();
private:
	void startupMode();
	void standardOperatingMode();
	void clearPacket(byte * packet, int packetLength);
	void displayPacket(const byte * packet, int packetLength, String packetName);
	
	ControlPanel controlPanel;
	SerialCommunicator serialCommunicator;
	PacketUnpacker packetUnpacker;
	
	byte gaugePacket[GAUGE_PACKET_LENGTH_IN_BYTES];//READ-ONLY by NGHService after initialization
	
	long gaugePacketLastReceiveTimeInMilliseconds;

	bool gaugesHaveBeenSetCCW;
};

#endif