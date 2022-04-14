#ifndef AnalogInput_h
#define AnalogInput_h

#include <Arduino.h>
#include <string.h>
#include <Interface_Input.h>

//NOTE: No input validation is performed
class AnalogInput : public Interface_Input
{
public:
	AnalogInput(uint8_t pinNumber, bool isInverted);
	void refreshInputStatus();
	String getInputStatusAsString();
	int getInputStatus(); //TODO rename
private:
	// Arduino Mega Range: A0-A15 (analog pins (aliases for 54-69))
	uint8_t pinNumber;
	bool isInverted;
	// Range: 0-ANALOG_INPUT_MAXIMUM(1023)
	int pinReading;
};

#endif
