#include <Arduino.h>
#include <PacketAssembler.h>
#include "../../configuration.h"


PacketAssembler::PacketAssembler(ControlPanel& controlPanel)
	: controlPanel(controlPanel)
{
	this->inputRefreshPacket = NULL;
}

void PacketAssembler::setInputRefreshPacket(const byte * inputRefreshPacket) {
	this->inputRefreshPacket = inputRefreshPacket;
}

void PacketAssembler::displayInputRefreshPacketInDecimal() {
	
	if (this->inputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println("Exception: PacketAssembler.displayInputRefreshPacketInDecimal(): inputRefreshPacket is not initialized");
	}
	
	Serial.println("PacketAssembler: inputRefreshPacket: (Decimal Format)");
	Serial.print("Position: N/A\tN/A\tN/A\t");
	for (int i = 0; i < (INPUT_REFRESH_PACKET_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES); i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	Serial.print("Value:    ");
	for (int i = 0; i < INPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(this->inputRefreshPacket[i]);
		Serial.print("\t");
	}
	Serial.println();
}

void PacketAssembler::assembleInputRefreshPacket() {
	
	//this->displayInputRefreshPacketInDecimal();
	
	if (this->inputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println("Exception: PacketAssembler.assembleInputRefreshPacket(): inputRefreshPacket is not initialized");
	}
	
	// (1) Populate Delimiter:
	for (int i = 0; i < NUMBER_OF_PACKET_DELIMITER_BYTES; i++) {
		this->inputRefreshPacket[i] = PACKET_DELIMITER_BYTE;
	}
	
	// (2) Populate Header:
	/* Originator */	this->saveByteToInputRefreshPacket(0x01, 1);
	/* Packet Type */	this->saveByteToInputRefreshPacket(0x01, 2);
	/* Packet Length */	this->saveByteToInputRefreshPacket((INPUT_REFRESH_PACKET_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES), 3);
	/* Current Mode */	this->saveByteToInputRefreshPacket(0x00, 4);//TODO
	/* Command */		this->saveByteToInputRefreshPacket(0x00, 5);//TODO
	/* Parity Byte */	this->saveByteToInputRefreshPacket(0x00, 6);//TODO
	/* Empty */			this->saveByteToInputRefreshPacket(0x00, 7);
	/* Empty */			this->saveByteToInputRefreshPacket(0x00, 8);
	/* Empty */			this->saveByteToInputRefreshPacket(0x00, 9);
	
	// (3) Populate Payload:
	byte tempByte = 0;
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleA.switch_StagingButton.getInputStatus(),
										   controlPanel.moduleA.switch_BrakeButton.getInputStatus(),
										   controlPanel.moduleB.switch_AbortButton.getInputStatus(),
										   controlPanel.moduleB.switch_PitchTrim.getInputStatus(),
										   controlPanel.moduleB.switch_YawTrim.getInputStatus(),
										   controlPanel.moduleB.switch_RollTrim.getInputStatus(),
										   controlPanel.moduleB.switch_TimeWarpUp.getInputStatus(),
										   controlPanel.moduleB.switch_TimeWarpDown.getInputStatus());
	this->saveByteToInputRefreshPacket(tempByte, 10);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleB.switch_Joystick.getInputStatus(),
										   controlPanel.moduleD.switch_SAS.getInputStatus(),
										   controlPanel.moduleD.switch_RCS.getInputStatus(),
										   controlPanel.moduleD.switch_Lights.getInputStatus(),
										   controlPanel.moduleD.switch_Gear.getInputStatus(),
										   controlPanel.moduleD.switch_Brake.getInputStatus(),
										   controlPanel.moduleD.switch_Map.getInputStatus(),
										   controlPanel.moduleD.switch_Mute.getInputStatus());
	this->saveByteToInputRefreshPacket(tempByte, 11);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleD.switch_AutopilotHold.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotPrograde.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotRetrograde.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotNormal.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotAntiNormal.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotRadialIn.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotRadialOut.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotTarget.getInputStatus());
	this->saveByteToInputRefreshPacket(tempByte, 12);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleD.switch_AutopilotAntiTarget.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotManeuver.getInputStatus(),
										   controlPanel.moduleE.switch_ActionGroup1.getInputStatus(),
										   controlPanel.moduleE.switch_ActionGroup2.getInputStatus(),
										   controlPanel.moduleE.switch_ActionGroup3.getInputStatus(),
										   controlPanel.moduleE.switch_Science.getInputStatus(),
										   controlPanel.moduleE.switch_Reset.getInputStatus(),
										   controlPanel.moduleE.switch_Solar.getInputStatus());
	this->saveByteToInputRefreshPacket(tempByte, 13);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleE.switch_Ladder.getInputStatus(),
										   controlPanel.moduleE.switch_AutoNavigation.getInputStatus(),
										   controlPanel.moduleE.switch_FairingButton.getInputStatus(),
										   controlPanel.moduleE.switch_ChuteButton.getInputStatus(),
										   controlPanel.moduleE.switch_SFC.getInputStatus(),
										   controlPanel.moduleE.switch_TGT.getInputStatus(),
										   controlPanel.moduleE.switch_RKT.getInputStatus(),
										   controlPanel.moduleE.switch_RVR.getInputStatus());
	this->saveByteToInputRefreshPacket(tempByte, 14);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleE.switch_90deg.getInputStatus(),
										   controlPanel.moduleE.switch_9deg.getInputStatus(),
										   controlPanel.moduleF.switch_trim.getInputStatus(),
										   controlPanel.moduleF.switch_4pos_AB.getInputStatus(),
										   controlPanel.moduleF.switch_4pos_CD.getInputStatus(),
										   controlPanel.moduleG.switch_HeatLife.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_TL.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_CL.getInputStatus());
	this->saveByteToInputRefreshPacket(tempByte, 15);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleH.switch_GlassCockpit_BL.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_TR.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_CR.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_BR.getInputStatus(),
										   controlPanel.moduleI.switch_MonopropellantIntake.getInputStatus(),
										   false, //Unused
										   false, //Unused
										   false);//Unused
	this->saveByteToInputRefreshPacket(tempByte, 16);
	
	//TODO Add analog outputs
	
	//this->displayInputRefreshPacketInDecimal();
}

byte PacketAssembler::compressBoolsIntoByte(bool bool1, bool bool2, bool bool3, bool bool4,
											bool bool5, bool bool6, bool bool7, bool bool8) {
	byte result = 0;
	if(bool8) { result |= (1 << 0); }
	if(bool7) { result |= (1 << 1); }
	if(bool6) { result |= (1 << 2); }
	if(bool5) { result |= (1 << 3); }
	if(bool4) { result |= (1 << 4); }
	if(bool3) { result |= (1 << 5); }
	if(bool2) { result |= (1 << 6); }
	if(bool1) { result |= (1 << 7); }
	//Serial.println(result, BIN);
	return result;
}

void PacketAssembler::saveByteToInputRefreshPacket(byte theByte, int byteNumber) {
	this->inputRefreshPacket[byteNumber - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES] = theByte;
}




















