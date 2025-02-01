#include <Arduino.h>
#include <StepperMotor2.h>
#include "..\..\configuration.h"


StepperMotor2::StepperMotor2(int speed, int ccwLimit, int cwLimit) {
	
	this->speed = speed;
	this->timeBetweenSteps = 1000000 / speed;
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