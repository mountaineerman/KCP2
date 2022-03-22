#include <Arduino.h>
#include <AnalogInput.h>

AnalogInput::AnalogInput(uint8_t pinNumber) {
	this->pinNumber = pinNumber;
	//Note: no pinMode initialization is required for analog inputs
}

void AnalogInput::refreshInputStatus() {
	this->pinReading = analogRead(this->pinNumber);
}

int AnalogInput::getInputStatus() {
	return this->pinReading;
}

String AnalogInput::getInputStatusAsString() {
	return String(this->pinReading);
}

//Used for simulation only:
void AnalogInput::setPinReading(int simulatedPinReading) {
	this->pinReading = simulatedPinReading;
}
