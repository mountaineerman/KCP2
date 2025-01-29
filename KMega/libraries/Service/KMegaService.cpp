#include <KMegaService.h>
#include <SerialCommunicator.h>

KMegaService::KMegaService()
	: controlPanel()
	, serialCommunicator()
	, packetUnpacker(controlPanel)
	, packetAssembler(controlPanel)
{	
	this->outputRefreshPacket[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(this->outputRefreshPacket, OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setOutputRefreshPacket(outputRefreshPacket);
	this->packetUnpacker.setOutputRefreshPacket(outputRefreshPacket);
	
	this->altitudePacket[ALTITUDE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(this->altitudePacket, ALTITUDE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setAltitudePacket(altitudePacket);
	this->packetAssembler.setAltitudePacket(altitudePacket);

	this->gaugePacketA[GAUGE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(this->gaugePacketA, GAUGE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setGaugePacketA(this->gaugePacketA);
	this->packetAssembler.setGaugePacketA(this->gaugePacketA);

	this->gaugePacketB[GAUGE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(this->gaugePacketB, GAUGE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setGaugePacketB(this->gaugePacketB);
	this->packetAssembler.setGaugePacketB(this->gaugePacketB);
	
	this->inputRefreshPacket[INPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setInputRefreshPacket(inputRefreshPacket);
	this->packetAssembler.setInputRefreshPacket(inputRefreshPacket);
	
	this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	this->outputRefreshPacketLastReceiveTimeInMilliseconds = millis();
	this->commsLEDErrorStateLastToggleTimeInMilliseconds = millis();
	
	this->outputsHaveBeenSetToIdleState = false;
	
	//this->testAltitudeGauge();
	
	this->controlPanel.moduleH.ledPWM_GlassCockpit_CL.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Button for DiagnosticMode
	this->controlPanel.moduleH.ledPWM_GlassCockpit_CR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Button for graceful shutdown of Control Panel

	this->nextMode = KMegaOperatingMode::STARTUP;
}

void KMegaService::run() {

	while (true) {
		switch (this->nextMode) {
			case KMegaOperatingMode::STARTUP:
				this->startupMode();
				break;

			case KMegaOperatingMode::STANDARD:
				this->standardOperatingMode();
				break;

			case KMegaOperatingMode::DIAGNOSTIC:
				this->controlPanel.runDiagnosticMode();
				this->nextMode = KMegaOperatingMode::SHUTDOWN;
				break;

			case KMegaOperatingMode::SHUTDOWN:
				this->shutdownMode();
				return;

			default:
				return;
		}
	}
}

void KMegaService::startupMode() {

	this->controlPanel.setAllLEDsTo(PWM_LED_MAXIMUM);
	delay(1000);
	this->controlPanel.setAllLEDsTo(PWM_LED_MINIMUM);
	delay(100);
	
	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); delay(100);
	this->serialCommunicator.establishKKIMSerialLink();
	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM); delay(100);
	
	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); delay(100);
	this->serialCommunicator.establishKNanoSerialLink();
	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM); delay(100);

	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); delay(100);
	this->serialCommunicator.establishNGHASerialLink();
	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM); delay(100);
	
	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); delay(100);
	this->serialCommunicator.establishNGHBSerialLink();
	this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM);

	this->nextMode = KMegaOperatingMode::STANDARD;
}

void KMegaService::standardOperatingMode() {

	bool sentInputRefreshPacket = false;
	bool gotOutputRefreshPacket = false;

	if ( (millis() - this->inputRefreshPacketLastSendTimeInMilliseconds) > INPUT_REFRESH_PACKET_SEND_RATE_IN_MILLISECONDS ) {
		sentInputRefreshPacket = true;
		this->controlPanel.refreshInputStatus();
		this->packetAssembler.assembleInputRefreshPacket();
		//this->displayPacket(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "inputRefreshPacket");//TODO verify. Old: //this->displayInputRefreshPacket();
		this->serialCommunicator.sendInputRefreshPacket();
		this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	}
	
	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	if ( this->serialCommunicator.getOutputRefreshPacket() ) {
		gotOutputRefreshPacket = true;
		this->outputRefreshPacketLastReceiveTimeInMilliseconds = millis();
		//this->displayPacket(outputRefreshPacket, OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "outputRefreshPacket");//TODO verify. Old: //this->displayOutputRefreshPacket();
		this->packetUnpacker.unpackOutputRefreshPacketIntoModel();
		
		this->controlPanel.writeLEDStatusToLEDDriverBoards();
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		this->packetAssembler.assembleGaugePacketA();
		this->serialCommunicator.sendGaugePacketA();
		this->packetAssembler.assembleGaugePacketB();
		this->serialCommunicator.sendGaugePacketB();
	}
	
	if ( (millis() - this->outputRefreshPacketLastReceiveTimeInMilliseconds) > MAX_TIME_WITHOUT_OUTPUT_REFRESH_PACKET_BEFORE_ERROR_IN_MILLISECONDS ) {
		this->updateCommsLEDToIndicateError();
	}
	
	if ( (millis() - this->outputRefreshPacketLastReceiveTimeInMilliseconds) > MAX_TIME_WITHOUT_OUTPUT_REFRESH_PACKET_BEFORE_IDLE_IN_MILLISECONDS ) {
		if(!this->outputsHaveBeenSetToIdleState) {
			this->controlPanel.setAllLEDsTo(PWM_LED_DIM);
			
			//this->packetAssembler.assembleNGHACalibrationRequest();
			//TODO Replace the following with the calibration request on the previous line. START...
			this->controlPanel.moduleC.stepper_HeatLife.setDesiredPosition(0);
			this->controlPanel.moduleC.stepper_Gforce.setDesiredPosition(0);
			this->controlPanel.moduleG.stepper_Mach.setDesiredPosition(0);
			this->controlPanel.moduleG.stepper_Pitch.setDesiredPosition(0);
			this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(0);
			this->controlPanel.moduleI.stepper_Fuel.setDesiredPosition(0);
			//TODO END...
			this->serialCommunicator.sendGaugePacketA();
			
			//this->packetAssembler.assembleNGHBCalibrationRequest();
			//TODO Replace the following with the calibration request on the previous line. START...
			this->controlPanel.moduleI.stepper_Charge.setDesiredPosition(0);
			this->controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPosition(0);
			this->controlPanel.moduleGT.stepper_Density.setDesiredPosition(0);
			this->controlPanel.moduleGT.stepper_Speed.setDesiredPosition(0);
			this->controlPanel.moduleGT.stepper_VertSpeed.setDesiredPosition(0);
			this->controlPanel.moduleGT.stepper_RadarAlt.setDesiredPosition(0);
			//TODO END...
			this->serialCommunicator.sendGaugePacketB();
			
			this->outputsHaveBeenSetToIdleState = true;
		}
	} else {
		this->outputsHaveBeenSetToIdleState = false;
	}
	
	//this->serialCommunicator.tallyCommunicationsDiagnosticData();
	//this->serialCommunicator.displayCommunicationsDiagnosticData();
	
	//TODO Idle if necessary
	delay(REFRESH_PERIOD_IN_MILLISECONDS); //TODO remove

	if (this->controlPanel.moduleH.switch_GlassCockpit_CL.getInputStatus()) {
		this->nextMode = KMegaOperatingMode::DIAGNOSTIC;
	} else if (this->controlPanel.moduleH.switch_GlassCockpit_CR.getInputStatus()) {
		this->nextMode = KMegaOperatingMode::SHUTDOWN;
	} else {
		this->nextMode = KMegaOperatingMode::STANDARD;
	}
}

void KMegaService::shutdownMode() {
	
	//this->packetAssembler.assembleNGHACalibrationRequest();
	//TODO Replace the following with the calibration request on the previous line. START...
	this->controlPanel.moduleC.stepper_HeatLife.setDesiredPosition(0);
	this->controlPanel.moduleC.stepper_Gforce.setDesiredPosition(0);
	this->controlPanel.moduleG.stepper_Mach.setDesiredPosition(0);
	this->controlPanel.moduleG.stepper_Pitch.setDesiredPosition(0);
	this->controlPanel.moduleG.stepper_Heading.setDesiredPosition(0);
	this->controlPanel.moduleI.stepper_Fuel.setDesiredPosition(0);
	//TODO END...
	this->serialCommunicator.sendGaugePacketA();

	//this->packetAssembler.assembleNGHBCalibrationRequest();
	//TODO Replace the following with the calibration request on the previous line. START...
	this->controlPanel.moduleI.stepper_Charge.setDesiredPosition(0);
	this->controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPosition(0);
	this->controlPanel.moduleGT.stepper_Density.setDesiredPosition(0);
	this->controlPanel.moduleGT.stepper_Speed.setDesiredPosition(0);
	this->controlPanel.moduleGT.stepper_VertSpeed.setDesiredPosition(0);
	this->controlPanel.moduleGT.stepper_RadarAlt.setDesiredPosition(0);
	//TODO END...
	this->serialCommunicator.sendGaugePacketB();
	
	serialCommunicator.teardownSerialLinks();

	controlPanel.moduleG.ledPWM_Comms.setPWM(PWM_LED_MINIMUM);
	controlPanel.setAllLEDsTo(PWM_LED_MINIMUM);
}

void KMegaService::updateCommsLEDToIndicateError() {
	if ( (millis() - this->commsLEDErrorStateLastToggleTimeInMilliseconds) > 1000 ) {
		if ( this->controlPanel.moduleG.ledPWM_Comms.getPWM() == PWM_LED_MAXIMUM ) {
			this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
		} else {
			this->controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM);
		}
		this->commsLEDErrorStateLastToggleTimeInMilliseconds = millis();
	}
}

void KMegaService::clearPacket(byte * packet, int packetLength) {
	for (int i = 0; i < packetLength; i++) {
		packet[i] = 0x00;
	}
}

void KMegaService::displayPacket(const byte * packet, int packetLength, String packetName) {//TODO verify
	Serial.println(F("KMegaService.displayPacket(): (decimal format)"));
	Serial.print("Packet: "); Serial.println(packetName);
	Serial.print("Byte Num: ");
	for (int i = 0; i < packetLength; i++) {
		Serial.print(i+1);
		Serial.print("\t");
	}
	Serial.println();
	
	Serial.print("Value:    ");
	for (int i = 0; i < packetLength; i++) {
		Serial.print(packet[i]);
		Serial.print("\t");
	}
	Serial.println();
}

void KMegaService::testAltitudeGauge() {
	while (true) {
		
		//float number = -345678;
		//int exponent = floor(log10(abs(number)));										//e.g., 5
		//float three_digit_unrounded_mantissa = number / pow(10,(exponent-2));			//e.g., -321.75
		//int three_digit_rounded_mantissa = round(three_digit_unrounded_mantissa);		//e.g., -322
		//float two_digit_unrounded_mantissa = number / pow(10,(exponent-1));				//e.g., -32.175
		//int two_digit_rounded_mantissa = round(two_digit_unrounded_mantissa);			//e.g., -32
		//float one_digit_unrounded_mantissa = number / pow(10,exponent);					//e.g., -3.2175
		//int one_digit_rounded_mantissa = round(one_digit_unrounded_mantissa);			//e.g., -3
		//
		//Serial.print("number: ");Serial.println(number);
		//Serial.print("exponent: ");Serial.println(exponent);
		//Serial.print("three_digit_unrounded_mantissa: ");Serial.println(three_digit_unrounded_mantissa);
		//Serial.print("three_digit_rounded_mantissa: ");Serial.println(three_digit_rounded_mantissa);
		//Serial.print("two_digit_unrounded_mantissa: ");Serial.println(two_digit_unrounded_mantissa);
		//Serial.print("two_digit_rounded_mantissa: ");Serial.println(two_digit_rounded_mantissa);
		//Serial.print("one_digit_unrounded_mantissa: ");Serial.println(one_digit_unrounded_mantissa);
		//Serial.print("one_digit_rounded_mantissa: ");Serial.println(one_digit_rounded_mantissa);
		//delay(5000);
		
		//nnE
		this->controlPanel.moduleGT.altitude = -99500;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//-99km
		this->controlPanel.moduleGT.altitude = -99499;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//-10km
		this->controlPanel.moduleGT.altitude = -9950;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//-9.9km
		this->controlPanel.moduleGT.altitude = -9949;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//-1.0km
		this->controlPanel.moduleGT.altitude = -950;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//-0.9km
		this->controlPanel.moduleGT.altitude = -949;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//-0.1km
		this->controlPanel.moduleGT.altitude = -99.5;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//-99m
		this->controlPanel.moduleGT.altitude = -99.4;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//-10m
		this->controlPanel.moduleGT.altitude = -10;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//-10m
		this->controlPanel.moduleGT.altitude = -9.5;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//-9m
		this->controlPanel.moduleGT.altitude = -9.4;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//-1m
		this->controlPanel.moduleGT.altitude = -0.95;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//-0.9m
		this->controlPanel.moduleGT.altitude = -0.94;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//-0.1m
		this->controlPanel.moduleGT.altitude = -0.1;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//0m
		this->controlPanel.moduleGT.altitude = -0.095;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//0m
		this->controlPanel.moduleGT.altitude = 0.095;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//0.1m
		this->controlPanel.moduleGT.altitude = 0.1;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//0.9m
		this->controlPanel.moduleGT.altitude = 0.94;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//1.0m
		this->controlPanel.moduleGT.altitude = 0.95;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//9.9m
		this->controlPanel.moduleGT.altitude = 9.94;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//10.0m
		this->controlPanel.moduleGT.altitude = 9.95;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//99.9m
		this->controlPanel.moduleGT.altitude = 99.94;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//100m
		this->controlPanel.moduleGT.altitude = 99.95;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//999m
		this->controlPanel.moduleGT.altitude = 999.4;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//1.00km
		this->controlPanel.moduleGT.altitude = 999.5;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//9.99km
		this->controlPanel.moduleGT.altitude = 9994;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//10.0km
		this->controlPanel.moduleGT.altitude = 9995;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//99.9km
		this->controlPanel.moduleGT.altitude = 99940;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//100km
		this->controlPanel.moduleGT.altitude = 99950;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//999km
		this->controlPanel.moduleGT.altitude = 999400;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//1.00Mm
		this->controlPanel.moduleGT.altitude = 999500;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//9.99Mm
		this->controlPanel.moduleGT.altitude = 9994000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//10.0Mm
		this->controlPanel.moduleGT.altitude = 9995000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//99.9Mm
		this->controlPanel.moduleGT.altitude = 99940000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//100Mm
		this->controlPanel.moduleGT.altitude = 99950000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//999Mm
		this->controlPanel.moduleGT.altitude = 999400000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//1.00Gm
		this->controlPanel.moduleGT.altitude = 999500000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//9.99Gm
		this->controlPanel.moduleGT.altitude = 9994000000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//10.0Gm
		this->controlPanel.moduleGT.altitude = 9995000000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//99.9Gm
		this->controlPanel.moduleGT.altitude = 99940000000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//100Gm
		this->controlPanel.moduleGT.altitude = 99950000000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		//999Gm
		this->controlPanel.moduleGT.altitude = 999400000000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
		
		//LnE
		this->controlPanel.moduleGT.altitude = 999500000000;
		this->packetAssembler.assembleAltitudePacket();
		this->serialCommunicator.sendAltitudePacket();
		delay(6000);
	}
}