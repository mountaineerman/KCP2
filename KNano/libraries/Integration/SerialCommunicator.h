#ifndef SERIAL_COMMUNICATOR_h
#define SERIAL_COMMUNICATOR_h

#include "../../configuration.h"

/* Serial Communicator
 * Responsible for serial communication with KMega.
 */
class SerialCommunicator
{
public:
	SerialCommunicator();
	void setAltitudePacket(const byte * altitudePacket);
	void establishKMegaSerialLink();
	void teardownKMegaSerialLink();
	
	//Ingests data from the Arduino Serial Buffer until:
	// a) A complete and valid AltitudePacket has been stored in the Packet Buffer, OR
	// b) There is no data left in the Serial Buffer
	//Returns true if some data was read, false otherwise.
	bool ingestDataFromSerialBufferToPacketBuffer();
	
	//Attempts to populate (shared) AltitudePacket. Returns true if successful and false if not.
	bool getAltitudePacket();

private:
	//Clear the packetBuffer and associated variables
	void clearPacketBuffer();
	
	int delimiterByteCounter; // The number of consecutive delimiter bytes that have been read
	int packetBufferCursor; //The position of the next element to write to in the Packet Buffer
	bool isValidPacketInPacketBuffer;
	byte receivedByte;
	byte packetBuffer[ALTITUDE_PACKET_LENGTH_IN_BYTES];
	byte * altitudePacket; 		//See KNanoService
};

#endif























