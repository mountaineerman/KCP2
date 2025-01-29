#include <Arduino.h>
#include <PacketAssembler.h>
#include "../../configuration.h"


PacketAssembler::PacketAssembler(ControlPanel& controlPanel)
	: controlPanel(controlPanel)
{
	this->altitudePacket = NULL;
	this->inputRefreshPacket = NULL;
}

void PacketAssembler::setAltitudePacket(const byte * altitudePacket) {
	this->altitudePacket = altitudePacket;
}

void PacketAssembler::setGaugePacketA(const byte * gaugePacketA) {
	this->gaugePacketA = gaugePacketA;
}

void PacketAssembler::setGaugePacketB(const byte * gaugePacketB) {
	this->gaugePacketB = gaugePacketB;
}

void PacketAssembler::setInputRefreshPacket(const byte * inputRefreshPacket) {
	this->inputRefreshPacket = inputRefreshPacket;
}

void PacketAssembler::displayPacket(const byte * packet, int packetLength, String packetName) {//TODO verify
	
	if (packet == NULL) {
		//TODO throw exception
		Serial.print(F("Exception: PacketAssembler.displayPacket(): ")); Serial.print(packetName); Serial.println(F(" is not initialized"));
	}
	
	Serial.println(F("PacketAssembler.displayPacket(): (decimal format)"));
	Serial.print(F("Packet: ")); Serial.println(packetName);
	Serial.print(F("Byte Num: "));
	for (int i = 0; i < packetLength; i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	
	Serial.print(F("Value:    "));
	for (int i = 0; i < packetLength; i++) {
		Serial.print(packet[i]);
		Serial.print("\t");
	}
	Serial.println();
}

void PacketAssembler::assembleAltitudePacket() {
	
	if (this->altitudePacket == NULL) {
		//TODO throw exception
		Serial.println("Exception: PacketAssembler.assembleAltitudePacket(): altitudePacket is not initialized");
	}
	
	// (1) Populate Delimiter:
	for (int i = 0; i < NUMBER_OF_PACKET_DELIMITER_BYTES; i++) {
		this->altitudePacket[i] = PACKET_DELIMITER_BYTE;
	}
	
	// (2) Populate Header:
	/* Originator */	this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x01, 1);
	/* Packet Type */	this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x03, 2);
	/* Packet Length */	this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, (ALTITUDE_PACKET_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES), 3);
	/* Current Mode */	this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x00, 4);//TODO
	/* Command */		this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x00, 5);
	/* Parity Byte */	this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x00, 6);//TODO
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x00, 7);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x00, 8);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::ALTITUDE, 0x00, 9);
	
	// (3) Populate Payload:
	this->saveFloatToAltitudePacketAtByteNumbers(controlPanel.moduleGT.altitude, 10, 13);
		
	//this->displayPacket(this->altitudePacket, ALTITUDE_PACKET_LENGTH_IN_BYTES, "altitudePacket");
}

void PacketAssembler::assembleGaugePacketA() {

	// (1) Populate Delimiter:
	for (int i = 0; i < NUMBER_OF_PACKET_DELIMITER_BYTES; i++) {
		this->gaugePacketA[i] = PACKET_DELIMITER_BYTE;
	}
	
	// (2) Populate Header:
	/* Originator */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x01, 1);
	/* Packet Type */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x06, 2);
	/* Packet Length */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, (GAUGE_PACKET_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES), 3);
	/* Current Mode */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x00, 4);//TODO
	/* Command */		this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x00, 5);//TODO
	/* Parity Byte */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x00, 6);//TODO
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x00, 7);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x00, 8);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_A, 0x00, 9);
	
	// (3) Populate Payload:
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleC.stepper_HeatLife.getDesiredPosition(), PacketType::GAUGE_PACKET_A, 10, 11);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleC.stepper_Gforce.getDesiredPosition(), PacketType::GAUGE_PACKET_A, 12, 13);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleG.stepper_Mach.getDesiredPosition(), PacketType::GAUGE_PACKET_A, 14, 15);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleG.stepper_Pitch.getDesiredPosition(), PacketType::GAUGE_PACKET_A, 16, 17);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleG.stepper_Heading.getDesiredPosition(), PacketType::GAUGE_PACKET_A, 18, 19);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleI.stepper_Fuel.getDesiredPosition(), PacketType::GAUGE_PACKET_A, 20, 21);
}

void PacketAssembler::assembleGaugePacketB() {

	// (1) Populate Delimiter:
	for (int i = 0; i < NUMBER_OF_PACKET_DELIMITER_BYTES; i++) {
		this->gaugePacketB[i] = PACKET_DELIMITER_BYTE;
	}
	
	// (2) Populate Header:
	/* Originator */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x01, 1);
	/* Packet Type */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x07, 2);
	/* Packet Length */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, (GAUGE_PACKET_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES), 3);
	/* Current Mode */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x00, 4);//TODO
	/* Command */		this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x00, 5);//TODO
	/* Parity Byte */	this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x00, 6);//TODO
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x00, 7);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x00, 8);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::GAUGE_PACKET_B, 0x00, 9);
	
	// (3) Populate Payload:
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleI.stepper_Charge.getDesiredPosition(), PacketType::GAUGE_PACKET_B, 10, 11);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleI.stepper_MonopropellantIntake.getDesiredPosition(), PacketType::GAUGE_PACKET_B, 12, 13);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleGT.stepper_Density.getDesiredPosition(), PacketType::GAUGE_PACKET_B, 14, 15);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleGT.stepper_Speed.getDesiredPosition(), PacketType::GAUGE_PACKET_B, 16, 17);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleGT.stepper_VertSpeed.getDesiredPosition(), PacketType::GAUGE_PACKET_B, 18, 19);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleGT.stepper_RadarAlt.getDesiredPosition(), PacketType::GAUGE_PACKET_B, 20, 21);
}

void PacketAssembler::assembleInputRefreshPacket() {
	
	//this->displayPacket(this->inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "inputRefreshPacket");
	
	if (this->inputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println(F("Exception: PacketAssembler.assembleInputRefreshPacket(): inputRefreshPacket is not initialized"));
	}
	
	// (1) Populate Delimiter:
	for (int i = 0; i < NUMBER_OF_PACKET_DELIMITER_BYTES; i++) {
		this->inputRefreshPacket[i] = PACKET_DELIMITER_BYTE;
	}
	
	// (2) Populate Header:
	/* Originator */	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x01, 1);
	/* Packet Type */	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x01, 2);
	/* Packet Length */	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, (INPUT_REFRESH_PACKET_LENGTH_IN_BYTES - NUMBER_OF_PACKET_DELIMITER_BYTES), 3);
	/* Current Mode */	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x00, 4);//TODO
	/* Command */		this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x00, 5);//TODO
	/* Parity Byte */	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x00, 6);//TODO
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x00, 7);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x00, 8);
	/* Empty */			this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, 0x00, 9);
	
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
	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, tempByte, 10);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleB.switch_Joystick.getInputStatus(),
										   controlPanel.moduleD.switch_SAS.getInputStatus(),
										   controlPanel.moduleD.switch_RCS.getInputStatus(),
										   controlPanel.moduleD.switch_Lights.getInputStatus(),
										   controlPanel.moduleD.switch_Gear.getInputStatus(),
										   controlPanel.moduleD.switch_Brake.getInputStatus(),
										   controlPanel.moduleD.switch_Map.getInputStatus(),
										   controlPanel.moduleD.switch_Mute.getInputStatus());
	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, tempByte, 11);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleD.switch_AutopilotHold.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotPrograde.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotRetrograde.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotNormal.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotAntiNormal.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotRadialIn.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotRadialOut.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotTarget.getInputStatus());
	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, tempByte, 12);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleD.switch_AutopilotAntiTarget.getInputStatus(),
										   controlPanel.moduleD.switch_AutopilotManeuver.getInputStatus(),
										   controlPanel.moduleE.switch_ActionGroup1.getInputStatus(),
										   controlPanel.moduleE.switch_ActionGroup2.getInputStatus(),
										   controlPanel.moduleE.switch_ActionGroup3.getInputStatus(),
										   controlPanel.moduleE.switch_Science.getInputStatus(),
										   controlPanel.moduleE.switch_Reset.getInputStatus(),
										   controlPanel.moduleE.switch_Solar.getInputStatus());
	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, tempByte, 13);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleE.switch_Ladder.getInputStatus(),
										   controlPanel.moduleE.switch_AutoNavigation.getInputStatus(),
										   controlPanel.moduleE.switch_FairingButton.getInputStatus(),
										   controlPanel.moduleE.switch_ChuteButton.getInputStatus(),
										   controlPanel.moduleE.switch_SFC.getInputStatus(),
										   controlPanel.moduleE.switch_TGT.getInputStatus(),
										   controlPanel.moduleE.switch_RKT.getInputStatus(),
										   controlPanel.moduleE.switch_RVR.getInputStatus());
	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, tempByte, 14);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleE.switch_90deg.getInputStatus(),
										   controlPanel.moduleE.switch_9deg.getInputStatus(),
										   controlPanel.moduleF.switch_trim.getInputStatus(),
										   controlPanel.moduleF.switch_4pos_AB.getInputStatus(),
										   controlPanel.moduleF.switch_4pos_CD.getInputStatus(),
										   controlPanel.moduleG.switch_HeatLife.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_TL.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_CL.getInputStatus());
	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, tempByte, 15);
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleH.switch_GlassCockpit_BL.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_TR.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_CR.getInputStatus(),
										   controlPanel.moduleH.switch_GlassCockpit_BR.getInputStatus(),
										   controlPanel.moduleI.switch_MonopropellantIntake.getInputStatus(),
										   false, //Unused
										   false, //Unused
										   false);//Unused
	this->saveByteToPacketAtByteNumber(PacketType::INPUT_REFRESH, tempByte, 16);
	
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleA.analogInput_Throttle.getInputStatus(), PacketType::INPUT_REFRESH, 17, 18);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleB.analogInput_Joystick_Pitch.getInputStatus(), PacketType::INPUT_REFRESH, 19, 20);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleB.analogInput_Joystick_Yaw.getInputStatus(), PacketType::INPUT_REFRESH, 21, 22);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleB.analogInput_Joystick_Roll.getInputStatus(), PacketType::INPUT_REFRESH, 23, 24);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleF.analogInput_MultiPot.getInputStatus(), PacketType::INPUT_REFRESH, 25, 26);
	this->saveNumberToPacketAtByteNumbers(controlPanel.moduleF.analogInput_Current.getInputStatus(), PacketType::INPUT_REFRESH, 27, 28);
	
	//this->saveFloatToInputRefreshPacketAtByteNumbers(controlPanel.moduleGT.altitude, 29, 32);//(For Debugging)
	
	//this->displayPacket(this->inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "inputRefreshPacket");
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

void PacketAssembler::saveByteToPacketAtByteNumber(PacketType packetType, byte theByte, int byteNumber) {
	
	if (packetType == PacketType::ALTITUDE) {
		this->altitudePacket[byteNumber - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES] = theByte;
	} else if (packetType == PacketType::GAUGE_PACKET_A) {
		this->gaugePacketA[byteNumber - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES] = theByte;
	} else if (packetType == PacketType::GAUGE_PACKET_B) {
		this->gaugePacketB[byteNumber - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES] = theByte;
	} else if (packetType == PacketType::INPUT_REFRESH) {
		this->inputRefreshPacket[byteNumber - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES] = theByte;
	}
}

void PacketAssembler::saveNumberToPacketAtByteNumbers(int number, PacketType packetType, int byteNum1, int byteNum2) {
	
	if (number > 65535) {
		//TODO throw exception
		Serial.println(F("Exception: PacketAssembler.saveNumberToPacketAtByteNumbers(): the number is larger than what can be stored in two bytes (65,535)"));
	}
	
	int largeByteNum = 0;
	int smallByteNum = 0;
	
	if (byteNum1 > byteNum2) {
		largeByteNum = byteNum1 - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
		smallByteNum = byteNum2 - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
	} else {
		largeByteNum = byteNum2 - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
		smallByteNum = byteNum1 - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
	}
	
	if (packetType == PacketType::ALTITUDE) {
		this->altitudePacket[smallByteNum] = (byte) (number & 0xFF);
		this->altitudePacket[largeByteNum] = (byte) ((number >> 8) & 0xFF);
	} else if (packetType == PacketType::GAUGE_PACKET_A) {
		this->gaugePacketA[smallByteNum] = (byte) (number & 0xFF);
		this->gaugePacketA[largeByteNum] = (byte) ((number >> 8) & 0xFF);
	} else if (packetType == PacketType::GAUGE_PACKET_B) {
		this->gaugePacketB[smallByteNum] = (byte) (number & 0xFF);
		this->gaugePacketB[largeByteNum] = (byte) ((number >> 8) & 0xFF);
	} else if (packetType == PacketType::INPUT_REFRESH) {
		this->inputRefreshPacket[smallByteNum] = (byte) (number & 0xFF);
		this->inputRefreshPacket[largeByteNum] = (byte) ((number >> 8) & 0xFF);
	}
}

void PacketAssembler::saveFloatToAltitudePacketAtByteNumbers(float number, int firstByteNum, int lastByteNum) {
	
	if (this->altitudePacket == NULL) {
		//TODO throw exception
		Serial.println(F("Exception: PacketAssembler.saveFloatAtByteNumbersToAltitudePacket(): altitudePacket is not initialized"));
	}
	
	union {
		float theFloat;
		unsigned char theByteArray[4];
	} floatToByteArray;
	
	floatToByteArray.theFloat = number;
	
	int start = firstByteNum - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
	int end = lastByteNum - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
	
	this->altitudePacket[start]   = floatToByteArray.theByteArray[3];
	this->altitudePacket[start+1] = floatToByteArray.theByteArray[2];
	this->altitudePacket[start+2] = floatToByteArray.theByteArray[1];
	this->altitudePacket[end]     = floatToByteArray.theByteArray[0];
}

//(For Debugging)
//void PacketAssembler::saveFloatToInputRefreshPacketAtByteNumbers(float number, int firstByteNum, int lastByteNum) {
//	
//	if (this->inputRefreshPacket == NULL) {
//		//TODO throw exception
//		Serial.println(F("Exception: PacketAssembler.saveFloatToInputRefreshPacketAtByteNumbers(): inputRefreshPacket is not initialized"));
//	}
//	
//	union {
//		float theFloat;
//		unsigned char theByteArray[4];
//	} floatToByteArray;
//	
//	floatToByteArray.theFloat = number;
//	
//	int start = firstByteNum - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
//	int end = lastByteNum - 1 + NUMBER_OF_PACKET_DELIMITER_BYTES;
//	
//	this->inputRefreshPacket[start]   = floatToByteArray.theByteArray[3];
//	this->inputRefreshPacket[start+1] = floatToByteArray.theByteArray[2];
//	this->inputRefreshPacket[start+2] = floatToByteArray.theByteArray[1];
//	this->inputRefreshPacket[end]     = floatToByteArray.theByteArray[0];
//}












