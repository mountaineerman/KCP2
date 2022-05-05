#ifndef PACKET_ASSEMBLER_h
#define PACKET_ASSEMBLER_h

#include <ControlPanel.h>

/* Packet Assembler
 * Responsible for reading the status of inputs in the KMega (ControlPanel) Model and assembling
 * them into the InputRefreshPacket and AltitudePacket.
 */
class PacketAssembler
{
public:
	PacketAssembler(ControlPanel& controlPanel);
	void setAltitudePacket(const byte * altitudePacket);
	void setInputRefreshPacket(const byte * inputRefreshPacket);
	
	void displayPacket(const byte * packet, int packetLength, String packetName);
	
	//Assembles the altitudePacket
	void assembleAltitudePacket();
	//Assembles the status of inputs in the KMega (ControlPanel) Model into an InputRefreshPacket
	void assembleInputRefreshPacket();

private:
	//See description in "ICD:KMega>KKIM" (Onenote)
	byte compressBoolsIntoByte(bool bool1, bool bool2, bool bool3, bool bool4,
							   bool bool5, bool bool6, bool bool7, bool bool8);
	
	void saveByteToInputRefreshPacket(byte theByte, int byteNumber);
	
	//Saves integer number value at the specified byte numbers. byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	void saveNumberToInputRefreshPacketAtByteNumbers(int number, int byteNum1, int byteNum2);
	
	//Saves float number value at the specified byte numbers. firstByteNum and lastByteNum are "Byte Numbers" as defined in ICD (Onenote).
	void saveFloatToAltitudePacketAtByteNumbers(float number, int firstByteNum, int lastByteNum);
	
	//void saveFloatToInputRefreshPacketAtByteNumbers(float number, int firstByteNum, int lastByteNum); //(For Debugging)
	
	ControlPanel& controlPanel;
	byte * altitudePacket;		//See KMegaService
	byte * inputRefreshPacket;	//See KMegaService
};

#endif






















