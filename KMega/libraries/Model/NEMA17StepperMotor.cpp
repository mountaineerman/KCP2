#include <Arduino.h>
#include "..\..\configuration.h"
#include <NEMA17StepperMotor.h>


NEMA17StepperMotor::NEMA17StepperMotor() {
	this->setDesiredPosition(NEMA17_STEPPER_MIN_POSITION);
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

int NEMA17StepperMotor::getDesiredPosition() {
	return this->desiredPosition;
}