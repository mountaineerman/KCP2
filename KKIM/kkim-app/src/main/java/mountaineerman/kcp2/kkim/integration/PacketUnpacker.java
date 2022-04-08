package mountaineerman.kcp2.kkim.integration;

import java.util.Arrays;

import mountaineerman.kcp2.kkim.IP;
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

		controlPanel.moduleA.stagingButton.setSP2TStatus(	fetchBitInByteInPacket(inputRefreshPacket, IP.StagingButton.firstByte, IP.StagingButton.bitNumber));
		controlPanel.moduleA.brakeButton.setStatus(			fetchBitInByteInPacket(inputRefreshPacket, IP.BrakeButton.firstByte, IP.BrakeButton.bitNumber));
		
		controlPanel.moduleD.brakeSwitch.setStatus(			fetchBitInByteInPacket(inputRefreshPacket, IP.BrakeSwitch.firstByte, IP.BrakeSwitch.bitNumber));
		//TODO Add remaining inputs
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

	//Extracts the byte at byteNumber (See Onenote: "ICD:KMega>KKIM"). Returns the bit located at bitNumber in the byte.
	private boolean fetchBitInByteInPacket(byte[] packet, int byteNumber, int bitNumber) {
		byte tempByte = packet[byteNumber-1];
		int temp = 8 - bitNumber;
		int bit = (tempByte >> temp) & 1;
		return (bit == 1);
	}
}



















