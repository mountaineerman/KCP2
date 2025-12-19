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
	void setGaugePacket(const byte * gaugePacket);
	
	void establishKMegaSerialLink();
	
	//Ingests data from the Arduino Serial Buffer until:
	// a) A complete and valid gaugePacket has been stored in the Packet Buffer, OR
	// b) There is no data left in the Serial Buffer
	void ingestDataFromSerialBufferToPacketBuffer();
	
	//Attempts to populate (shared) gaugePacket. Returns true if successful and false if not.
	bool getGaugePacket();
	
	void teardownKMegaSerialLink();

private:
	//Clear the packetBuffer and associated variables
	void clearPacketBuffer();
	//void displayPacketBufferInDecimal(); //TODO remove
	
	int delimiterByteCounter; // The number of consecutive delimiter bytes that have been read
	int packetBufferCursor; //The position of the next element to write to in the Packet Buffer
	bool isValidPacketInPacketBuffer;
	byte receivedByte;
	byte packetBuffer[GAUGE_PACKET_LENGTH_IN_BYTES];
	byte * gaugePacket; //See NGHService
};

#endif