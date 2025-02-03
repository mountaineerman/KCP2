#include <Arduino.h>
#include <StepperMotor2.h>
#include "..\..\configuration.h"



StepperMotor2::StepperMotor2(uint8_t pinStep, uint8_t pinDirection, bool arePinsInverted, int speed, int ccwLimit, int cwLimit) {
	
	this->arePinsInverted = arePinsInverted;
	
	this->pinStep = pinStep;
	pinMode(this->pinStep, OUTPUT);
	digitalWrite(this->pinStep, LOW);
	
	this->pinDirection = pinDirection;
	pinMode(this->pinDirection, OUTPUT);
	this->isDirectionPinInCWMode = true;
	if (this->arePinsInverted) {
		digitalWrite(this->pinDirection, HIGH);
	} else {
		digitalWrite(this->pinDirection, LOW);
	}
	
	this->speed = speed;
	this->timeBetweenSteps = 1000000 / speed;
	this->ccwLimit = ccwLimit;
	this->cwLimit = cwLimit;
	this->setDesiredPosition(0);
	this->currentPosition = 0;
}

void StepperMotor2::setDesiredPosition(int desiredPosition) {
	
	if (desiredPosition < this->ccwLimit) {
		this->desiredPosition = this->ccwLimit;
	} else if (desiredPosition > this->cwLimit) {
		this->desiredPosition = this->cwLimit;
	} else {
		this->desiredPosition = desiredPosition;
	}
}

void StepperMotor2::setDesiredRelativePosition(int desiredRelativePosition) {

	this->setDesiredPosition(this->currentPosition + desiredRelativePosition);
}

bool StepperMotor2::runStepperIfNecessary() {
	
	if (this->currentPosition == this->desiredPosition) {
		return false;
	} else {
		//Update current position; Set direction if it needs to change
		if (this->currentPosition < this->desiredPosition) {//Direction must be CW
			if (!this->isDirectionPinInCWMode) {//Only update direction pin if the current mode is CCW
				this->isDirectionPinInCWMode = true;
				if (this->arePinsInverted) {
					digitalWrite(this->pinDirection, HIGH);
				} else {
					digitalWrite(this->pinDirection, LOW);
				}
				delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
			}
			this->currentPosition++;

		} else {//Direction must be CCW
			if (this->isDirectionPinInCWMode) {//Only update direction pin if the current mode is CW
				this->isDirectionPinInCWMode = false;
				if (this->arePinsInverted) {
					digitalWrite(this->pinDirection, LOW);
				} else {
					digitalWrite(this->pinDirection, HIGH);
				}
				delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
			}
			this->currentPosition--;
		}

		//Step:
		digitalWrite(this->pinStep, HIGH); //Note: transition from LOW to HIGH causes step
		delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
		digitalWrite(this->pinStep, LOW); //Reset for future steps
		delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
		return true;
	}
}

void StepperMotor2::blockRunToDesiredPosition() {
	while(this->runStepperIfNecessary()) {
		delayMicroseconds(this->timeBetweenSteps - STEPPER_AVERAGE_RUNSTEPPERIFNECESSARY_TIME_IN_MICROSECONDS);
	}
}

int StepperMotor2::getCurrentPosition() {
	return this->currentPosition;
}

void StepperMotor2::setSpeed(int speed) {
	this->speed = speed;
	this->timeBetweenSteps = 1000000 / speed;
}

int StepperMotor2::getSpeed() {
	return this->speed;
}

long StepperMotor2::getTimeBetweenSteps() {
	return this->timeBetweenSteps;
}

int StepperMotor2::get_ccwLimit() {
	return this->ccwLimit;
}

int StepperMotor2::get_cwLimit() {
	return this->cwLimit;
}