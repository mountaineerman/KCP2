#include <Arduino.h>
#include <LED_PWM.h>
#include <Adafruit_TLC5947.h>

LED_PWM::LED_PWM(uint16_t channel, Adafruit_TLC5947& ledDriverBoards)
	: ledDriverBoards(ledDriverBoards)
{
	this->channel = channel;
	this->pwm = 0;
}

void LED_PWM::setPWM(uint16_t pwm) {
	this->pwm = pwm;
	this->ledDriverBoards.setPWM(this->channel, this->pwm);
	this->ledDriverBoards.write(); //TODO replace this with one call every refresh cycle
}

//Used for debugging only:
uint16_t LED_PWM::getPWM() {
	return this->pwm;
}
