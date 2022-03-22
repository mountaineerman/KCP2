#include <Arduino.h>
#include <SwitchSP2T.h>
#include <MuxShield.h>

//Arduino-monitored switch
SwitchSP2T::SwitchSP2T(uint8_t pinNumber, bool isPullupInput) {
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
SwitchSP2T::SwitchSP2T(uint8_t muxColumnNumber, bool isPullupInput, uint8_t muxRowNumber, MuxShield& mux)
	: mux(mux)
{
	this->pinNumber = muxColumnNumber;
	this->isPullupInput = isPullupInput;
	this->isMuxInput = true;
	this->muxRowNumber = muxRowNumber;
	//this->mux = mux;

	//Note: Pullup mode is set for the multiplexer by row (separately), not by pin. See MuxShield::setMode(...)
}

void SwitchSP2T::refreshInputStatus() {

	bool rawStatus;
	
	if(this->isMuxInput) {
		rawStatus = this->mux.digitalReadMS(this->muxRowNumber,this->pinNumber);
	} else { //Read via Arduino
		rawStatus = digitalRead(this->pinNumber);
	}
	
	if(this->isPullupInput) {
		this->status = !rawStatus;
	} else {
		this->status = rawStatus;
	}
}

bool SwitchSP2T::getInputStatus() {
	return this->status;
}

String SwitchSP2T::getInputStatusAsString() {
	return String(this->status);
}

//Used for simulation only:
void SwitchSP2T::setStatus(bool simulatedStatus) {
	this->status = simulatedStatus;
}
