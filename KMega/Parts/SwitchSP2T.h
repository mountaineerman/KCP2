#ifndef SwitchSP2T_h
#define SwitchSP2T_h

#include "Arduino.h"
#include "../LocalArduinoLibraries/MuxShield.h"

//NOTE: No input validation is performed
class SwitchSP2T
{
public:
	//Arduino-monitored switch
	SwitchSP2T(uint8_t pinNumber, bool isPullupInput);
	//Multiplexer-monitored switch
	SwitchSP2T(uint8_t muxColumnNumber, bool isPullupInput, uint8_t muxRowNumber, MuxShield& mux);
	void refreshStatus();
	bool getStatus();
protected:
	//Used for simulation only:
	void setStatus(bool simulatedStatus);
private:
	/* Arduino Mega Range: 0-53 (digital pins), A0-A15 (analog pins (aliases for 54-69)).
	 * Multiplexer Range: 0-15 */
	uint8_t pinNumber;
	bool isPullupInput;
	bool isMuxInput; //If true, it is a Multiplexer input. If false, it is an Arduino Mega input.
	uint8_t muxRowNumber; //Range: 1-3
	/* TRUE = HIGH (ON)
	 * FALSE = LOW (OFF) */
	bool status;
	MuxShield& mux;
};

#endif
