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

		controlPanel.moduleA.stagingButton.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.StagingButton.firstByte, IP.StagingButton.bitNumber));
		controlPanel.moduleA.brakeButton.setStatus(				this.fetchBitInByteInPacket(inputRefreshPacket, IP.BrakeButton.firstByte, IP.BrakeButton.bitNumber));
		
		controlPanel.moduleB.abortButton.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.AbortButton.firstByte, IP.AbortButton.bitNumber));
		controlPanel.moduleB.timeWarpUpButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.TimeWarpUpButton.firstByte, IP.TimeWarpUpButton.bitNumber));
		controlPanel.moduleB.timeWarpDownButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.TimeWarpDownButton.firstByte, IP.TimeWarpDownButton.bitNumber));
		controlPanel.moduleB.joystickButton.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.JoystickButton.firstByte, IP.JoystickButton.bitNumber));
		controlPanel.moduleB.trimPitchSwitch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.TrimPitchSwitch.firstByte, IP.TrimPitchSwitch.bitNumber));
		controlPanel.moduleB.trimYawSwitch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.TrimYawSwitch.firstByte, IP.TrimYawSwitch.bitNumber));
		controlPanel.moduleB.trimRollSwitch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.TrimRollSwitch.firstByte, IP.TrimRollSwitch.bitNumber));
		
		controlPanel.moduleD.sasSwitch.setStatus(				this.fetchBitInByteInPacket(inputRefreshPacket, IP.SAS_Switch.firstByte, IP.SAS_Switch.bitNumber));
		controlPanel.moduleD.rcsSwitch.setStatus(				this.fetchBitInByteInPacket(inputRefreshPacket, IP.RCS_Switch.firstByte, IP.RCS_Switch.bitNumber));
		controlPanel.moduleD.lightsSwitch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.LightsSwitch.firstByte, IP.LightsSwitch.bitNumber));
		controlPanel.moduleD.gearSwitch.setStatus(				this.fetchBitInByteInPacket(inputRefreshPacket, IP.GearSwitch.firstByte, IP.GearSwitch.bitNumber));
		controlPanel.moduleD.brakeSwitch.setStatus(				this.fetchBitInByteInPacket(inputRefreshPacket, IP.BrakeSwitch.firstByte, IP.BrakeSwitch.bitNumber));
		controlPanel.moduleD.mapSwitch.setStatus(				this.fetchBitInByteInPacket(inputRefreshPacket, IP.MapSwitch.firstByte, IP.MapSwitch.bitNumber));
		controlPanel.moduleD.muteSwitch.setStatus(				this.fetchBitInByteInPacket(inputRefreshPacket, IP.MuteSwitch.firstByte, IP.MuteSwitch.bitNumber));
		controlPanel.moduleD.autoHoldButton.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoHoldButton.firstByte, IP.AutoHoldButton.bitNumber));
		controlPanel.moduleD.autoProgradeButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoProgradeButton.firstByte, IP.AutoProgradeButton.bitNumber));
		controlPanel.moduleD.autoRetrogradeButton.setSP2TStatus(this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoRetrogradeButton.firstByte, IP.AutoRetrogradeButton.bitNumber));
		controlPanel.moduleD.autoNormalButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoNormalButton.firstByte, IP.AutoNormalButton.bitNumber));
		controlPanel.moduleD.autoAntiNormalButton.setSP2TStatus(this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoAntiNormalButton.firstByte, IP.AutoAntiNormalButton.bitNumber));
		controlPanel.moduleD.autoRadialInButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoRadialInButton.firstByte, IP.AutoRadialInButton.bitNumber));
		controlPanel.moduleD.autoRadialOutButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoRadialOutButton.firstByte, IP.AutoRadialOutButton.bitNumber));
		controlPanel.moduleD.autoTargetButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoTargetButton.firstByte, IP.AutoTargetButton.bitNumber));
		controlPanel.moduleD.autoAntiTargetButton.setSP2TStatus(this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoAntiTargetButton.firstByte, IP.AutoAntiTargetButton.bitNumber));
		controlPanel.moduleD.autoManeuverButton.setSP2TStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.AutoManeuverButton.firstByte, IP.AutoManeuverButton.bitNumber));
		
		controlPanel.moduleE.sp3t_SFC_Switch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.SP3T_SFC_Switch.firstByte, IP.SP3T_SFC_Switch.bitNumber));
		controlPanel.moduleE.sp3t_TGT_Switch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.SP3T_TGT_Switch.firstByte, IP.SP3T_TGT_Switch.bitNumber));
		controlPanel.moduleE.sp3t_RKT_Switch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.SP3T_RKT_Switch.firstByte, IP.SP3T_RKT_Switch.bitNumber));
		controlPanel.moduleE.sp3t_RVR_Switch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.SP3T_RVR_Switch.firstByte, IP.SP3T_RVR_Switch.bitNumber));
		controlPanel.moduleE.sp3t_90degSwitch.setStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.SP3T_90degSwitch.firstByte, IP.SP3T_90degSwitch.bitNumber));
		controlPanel.moduleE.sp3t_9degSwitch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.SP3T_9degSwitch.firstByte, IP.SP3T_9degSwitch.bitNumber));
		controlPanel.moduleE.ag1Switch.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.Ag1Switch.firstByte, IP.Ag1Switch.bitNumber));
		controlPanel.moduleE.ag2Switch.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.Ag2Switch.firstByte, IP.Ag2Switch.bitNumber));
		controlPanel.moduleE.ag3Switch.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.Ag3Switch.firstByte, IP.Ag3Switch.bitNumber));
		controlPanel.moduleE.scienceSwitch.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.ScienceSwitch.firstByte, IP.ScienceSwitch.bitNumber));
		controlPanel.moduleE.resetSwitch.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.ResetSwitch.firstByte, IP.ResetSwitch.bitNumber));
		controlPanel.moduleE.solarSwitch.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.SolarSwitch.firstByte, IP.SolarSwitch.bitNumber));
		controlPanel.moduleE.ladderSwitch.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.LadderSwitch.firstByte, IP.LadderSwitch.bitNumber));
		controlPanel.moduleE.atnvSwitch.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.ATNV_Switch.firstByte, IP.ATNV_Switch.bitNumber));
		controlPanel.moduleE.fairingButton.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.FairingButton.firstByte, IP.FairingButton.bitNumber));
		controlPanel.moduleE.chuteButton.setSP2TStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.ChuteButton.firstByte, IP.ChuteButton.bitNumber));
		
		controlPanel.moduleF.trimPrimarySwitch.setStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.TrimPrimarySwitch.firstByte, IP.TrimPrimarySwitch.bitNumber));
		controlPanel.moduleF.sensitivitySwitch.setsensorABStatus(this.fetchBitInByteInPacket(inputRefreshPacket, IP.SensitivitySwitch_AB.firstByte, IP.SensitivitySwitch_AB.bitNumber));
		controlPanel.moduleF.sensitivitySwitch.setsensorCDStatus(this.fetchBitInByteInPacket(inputRefreshPacket, IP.SensitivitySwitch_CD.firstByte, IP.SensitivitySwitch_CD.bitNumber));
		
		controlPanel.moduleG.heatLifeSwitch.setStatus(			this.fetchBitInByteInPacket(inputRefreshPacket, IP.HeatLifeSwitch.firstByte, IP.HeatLifeSwitch.bitNumber));
		
		controlPanel.moduleH.glassTL_Button.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.GlassTL_Button.firstByte, IP.GlassTL_Button.bitNumber));
		controlPanel.moduleH.glassCL_Button.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.GlassCL_Button.firstByte, IP.GlassCL_Button.bitNumber));
		controlPanel.moduleH.glassBL_Button.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.GlassBL_Button.firstByte, IP.GlassBL_Button.bitNumber));
		controlPanel.moduleH.glassTR_Button.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.GlassTR_Button.firstByte, IP.GlassTR_Button.bitNumber));
		controlPanel.moduleH.glassCR_Button.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.GlassCR_Button.firstByte, IP.GlassCR_Button.bitNumber));
		controlPanel.moduleH.glassBR_Button.setSP2TStatus(		this.fetchBitInByteInPacket(inputRefreshPacket, IP.GlassBR_Button.firstByte, IP.GlassBR_Button.bitNumber));
		
		controlPanel.moduleI.monopropIntakeSwitch.setStatus(	this.fetchBitInByteInPacket(inputRefreshPacket, IP.MonopropIntakeSwitch.firstByte, IP.MonopropIntakeSwitch.bitNumber));
		
		controlPanel.moduleA.analogInput_Throttle.setRawValueAndCalculateBoundAndCalibratedValues(		this.convertTwoBytesInPacketIntoInteger(inputRefreshPacket, IP.AnalogInput_Throttle.firstByte, IP.AnalogInput_Throttle.lastByte));
		controlPanel.moduleB.analogInput_Joystick_FwdBck.setRawValueAndCalculateBoundAndCalibratedValues(this.convertTwoBytesInPacketIntoInteger(inputRefreshPacket, IP.AnalogInput_Joystick_FwdBck.firstByte, IP.AnalogInput_Joystick_FwdBck.lastByte));
		controlPanel.moduleB.analogInput_Joystick_LftRgh.setRawValueAndCalculateBoundAndCalibratedValues(	this.convertTwoBytesInPacketIntoInteger(inputRefreshPacket, IP.AnalogInput_Joystick_LftRgh.firstByte, IP.AnalogInput_Joystick_LftRgh.lastByte));
		controlPanel.moduleB.analogInput_Joystick_Twist.setRawValueAndCalculateBoundAndCalibratedValues(	this.convertTwoBytesInPacketIntoInteger(inputRefreshPacket, IP.AnalogInput_Joystick_Twist.firstByte, IP.AnalogInput_Joystick_Twist.lastByte));
		controlPanel.moduleF.analogInput_MultiPot.setRawValueAndCalculateBoundAndCalibratedValues(		this.convertTwoBytesInPacketIntoInteger(inputRefreshPacket, IP.AnalogInput_MultiPot.firstByte, IP.AnalogInput_MultiPot.lastByte));
		controlPanel.moduleF.analogInput_Current.setRawValueAndCalculateBoundAndCalibratedValues(		this.convertTwoBytesInPacketIntoInteger(inputRefreshPacket, IP.AnalogInput_Current.firstByte, IP.AnalogInput_Current.lastByte));
	}
	
	//Returns the integer stored in packet located at the specified byte numbers (see ICD).
	//byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Onenote). Byte numbers can be provided in any order.
	private int convertTwoBytesInPacketIntoInteger(byte[] packet, int byteNum1, int byteNum2) {
		
		int largestByteNum = 0;
		int smallestByteNum = 0;
		
		if (byteNum1 > byteNum2) {
			largestByteNum = byteNum1 - 1;
			smallestByteNum = byteNum2 - 1;
		} else {
			largestByteNum = byteNum2 - 1;
			smallestByteNum = byteNum1 - 1;
		}

		byte[] tempTwoByteArray = new byte[2];
		tempTwoByteArray[0] = packet[largestByteNum];
		tempTwoByteArray[1] = packet[smallestByteNum];
		
		return ((tempTwoByteArray[0] & 0xff) << 8) | (tempTwoByteArray[1] & 0xff);
		//return ByteBuffer.wrap(tempTwoByteArray).getInt();
		//return (*((int *)tempTwoByteArray)); //TODO remove
	}

	//Extracts the byte at byteNumber (See Onenote: "ICD:KMega>KKIM"). Returns the bit located at bitNumber in the byte.
	private boolean fetchBitInByteInPacket(byte[] packet, int byteNumber, int bitNumber) {
		byte tempByte = packet[byteNumber-1];
		int temp = 8 - bitNumber;
		int bit = (tempByte >> temp) & 1;
		return (bit == 1);
	}
}



















