package mountaineerman.kcp2.kkim.integration;

import java.util.Arrays;
import com.fazecast.jSerialComm.*; // Serial Communication library
import mountaineerman.kcp2.kkim.KKIMProp; 

/* Serial Communicator
 * Responsible for serial communication with KMega.
 * 
 * Uses the jSerialComm library: 
 * https://fazecast.github.io/jSerialComm/
 * 
 * Note: The computer's Serial Read Buffer Size is 4096 bytes.
 * Note: The computer's Serial Write Buffer Size is 4096 bytes.
 */
public class SerialCommunicator {
	
	private SerialPort serialPort = null;
	
	//All Packets:
	private byte[] oneByteBuffer = new byte[1];
	private byte receivedByte = KKIMProp.getallPacketsNullByte();
	private int delimiterByteCounter = 0;
	private int packetBufferByteCounter = 0;
	private byte[] packetHeaderBuffer = new byte[KKIMProp.getallPacketsHeaderLengthInBytes()];
	private int packetLength = 0;
	private PacketType packetType = PacketType.INVALID;
	private byte[] packetBuffer = new byte[0];
	private boolean isValidPacketInPacketBuffer = false;
	
	//Communications Diagnostic Information:
	private long numberOfRejectedIncomingBytes = 0;
	private long numberOfRejectedInputRefreshPackets = 0;
	private long numberOfAcceptedInputRefreshPackets = 0;
	private long numberOfAcceptedKKIMTerminalDisplayPackets = 0;
	private long numberOfRejectedKKIMTerminalDisplayPackets = 0;
	private long numberOfSentOutputRefreshPackets = 0;
	private long numberOfOutputRefreshPacketsNotSent = 0;
	
	
	public SerialCommunicator() {
		this.clearPacketBufferAndFriends();
	}
	
 	public void establishSerialLink() {
 		
 		System.out.print("Establishing serial connection to KMega... ");
 		
 		this.serialPort = SerialPort.getCommPort(KKIMProp.getkMegaPortNumber());
 		this.serialPort.setBaudRate(KKIMProp.getkMegaPortBaudrate());
 		this.serialPort.openPort();
 		this.serialPort.flushIOBuffers();
 		
 		//System.out.println("Computer Serial Read Buffer Size: " + serialPort.getDeviceReadBufferSize());
 		//System.out.println("Computer Serial Write Buffer Size: " + serialPort.getDeviceWriteBufferSize());
 		
 		System.out.println("DONE");
	}
		
	//Ingests data from the Serial Read Buffer until:
	// a) A complete and valid InputRefreshPacket has been stored in the packetBuffer, OR
 	// b) A complete and valid KKIMTerminalDisplayPacket has been stored in the packetBuffer, OR
 	// c) There is no data left in the Serial Buffer
 	
	public void ingestDataFromSerialPortToPacketBuffer() throws RuntimeException {
		
		//		if (this.serialPort.bytesAvailable() > 0) {
		//			System.out.println("Bytes available in Serial Buffer: " + this.serialPort.bytesAvailable());
		//		}
		
		while (this.serialPort.bytesAvailable() > 0) {
			
			this.serialPort.readBytes(this.oneByteBuffer, 1);
			this.receivedByte = this.oneByteBuffer[0];
			
			if (0 <= this.delimiterByteCounter && this.delimiterByteCounter < KKIMProp.getallPacketsNumberOfDelimiterBytes()) { //Packet has not started yet
				if (this.receivedByte == KKIMProp.getallPacketsDelimiterByte()) {
					this.delimiterByteCounter++;
				} else {
					//System.out.println("Rejected byte: [" + this.receivedByte + "]. Expected: [" + KKIMProp.getallPacketsDelimiterByte() + "].");
					this.delimiterByteCounter = 0;
					this.numberOfRejectedIncomingBytes++;
				}
			} else if (this.delimiterByteCounter == KKIMProp.getallPacketsNumberOfDelimiterBytes()) { //Packet read in progress
				this.packetBufferByteCounter++;
				if (this.packetBufferByteCounter < KKIMProp.getallPacketsHeaderLengthInBytes()) {//Header read in progress
					this.packetHeaderBuffer[this.packetBufferByteCounter-1] = this.receivedByte;
				} else if (this.packetBufferByteCounter == KKIMProp.getallPacketsHeaderLengthInBytes()) {//Header read complete
					this.packetHeaderBuffer[this.packetBufferByteCounter-1] = this.receivedByte;
					this.determinePacketTypeBasedOnHeader(this.getIntegerInPacketAtByteNumber(this.packetHeaderBuffer, 2));
					//System.out.println("packetType: " + this.packetType);
					this.packetLength = this.getIntegerInPacketAtByteNumber(this.packetHeaderBuffer, 3);
					this.packetBuffer = new byte[this.packetLength];
					Arrays.fill(this.packetBuffer, KKIMProp.getallPacketsNullByte());
					System.arraycopy(this.packetHeaderBuffer, 0, this.packetBuffer, 0, KKIMProp.getallPacketsHeaderLengthInBytes());
				} else {//Payload read in progress
					this.packetBuffer[this.packetBufferByteCounter-1] = this.receivedByte;
					if (this.packetBufferByteCounter == this.packetLength) { //A full packet is in the packetBuffer
						switch (this.packetType) {
							case INPUT_REFRESH_PACKET:
								if (true /*TODO:packet is valid*/) {
									this.isValidPacketInPacketBuffer = true;
									this.numberOfAcceptedInputRefreshPackets++;
									return;
								} else { //Packet is invalid
									this.clearPacketBufferAndFriends();
									this.numberOfRejectedInputRefreshPackets++;
								}
								break;
							case KKIM_TERMINAL_DISPLAY_PACKET:
								if (true /*TODO:packet is valid*/) {
									this.isValidPacketInPacketBuffer = true;
									this.numberOfAcceptedKKIMTerminalDisplayPackets++;
									return;
								} else { //Packet is invalid
									this.clearPacketBufferAndFriends();
									this.numberOfRejectedKKIMTerminalDisplayPackets++;
								}
								break;
							default:
								break;
						}
					}
				}
			} else {
				throw new RuntimeException("SerialCommunicator's delimiterByteCounter is outside of permitted range [0-" + KKIMProp.getallPacketsNumberOfDelimiterBytes() + "]: " + this.delimiterByteCounter);					
			}
		}
	}
	
	public boolean getisValidPacketInPacketBuffer() {
		return this.isValidPacketInPacketBuffer;
	}
	
	public PacketType getPacketTypeInPacketBuffer() {
		return this.packetType;
	}
	
	public byte[] getPacketBuffer() {
		return this.packetBuffer;
	}
	
	public void printCommunicationsDiagnosticInformation() {
		System.out.println("numberOfRejectedIncomingBytes: " + numberOfRejectedIncomingBytes);
		System.out.println("numberOfRejectedInputRefreshPackets: " + numberOfRejectedInputRefreshPackets);
		System.out.println("numberOfAcceptedInputRefreshPackets: " + numberOfAcceptedInputRefreshPackets);
		System.out.println();
		System.out.println("numberOfAcceptedKKIMTerminalDisplayPackets: " + numberOfAcceptedKKIMTerminalDisplayPackets);
		System.out.println("numberOfRejectedKKIMTerminalDisplayPackets: " + numberOfRejectedKKIMTerminalDisplayPackets);
		System.out.println();
		System.out.println("numberOfSentOutputRefreshPackets: " + numberOfSentOutputRefreshPackets);
		System.out.println("numberOfOutputRefreshPacketsNotSent: " + numberOfOutputRefreshPacketsNotSent);
		System.out.println("Bytes available in Serial Read Buffer: " + this.serialPort.bytesAvailable());
		System.out.println();
	}

	//Clear the inputRefreshPacketBuffer and reset associated variables
	public void clearPacketBufferAndFriends() {
		Arrays.fill(this.oneByteBuffer, KKIMProp.getallPacketsNullByte());
		this.receivedByte = KKIMProp.getallPacketsNullByte();
		this.delimiterByteCounter = 0;
		this.packetBufferByteCounter = 0;	
		Arrays.fill(this.packetHeaderBuffer, KKIMProp.getallPacketsNullByte());
		this.packetLength = 0;
		this.packetType = PacketType.INVALID;
		Arrays.fill(this.packetBuffer, KKIMProp.getallPacketsNullByte());
		this.isValidPacketInPacketBuffer = false;
	}

	//Sends an outputRefreshPacket to KMega
	public void sendOutputRefreshPacket(byte[] packet) {
		
		int numberOfBytesWritten = this.serialPort.writeBytes(packet, KKIMProp.getkMegaOutputRefreshPacketLengthInBytes());
		
		if (numberOfBytesWritten == KKIMProp.getkMegaOutputRefreshPacketLengthInBytes()) {
			this.numberOfSentOutputRefreshPackets++;
		} else {
			this.numberOfOutputRefreshPacketsNotSent++;
		}
	}
		
	public void teardownSerialLink() {//TODO
		
	}
	
	public void ingestDataFromSerialPortAndDisplay() {//TODO scrap
	
		if (this.serialPort.bytesAvailable() > 0) {
			int numberOfBytesAvailableInSerialBuffer = this.serialPort.bytesAvailable();
			byte[] displayBuffer = new byte[numberOfBytesAvailableInSerialBuffer];
			this.serialPort.readBytes(displayBuffer, numberOfBytesAvailableInSerialBuffer);
			String result = new String(displayBuffer);
			System.out.print(result);
		}
	}
	
	private int getIntegerInPacketAtByteNumber(byte[] packet, int byteNumber) {
		return (int) packet[byteNumber-1];		
	}
	
	private void determinePacketTypeBasedOnHeader(int intPacketType) {
		switch (intPacketType) {
			case 0: this.packetType = PacketType.INVALID;
				break;
			case 1: this.packetType = PacketType.INPUT_REFRESH_PACKET;
				break;
			case 2: this.packetType = PacketType.OUTPUT_REFRESH_PACKET;
				break;
			case 3: this.packetType = PacketType.ALTITUDE_PACKET;
				break;
			case 4: this.packetType = PacketType.KKIM_TERMINAL_DISPLAY_PACKET;
				break;
			case 5: this.packetType = PacketType.KKIM_USER_INPUT_PACKET;
				break;
			default: this.packetType = PacketType.INVALID;
				break;
		}
	}
}
