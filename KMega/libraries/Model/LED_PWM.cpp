#include <Arduino.h>
#include <LED_PWM.h>
#include <Adafruit_TLC5947.h>
#include "../../configuration.h"

LED_PWM::LED_PWM(uint16_t channel, Adafruit_TLC5947& ledDriverBoards)
	: ledDriverBoards(ledDriverBoards)
{
	this->channel = channel;
	this->pwm = 0;
}

void LED_PWM::setPWM(int pwm) {
	
	if (pwm > PWM_LED_MAXIMUM) {
		pwm = PWM_LED_MAXIMUM;
	}
	
	if (pwm < PWM_LED_MINIMUM) {
		pwm = PWM_LED_MINIMUM;
	}
	
	this->pwm = pwm;
	this->ledDriverBoards.setPWM(this->channel, this->pwm);
}

void LED_PWM::setPWMAndWriteImmediately(int pwm) {
	this->setPWM(pwm);
	this->ledDriverBoards.write();
}