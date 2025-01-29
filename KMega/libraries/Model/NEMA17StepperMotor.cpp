#include <Arduino.h>
#include "..\..\configuration.h"
#include <NEMA17StepperMotor.h>


NEMA17StepperMotor::NEMA17StepperMotor() {

	this->setDesiredPosition(NEMA17_STEPPER_MIN_POSITION);
	this->currentPosition = NEMA17_STEPPER_MIN_POSITION;
	this->motorLastStepped = micros();
}

void NEMA17StepperMotor::setDesiredPosition(int desiredPosition) {

	if (desiredPosition < NEMA17_STEPPER_MIN_POSITION) {
		this->desiredPosition = NEMA17_STEPPER_MIN_POSITION;
	} else if (desiredPosition > NEMA17_STEPPER_MAX_POSITION) {
		this->desiredPosition = NEMA17_STEPPER_MAX_POSITION;
	} else {
		this->desiredPosition = desiredPosition;
	}
}

void NEMA17StepperMotor::setDesiredRelativePosition(int desiredRelativePosition) {
	int requestedPosition = this->currentPosition + desiredRelativePosition;
	int rangeCorrectedRequestedPosition = -1;
	if (requestedPosition < NEMA17_STEPPER_MIN_POSITION) {
		rangeCorrectedRequestedPosition = requestedPosition + NEMA17_STEPPER_NUMBER_OF_POSSIBLE_POSITIONS;
	} else if (requestedPosition > NEMA17_STEPPER_MAX_POSITION) {
		rangeCorrectedRequestedPosition = requestedPosition - NEMA17_STEPPER_NUMBER_OF_POSSIBLE_POSITIONS;
	} else {
		rangeCorrectedRequestedPosition = requestedPosition;
	}
	this->setDesiredPosition(rangeCorrectedRequestedPosition);
}

int NEMA17StepperMotor::getCurrentPosition() {
	return this->currentPosition;
}

int NEMA17StepperMotor::getDesiredPosition() {
	return this->desiredPosition;
}