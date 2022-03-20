#ifndef LED_PWM_h
#define LED_PWM_h

#include <Arduino.h>
#include <Adafruit_TLC5947.h>

/* Light Emitting Diode capable of being dimmed via Pulse-Width Modulation
 *
 * Note: theoretically, a PWM LED could be driven by an Arduino Board or a driver board.
 * However, for KCP2, the hardware design is such that all PWM LEDs are driven by the
 * TLC5947 LED Driver Chip (http://www.adafruit.com/products/1429). This class is written
 * with this limitation in mind.
 */
class LED_PWM
{
public:
	LED_PWM(uint16_t channel, Adafruit_TLC5947& ledDriverBoards);
	
	//Sets the pwm level for the LED_PWM. Requires a call to controlPanel.writeLEDStatusToLEDDriverBoards() to "push" the value to the LED Driver Boards and make the LED change state.
	void setPWM(int pwm);
	//Sets the pwm level for the LED_PWM and "pushes" the value to the LED Driver Boards immediately.
	void setPWMAndWriteImmediately(int pwm);
	
private:
	/* Range: Board1: 0-23,
	 * 		  Board2: 24-47,
	 * 		  Board3: 48-71,
	 * 		  Board4: 72-95 */
	uint16_t channel;
	/* The PWM value sent to the TLC5947 LED Driver Chip. Range: [0-4095]. For more information, see:
	 *     -Adafruit_TLC5947.h
	 *     -http://www.adafruit.com/products/1429 */
	int pwm;
	Adafruit_TLC5947& ledDriverBoards;
};

#endif
