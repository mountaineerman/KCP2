#ifndef NGH_SERVICE_h
#define NGH_SERVICE_h

#include <ControlPanel.h>
#include <SerialCommunicator.h>
#include <PacketUnpacker.h>

enum NGHOperatingMode {STARTUP, STANDARD};

/* NGH Service
 * Responsible for orchestrating NGH
 */
class NGHService
{
public:
	NGHService();

	//Run through the NGH Operating Modes until termination
	void run();
private:
	void startupMode();
	void standardOperatingMode();
	void clearPacket(byte * packet, int packetLength);
	
	ControlPanel controlPanel;
	SerialCommunicator serialCommunicator;
	PacketUnpacker packetUnpacker;
	
	byte gaugePacket[GAUGE_PACKET_LENGTH_IN_BYTES];//READ-ONLY by NGHService after initialization
	
	long gaugePacketLastReceiveTimeInMilliseconds;

	bool gaugesHaveBeenSetCCW;
	NGHOperatingMode nextMode;
};

#endif