#include "Arduino.h"
#include "LED.h"

LED::LED(uint8_t pin) {
	this->pin = pin;
	this->state = false;
	pinMode(this->pin, OUTPUT);
}

void LED::setState(bool state) {
	this->state = state;
	if (this->state == true) {
		digitalWrite(this->pin, HIGH);
	} else {
		digitalWrite(this->pin, LOW);
	}
}

//bool LED::getState() {
//	return this->state;
//}