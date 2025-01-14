#ifndef PACKET_UNPACKER_h
#define PACKET_UNPACKER_h

#include <ControlPanel.h>

/* Packet Unpacker
 * Responsible for extracting the gaugePacket contents and updating
 * the applicable parts of the NGH (ControlPanel) Model.
 */
class PacketUnpacker
{
public:
	PacketUnpacker(ControlPanel& controlPanel);
	void setGaugePacket(const byte * gaugePacket);
	
	//Unpacks the OutputRefreshPacket into the KMega (ControlPanel) Model. If successful, clears the OutputRefreshPacket.
	void unpackGaugePacketIntoModel();

private:
	//Returns the integer stored in OutputRefreshPacket located at the specified byte numbers (see ICD in OneNote). Byte numbers can be provided in any order.
	int convertTwoBytesInGaugePacketIntoInteger(int byteNum1, int byteNum2);
	
	void clearGaugePacket();
	
	ControlPanel& controlPanel;
	byte * gaugePacket; //See NGHService
};

#endif























