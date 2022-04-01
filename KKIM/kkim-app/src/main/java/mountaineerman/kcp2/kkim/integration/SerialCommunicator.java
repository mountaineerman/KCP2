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
	private long numberOfRejectedIncomingBytes = 0;
	private long numberOfRejectedInputRefreshPackets = 0;
	private long numberOfAcceptedInputRefreshPackets = 0;
	private long numberOfSentOutputRefreshPackets = 0;
	private long numberOfOutputRefreshPacketsNotSent = 0;
	
	
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
		
		//		if (this.serialPort.bytesAvailable() > 0) {
		//			System.out.println("Bytes available in Serial Buffer: " + this.serialPort.bytesAvailable());
		//		}
		
		while (this.serialPort.bytesAvailable() > 0) {
			
			this.serialPort.readBytes(this.tempBuffer, 1);
			this.receivedByte = this.tempBuffer[0];
			
			if (0 <= this.delimiterByteCounter && this.delimiterByteCounter < KKIMProp.getallPacketsNumberOfDelimiterBytes()) { //Packet has not started yet
				if (this.receivedByte == KKIMProp.getallPacketsDelimiterByte()) {
					this.delimiterByteCounter++;
				} else {
					this.delimiterByteCounter = 0;
					this.numberOfRejectedIncomingBytes++;
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
					this.numberOfAcceptedInputRefreshPackets++;
					return;
				} else { //Packet is invalid
					this.clearInputRefreshPacketBuffer();
					this.numberOfRejectedInputRefreshPackets++;
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
		System.out.println("numberOfRejectedIncomingBytes: " + numberOfRejectedIncomingBytes);
		System.out.println("numberOfRejectedInputRefreshPackets: " + numberOfRejectedInputRefreshPackets);
		System.out.println("numberOfAcceptedInputRefreshPackets: " + numberOfAcceptedInputRefreshPackets);
		System.out.println("numberOfSentOutputRefreshPackets: " + numberOfSentOutputRefreshPackets);
		System.out.println("numberOfOutputRefreshPacketsNotSent: " + numberOfOutputRefreshPacketsNotSent);
		System.out.println("Bytes available in Serial Read Buffer: " + this.serialPort.bytesAvailable());
		System.out.println();
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
		
		int numberOfBytesWritten = this.serialPort.writeBytes(packet, KKIMProp.getkMegaOutputRefreshPacketLengthInBytes());
		
		if (numberOfBytesWritten == KKIMProp.getkMegaOutputRefreshPacketLengthInBytes()) {
			this.numberOfSentOutputRefreshPackets++;
		} else {
			this.numberOfOutputRefreshPacketsNotSent++;
		}
	}
		
	public void teardownSerialLink() {//TODO
		
	}
	
	public void ingestDataFromSerialPortAndDisplay() {
	
		if (this.serialPort.bytesAvailable() > 0) {
			int numberOfBytesAvailableInSerialBuffer = this.serialPort.bytesAvailable();
			byte[] displayBuffer = new byte[numberOfBytesAvailableInSerialBuffer];
			this.serialPort.readBytes(displayBuffer, numberOfBytesAvailableInSerialBuffer);
			String result = new String(displayBuffer);
			System.out.print(result);
		}
	}
}
