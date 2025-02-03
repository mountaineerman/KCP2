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
	private byte[] gaugePacketBuffer = new byte[KKIMProp.getkMegaGaugePacketLengthInBytes()];
	
	public PacketAssembler(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		Arrays.fill(this.outputRefreshPacketBuffer, KKIMProp.getallPacketsNullByte());
		Arrays.fill(this.gaugePacketBuffer, KKIMProp.getallPacketsNullByte());
	}
	
	//Assembles the status of the KKIM Model into an OutputRefreshPacket
	public byte[] assembleOutputRefreshPacket() {

		Arrays.fill(this.outputRefreshPacketBuffer, KKIMProp.getallPacketsNullByte());
		
		// (1) Populate Delimiter:
		for (int i = 0; i < KKIMProp.getallPacketsNumberOfDelimiterBytes(); i++) {
			this.outputRefreshPacketBuffer[i] = KKIMProp.getallPacketsDelimiterByte();
		}
		
		// (2) Populate Header:
		/* Originator */		this.saveByteToPacketBuffer(2, 1);
		/* Packet Type */		this.saveByteToPacketBuffer(2, 2);
		/* Packet Length */		this.saveByteToPacketBuffer((KKIMProp.getkMegaOutputRefreshPacketLengthInBytes() - KKIMProp.getallPacketsNumberOfDelimiterBytes()), 3);
		/* Requested Mode */	this.saveByteToPacketBuffer(0, 4);//TODO
		/* Command */			this.saveByteToPacketBuffer(0, 5);//TODO
		/* Parity Byte */		this.saveByteToPacketBuffer(0, 6);//TODO
		/* Empty */				this.saveByteToPacketBuffer(0, 7);
		/* Empty */				this.saveByteToPacketBuffer(0, 8);
		/* Empty */				this.saveByteToPacketBuffer(0, 9);
		
		// (3) Populate Payload:
		//LEDs
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.ModuleABrakeLED.firstByte, OP.ModuleABrakeLED.lastByte, controlPanel.moduleA.brakeLED.getPWM());
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Heat_Red.firstByte, OP.StepperLED_Heat_Red.lastByte, controlPanel.moduleC.stepperLED_Heat.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Heat_Green.firstByte, OP.StepperLED_Heat_Green.lastByte, controlPanel.moduleC.stepperLED_Heat.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Heat_Blue.firstByte, OP.StepperLED_Heat_Blue.lastByte, controlPanel.moduleC.stepperLED_Heat.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_LifeSupport_Red.firstByte, OP.StepperLED_LifeSupport_Red.lastByte, controlPanel.moduleC.stepperLED_LifeSupport.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_LifeSupport_Green.firstByte, OP.StepperLED_LifeSupport_Green.lastByte, controlPanel.moduleC.stepperLED_LifeSupport.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_LifeSupport_Blue.firstByte, OP.StepperLED_LifeSupport_Blue.lastByte, controlPanel.moduleC.stepperLED_LifeSupport.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_GForce_Red.firstByte, OP.StepperLED_GForce_Red.lastByte, controlPanel.moduleC.stepperLED_GForce.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_GForce_Green.firstByte, OP.StepperLED_GForce_Green.lastByte, controlPanel.moduleC.stepperLED_GForce.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_GForce_Blue.firstByte, OP.StepperLED_GForce_Blue.lastByte, controlPanel.moduleC.stepperLED_GForce.getBluPWMValue());
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.ModuleDBrakeLED.firstByte, OP.ModuleDBrakeLED.lastByte, controlPanel.moduleD.brakeLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoHoldLED.firstByte, OP.AutoHoldLED.lastByte, controlPanel.moduleD.autoHoldLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoProgradeLED.firstByte, OP.AutoProgradeLED.lastByte, controlPanel.moduleD.autoProgradeLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoRetrogradeLED.firstByte, OP.AutoRetrogradeLED.lastByte, controlPanel.moduleD.autoRetrogradeLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoNormalRedLED.firstByte, OP.AutoNormalRedLED.lastByte, controlPanel.moduleD.autoNormalRedLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoNormalBluLED.firstByte, OP.AutoNormalBluLED.lastByte, controlPanel.moduleD.autoNormalBluLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoAntiNormalRedLED.firstByte, OP.AutoAntiNormalRedLED.lastByte, controlPanel.moduleD.autoAntiNormalRedLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoAntiNormalBluLED.firstByte, OP.AutoAntiNormalBluLED.lastByte, controlPanel.moduleD.autoAntiNormalBluLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoRadialInGrnLED.firstByte, OP.AutoRadialInGrnLED.lastByte, controlPanel.moduleD.autoRadialInGrnLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoRadialInBluLED.firstByte, OP.AutoRadialInBluLED.lastByte, controlPanel.moduleD.autoRadialInBluLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoRadialOutGrnLED.firstByte, OP.AutoRadialOutGrnLED.lastByte, controlPanel.moduleD.autoRadialOutGrnLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoRadialOutBluLED.firstByte, OP.AutoRadialOutBluLED.lastByte, controlPanel.moduleD.autoRadialOutBluLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoTargetRedLED.firstByte, OP.AutoTargetRedLED.lastByte, controlPanel.moduleD.autoTargetRedLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoTargetBluLED.firstByte, OP.AutoTargetBluLED.lastByte, controlPanel.moduleD.autoTargetBluLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoAntiTargetRedLED.firstByte, OP.AutoAntiTargetRedLED.lastByte, controlPanel.moduleD.autoAntiTargetRedLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoAntiTargetBluLED.firstByte, OP.AutoAntiTargetBluLED.lastByte, controlPanel.moduleD.autoAntiTargetBluLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.AutoManeuverLED.firstByte, OP.AutoManeuverLED.lastByte, controlPanel.moduleD.autoManeuverLED.getPWM());
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.FairingLED.firstByte, OP.FairingLED.lastByte, controlPanel.moduleE.fairingLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.ParachuteLED.firstByte, OP.ParachuteLED.lastByte, controlPanel.moduleE.parachuteLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.SP3T_SpeedMode_ORB_LED.firstByte, OP.SP3T_SpeedMode_ORB_LED.lastByte, controlPanel.moduleE.sp3tSpeedModeSwitch.centerPositionLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.SP3T_VehicleMode_PLN_LED.firstByte, OP.SP3T_VehicleMode_PLN_LED.lastByte, controlPanel.moduleE.sp3tVehicleModeSwitch.centerPositionLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.SP3T_Pitch_30degLED.firstByte, OP.SP3T_Pitch_30degLED.lastByte, controlPanel.moduleE.sp3tPitchSwitch.centerPositionLED.getPWM());
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Sensitivity100PercentLED.firstByte, OP.Sensitivity100PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity100PercentLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Sensitivity75PercentLED.firstByte, OP.Sensitivity75PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity75PercentLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Sensitivity50PercentLED.firstByte, OP.Sensitivity50PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity50PercentLED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Sensitivity25PercentLED.firstByte, OP.Sensitivity25PercentLED.lastByte, controlPanel.moduleF.sensitivitySwitch.sensitivity25PercentLED.getPWM());
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Mach_Red.firstByte, OP.StepperLED_Mach_Red.lastByte, controlPanel.moduleG.stepperLED_Mach.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Mach_Green.firstByte, OP.StepperLED_Mach_Green.lastByte, controlPanel.moduleG.stepperLED_Mach.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Mach_Blue.firstByte, OP.StepperLED_Mach_Blue.lastByte, controlPanel.moduleG.stepperLED_Mach.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Pitch_Red.firstByte, OP.StepperLED_Pitch_Red.lastByte, controlPanel.moduleG.stepperLED_Pitch.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Pitch_Green.firstByte, OP.StepperLED_Pitch_Green.lastByte, controlPanel.moduleG.stepperLED_Pitch.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Pitch_Blue.firstByte, OP.StepperLED_Pitch_Blue.lastByte, controlPanel.moduleG.stepperLED_Pitch.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Heading_Red.firstByte, OP.StepperLED_Heading_Red.lastByte, controlPanel.moduleG.stepperLED_Heading.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Heading_Green.firstByte, OP.StepperLED_Heading_Green.lastByte, controlPanel.moduleG.stepperLED_Heading.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Heading_Blue.firstByte, OP.StepperLED_Heading_Blue.lastByte, controlPanel.moduleG.stepperLED_Heading.getBluPWMValue());
		//Note: Communications LED intentionally not sent. KMega directly controls it.
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.GlassCockpitLED_TL.firstByte, OP.GlassCockpitLED_TL.lastByte, controlPanel.moduleH.glassTL_LED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.GlassCockpitLED_CL.firstByte, OP.GlassCockpitLED_CL.lastByte, controlPanel.moduleH.glassCL_LED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.GlassCockpitLED_BL.firstByte, OP.GlassCockpitLED_BL.lastByte, controlPanel.moduleH.glassBL_LED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.GlassCockpitLED_TR.firstByte, OP.GlassCockpitLED_TR.lastByte, controlPanel.moduleH.glassTR_LED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.GlassCockpitLED_CR.firstByte, OP.GlassCockpitLED_CR.lastByte, controlPanel.moduleH.glassCR_LED.getPWM());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.GlassCockpitLED_BR.firstByte, OP.GlassCockpitLED_BR.lastByte, controlPanel.moduleH.glassBR_LED.getPWM());
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Red.firstByte, OP.StepperLED_Fuel_Red.lastByte, controlPanel.moduleI.stepperLED_Fuel.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Green.firstByte, OP.StepperLED_Fuel_Green.lastByte, controlPanel.moduleI.stepperLED_Fuel.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Fuel_Blue.firstByte, OP.StepperLED_Fuel_Blue.lastByte, controlPanel.moduleI.stepperLED_Fuel.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.DeltaChargeLED_Red.firstByte, OP.DeltaChargeLED_Red.lastByte, controlPanel.moduleI.stepperLED_deltaCharge.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.DeltaChargeLED_Green.firstByte, OP.DeltaChargeLED_Green.lastByte, controlPanel.moduleI.stepperLED_deltaCharge.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.DeltaChargeLED_Blue.firstByte, OP.DeltaChargeLED_Blue.lastByte, controlPanel.moduleI.stepperLED_deltaCharge.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Charge_Red.firstByte, OP.StepperLED_Charge_Red.lastByte, controlPanel.moduleI.stepperLED_Charge.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Charge_Green.firstByte, OP.StepperLED_Charge_Green.lastByte, controlPanel.moduleI.stepperLED_Charge.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Charge_Blue.firstByte, OP.StepperLED_Charge_Blue.lastByte, controlPanel.moduleI.stepperLED_Charge.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Monopropellant_Red.firstByte, OP.StepperLED_Monopropellant_Red.lastByte, controlPanel.moduleI.stepperLED_Monopropellant.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Monopropellant_Green.firstByte, OP.StepperLED_Monopropellant_Green.lastByte, controlPanel.moduleI.stepperLED_Monopropellant.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Monopropellant_Blue.firstByte, OP.StepperLED_Monopropellant_Blue.lastByte, controlPanel.moduleI.stepperLED_Monopropellant.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_IntakeAir_Red.firstByte, OP.StepperLED_IntakeAir_Red.lastByte, controlPanel.moduleI.stepperLED_IntakeAir.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_IntakeAir_Green.firstByte, OP.StepperLED_IntakeAir_Green.lastByte, controlPanel.moduleI.stepperLED_IntakeAir.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_IntakeAir_Blue.firstByte, OP.StepperLED_IntakeAir_Blue.lastByte, controlPanel.moduleI.stepperLED_IntakeAir.getBluPWMValue());
		
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_AirDensity_Red.firstByte, OP.StepperLED_AirDensity_Red.lastByte, controlPanel.moduleGT.stepperLED_AirDensity.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_AirDensity_Green.firstByte, OP.StepperLED_AirDensity_Green.lastByte, controlPanel.moduleGT.stepperLED_AirDensity.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_AirDensity_Blue.firstByte, OP.StepperLED_AirDensity_Blue.lastByte, controlPanel.moduleGT.stepperLED_AirDensity.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Speed_Red.firstByte, OP.StepperLED_Speed_Red.lastByte, controlPanel.moduleGT.stepperLED_Speed.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Speed_Green.firstByte, OP.StepperLED_Speed_Green.lastByte, controlPanel.moduleGT.stepperLED_Speed.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_Speed_Blue.firstByte, OP.StepperLED_Speed_Blue.lastByte, controlPanel.moduleGT.stepperLED_Speed.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_VerticalSpeed_Red.firstByte, OP.StepperLED_VerticalSpeed_Red.lastByte, controlPanel.moduleGT.stepperLED_VerticalSpeed.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_VerticalSpeed_Green.firstByte, OP.StepperLED_VerticalSpeed_Green.lastByte, controlPanel.moduleGT.stepperLED_VerticalSpeed.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_VerticalSpeed_Blue.firstByte, OP.StepperLED_VerticalSpeed_Blue.lastByte, controlPanel.moduleGT.stepperLED_VerticalSpeed.getBluPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_RadarAltitude_Red.firstByte, OP.StepperLED_RadarAltitude_Red.lastByte, controlPanel.moduleGT.stepperLED_RadarAltitude.getRedPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_RadarAltitude_Green.firstByte, OP.StepperLED_RadarAltitude_Green.lastByte, controlPanel.moduleGT.stepperLED_RadarAltitude.getGrnPWMValue());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.StepperLED_RadarAltitude_Blue.firstByte, OP.StepperLED_RadarAltitude_Blue.lastByte, controlPanel.moduleGT.stepperLED_RadarAltitude.getBluPWMValue());
		
		//Stepper Motors
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_HeatLife.firstByte, OP.Stepper_HeatLife.lastByte, controlPanel.moduleC.stepper_HeatLife.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_Gforce.firstByte, OP.Stepper_Gforce.lastByte, controlPanel.moduleC.stepper_Gforce.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_Mach.firstByte, OP.Stepper_Mach.lastByte, controlPanel.moduleG.stepper_Mach.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_Pitch.firstByte, OP.Stepper_Pitch.lastByte, controlPanel.moduleG.stepper_Pitch.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_Heading.firstByte, OP.Stepper_Heading.lastByte, controlPanel.moduleG.stepper_Heading.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_Fuel.firstByte, OP.Stepper_Fuel.lastByte, controlPanel.moduleI.stepper_Fuel.getDesiredPosition());

		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_Charge.firstByte, OP.Stepper_Charge.lastByte, controlPanel.moduleI.stepper_Charge.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_MonopropellantIntake.firstByte, OP.Stepper_MonopropellantIntake.lastByte, controlPanel.moduleI.stepper_MonopropellantIntake.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_AirDensity.firstByte, OP.Stepper_AirDensity.lastByte, controlPanel.moduleGT.stepper_AirDensity.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_Speed.firstByte, OP.Stepper_Speed.lastByte, controlPanel.moduleGT.stepper_Speed.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_VerticalSpeed.firstByte, OP.Stepper_VerticalSpeed.lastByte, controlPanel.moduleGT.stepper_VerticalSpeed.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(OP.Stepper_RadarAltitude.firstByte, OP.Stepper_RadarAltitude.lastByte, controlPanel.moduleGT.stepper_RadarAltitude.getDesiredPosition());
		
		//Altitude
		this.saveFloatToOutputRefreshPacketBufferAtByteNumbers(OP.Altitude.firstByte, OP.Altitude.lastByte, controlPanel.altitudeToDisplay);
		
		//this.displayOutputRefreshPacketBufferInDecimal();
		return this.outputRefreshPacketBuffer;//TODO return copy instead of original
	}
	
	public byte[] assembleGaugePacketA() {
        
		Arrays.fill(this.gaugePacketBuffer, KKIMProp.getallPacketsNullByte());
		
		// (1) Populate Delimiter:
		for (int i = 0; i < KKIMProp.getallPacketsNumberOfDelimiterBytes(); i++) {
			this.gaugePacketBuffer[i] = KKIMProp.getallPacketsDelimiterByte();
		}
		
		// (2) Populate Header:
		/* Originator */		this.saveByteToPacketBuffer(2, 1);
		/* Packet Type */		this.saveByteToPacketBuffer(6, 2);
		/* Packet Length */		this.saveByteToPacketBuffer((KKIMProp.getkMegaGaugePacketLengthInBytes() - KKIMProp.getallPacketsNumberOfDelimiterBytes()), 3);
		/* Requested Mode */	this.saveByteToPacketBuffer(0, 4);//TODO
		/* Command */			this.saveByteToPacketBuffer(0, 5);//TODO
		/* Parity Byte */		this.saveByteToPacketBuffer(0, 6);//TODO
		/* Empty */				this.saveByteToPacketBuffer(0, 7);
		/* Empty */				this.saveByteToPacketBuffer(0, 8);
		/* Empty */				this.saveByteToPacketBuffer(0, 9);
		
		// (3) Populate Payload:
		this.saveTwoByteIntToPacketBufferAtByteNumbers(10, 11, controlPanel.moduleC.stepper_HeatLife.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(12, 13, controlPanel.moduleC.stepper_Gforce.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(14, 15, controlPanel.moduleG.stepper_Mach.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(16, 17, controlPanel.moduleG.stepper_Pitch.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(18, 19, controlPanel.moduleG.stepper_Heading.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(20, 21, controlPanel.moduleI.stepper_Fuel.getDesiredPosition());

		return this.gaugePacketBuffer;
    }

	public byte[] assembleGaugePacketB() {
        
		Arrays.fill(this.gaugePacketBuffer, KKIMProp.getallPacketsNullByte());
		
		// (1) Populate Delimiter:
		for (int i = 0; i < KKIMProp.getallPacketsNumberOfDelimiterBytes(); i++) {
			this.gaugePacketBuffer[i] = KKIMProp.getallPacketsDelimiterByte();
		}
		
		// (2) Populate Header:
		/* Originator */		this.saveByteToPacketBuffer(2, 1);
		/* Packet Type */		this.saveByteToPacketBuffer(6, 2);
		/* Packet Length */		this.saveByteToPacketBuffer((KKIMProp.getkMegaGaugePacketLengthInBytes() - KKIMProp.getallPacketsNumberOfDelimiterBytes()), 3);
		/* Requested Mode */	this.saveByteToPacketBuffer(0, 4);//TODO
		/* Command */			this.saveByteToPacketBuffer(0, 5);//TODO
		/* Parity Byte */		this.saveByteToPacketBuffer(0, 6);//TODO
		/* Empty */				this.saveByteToPacketBuffer(0, 7);
		/* Empty */				this.saveByteToPacketBuffer(0, 8);
		/* Empty */				this.saveByteToPacketBuffer(0, 9);
		
		// (3) Populate Payload:
		this.saveTwoByteIntToPacketBufferAtByteNumbers(10, 11, controlPanel.moduleI.stepper_Charge.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(12, 13, controlPanel.moduleI.stepper_MonopropellantIntake.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(14, 15, controlPanel.moduleGT.stepper_AirDensity.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(16, 17, controlPanel.moduleGT.stepper_Speed.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(18, 19, controlPanel.moduleGT.stepper_VerticalSpeed.getDesiredPosition());
		this.saveTwoByteIntToPacketBufferAtByteNumbers(20, 21, controlPanel.moduleGT.stepper_RadarAltitude.getDesiredPosition());

		return this.gaugePacketBuffer;
    }

	@SuppressWarnings("unused")
	private void displayOutputRefreshPacketBufferInDecimal() {
		System.out.println("PacketAssembler: Displaying outputRefreshPacketBuffer in decimal format:");
		System.out.println(Arrays.toString(this.outputRefreshPacketBuffer));
	}
	
	/** Saves theByte to the specified byteNumber in the relevant packet. See "ICD" in OneNote.
	 * @param theByte number from 0-255
	 * @param byteNumber 1-indexed position in Header or Payload
	 */
	private void saveByteToPacketBuffer(int theByte, int byteNumber) {
		
		int position = byteNumber - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		
		if ( KKIMProp.getkMegaSendPacketType().equals("outputRefreshPacket") ) {
			this.outputRefreshPacketBuffer[position] = (byte) theByte;
		} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacketA") ||
					KKIMProp.getkMegaSendPacketType().equals("gaugePacketB") ) {
			this.gaugePacketBuffer[position] = (byte) theByte;
		} else {
			throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.getkMegaSendPacketType());
		}
	}
	
	/** Saves number value at the specified byte numbers (see ICD) to the relevant packet.
	 * byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	 */
	private void saveTwoByteIntToPacketBufferAtByteNumbers(int byteNum1, int byteNum2, int twoByteInteger) {
		
		int largeByteNum = 0;
		int smallByteNum = 0;
		
		if (byteNum1 > byteNum2) {
			largeByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		} else {
			largeByteNum = byteNum2 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
			smallByteNum = byteNum1 - 1 + KKIMProp.getallPacketsNumberOfDelimiterBytes();
		}
		
		if ( KKIMProp.getkMegaSendPacketType().equals("outputRefreshPacket") ) {
			this.outputRefreshPacketBuffer[smallByteNum] = (byte) (twoByteInteger & 0xFF);
			this.outputRefreshPacketBuffer[largeByteNum] = (byte) ((twoByteInteger >> 8) & 0xFF);
		} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacketA") ||
					KKIMProp.getkMegaSendPacketType().equals("gaugePacketB") ) {
			this.gaugePacketBuffer[smallByteNum] = (byte) (twoByteInteger & 0xFF);
			this.gaugePacketBuffer[largeByteNum] = (byte) ((twoByteInteger >> 8) & 0xFF);
		} else {
			throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.getkMegaSendPacketType());
		}
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