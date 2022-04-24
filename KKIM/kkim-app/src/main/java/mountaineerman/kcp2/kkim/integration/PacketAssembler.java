package mountaineerman.kcp2.kkim.integration;

import java.util.Arrays;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;
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
		//LEDs
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.ModuleABrakeLED.firstByte, OP.ModuleABrakeLED.lastByte, controlPanel.moduleA.brakeLED.getPWM());
		
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.ModuleDBrakeLED.firstByte, OP.ModuleDBrakeLED.lastByte, controlPanel.moduleD.brakeLED.getPWM());
		//TODO Add remaining LEDs: Module D
		
		//TODO Add remaining LEDs: Module E
		
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.Sensitivity100PercentLED.firstByte, OP.Sensitivity100PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity100PercentLED.getPWM());
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.Sensitivity75PercentLED.firstByte, OP.Sensitivity75PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity75PercentLED.getPWM());
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.Sensitivity50PercentLED.firstByte, OP.Sensitivity50PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity50PercentLED.getPWM());
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.Sensitivity25PercentLED.firstByte, OP.Sensitivity25PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity25PercentLED.getPWM());
		
		//TODO Add remaining LEDs: Module G
		
		//TODO Add remaining LEDs: Module H
		
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.StepperLED_Fuel_Green.firstByte, OP.StepperLED_Fuel_Green.lastByte, controlPanel.moduleI.stepperLED_Fuel_Green.getPWM());//TODO replace with RGB_PWM_LED
		//TODO Add remaining LEDs
		//Stepper Motors
		this.saveNumberAtByteNumbersToOutputRefreshPacketBuffer(OP.Stepper_Gforce.firstByte, OP.Stepper_Gforce.lastByte, controlPanel.moduleC.stepper_Gforce.getDesiredPosition());
		
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
	
	//Saves number value at the specified byte numbers (see ICD).
	//byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	private void saveNumberAtByteNumbersToOutputRefreshPacketBuffer(int byteNum1, int byteNum2, int number) {
		
		int largeByteNum = 0;
		int smallByteNum = 0;
		
		if (byteNum1 > byteNum2) {
			largeByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		} else {
			largeByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		}
		
		this.outputRefreshPacketBuffer[smallByteNum] = (byte) (number & 0xFF);
		this.outputRefreshPacketBuffer[largeByteNum] = (byte) ((number >> 8) & 0xFF);
	}
}

















