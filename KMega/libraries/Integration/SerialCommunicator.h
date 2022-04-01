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
	void setOutputRefreshPacket(const byte * outputRefreshPacket);
	void setInputRefreshPacket(const byte * inputRefreshPacket);
	
	void establishSerialLink();
	
	//Ingests data from the Arduino Serial Buffer until:
	// a) A complete and valid OutputRefreshPacket has been stored in the Packet Buffer, OR
	// b) There is no data left in the Serial Buffer
	void ingestDataFromSerialBufferToPacketBuffer();
	
	//Attempts to populate (shared) outputRefreshPacket. Returns true if successful and false if not.
	bool getOutputRefreshPacket();
	
	//Attempts to send an inputRefreshPacket to KKIM.
	void sendInputRefreshPacket();
	
	void tallyCommunicationsDiagnosticData();//TODO Inject Communications Diagnostic Data into inputRefreshPacket
	void displayCommunicationsDiagnosticData();
	
	void teardownSerialLink();

private:
	//Clear the packetBuffer and associated variables
	void clearPacketBuffer();
	//void displayPacketBufferInDecimal(); //TODO remove
	
	int delimiterByteCounter; // The number of consecutive delimiter bytes that have been read
	int packetBufferCursor; //The position of the next element to write to in the Packet Buffer
	bool isValidPacketInPacketBuffer;
	byte receivedByte;
	byte packetBuffer[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES];
	byte * outputRefreshPacket; //See KMegaService
	
	int numberOfBytesWritten;
	byte * inputRefreshPacket;  //See KMegaService
	
	unsigned int timer;
	unsigned int running_numberOfRejectedIncomingBytes;
	unsigned int running_numberOfRejectedOutputRefreshPackets;
	unsigned int running_numberOfAcceptedOutputRefreshPackets;
	unsigned int running_numberOfSentInputRefreshPackets;
	unsigned int running_numberOfInputRefreshPacketsNotSent;
	unsigned int numberOfRejectedIncomingBytes;
	unsigned int numberOfRejectedOutputRefreshPackets;
	unsigned int numberOfAcceptedOutputRefreshPackets;
	unsigned int numberOfSentInputRefreshPackets;
	unsigned int numberOfInputRefreshPacketsNotSent;
};

#endif























