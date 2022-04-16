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
		digitalWrite(this->pin, LOW);
	} else {
		digitalWrite(this->pin, HIGH);
	}
}