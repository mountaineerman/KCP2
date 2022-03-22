#ifndef PACKET_ASSEMBLER_h
#define PACKET_ASSEMBLER_h

#include <ControlPanel.h>

/* Packet Assembler
 * Responsible for reading the status of inputs in the KMega (ControlPanel) Model and assembling
 * them into an InputRefreshPacket.
 */
class PacketAssembler
{
public:
	PacketAssembler(ControlPanel& controlPanel);
	void setInputRefreshPacket(byte * inputRefreshPacket);
	void displayInputRefreshPacketInDecimal();
	//void displayInputRefreshPacketInHexadecimal(); //TODO
	//void displayInputRefreshPacketInBinary(); //TODO
	
	//Assembles the status of inputs in the KMega (ControlPanel) Model into an InputRefreshPacket
	void assembleInputRefreshPacket();

private:
	//See description in "ICD:KMega>KKIM" (Onenote)
	byte compressBoolsIntoByte(bool bool1, bool bool2, bool bool3, bool bool4,
							   bool bool5, bool bool6, bool bool7, bool bool8);
	
	void saveByteToInputRefreshPacket(byte theByte, int byteNumber);
	
	ControlPanel& controlPanel;
	byte * inputRefreshPacket; //See KMegaService
};

#endif






















