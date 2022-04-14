#include <Arduino.h>
#include <AnalogInput.h>

AnalogInput::AnalogInput(uint8_t pinNumber, bool isInverted) {
	this->pinNumber = pinNumber;
	this->isInverted = isInverted;
	//Note: no pinMode initialization is required for analog inputs
}

void AnalogInput::refreshInputStatus() {
	if (this->isInverted) {
		this->pinReading = ANALOG_INPUT_MAXIMUM - analogRead(this->pinNumber);
	} else {
		this->pinReading = analogRead(this->pinNumber);
	}
}

int AnalogInput::getInputStatus() {
	return this->pinReading;
}

String AnalogInput::getInputStatusAsString() {
	return String(this->pinReading);
}