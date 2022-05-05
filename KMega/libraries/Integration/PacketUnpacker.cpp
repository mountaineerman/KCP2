#include <Arduino.h>
#include <PacketUnpacker.h>
#include "../../configuration.h"


PacketUnpacker::PacketUnpacker(ControlPanel& controlPanel)
	: controlPanel(controlPanel)
{
	this->outputRefreshPacket = NULL;
}

void PacketUnpacker::setOutputRefreshPacket(const byte * outputRefreshPacket) {
	this->outputRefreshPacket = outputRefreshPacket;
}

void PacketUnpacker::displayOutputRefreshPacketInDecimal() {
	
	if (this->outputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println(F("Exception: PacketUnpacker.displayOutputRefreshPacketInDecimal(): OutputRefreshPacket is not initialized"));
	}
	
	Serial.println(F("PacketUnpacker: outputRefreshPacket: (Decimal Format)"));
	Serial.print("Position: ");
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	Serial.print("Value:    ");
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		Serial.print(this->outputRefreshPacket[i]);
		Serial.print("\t");
	}
	Serial.println();
}

void PacketUnpacker::unpackOutputRefreshPacketIntoModel() {
	
	//this->displayOutputRefreshPacketInDecimal();
	
	if (this->outputRefreshPacket == NULL) {
		//TODO throw exception
		Serial.println(F("Exception: PacketUnpacker.unpackOutputRefreshPacketIntoModel(): OutputRefreshPacket is not initialized"));
	}
	
	//LEDs
	controlPanel.moduleA.ledPWM_BrakeModuleA.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(10,11) );
	controlPanel.moduleD.ledPWM_BrakeModuleD.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(12,13) );
	controlPanel.moduleD.ledPWM_AutopilotHold.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(14,15) );
	controlPanel.moduleD.ledPWM_AutopilotPrograde.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(16,17) );
	controlPanel.moduleD.ledPWM_AutopilotRetrograde.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(18,19) );
	
	controlPanel.moduleD.ledPWM_AutopilotManeuver.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(20,21) );
	controlPanel.moduleE.ledPWM_Fairing.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(22,23) );
	controlPanel.moduleE.ledPWM_Chute.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(24,25) );
	controlPanel.moduleE.ledPWM_ORB.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(26,27) );
	controlPanel.moduleE.ledPWM_PLN.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(28,29) );
	
	controlPanel.moduleE.ledPWM_30deg.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(30,31) );
	controlPanel.moduleF.ledPWM_twistSwitch100.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(32,33) );
	controlPanel.moduleF.ledPWM_twistSwitch75.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(34,35) );
	controlPanel.moduleF.ledPWM_twistSwitch50.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(36,37) );
	controlPanel.moduleF.ledPWM_twistSwitch25.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(38,39) );
	
	//controlPanel.moduleG.ledPWM_Comms.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(40,41) ); //Note: KMega evaluates serial channels to decide COMMS LED status.
	controlPanel.moduleH.ledPWM_GlassCockpit_TL.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(42,43) );
	//controlPanel.moduleH.ledPWM_GlassCockpit_CL.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(44,45) );//TODO
	controlPanel.moduleH.ledPWM_GlassCockpit_BL.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(46,47) );
	controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(48,49) );
	
	//controlPanel.moduleH.ledPWM_GlassCockpit_CR.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(50,51) );//TODO
	controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(52,53) );
	controlPanel.moduleC.ledPWM_HEAT_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(54,55) );
	controlPanel.moduleC.ledPWM_HEAT_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(56,57) );
	controlPanel.moduleC.ledPWM_HEAT_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(58,59) );
	
	controlPanel.moduleC.ledPWM_LIFE_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(60,61) );
	controlPanel.moduleC.ledPWM_LIFE_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(62,63) );
	controlPanel.moduleC.ledPWM_LIFE_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(64,65) );
	controlPanel.moduleC.ledPWM_GFORCE_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(66,67) );
	controlPanel.moduleC.ledPWM_GFORCE_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(68,69) );
	
	controlPanel.moduleC.ledPWM_GFORCE_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(70,71) );
	controlPanel.moduleD.ledPWM_AutopilotNormal_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(72,73) );
	controlPanel.moduleD.ledPWM_AutopilotNormal_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(74,75) );
	controlPanel.moduleD.ledPWM_AutopilotAntiNormal_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(76,77) );
	controlPanel.moduleD.ledPWM_AutopilotAntiNormal_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(78,79) );
	
	controlPanel.moduleD.ledPWM_AutopilotRadialIn_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(80,81) );
	controlPanel.moduleD.ledPWM_AutopilotRadialIn_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(82,83) );
	controlPanel.moduleD.ledPWM_AutopilotRadialOut_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(84,85) );
	controlPanel.moduleD.ledPWM_AutopilotRadialOut_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(86,87) );
	controlPanel.moduleD.ledPWM_AutopilotTarget_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(88,89) );
	
	controlPanel.moduleD.ledPWM_AutopilotTarget_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(90,91) );
	controlPanel.moduleD.ledPWM_AutopilotAntiTarget_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(92,93) );
	controlPanel.moduleD.ledPWM_AutopilotAntiTarget_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(94,95) );
	controlPanel.moduleG.ledPWM_MACH_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(96,97) );
	controlPanel.moduleG.ledPWM_MACH_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(98,99) );
	
	controlPanel.moduleG.ledPWM_MACH_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(100,101) );
	controlPanel.moduleG.ledPWM_PITCH_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(102,103) );
	controlPanel.moduleG.ledPWM_PITCH_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(104,105) );
	controlPanel.moduleG.ledPWM_PITCH_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(106,107) );
	controlPanel.moduleG.ledPWM_HEADING_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(108,109) );
	
	controlPanel.moduleG.ledPWM_HEADING_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(110,111) );
	controlPanel.moduleG.ledPWM_HEADING_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(112,113) );
	controlPanel.moduleI.ledPWM_FUEL_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(114,115) );
	controlPanel.moduleI.ledPWM_FUEL_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(116,117) );
	controlPanel.moduleI.ledPWM_FUEL_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(118,119) );
	
	controlPanel.moduleI.ledPWM_CHARGE_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(120,121) );
	controlPanel.moduleI.ledPWM_CHARGE_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(122,123) );
	controlPanel.moduleI.ledPWM_CHARGE_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(124,125) );
	controlPanel.moduleI.ledPWM_DeltaCHARGE_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(126,127) );
	controlPanel.moduleI.ledPWM_DeltaCHARGE_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(128,129) );
	
	controlPanel.moduleI.ledPWM_DeltaCHARGE_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(130,131) );
	controlPanel.moduleI.ledPWM_MONOPROPELLANT_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(132,133) );
	controlPanel.moduleI.ledPWM_MONOPROPELLANT_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(134,135) );
	controlPanel.moduleI.ledPWM_MONOPROPELLANT_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(136,137) );
	controlPanel.moduleI.ledPWM_INTAKE_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(138,139) );
	
	controlPanel.moduleI.ledPWM_INTAKE_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(140,141) );
	controlPanel.moduleI.ledPWM_INTAKE_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(142,143) );
	controlPanel.moduleGT.ledPWM_DENSITY_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(144,145) );
	controlPanel.moduleGT.ledPWM_DENSITY_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(146,147) );
	controlPanel.moduleGT.ledPWM_DENSITY_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(148,149) );
	
	controlPanel.moduleGT.ledPWM_SPEED_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(150,151) );
	controlPanel.moduleGT.ledPWM_SPEED_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(152,153) );
	controlPanel.moduleGT.ledPWM_SPEED_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(154,155) );
	controlPanel.moduleGT.ledPWM_VSPEED_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(156,157) );
	controlPanel.moduleGT.ledPWM_VSPEED_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(158,159) );
	
	controlPanel.moduleGT.ledPWM_VSPEED_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(160,161) );
	controlPanel.moduleGT.ledPWM_RADARALT_Red.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(162,163) );
	controlPanel.moduleGT.ledPWM_RADARALT_Green.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(164,165) );
	controlPanel.moduleGT.ledPWM_RADARALT_Blue.setPWM( this->convertTwoBytesInOutputRefreshPacketIntoInteger(166,167) );
	//Stepper Motors
	controlPanel.moduleC.stepper_HeatLife.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(168,169) );
	
	controlPanel.moduleC.stepper_Gforce.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(170,171) );
	controlPanel.moduleG.stepper_Mach.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(172,173) );
	controlPanel.moduleG.stepper_Pitch.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(174,175) );
	controlPanel.moduleG.stepper_Heading.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(176,177) );
	controlPanel.moduleI.stepper_Fuel.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(178,179) );
	
	controlPanel.moduleI.stepper_Charge.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(180,181) );
	controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(182,183) );
	controlPanel.moduleGT.stepper_Density.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(184,185) );
	controlPanel.moduleGT.stepper_Speed.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(186,187) );
	controlPanel.moduleGT.stepper_VertSpeed.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(188,189) );
	
	controlPanel.moduleGT.stepper_RadarAlt.setDesiredPosition( this->convertTwoBytesInOutputRefreshPacketIntoInteger(190,191) );
	
	//Altitude
	controlPanel.moduleGT.altitude = this->convertFourBytesInOutputRefreshPacketIntoFloat(192,195);
	
	this->clearOutputRefreshPacket();
}

void PacketUnpacker::clearOutputRefreshPacket() {
	for (int i = 0; i < OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
		this->outputRefreshPacket[i] = 0x00;
	}
}

int PacketUnpacker::convertTwoBytesInOutputRefreshPacketIntoInteger(int byteNum1, int byteNum2) {
	
	if (byteNum1 > OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
		//TODO throw exception
		Serial.println(F("Exception: PacketUnpacker.convertTwoByteArrayIntoInteger(): byteNum1 greater than length of OutputRefreshPacket"));
	}
	
	if (byteNum2 > OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES) {
		//TODO throw exception
		Serial.println(F("Exception: PacketUnpacker.convertTwoByteArrayIntoInteger(): byteNum2 greater than length of OutputRefreshPacket"));
	}
	
	int largestByteNum = 0;
	int smallestByteNum = 0;
	
	if (byteNum1 > byteNum2) {
		largestByteNum = byteNum1 - 1;
		smallestByteNum = byteNum2 - 1;
	} else {
		largestByteNum = byteNum2 - 1;
		smallestByteNum = byteNum1 - 1;
	}
	
	char tempTwoByteArray[2];
	tempTwoByteArray[0] = this->outputRefreshPacket[smallestByteNum];
	tempTwoByteArray[1] = this->outputRefreshPacket[largestByteNum];
	
	return (*((int *)tempTwoByteArray));
}


float PacketUnpacker::convertFourBytesInOutputRefreshPacketIntoFloat(int firstByteNum, int lastByteNum) {
	
	if (firstByteNum + 3 != lastByteNum) {
		//TODO throw exception
		Serial.println(F("Exception: PacketUnpacker::convertFourBytesInOutputRefreshPacketIntoFloat(): firstByteNum and lastByteNum are not a 4 bytes range."));
		return -1;
	}
	
	union {
		float theFloat;
		unsigned char theByteArray[4];
	} byteArrayToFloat;
	
	int start = firstByteNum - 1;
	int end = lastByteNum - 1;
	
	byteArrayToFloat.theByteArray[3] = this->outputRefreshPacket[start];
	byteArrayToFloat.theByteArray[2] = this->outputRefreshPacket[start+1];
	byteArrayToFloat.theByteArray[1] = this->outputRefreshPacket[start+2];
	byteArrayToFloat.theByteArray[0] = this->outputRefreshPacket[end];
	
	return byteArrayToFloat.theFloat;
}





















