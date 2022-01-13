#ifndef AnalogInput_h
#define AnalogInput_h

#include <Arduino.h>

//NOTE: No input validation is performed
class AnalogInput
{
public:
	AnalogInput(uint8_t pinNumber);
	void refreshPinReading();
	int getPinReading();
protected:
	//Used for simulation only:
	void setPinReading(int simulatedPinReading);
private:
	// Arduino Mega Range: A0-A15 (analog pins (aliases for 54-69))
	uint8_t pinNumber;
	// Range: 0-1023
	int pinReading;
};

#endif
