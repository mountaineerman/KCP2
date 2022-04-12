#include <Arduino.h>
#include <StepperMotor.h>
#include <AccelStepper.h>
#include "..\..\configuration.h"

StepperMotor::StepperMotor(uint8_t pinStep, uint8_t pinDirection, bool arePinsInverted)
	: stepper(AccelStepper::DRIVER, pinStep, pinDirection) {

	this->pinStep = pinStep;
	this->pinDirection = pinDirection;
	this->arePinsInverted = arePinsInverted;
	this->desiredPosition = 0;

	this->stepper.setMaxSpeed(STEPPER_MAX_SPEED);
	this->stepper.setAcceleration(STEPPER_MAX_ACCELERATION);
	this->stepper.setPinsInverted(this->arePinsInverted);
}

void StepperMotor::setDesiredPosition(long desiredPosition) {
	this->desiredPosition = desiredPosition;
	this->stepper.moveTo(this->desiredPosition);
}

void StepperMotor::setDesiredRelativePosition(long desiredRelativePosition) {
	this->stepper.move(desiredRelativePosition);
}

bool StepperMotor::runStepperIfNecessary() {
	return this->stepper.run();
	
//	bool isStillMoving = false;
//	unsigned long startTime = micros();
//	isStillMoving = this->stepper.run();
//	unsigned long endTime = micros();
//	Serial.print(isStillMoving); Serial.print(" "); Serial.println(endTime - startTime);
//	return isStillMoving;
}

void StepperMotor::runToDesiredPosition() {
	this->stepper.runToPosition();
}

long StepperMotor::getCurrentPosition() {
	return this->stepper.currentPosition();
}
