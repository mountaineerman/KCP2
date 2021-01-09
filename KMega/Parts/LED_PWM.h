#ifndef LED_PWM_h
#define LED_PWM_h

#include "Arduino.h"
#include "Adafruit_TLC5947.h"

/* Note: theoretically, a PWM LED could be driven by an Arduino Board or a driver board.
 * However, for KCP2, the hardware design is such that all PWM LEDs are driven by the
 * TLC5947 LED Driver Chip (http://www.adafruit.com/products/1429). This class is written
 * with this limitation in mind.
 *
 * No input validation is performed. */
class LED_PWM
{
public:
	LED_PWM(uint16_t channel, Adafruit_TLC5947& ledDriverBoards);
	void setPWM(uint16_t pwm);
protected:
	//Used for debugging only:
	uint16_t getPWM();
private:
	/* Range: Board1: 0-23,
	 * 		  Board2: 24-47,
	 * 		  Board3: 48-71,
	 * 		  Board4: 72-95 */
	uint16_t channel;
	/* The PWM value sent to the TLC5947 LED Driver Chip. Range: [0-4095]. For more information, see:
	 *     -Adafruit_TLC5947.h
	 *     -http://www.adafruit.com/products/1429 */
	uint16_t pwm;
	Adafruit_TLC5947& ledDriverBoards;
};

#endif
