package mountaineerman.kcp2.kkim.integration;

import java.util.Arrays;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.model.ControlPanel;

/* Packet Assembler
 * Responsible for reading the relevant parts of the KKIM Model and assembling
 * them into an OutputRefreshPacket.
 */
public class PacketAssembler {

	private ControlPanel controlPanel;
	private byte[] outputRefreshPacketBuffer = new byte[KKIMProp.getkMegaOutputRefreshPacketLengthInBytes()];
	
	public PacketAssembler(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		Arrays.fill(this.outputRefreshPacketBuffer, KKIMProp.getallPacketsNullByte());
	}
	
	//Assembles the status of the KKIM Model into an OutputRefreshPacket
	public byte[] assembleOutputRefreshPacket() {

		Arrays.fill(this.outputRefreshPacketBuffer, KKIMProp.getallPacketsNullByte());
		
		// (1) Populate Delimiter:
		for (int i = 0; i < KKIMProp.getallPacketsNumberOfDelimiterBytes(); i++) {
			this.outputRefreshPacketBuffer[i] = KKIMProp.getallPacketsDelimiterByte();
		}
		
		// (2) Populate Header:
		/* Originator */		this.saveByteToOutputRefreshPacketBuffer(2, 1);
		/* Packet Type */		this.saveByteToOutputRefreshPacketBuffer(2, 2);
		/* Packet Length */		this.saveByteToOutputRefreshPacketBuffer((KKIMProp.getkMegaOutputRefreshPacketLengthInBytes() - KKIMProp.getallPacketsNumberOfDelimiterBytes()), 3);
		/* Requested Mode */	this.saveByteToOutputRefreshPacketBuffer(0, 4);//TODO
		/* Command */			this.saveByteToOutputRefreshPacketBuffer(0, 5);//TODO
		/* Parity Byte */		this.saveByteToOutputRefreshPacketBuffer(0, 6);//TODO
		/* Empty */				this.saveByteToOutputRefreshPacketBuffer(0, 7);
		/* Empty */				this.saveByteToOutputRefreshPacketBuffer(0, 8);
		/* Empty */				this.saveByteToOutputRefreshPacketBuffer(0, 9);
		
		// (3) Populate Payload:
		this.savePWMAtByteNumbersInOutputRefreshPacketBuffer(10, 11, controlPanel.moduleA.brakeLED.getPWM());
		this.savePWMAtByteNumbersInOutputRefreshPacketBuffer(12, 13, controlPanel.moduleD.brakeLED.getPWM());
		//TODO Add remaining outputs
		this.savePWMAtByteNumbersInOutputRefreshPacketBuffer(116, 117, controlPanel.moduleI.stepperLED_Fuel_Green.getPWM());//TODO replace with RGB_PWM_LED
		//TODO Add remaining outputs
		//TODO Use OutputRefreshPacketStructure enum instead of hardcoding?
		
		//this.displayOutputRefreshPacketBufferInDecimal();
		return this.outputRefreshPacketBuffer;//TODO return copy instead of original
	}
	
	private void displayOutputRefreshPacketBufferInDecimal() {
		System.out.println("PacketAssembler: Displaying outputRefreshPacketBuffer in decimal format:");
		System.out.println(Arrays.toString(this.outputRefreshPacketBuffer));
	}
	
	//Saves theByte to the specified byteNumber in the outputRefreshPacketBuffer. See "ICD" in OneNote.
	private void saveByteToOutputRefreshPacketBuffer(int theByte, int byteNumber) {
		this.outputRefreshPacketBuffer[byteNumber - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes()] = (byte) theByte;
	}
	
	//Saves LED PWM value at the specified byte numbers (see ICD).
	//byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	private void savePWMAtByteNumbersInOutputRefreshPacketBuffer(int byteNum1, int byteNum2, int PWM) {
		
		int largeByteNum = 0;
		int smallByteNum = 0;
		
		if (byteNum1 > byteNum2) {
			largeByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		} else {
			largeByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		}
		
		this.outputRefreshPacketBuffer[smallByteNum] = (byte) (PWM & 0xFF);
		this.outputRefreshPacketBuffer[largeByteNum] = (byte) ((PWM >> 8) & 0xFF);
	}
}

















