#include <NGHService.h>
#include <SerialCommunicator.h>

NGHService::NGHService()
	: controlPanel()
	, serialCommunicator()
	, packetUnpacker(controlPanel)
{	
	this->gaugePacket[GAUGE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(gaugePacket, GAUGE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setGaugePacket(gaugePacket);
	this->packetUnpacker.setGaugePacket(gaugePacket);
	
	this->gaugePacketLastReceiveTimeInMilliseconds = millis();
	this->gaugesHaveBeenSetCCW = false;
	this->startupMode();
	//FIXME
	// while (true) {
	// 	this->standardOperatingMode();
	// }
}

void NGHService::startupMode() {

	//FIXME
	// this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CW_LIMIT, 500);
	this->controlPanel.blockRunAllGearedSteppersToPosition(100, 500);
	delay(200);
	this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CCW_LIMIT, 500);
	//FIXME
	//this->serialCommunicator.establishKMegaSerialLink();
	// FIXME
	// this->controlPanel.moduleG.stepper_Heading.runStepperIfNecessary();
	delay(5000);
	this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(798);
	this->controlPanel.moduleG.stepper_Heading.runToDesiredPosition();
	delay(5000);
	this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(0);
	this->controlPanel.moduleG.stepper_Heading.runToDesiredPosition();
	delay(5000);
	this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(802);
	this->controlPanel.moduleG.stepper_Heading.runToDesiredPosition();
	delay(5000);
	this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(0);
	this->controlPanel.moduleG.stepper_Heading.runToDesiredPosition();
}

void NGHService::standardOperatingMode() {

	bool gotGaugePacket = false;

	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	if ( this->serialCommunicator.getGaugePacket() ) {
		gotGaugePacket = true;
		this->gaugePacketLastReceiveTimeInMilliseconds = millis();
		this->packetUnpacker.unpackGaugePacketIntoModel();
	}

	if ( (millis() - this->gaugePacketLastReceiveTimeInMilliseconds) > MAX_TIME_WITHOUT_PACKET_BEFORE_GAUGE_RESET_IN_MILLISECONDS ) {
		if(!this->gaugesHaveBeenSetCCW) {
			// [GaugePacketA]
			this->controlPanel.moduleC.stepper_HeatLife.setDesiredPosition(STEPPER_CCW_LIMIT);
			this->controlPanel.moduleC.stepper_Gforce.setDesiredPosition(STEPPER_CCW_LIMIT);
			this->controlPanel.moduleG.stepper_Mach.setDesiredPosition(STEPPER_CCW_LIMIT);
			this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(STEPPER_CCW_LIMIT);
			this->controlPanel.moduleG.stepper_Pitch.setDesiredPosition(STEPPER_CCW_LIMIT);
			this->controlPanel.moduleI.stepper_Fuel.setDesiredPosition(STEPPER_CCW_LIMIT);
			// [GaugePacketB]
			// this->controlPanel.moduleI.stepper_Charge.setDesiredPosition(STEPPER_CCW_LIMIT);
			// this->controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPosition(STEPPER_CCW_LIMIT);
			// this->controlPanel.moduleGT.stepper_Density.setDesiredPosition(STEPPER_CCW_LIMIT);
			// this->controlPanel.moduleGT.stepper_Speed.setDesiredPosition(STEPPER_CCW_LIMIT);
			// this->controlPanel.moduleGT.stepper_VertSpeed.setDesiredPosition(STEPPER_CCW_LIMIT);
			// this->controlPanel.moduleGT.stepper_RadarAlt.setDesiredPosition(STEPPER_CCW_LIMIT);
			this->gaugesHaveBeenSetCCW = true;
		}
	} else {
		this->gaugesHaveBeenSetCCW = false;
	}
		
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	
	//TODO Idle if necessary
	delay(1);
}

void NGHService::clearPacket(byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}