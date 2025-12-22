#include <Arduino.h>
#include <StepperMotor2.h>
#include "../../configuration.h"


StepperMotor2::StepperMotor2(int maxStepperSpeed, int ccwLimit, int cwLimit) {
	
	this->maxStepperSpeed = maxStepperSpeed;
	this->ccwLimit = ccwLimit;
	this->cwLimit = cwLimit;
	this->setDesiredPosition(0);
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

int StepperMotor2::getDesiredPosition() {
	return this->desiredPosition;
}

int StepperMotor2::get_ccwLimit() {
	return this->ccwLimit;
}

int StepperMotor2::get_cwLimit() {
	return this->cwLimit;
}