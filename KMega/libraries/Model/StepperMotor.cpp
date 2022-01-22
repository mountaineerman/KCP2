#include <Arduino.h>
#include <StepperMotor.h>
#include <AccelStepper.h>
#include "..\..\configuration.h"

StepperMotor::StepperMotor(uint8_t pinStep, uint8_t pinDirection)
	: stepper(AccelStepper::DRIVER, pinStep, pinDirection) {

	this->pinStep = pinStep;
	this->pinDirection = pinDirection;
	this->desiredPosition = 0;

	this->stepper.setMaxSpeed(STEPPER_MAX_SPEED);
	this->stepper.setAcceleration(STEPPER_MAX_ACCELERATION);
	this->stepper.setPinsInverted(true);
}

void StepperMotor::setDesiredPosition(long desiredPosition) {
	this->desiredPosition = desiredPosition;
	this->stepper.moveTo(this->desiredPosition);
}

void StepperMotor::setDesiredRelativePosition(long desiredRelativePosition) {
	this->stepper.move(desiredRelativePosition);
}

void StepperMotor::run() {
	this->stepper.run();
}

void StepperMotor::runToDesiredPosition() {
	this->stepper.runToPosition();
}

void StepperMotor::resetStepperToStartingPosition() {
	this->setDesiredPosition(STEPPER_CCW_LIMIT);//TODO replace with non-blocking mechanism
	this->runToDesiredPosition();
}

long StepperMotor::getCurrentPosition() {
	return this->stepper.currentPosition();
}
