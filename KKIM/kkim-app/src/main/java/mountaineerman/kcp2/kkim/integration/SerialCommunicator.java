package mountaineerman.kcp2.kkim.integration;

import java.util.Arrays;
import java.util.Date;

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
	
	//InputRefreshPacket:
	private byte[] tempBuffer = new byte[1];
	private byte receivedByte = KKIMProp.getallPacketsNullByte();
	private int delimiterByteCounter = 0;
	private byte[] inputRefreshPacketBuffer = new byte[KKIMProp.getkMegaInputRefreshPacketLengthInBytes()];
	private int inputRefreshPacketBufferCursor = 0;
	private boolean isValidPacketInInputRefreshPacketBuffer = false;
	
	//OutputRefreshPacket:
	
	//Communications Diagnostic Information:
	private long running_numberOfRejectedIncomingBytes = 0;
	private long running_numberOfRejectedInputRefreshPackets = 0;
	private long running_numberOfAcceptedInputRefreshPackets = 0;
	private long running_numberOfSentOutputRefreshPackets = 0;
	private long running_numberOfOutputRefreshPacketsNotSent = 0;//TODO scrap?
	
//	private long numberOfRejectedIncomingBytes;
//	private long numberOfRejectedInputRefreshPackets;
//	private long numberOfAcceptedInputRefreshPackets;
//	private long numberOfSentOutputRefreshPackets;
//	private long numberOfOutputRefreshPacketsNotSent;
	
	
	public SerialCommunicator() {
		
		Arrays.fill(this.tempBuffer, KKIMProp.getallPacketsNullByte());
		Arrays.fill(this.inputRefreshPacketBuffer, KKIMProp.getallPacketsNullByte());
		
//		this->numberOfBytesWritten = 0;
	}
	
 	public void establishSerialLink() {
 		
 		System.out.print("Establishing serial connection to KMega... ");
 		
 		this.serialPort = SerialPort.getCommPort(KKIMProp.getkMegaPortNumber());//SerialPort comPort = SerialPort.getCommPort(KKIMProp.getkMegaPortNumber());
 		this.serialPort.setBaudRate(KKIMProp.getkMegaPortBaudrate());
 		this.serialPort.openPort();
 		this.serialPort.flushIOBuffers();
 		
 		//System.out.println("Computer Serial Read Buffer Size: " + serialPort.getDeviceReadBufferSize());
 		//System.out.println("Computer Serial Write Buffer Size: " + serialPort.getDeviceWriteBufferSize());
 		
 		System.out.println("DONE");
	}
		
	//Ingests data from the Serial Read Buffer until:
	// a) A complete and valid InputRefreshPacket has been stored in the inputRefreshPacketBuffer, OR
	// b) There is no data left in the Serial Buffer
	public void ingestDataFromSerialPortToInputRefreshPacketBuffer() throws RuntimeException {
		
		while (this.serialPort.bytesAvailable() > 0) {
			
			this.serialPort.readBytes(this.tempBuffer, 1);
			this.receivedByte = this.tempBuffer[0];
			//System.out.println("Received byte: " + this.tempBuffer[0]);
			
			//System.out.println(this.delimiterByteCounter);
			if (0 <= this.delimiterByteCounter && this.delimiterByteCounter < KKIMProp.getallPacketsNumberOfDelimiterBytes()) { //Packet has not started yet
				if (this.receivedByte == KKIMProp.getallPacketsDelimiterByte()) {
					this.delimiterByteCounter++;
				} else {
					this.delimiterByteCounter = 0;
					this.running_numberOfRejectedIncomingBytes++;
				}
			} else if (this.delimiterByteCounter == KKIMProp.getallPacketsNumberOfDelimiterBytes()) { //Packet read in progress
				this.inputRefreshPacketBuffer[this.inputRefreshPacketBufferCursor] = this.receivedByte;
				this.inputRefreshPacketBufferCursor++;
			} else {
				throw new RuntimeException("SerialCommunicator's delimiterByteCounter is outside of permitted range [0-" + KKIMProp.getallPacketsNumberOfDelimiterBytes() + "]: " + this.delimiterByteCounter);					
			}
			
			if (this.inputRefreshPacketBufferCursor == KKIMProp.getkMegaInputRefreshPacketLengthInBytes()) { //A full packet is in the inputRefreshPacketBuffer
				if (true /*TODO:packet is valid*/) {
					this.isValidPacketInInputRefreshPacketBuffer = true;
					this.running_numberOfAcceptedInputRefreshPackets++;
					return;
				} else { //Packet is invalid
					this.clearInputRefreshPacketBuffer();
					this.running_numberOfRejectedInputRefreshPackets++;
				}
			}
		}
	}
	
	public boolean getisValidPacketInInputRefreshPacketBuffer() {
		return this.isValidPacketInInputRefreshPacketBuffer;
	}
	
	public byte[] getinputRefreshPacketBuffer() {
		return this.inputRefreshPacketBuffer;
	}
	
	public void printCommunicationsDiagnosticInformation() {
		System.out.println("running_numberOfRejectedIncomingBytes: " + running_numberOfRejectedIncomingBytes);
		System.out.println("running_numberOfRejectedInputRefreshPackets: " + running_numberOfRejectedInputRefreshPackets);
		System.out.println("running_numberOfAcceptedInputRefreshPackets: " + running_numberOfAcceptedInputRefreshPackets);
		System.out.println("running_numberOfSentOutputRefreshPackets: " + running_numberOfSentOutputRefreshPackets);
		System.out.println("running_numberOfOutputRefreshPacketsNotSent: " + running_numberOfOutputRefreshPacketsNotSent);
		System.out.println("Bytes available in Serial Read Buffer: " + this.serialPort.bytesAvailable());
	}

	//Clear the inputRefreshPacketBuffer and reset associated variables
	public void clearInputRefreshPacketBuffer() {
		this.delimiterByteCounter = 0;
		this.inputRefreshPacketBufferCursor = 0;	
		this.isValidPacketInInputRefreshPacketBuffer = false;	
		Arrays.fill(this.inputRefreshPacketBuffer, KKIMProp.getallPacketsNullByte());
	}

	//Sends an outputRefreshPacket to KMega
	public void sendOutputRefreshPacket(byte[] packet) {
		
		System.out.println(this.serialPort.writeBytes(packet, KKIMProp.getkMegaOutputRefreshPacketLengthInBytes()));
		this.running_numberOfSentOutputRefreshPackets++;
		
//		if ( Serial.availableForWrite() >= INPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
//			numberOfBytesWritten = Serial.write(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
//			
//			if (numberOfBytesWritten == INPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
//				this->running_numberOfSentInputRefreshPackets++;
//			} else {
//				this->running_numberOfInputRefreshPacketsNotSent++;
//			}
//		} else {
//			this->running_numberOfInputRefreshPacketsNotSent++;
//		}
		
//		this.serialPort.bytesAwaitingWrite();
//		this.serialPort.writeBytes(buffer, bytesToWrite);
	}
		
	public void teardownSerialLink() {//TODO
		
	}
}
