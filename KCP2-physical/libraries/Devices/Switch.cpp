#include "Arduino.h"
#include "Switch.h"

/*
	pin - assigned pin on Arduino
	mode - options: INPUT, INPUT_PULLUP, or OUTPUT
*/
Switch::Switch(uint8_t pin, uint8_t mode) : m_pin(pin)
{
	pinMode(pin, mode);
}

void Switch::refreshState()
{
	m_state = digitalRead(m_pin);
}

bool Switch::getState() const
{
	return m_state;
}