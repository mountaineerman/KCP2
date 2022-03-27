package mountaineerman.kcp2.kkim.integration;

import java.util.Arrays;

import mountaineerman.kcp2.kkim.model.ControlPanel;

/* Packet Assembler
 * Responsible for reading the relevant parts of the KKIM Model and assembling
 * them into an OutputRefreshPacket.
 */
public class PacketAssembler {

	private ControlPanel controlPanel;
	
	public PacketAssembler(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}
	
	public void displayPacketInDecimal(byte[] packet) {
		System.out.println("PacketAssembler: Displaying packet in decimal format:");
		System.out.println(Arrays.toString(packet));
	}
	
	//Assembles the status of the KKIM Model into an OutputRefreshPacket
	public byte[] assembleOutputRefreshPacket() {//TODO
		
	}
	
}



//private:
	//See description in "ICD:KMega>KKIM" (Onenote)
	byte compressBoolsIntoByte(bool bool1, bool bool2, bool bool3, bool bool4,
							   bool bool5, bool bool6, bool bool7, bool bool8);
	
	void saveByteToInputRefreshPacket(byte theByte, int byteNumber);
	
	ControlPanel& controlPanel;
	byte * inputRefreshPacket; //See KMegaService
};





PacketAssembler::PacketAssembler(ControlPanel& controlPanel)
	: controlPanel(controlPanel)
{
	this->inputRefreshPacket = NULL;
}

void PacketAssembler::setInputRefreshPacket(byte * inputRefreshPacket) {
	this->inputRefreshPacket = inputRefreshPacket;
}

void PacketAssembler::displayInputRefreshPacketInDecimal() {
	
	if (!this->inputRefreshPacket) {
		//TODO throw exception
		Serial.println("Exception: PacketAssembler's inputRefreshPacket is not initialized");
	}
	
	Serial.println("PacketAssembler: inputRefreshPacket: (Decimal Format)");
	Serial.print("Position: ");
	for (int i = 0; i < INPUT_REFRESH_PACKET_LENGTH_IN_BYTES; i++) {
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
	
	if (!this->inputRefreshPacket) {
		//TODO throw exception
		Serial.println("Exception: PacketAssembler's inputRefreshPacket is not initialized");
	}
	
	// (1) Populate Delimiter:
	for (int i = 0; i < NUMBER_OF_PACKET_DELIMITER_BYTES; i++) {
		this->inputRefreshPacket[i] = PACKET_DELIMITER_BYTE;
	}
	
	// (2) Populate Header:
	/* Originator */	this->saveByteToInputRefreshPacket(1, 0x01);
	/* Packet Type */	this->saveByteToInputRefreshPacket(2, 0x01);
	/* Packet Length */	this->saveByteToInputRefreshPacket(3, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
	/* Current Mode */	this->saveByteToInputRefreshPacket(4, 0x00);//TODO
	/* Command */		this->saveByteToInputRefreshPacket(5, 0x00);//TODO
	/* Parity Byte */	this->saveByteToInputRefreshPacket(6, 0x00);//TODO
	/* Empty */			this->saveByteToInputRefreshPacket(7, 0x00);
	/* Empty */			this->saveByteToInputRefreshPacket(8, 0x00);
	/* Empty */			this->saveByteToInputRefreshPacket(9, 0x00);
	
	// (3) Populate Payload:
	byte tempByte = 0;
	
	tempByte = this->compressBoolsIntoByte(controlPanel.moduleA.switch_StagingButton.getInputStatus(),
										   controlPanel.moduleA.switch_BrakeButton.getInputStatus(),
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false);//TODO
	this->saveByteToInputRefreshPacket(tempByte, 11);
	
	tempByte = this->compressBoolsIntoByte(false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   false,//TODO
										   controlPanel.moduleD.switch_Brake.getInputStatus(),
										   false,//TODO
										   false);//TODO
	this->saveByteToInputRefreshPacket(tempByte, 12);	
	//TODO Add remaining outputs
	
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
	this->inputRefreshPacket[byteNumber-1] = theByte;
}




















