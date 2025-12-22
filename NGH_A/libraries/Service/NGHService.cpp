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
	this->coffeeMode = false;
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

	// this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CW_LIMIT, 500);
	// delay(200);
	// this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CCW_LIMIT, 500);
	delay(2000);

	this->serialCommunicator.establishKMegaSerialLink();

	this->nextMode = NGHOperatingMode::STANDARD;
}

void NGHService::standardOperatingMode() {

	bool gotGaugePacket = false;

	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	if ( this->serialCommunicator.getGaugePacket() ) {
		gotGaugePacket = true;
		this->gaugePacketLastReceiveTimeInMilliseconds = millis();
		byte command = this->packetUnpacker.unpackGaugePacketIntoModel();
		if (command == 0x01) {//Coffee Command
			this->coffeeMode = true;
		} else if (command == 0x02) {//Calibration Command
			this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CW_LIMIT, 500);
			delay(200);
			this->controlPanel.blockRunAllGearedSteppersToPosition(STEPPER_CCW_LIMIT, 500);
		}
	}

	if (this->coffeeMode == false) {
		if ( (millis() - this->gaugePacketLastReceiveTimeInMilliseconds) > MAX_TIME_WITHOUT_PACKET_BEFORE_GAUGE_RESET_IN_MILLISECONDS ) {
			if(!this->gaugesHaveBeenSetCCW) {
				if (NGH_A) {
					// [GaugePacketA]
					this->controlPanel.moduleC.stepper_HeatLife.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleC.stepper_Gforce.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleG.stepper_Mach.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(NEMA17_STEPPER_MIN_POSITION);
					this->controlPanel.moduleG.stepper_Pitch.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleIa.stepper_Fuel.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
				} else {
					// [GaugePacketB]
					this->controlPanel.moduleIb.stepper_Charge.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleIb.stepper_MonopropellantIntake.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleGT.stepper_Density.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleGT.stepper_Speed.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleGT.stepper_VertSpeed.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
					this->controlPanel.moduleGT.stepper_RadarAlt.setDesiredPositionAndTravelStatus(STEPPER_CCW_LIMIT);
				}
				this->gaugesHaveBeenSetCCW = true;
			}
		} else {
			this->gaugesHaveBeenSetCCW = false;
		}
	}

	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();
	this->controlPanel.runStepperIfNecessary();

	this->nextMode = NGHOperatingMode::STANDARD;
}

void NGHService::clearPacket(byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}