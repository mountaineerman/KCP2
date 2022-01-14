#include <Arduino.h>
#include <StepperMotor.h>
#include <AccelStepper.h>
#include "..\..\configuration.h"

StepperMotor::StepperMotor(uint8_t pinStep, uint8_t pinDirection)
	: stepper(AccelStepper::DRIVER, this->pinStep, this->pinDirection) {

	this->pinStep = pinStep;
	this->pinDirection = pinDirection;
	this->kkimDesiredPosition = 0;

	//Finish stepper setup:
	this->stepper.setMaxSpeed(STEPPER_MAX_SPEED);
	this->stepper.setAcceleration(STEPPER_MAX_ACCELERATION);

	this->hardResetMotorPosition();
}

void StepperMotor::setKkimDesiredPosition(long desiredPosition) {
	this->kkimDesiredPosition = desiredPosition;
	this->stepper.moveTo(this->kkimDesiredPosition);
}

void StepperMotor::run() {
	this->stepper.run();
}

void StepperMotor::softResetMotorPosition() {
	this->stepper.setCurrentPosition(STEPPER_SOFT_RESET_SIZE);
	this->stepper.runToNewPosition(STEPPER_CCW_LIMIT); //TODO replace with non-blocking mechanism
}

void StepperMotor::hardResetMotorPosition() {
	this->stepper.setCurrentPosition(STEPPER_CCW_LIMIT);
	this->stepper.runToNewPosition(STEPPER_CW_LIMIT);  //TODO replace with non-blocking mechanism
	this->stepper.runToNewPosition(STEPPER_CCW_LIMIT); //TODO replace with non-blocking mechanism
}

void StepperMotor::manualCalibration() {
	//TBD
}

void StepperMotor::gracefulShutdown() {
	this->stepper.runToNewPosition(STEPPER_CCW_LIMIT); //TODO replace with non-blocking mechanism
	//TODO ignore kkimDesiredPosition;
}

long StepperMotor::getKkimDesiredPosition() {
	return this->kkimDesiredPosition;
}

long StepperMotor::getCurrentPosition() {
	return this->stepper.currentPosition();
}
