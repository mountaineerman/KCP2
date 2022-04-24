#include <Arduino.h>
#include <StepperMotor2.h>
#include "..\..\configuration.h"

//TODO Remove AccelStepper library after testing this one works

StepperMotor2::StepperMotor2(uint8_t pinStep, uint8_t pinDirection, bool arePinsInverted, int maxSpeed, int ccwLimit, int cwLimit) {
	
	this->pinStep = pinStep;
	pinMode(this->pinStep, OUTPUT);
	digitalWrite(this->pinStep, LOW);
	
	this->pinDirection = pinDirection;
	pinMode(this->pinDirection, OUTPUT);
	
	this->arePinsInverted = arePinsInverted;
	this->maxSpeed = maxSpeed;
	this->maxTimeBetweenSteps = 1000000 / maxSpeed;
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
		if (this->currentPosition < this->desiredPosition) {//Set direction: Clockwise
			if (this->arePinsInverted) {
				digitalWrite(this->pinDirection, HIGH);
			} else {
				digitalWrite(this->pinDirection, LOW);
			}
			this->currentPosition++;
		} else {//Set direction: Counter-Clockwise
			if (this->arePinsInverted) {
				digitalWrite(this->pinDirection, LOW);
			} else {
				digitalWrite(this->pinDirection, HIGH);
			}
			this->currentPosition--;
		}
		//Step:
		digitalWrite(this->pinStep, HIGH); //Note: transition from LOW to HIGH causes step
		delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
		digitalWrite(this->pinStep, LOW); //Reset for future steps
		return true;
	}
}

void StepperMotor2::blockRunToDesiredPosition() {
	while(this->runStepperIfNecessary()) {
		delayMicroseconds(this->maxTimeBetweenSteps - STEPPER_AVERAGE_RUNSTEPPERIFNECESSARY_TIME_IN_MICROSECONDS);
	}
}

int StepperMotor2::getCurrentPosition() {
	return this->currentPosition;
}

int StepperMotor2::get_maxSpeed() {
	return this->maxSpeed;
}

long StepperMotor2::get_maxTimeBetweenSteps() {
	return this->maxTimeBetweenSteps;
}

int StepperMotor2::get_ccwLimit() {
	return this->ccwLimit;
}

int StepperMotor2::get_cwLimit() {
	return this->cwLimit;
}