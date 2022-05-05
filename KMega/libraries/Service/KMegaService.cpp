#include <KMegaService.h>
#include <SerialCommunicator.h>

KMegaService::KMegaService()
	: controlPanel()
	, serialCommunicator()
	, packetUnpacker(controlPanel)
	, packetAssembler(controlPanel)
{	
	this->outputRefreshPacket[OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(outputRefreshPacket, OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setOutputRefreshPacket(outputRefreshPacket);
	this->packetUnpacker.setOutputRefreshPacket(outputRefreshPacket);
	
	this->altitudePacket[ALTITUDE_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(altitudePacket, ALTITUDE_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setAltitudePacket(altitudePacket);
	this->packetAssembler.setAltitudePacket(altitudePacket);
	
	this->inputRefreshPacket[INPUT_REFRESH_PACKET_LENGTH_IN_BYTES] = {};
	this->clearPacket(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES);
	this->serialCommunicator.setInputRefreshPacket(inputRefreshPacket);
	this->packetAssembler.setInputRefreshPacket(inputRefreshPacket);
	
	this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	
	
	this->startupMode(); //TODO move out of constructor
	
	//this->testAltitudeGauge();
	
	this->controlPanel.moduleH.ledPWM_GlassCockpit_CL.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Button for DiagnosticMode
	this->controlPanel.moduleH.ledPWM_GlassCockpit_CR.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); //Indicate Button for graceful shutdown of Control Panel
	while (true) {//TODO move out of constructor
		
		if (this->controlPanel.moduleH.switch_GlassCockpit_CL.getInputStatus()) {
			this->controlPanel.runDiagnosticMode();
			this->shutdownMode();
			break;
		} else if (this->controlPanel.moduleH.switch_GlassCockpit_CR.getInputStatus()) {
			this->shutdownMode();
			break;
		} else {
			this->standardOperatingMode();
		}
	}
}

void KMegaService::startupMode() {

	controlPanel.setAllLEDsOn();
	delay(1000);
	controlPanel.setAllLEDsOff();
	delay(100);
	
	controlPanel.sweepStepperMotorsThroughMaxMinToCalibrate();
	
	controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); delay(100);
	this->serialCommunicator.establishKKIMSerialLink();
	controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM); delay(100);
	
	controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MAXIMUM); delay(100);
	this->serialCommunicator.establishKNanoSerialLink();
	controlPanel.moduleG.ledPWM_Comms.setPWMAndWriteImmediately(PWM_LED_MINIMUM);
}

void KMegaService::standardOperatingMode() {

	long time1 = 0;
	long time2 = 0;
	long time3 = 0;
	long time4 = 0;
	long time5 = 0;
	long time6 = 0;
	long time7 = 0;
	long time8 = 0;
	long time9 = 0;
	long time10 = 0;
	long time11 = 0;
	long time12 = 0;
	bool sentInputRefreshPacket = false;
	bool gotOutputRefreshPacket = false;


	if ( (millis() - this->inputRefreshPacketLastSendTimeInMilliseconds) > INPUT_REFRESH_PACKET_SEND_RATE_IN_MILLISECONDS ) {
		sentInputRefreshPacket = true;
		time1 = millis();
		this->controlPanel.refreshInputStatus();
		time2 = millis();
		this->packetAssembler.assembleInputRefreshPacket();
		time3 = millis();
		//this->displayPacket(inputRefreshPacket, INPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "inputRefreshPacket");//TODO verify. Old: //this->displayInputRefreshPacket();
		this->serialCommunicator.sendInputRefreshPacket();
		time4 = millis();
		this->inputRefreshPacketLastSendTimeInMilliseconds = millis();
	}
	
	time5 = millis();
	this->serialCommunicator.ingestDataFromSerialBufferToPacketBuffer();
	time6 = millis();
	if ( this->serialCommunicator.getOutputRefreshPacket() ) {
		gotOutputRefreshPacket = true;
		//this->displayPacket(outputRefreshPacket, OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES, "outputRefreshPacket");//TODO verify. Old: //this->displayOutputRefreshPacket();
		time7 = millis();
		this->packetUnpacker.unpackOutputRefreshPacketIntoModel();
		
		//TODO remove eventually:
		//this->serialCommunicator.sendKKIMTerminalDisplayPacket("Hello world!", 12);
		//char floatAsCharArray[50] = {};
		//dtostrf(float_value, min_width, num_digits_after_decimal, where_to_store_string)
		//dtostrf(this->controlPanel.moduleGT.altitude, 50, 45, floatAsCharArray);
		//this->serialCommunicator.sendKKIMTerminalDisplayPacket("altitude: ",10);
		//this->serialCommunicator.sendKKIMTerminalDisplayPacket(floatAsCharArray,50);
		
		time8 = millis();
		this->controlPanel.writeLEDStatusToLEDDriverBoards();
		time9 = millis();
		this->packetAssembler.assembleAltitudePacket();
		time10 = millis();
		this->serialCommunicator.sendAltitudePacket();
		time11 = millis();
	}
	
	//this->serialCommunicator.tallyCommunicationsDiagnosticData();
	//this->serialCommunicator.displayCommunicationsDiagnosticData();
	time12 = millis();
	
	this->controlPanel.runStepperIfNecessary();
	
//	if (sentInputRefreshPacket && gotOutputRefreshPacket) {
//		Serial.println();
//		Serial.print("controlPanel.refreshInputStatus: "); Serial.println(time2 - time1);
//		Serial.print("packetAssembler.assembleInputRefreshPacket: "); Serial.println(time3 - time2);
//		Serial.print("serialCommunicator.sendInputRefreshPacket: "); Serial.println(time4 - time3);
//		Serial.print("serialCommunicator.ingestDataFromSerialBufferToPacketBuffer: "); Serial.println(time6 - time5);
//		Serial.print("serialCommunicator.getOutputRefreshPacket: "); Serial.println(time7 - time6);
//		Serial.print("packetUnpacker.unpackOutputRefreshPacketIntoModel: "); Serial.println(time8 - time7);
//		Serial.print("controlPanel.writeLEDStatusToLEDDriverBoards: "); Serial.println(time9 - time8);
//		Serial.print("packetAssembler.assembleAltitudePacket: "); Serial.println(time10 - time9);
//		Serial.print("serialCommunicator.sendAltitudePacket: "); Serial.println(time11 - time10);
//		Serial.print("Total: "); Serial.println(time12 - time1);
//	}
	
	//TODO Idle if necessary
	delay(REFRESH_PERIOD_IN_MILLISECONDS); //TODO remove
}

//void KMegaService::diagnosticMode() {
//	//controlPanel.runDiagnosticMode();
//}

void KMegaService::shutdownMode() {
	
	serialCommunicator.teardownSerialLinks();

	controlPanel.blockRunAllSteppersToPosition(STEPPER_CCW_LIMIT);
	
	controlPanel.setAllLEDsOff();
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
		-10km
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
		9.9m
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