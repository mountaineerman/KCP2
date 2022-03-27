package mountaineerman.kcp2.kkim.integration;

import java.util.Arrays;

import mountaineerman.kcp2.kkim.model.ControlPanel;

/* Packet Unpacker
 * Responsible for extracting the InputRefreshPacket contents and updating
 * the applicable parts of the KKIM (ControlPanel) Model.
 */
public class PacketUnpacker {

	private ControlPanel controlPanel;
	
	public PacketUnpacker(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}
	
	public void displayPacketInDecimal(byte[] packet) {
		System.out.println("PacketUnpacker: Displaying packet in decimal format:");
		System.out.println(Arrays.toString(packet));
	}
	
	public void unpackInputRefreshPacketIntoModel(byte[] inputRefreshPacket) {
		//Module A
		byte tempByte = this.fetchByteFromPacket(inputRefreshPacket, 10);
		controlPanel.stagingButton.setSP2TStatus( this.fetchBitInByte(tempByte, 1) );
		controlPanel.brakeButton.setSP2TStatus( this.fetchBitInByte(tempByte, 2) );
		//Module D
		tempByte = this.fetchByteFromPacket(inputRefreshPacket, 11);
		controlPanel.brakeSwitch.setStatus( this.fetchBitInByte(tempByte, 6) );
		//TODO Add remaining outputs

	}
	
	//Returns the integer stored in packet located at the specified byte numbers (see ICD).
	//byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	private int convertTwoBytesInPacketIntoInteger(byte[] packet, int byteNum1, int byteNum2) {//TODO
		
//		int largestByteNum = 0;
//		int smallestByteNum = 0;
//		
//		if (byteNum1 > byteNum2) {
//			largestByteNum = byteNum1 - 1;
//			smallestByteNum = byteNum2 - 1;
//		} else {
//			largestByteNum = byteNum2 - 1;
//			smallestByteNum = byteNum1 - 1;
//		}
//
//		char tempTwoByteArray[2];
//		tempTwoByteArray[0] = this->outputRefreshPacket[largestByteNum];
//		tempTwoByteArray[1] = this->outputRefreshPacket[smallestByteNum];
//		
//		return (*((int *)tempTwoByteArray));
		return 0;
	}
	
	//Returns the byte in packet located at the specified byte number. See "ICD" in OneNote.
	private byte fetchByteFromPacket(byte[] packet, int byteNumber) {
		return packet[byteNumber];
	}
	
	//Returns the bit located at bitNumber in theByte. See "ICD:KMega>KKIM" in Onenote.
	private boolean fetchBitInByte(byte theByte, int bitNumber) {
		int temp = 8 - bitNumber;
		int bit = (theByte >> temp) & 1;
		return (bit == 1);
	}
}



















