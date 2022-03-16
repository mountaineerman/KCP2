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
	void establishSerialLink();
	void ingestData();
	void teardownSerialLink();
private:
	void clearPacketBuffer();
	void displayPacketBufferInDecimal();
	
	int delimiterByteCounter; // The number of consecutive delimiter bytes that have been read
	byte receivedByte;
	byte packetBuffer[INCOMING_PACKET_LENGTH_IN_BYTES];
	int packetBufferCursor; //The position of the next element to write to in the Packet Buffer
	//TODO numberOfRejectedBytesPerSecond
	//TODO numberOfRejectedPacketsPerSecond
	//TODO numberOfAcceptedPacketsPerSecond
};

#endif























