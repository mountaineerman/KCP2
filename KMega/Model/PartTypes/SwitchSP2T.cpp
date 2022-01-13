#include <Arduino.h>
#include "C:\dev\KCP2\KMega\Model\PartTypes\SwitchSP2T.h"
#include "C:\dev\KCP2\KMega\LocalArduinoLibraries\MuxShield\MuxShield.h"

//Arduino-monitored switch
SwitchSP2T::SwitchSP2T(uint8_t pinNumber, bool isPullupInput) { // @suppress("Class members should be properly initialized")
	this->pinNumber = pinNumber;
	this->isPullupInput = isPullupInput;
	this->isMuxInput = false;

	if (this->isPullupInput) {
		pinMode(pinNumber, INPUT_PULLUP);
	} else {
		pinMode(pinNumber, INPUT);
	}
}

//Multiplexer-monitored switch
SwitchSP2T::SwitchSP2T(uint8_t muxColumnNumber, bool isPullupInput, uint8_t muxRowNumber, MuxShield& mux) { // @suppress("Class members should be properly initialized")
	this->pinNumber = muxColumnNumber;
	this->isPullupInput = isPullupInput;
	this->isMuxInput = true;
	this->muxRowNumber = muxRowNumber;
	this->mux = mux;

	//Note: Pullup mode is set for the multiplexer by row (separately), not by pin. See MuxShield::setMode(...)
}

void SwitchSP2T::refreshStatus() {
	if(this->isMuxInput) {
		this->status = this->mux.digitalReadMS(this->muxRowNumber,this->pinNumber);
	} else { //Read via Arduino
		this->status = digitalRead(this->pinNumber);
	}
}

bool SwitchSP2T::getStatus() {
	return this->status;
}

//Used for simulation only:
void SwitchSP2T::setStatus(bool simulatedStatus) {
	this->status = simulatedStatus;
}
