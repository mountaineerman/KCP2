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
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.ModuleABrakeLED.firstByte, OP.ModuleABrakeLED.lastByte, controlPanel.moduleA.brakeLED.getPWM());
		
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.ModuleDBrakeLED.firstByte, OP.ModuleDBrakeLED.lastByte, controlPanel.moduleD.brakeLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoHoldLED.firstByte, OP.AutoHoldLED.lastByte, controlPanel.moduleD.autoHoldLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoProgradeLED.firstByte, OP.AutoProgradeLED.lastByte, controlPanel.moduleD.autoProgradeLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoRetrogradeLED.firstByte, OP.AutoRetrogradeLED.lastByte, controlPanel.moduleD.autoRetrogradeLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoNormalRedLED.firstByte, OP.AutoNormalRedLED.lastByte, controlPanel.moduleD.autoNormalRedLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoNormalBluLED.firstByte, OP.AutoNormalBluLED.lastByte, controlPanel.moduleD.autoNormalBluLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoAntiNormalRedLED.firstByte, OP.AutoAntiNormalRedLED.lastByte, controlPanel.moduleD.autoAntiNormalRedLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoAntiNormalBluLED.firstByte, OP.AutoAntiNormalBluLED.lastByte, controlPanel.moduleD.autoAntiNormalBluLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoRadialInGrnLED.firstByte, OP.AutoRadialInGrnLED.lastByte, controlPanel.moduleD.autoRadialInGrnLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoRadialInBluLED.firstByte, OP.AutoRadialInBluLED.lastByte, controlPanel.moduleD.autoRadialInBluLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoRadialOutGrnLED.firstByte, OP.AutoRadialOutGrnLED.lastByte, controlPanel.moduleD.autoRadialOutGrnLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoRadialOutBluLED.firstByte, OP.AutoRadialOutBluLED.lastByte, controlPanel.moduleD.autoRadialOutBluLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoTargetRedLED.firstByte, OP.AutoTargetRedLED.lastByte, controlPanel.moduleD.autoTargetRedLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoTargetBluLED.firstByte, OP.AutoTargetBluLED.lastByte, controlPanel.moduleD.autoTargetBluLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoAntiTargetRedLED.firstByte, OP.AutoAntiTargetRedLED.lastByte, controlPanel.moduleD.autoAntiTargetRedLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoAntiTargetBluLED.firstByte, OP.AutoAntiTargetBluLED.lastByte, controlPanel.moduleD.autoAntiTargetBluLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.AutoManeuverLED.firstByte, OP.AutoManeuverLED.lastByte, controlPanel.moduleD.autoManeuverLED.getPWM());
		
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.FairingLED.firstByte, OP.FairingLED.lastByte, controlPanel.moduleE.fairingLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.ParachuteLED.firstByte, OP.ParachuteLED.lastByte, controlPanel.moduleE.parachuteLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.SP3T_SpeedMode_ORB_LED.firstByte, OP.SP3T_SpeedMode_ORB_LED.lastByte, controlPanel.moduleE.sp3tSpeedModeSwitch.centerPositionLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.SP3T_VehicleMode_PLN_LED.firstByte, OP.SP3T_VehicleMode_PLN_LED.lastByte, controlPanel.moduleE.sp3tVehicleModeSwitch.centerPositionLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.SP3T_Pitch_30degLED.firstByte, OP.SP3T_Pitch_30degLED.lastByte, controlPanel.moduleE.sp3tPitchSwitch.centerPositionLED.getPWM());
		
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Sensitivity100PercentLED.firstByte, OP.Sensitivity100PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity100PercentLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Sensitivity75PercentLED.firstByte, OP.Sensitivity75PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity75PercentLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Sensitivity50PercentLED.firstByte, OP.Sensitivity50PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity50PercentLED.getPWM());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Sensitivity25PercentLED.firstByte, OP.Sensitivity25PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity25PercentLED.getPWM());
		
		//TODO Add remaining LEDs: Module G
		
		//TODO Add remaining LEDs: Module H
		
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Red.firstByte, OP.StepperLED_Fuel_Red.lastByte, controlPanel.moduleI.stepperLED_Fuel_Red.getPWM());//TODO replace with RGB_PWM_LED
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Green.firstByte, OP.StepperLED_Fuel_Green.lastByte, controlPanel.moduleI.stepperLED_Fuel_Green.getPWM());//TODO replace with RGB_PWM_LED
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Blue.firstByte, OP.StepperLED_Fuel_Blue.lastByte, controlPanel.moduleI.stepperLED_Fuel_Blue.getPWM());//TODO replace with RGB_PWM_LED
		//TODO Add remaining LEDs
		
		//Stepper Motors
		//this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Stepper_Gforce.firstByte, OP.Stepper_Gforce.lastByte, controlPanel.moduleC.stepper_Gforce.getDesiredPosition());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Stepper_Fuel.firstByte, OP.Stepper_Fuel.lastByte, controlPanel.moduleI.stepper_Fuel.getDesiredPosition());
		
		//Altitude
		this.saveFloatToOutputRefreshPacketBufferAtByteNumbers(OP.Altitude.firstByte, OP.Altitude.lastByte, controlPanel.altitudeToDisplay);
		
		//this.displayOutputRefreshPacketBufferInDecimal();
		return this.outputRefreshPacketBuffer;//TODO return copy instead of original
	}
	
	@SuppressWarnings("unused")
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
	private void saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(int byteNum1, int byteNum2, int twoByteInteger) {
		
		int largeByteNum = 0;
		int smallByteNum = 0;
		
		if (byteNum1 > byteNum2) {
			largeByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		} else {
			largeByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		}
		
		this.outputRefreshPacketBuffer[smallByteNum] = (byte) (twoByteInteger & 0xFF);
		this.outputRefreshPacketBuffer[largeByteNum] = (byte) ((twoByteInteger >> 8) & 0xFF);
	}
	
	//Saves float at the specified byte numbers (see ICD).
	//byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	private void saveFloatToOutputRefreshPacketBufferAtByteNumbers(int byteNum1, int byteNum2, float theFloat) {
		
		int largeByteNum = 0;
		int smallByteNum = 0;
		
		if (byteNum1 > byteNum2) {
			largeByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		} else {
			largeByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		}
		
		int intBits =  Float.floatToIntBits(theFloat);
		this.outputRefreshPacketBuffer[smallByteNum]   = (byte) (intBits >> 24);
		this.outputRefreshPacketBuffer[smallByteNum+1] = (byte) (intBits >> 16);
		this.outputRefreshPacketBuffer[smallByteNum+2] = (byte) (intBits >> 8);
		this.outputRefreshPacketBuffer[largeByteNum]   = (byte) (intBits);
	}
}

















