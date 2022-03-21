#ifndef SERIAL_COMMUNICATOR_h
#define SERIAL_COMMUNICATOR_h

#include "../../configuration.h"

/* Serial Communicator
 * Responsible for serial communication with all other entities, including KKIM and KNano.
 */
class SerialCommunicator
{
public:
	SerialCommunicator();
	void setOutputRefreshPacket(byte * outputRefreshPacket);
	void setInputRefreshPacket(byte * inputRefreshPacket);
	
	void establishSerialLink();
	
	//Ingests data from the Arduino Serial Buffer until:
	// a) A complete and valid OutputRefreshPacket has been stored in the Packet Buffer, OR
	// b) There is no data left in the Serial Buffer
	void ingestDataFromSerialBufferToPacketBuffer();
	
	//Attempts to populate (shared) outputRefreshPacket[] with a valid OutputRefreshPacket. Returns true if successful and false if not.
	bool getOutputRefreshPacket();
	
	void teardownSerialLink();

private:
	//Clear the packetBuffer and associated variables
	void clearPacketBuffer();
	void displayPacketBufferInDecimal(); //TODO remove
	
	int delimiterByteCounter; // The number of consecutive delimiter bytes that have been read
	int packetBufferCursor; //The position of the next element to write to in the Packet Buffer
	bool isValidPacketInPacketBuffer;
	byte receivedByte;
	byte packetBuffer[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES];
	byte * outputRefreshPacket; //See KMegaService
	byte * inputRefreshPacket;  //See KMegaService
	//TODO numberOfRejectedBytesPerSecond
	//TODO numberOfRejectedPacketsPerSecond
	//TODO numberOfAcceptedPacketsPerSecond
};

#endif























