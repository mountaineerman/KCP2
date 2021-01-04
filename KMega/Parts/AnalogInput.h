#ifndef AnalogInput_h
#define AnalogInput_h

#include "Arduino.h"

//NOTE: No input validation is performed
class AnalogInput
{
public:
	AnalogInput(uint8_t pinNumber);
	//void refreshStatus();
	//bool getStatus();
private:
	// Arduino Mega Range: A0-A15 (analog pins (aliases for 54-69))
	uint8_t pinNumber;
//	bool status;
};

#endif
