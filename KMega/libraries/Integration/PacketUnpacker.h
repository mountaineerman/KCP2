#ifndef PACKET_UNPACKER_h
#define PACKET_UNPACKER_h

#include <ControlPanel.h>

/* Packet Unpacker
 * Responsible for extracting the OutputRefreshPacket contents and updating
 * the applicable parts of the KMega (ControlPanel) Model.
 */
class PacketUnpacker
{
public:
	PacketUnpacker(ControlPanel& controlPanel);
	void setOutputRefreshPacket(const byte * outputRefreshPacket);
	void displayOutputRefreshPacketInDecimal();
	
	//Unpacks the OutputRefreshPacket into the KMega (ControlPanel) Model. If successful, clears the OutputRefreshPacket.
	void unpackOutputRefreshPacketIntoModel();

private:
	//Returns the integer stored in OutputRefreshPacket located at the specified byte numbers (see ICD in OneNote). Byte numbers can be provided in any order.
	int convertTwoBytesInOutputRefreshPacketIntoInteger(int byteNum1, int byteNum2);
	//Returns float number value stored in OutputRefreshPacket located at the specified byte numbers. firstByteNum and lastByteNum are "Byte Numbers" as defined in ICD (Onenote).
	float convertFourBytesInOutputRefreshPacketIntoFloat(int firstByteNum, int lastByteNum);
	
	void clearOutputRefreshPacket();
	
	ControlPanel& controlPanel;
	byte * outputRefreshPacket; //See KMegaService
};

#endif























