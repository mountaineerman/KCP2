#include <Arduino.h>
#include <AnalogInput.h>

AnalogInput::AnalogInput(uint8_t pinNumber) { // @suppress("Class members should be properly initialized")
	this->pinNumber = pinNumber;
	//Note: no pinMode initialization is required for analog inputs
}

void AnalogInput::refreshPinReading() {
	this->pinReading = analogRead(this->pinNumber);
}

int AnalogInput::getPinReading() {
	return this->pinReading;
}

//Used for simulation only:
void AnalogInput::setPinReading(int simulatedPinReading) {
	this->pinReading = simulatedPinReading;
}
