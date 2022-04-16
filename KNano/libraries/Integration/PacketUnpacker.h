#ifndef PACKET_UNPACKER_h
#define PACKET_UNPACKER_h

#include <AltitudeGauge.h>

/* Packet Unpacker
 * Responsible for extracting the AltitudePacket contents and updating the applicable parts of the KNano (AltitudeGauge) Model.
 */
class PacketUnpacker
{
public:
	PacketUnpacker(AltitudeGauge& altitudeGauge);
	
	void setAltitudePacket(const byte * altitudePacket);
	
	//Unpacks the AltitudePacket into the KNano (AltitudeGauge) Model. If successful, clears the AltitudePacket.
	void unpackAltitudePacketIntoModel();

private:
	//Returns float number value stored in AltitudePacket located at the specified byte numbers. firstByteNum and lastByteNum are "Byte Numbers" as defined in ICD (OneNote).
	float convertFourBytesInAltitudePacketIntoFloat(int firstByteNum, int lastByteNum);
	
	void clearAltitudePacket();
	
	AltitudeGauge& altitudeGauge;
	byte * altitudePacket;//See KNanoService
};

#endif























