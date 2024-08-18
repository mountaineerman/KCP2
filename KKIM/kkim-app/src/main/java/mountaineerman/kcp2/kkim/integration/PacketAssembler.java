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
		
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Heat_Red.firstByte, OP.StepperLED_Heat_Red.lastByte, controlPanel.moduleC.stepperLED_Heat.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Heat_Green.firstByte, OP.StepperLED_Heat_Green.lastByte, controlPanel.moduleC.stepperLED_Heat.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Heat_Blue.firstByte, OP.StepperLED_Heat_Blue.lastByte, controlPanel.moduleC.stepperLED_Heat.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_LifeSupport_Red.firstByte, OP.StepperLED_LifeSupport_Red.lastByte, controlPanel.moduleC.stepperLED_LifeSupport.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_LifeSupport_Green.firstByte, OP.StepperLED_LifeSupport_Green.lastByte, controlPanel.moduleC.stepperLED_LifeSupport.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_LifeSupport_Blue.firstByte, OP.StepperLED_LifeSupport_Blue.lastByte, controlPanel.moduleC.stepperLED_LifeSupport.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_GForce_Red.firstByte, OP.StepperLED_GForce_Red.lastByte, controlPanel.moduleC.stepperLED_GForce.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_GForce_Green.firstByte, OP.StepperLED_GForce_Green.lastByte, controlPanel.moduleC.stepperLED_GForce.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_GForce_Blue.firstByte, OP.StepperLED_GForce_Blue.lastByte, controlPanel.moduleC.stepperLED_GForce.getBluPWMValue());
		
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
		
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Mach_Red.firstByte, OP.StepperLED_Mach_Red.lastByte, controlPanel.moduleG.stepperLED_Mach.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Mach_Green.firstByte, OP.StepperLED_Mach_Green.lastByte, controlPanel.moduleG.stepperLED_Mach.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Mach_Blue.firstByte, OP.StepperLED_Mach_Blue.lastByte, controlPanel.moduleG.stepperLED_Mach.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Pitch_Red.firstByte, OP.StepperLED_Pitch_Red.lastByte, controlPanel.moduleG.stepperLED_Pitch.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Pitch_Green.firstByte, OP.StepperLED_Pitch_Green.lastByte, controlPanel.moduleG.stepperLED_Pitch.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Pitch_Blue.firstByte, OP.StepperLED_Pitch_Blue.lastByte, controlPanel.moduleG.stepperLED_Pitch.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Heading_Red.firstByte, OP.StepperLED_Heading_Red.lastByte, controlPanel.moduleG.stepperLED_Heading.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Heading_Green.firstByte, OP.StepperLED_Heading_Green.lastByte, controlPanel.moduleG.stepperLED_Heading.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Heading_Blue.firstByte, OP.StepperLED_Heading_Blue.lastByte, controlPanel.moduleG.stepperLED_Heading.getBluPWMValue());
		//Note: Communications LED intentionally not sent. KMega directly controls it.
		
		//TODO Add remaining LEDs: Module H
		
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Red.firstByte, OP.StepperLED_Fuel_Red.lastByte, controlPanel.moduleI.stepperLED_Fuel.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Green.firstByte, OP.StepperLED_Fuel_Green.lastByte, controlPanel.moduleI.stepperLED_Fuel.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Blue.firstByte, OP.StepperLED_Fuel_Blue.lastByte, controlPanel.moduleI.stepperLED_Fuel.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.DeltaChargeLED_Red.firstByte, OP.DeltaChargeLED_Red.lastByte, controlPanel.moduleI.stepperLED_deltaCharge.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.DeltaChargeLED_Green.firstByte, OP.DeltaChargeLED_Green.lastByte, controlPanel.moduleI.stepperLED_deltaCharge.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.DeltaChargeLED_Blue.firstByte, OP.DeltaChargeLED_Blue.lastByte, controlPanel.moduleI.stepperLED_deltaCharge.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Charge_Red.firstByte, OP.StepperLED_Charge_Red.lastByte, controlPanel.moduleI.stepperLED_Charge.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Charge_Green.firstByte, OP.StepperLED_Charge_Green.lastByte, controlPanel.moduleI.stepperLED_Charge.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Charge_Blue.firstByte, OP.StepperLED_Charge_Blue.lastByte, controlPanel.moduleI.stepperLED_Charge.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Monopropellant_Red.firstByte, OP.StepperLED_Monopropellant_Red.lastByte, controlPanel.moduleI.stepperLED_Monopropellant.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Monopropellant_Green.firstByte, OP.StepperLED_Monopropellant_Green.lastByte, controlPanel.moduleI.stepperLED_Monopropellant.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Monopropellant_Blue.firstByte, OP.StepperLED_Monopropellant_Blue.lastByte, controlPanel.moduleI.stepperLED_Monopropellant.getBluPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_IntakeAir_Red.firstByte, OP.StepperLED_IntakeAir_Red.lastByte, controlPanel.moduleI.stepperLED_IntakeAir.getRedPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_IntakeAir_Green.firstByte, OP.StepperLED_IntakeAir_Green.lastByte, controlPanel.moduleI.stepperLED_IntakeAir.getGrnPWMValue());
		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_IntakeAir_Blue.firstByte, OP.StepperLED_IntakeAir_Blue.lastByte, controlPanel.moduleI.stepperLED_IntakeAir.getBluPWMValue());
		
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_AirDensity_Red.firstByte, OP.StepperLED_AirDensity_Red.lastByte, controlPanel.moduleGT.stepperLED_AirDensity.getRedPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_AirDensity_Green.firstByte, OP.StepperLED_AirDensity_Green.lastByte, controlPanel.moduleGT.stepperLED_AirDensity.getGrnPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_AirDensity_Blue.firstByte, OP.StepperLED_AirDensity_Blue.lastByte, controlPanel.moduleGT.stepperLED_AirDensity.getBluPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Speed_Red.firstByte, OP.StepperLED_Speed_Red.lastByte, controlPanel.moduleGT.stepperLED_Speed.getRedPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Speed_Green.firstByte, OP.StepperLED_Speed_Green.lastByte, controlPanel.moduleGT.stepperLED_Speed.getGrnPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_Speed_Blue.firstByte, OP.StepperLED_Speed_Blue.lastByte, controlPanel.moduleGT.stepperLED_Speed.getBluPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_VerticalSpeed_Red.firstByte, OP.StepperLED_VerticalSpeed_Red.lastByte, controlPanel.moduleGT.stepperLED_VerticalSpeed.getRedPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_VerticalSpeed_Green.firstByte, OP.StepperLED_VerticalSpeed_Green.lastByte, controlPanel.moduleGT.stepperLED_VerticalSpeed.getGrnPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_VerticalSpeed_Blue.firstByte, OP.StepperLED_VerticalSpeed_Blue.lastByte, controlPanel.moduleGT.stepperLED_VerticalSpeed.getBluPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_RadarAltitude_Red.firstByte, OP.StepperLED_RadarAltitude_Red.lastByte, controlPanel.moduleGT.stepperLED_RadarAltitude.getRedPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_RadarAltitude_Green.firstByte, OP.StepperLED_RadarAltitude_Green.lastByte, controlPanel.moduleGT.stepperLED_RadarAltitude.getGrnPWMValue());
//		this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.StepperLED_RadarAltitude_Blue.firstByte, OP.StepperLED_RadarAltitude_Blue.lastByte, controlPanel.moduleGT.stepperLED_RadarAltitude.getBluPWMValue());
		
		
		//Stepper Motors
		//this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Stepper_Gforce.firstByte, OP.Stepper_Gforce.lastByte, controlPanel.moduleC.stepper_Gforce.getDesiredPosition());
		//this.saveTwoByteIntToOutputRefreshPacketBufferAtByteNumbers(OP.Stepper_Fuel.firstByte, OP.Stepper_Fuel.lastByte, controlPanel.moduleI.stepper_Fuel.getDesiredPosition());
		
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

















