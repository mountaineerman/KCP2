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
	
	//Unpacks the GaugePacket into the NGH (ControlPanel) Model and clears the GaugePacket.
	//If a command was sent, returns the command without unpacking the other contents.
	byte unpackGaugePacketIntoModel();

private:
	//Returns the integer stored in GaugePacket located at the specified byte numbers (see ICD in Joplin). Byte numbers can be provided in any order.
	int convertTwoBytesInGaugePacketIntoInteger(int byteNum1, int byteNum2);
	
	void clearGaugePacket();
	
	ControlPanel& controlPanel;
	byte * gaugePacket; //See NGHService
};

#endif























