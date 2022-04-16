#ifndef LED_h
#define LED_h

#include "Arduino.h"

/* Light Emitting Diode controlled directly via Arduino
 */
class LED
{
public:
	LED(uint8_t pin);
	void setState(bool state);

private:
	 /* RANGE: 	Nano: Theoretical: 0-12, A0-A7
	 * 			Nano: By design:   A1-A4
	 * 			Mega: Theoretical: 0-53, A0-A15
	 * 			Mega: By design:   None
	 * 			 Mux: By design:   None */
	uint8_t pin;

	/* LED Output State:  true == ON
	 *                   false == OFF  */
	bool state;
};

#endif
