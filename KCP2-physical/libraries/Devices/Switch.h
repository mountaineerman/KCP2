#ifndef Switch_h
#define Switch_h

#include "Arduino.h"


/*------------------------------------------------------------------------------

Purpose: Model of ON/OFF switch. Has child which models a momentary button.

------------------------------------------------------------------------------*/
class Switch
{
	private:
		bool m_state;
		uint8_t m_pin; //Range: 0-255
		
	public:
		Switch(uint8_t pin, uint8_t mode); //for pinMode(), see C:\Program Files (x86)\Arduino\hardware\arduino\avr\cores\arduino\wiring_digital.c
		void refreshState();
		bool getState() const;
		
};

#endif