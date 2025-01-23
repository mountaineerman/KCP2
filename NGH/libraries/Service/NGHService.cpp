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
	
	this->nextMode = NGHOperatingMode::STARTUP;
}

void NGHService::run() {

	while (true) {
		switch (this->nextMode) {
			case NGHOperatingMode::STARTUP:
				this->startupMode();
				break;

			case NGHOperatingMode::STANDARD:
				this->standardOperatingMode();
				break;

			default:
				return;
		}
	}
}

void NGHService::startupMode() {

	this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CW_LIMIT, 500);
	delay(200);
	this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CCW_LIMIT, 500);
	
	this->serialCommunicator.establishKMegaSerialLink();

	this->nextMode = NGHOperatingMode::STANDARD;
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
			this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(NEMA17_STEPPER_MIN_POSITION);
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

	this->nextMode = NGHOperatingMode::STANDARD;
}

void NGHService::clearPacket(byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}