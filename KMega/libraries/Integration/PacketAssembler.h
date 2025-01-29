#ifndef PACKET_ASSEMBLER_h
#define PACKET_ASSEMBLER_h

#include <ControlPanel.h>

enum PacketType {ALTITUDE, GAUGE_PACKET_A, GAUGE_PACKET_B, INPUT_REFRESH};

/* Packet Assembler
 * Responsible for reading the status of inputs in the KMega (ControlPanel) Model and assembling
 * them into the InputRefreshPacket and AltitudePacket.
 */
class PacketAssembler
{
public:
	PacketAssembler(ControlPanel& controlPanel);
	void setAltitudePacket(const byte * altitudePacket);
	void setGaugePacketA(const byte * gaugePacketA);
	void setGaugePacketB(const byte * gaugePacketB);
	void setInputRefreshPacket(const byte * inputRefreshPacket);
	
	void displayPacket(const byte * packet, int packetLength, String packetName);
	
	void assembleAltitudePacket();
	void assembleGaugePacketA();
	void assembleGaugePacketB();
	//Assembles the status of inputs in the KMega (ControlPanel) Model into an InputRefreshPacket
	void assembleInputRefreshPacket();

private:
	//See description in "ICD:KMega>KKIM" (Onenote)
	byte compressBoolsIntoByte(bool bool1, bool bool2, bool bool3, bool bool4,
							   bool bool5, bool bool6, bool bool7, bool bool8);
	
	void saveByteToPacketAtByteNumber(PacketType packetType, byte theByte, int byteNumber);
	
	//Saves integer Number value to the Packet at the specified Byte Numbers. byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	void saveNumberToPacketAtByteNumbers(int number, PacketType packetType, int byteNum1, int byteNum2);
	
	//Saves float number value at the specified byte numbers. firstByteNum and lastByteNum are "Byte Numbers" as defined in ICD (Onenote).
	void saveFloatToAltitudePacketAtByteNumbers(float number, int firstByteNum, int lastByteNum);
	
	//void saveFloatToInputRefreshPacketAtByteNumbers(float number, int firstByteNum, int lastByteNum); //(For Debugging)
	
	ControlPanel& controlPanel;
	byte * altitudePacket;		//See KMegaService
	byte * gaugePacketA;		//See KMegaService
	byte * gaugePacketB;		//See KMegaService
	byte * inputRefreshPacket;	//See KMegaService
};

#endif






















