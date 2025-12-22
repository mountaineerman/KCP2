//[GaugePacketA] (does not apply to [GaugePacketB])
#include <Arduino.h>
#include "../../configuration.h"
#include <NEMA17StepperMotor.h>


NEMA17StepperMotor::NEMA17StepperMotor(uint8_t pinStep, uint8_t pinDirection, uint8_t pinSleep, uint8_t pinMS1, uint8_t pinMS2) {

	this->pinStep = pinStep;
	this->pinDirection = pinDirection;
	this->pinSleep = pinSleep;
	this->pinMS1 = pinMS1;
	this->pinMS2 = pinMS2;
	this->setDesiredPosition(NEMA17_STEPPER_MIN_POSITION);
	this->currentPosition = NEMA17_STEPPER_MIN_POSITION;
	this->motorLastStepped = micros();

	pinMode(this->pinStep, OUTPUT);
	pinMode(this->pinDirection, OUTPUT);
	pinMode(this->pinSleep, OUTPUT);
	pinMode(this->pinMS1, OUTPUT);
	pinMode(this->pinMS2, OUTPUT);

	this->isDirectionPinInCWMode = true;
	digitalWrite(this->pinDirection, LOW);
	digitalWrite(this->pinStep, LOW);

	/* Set MicroStep Resolution. Options:
	 * 		MS1	MS2	Resolution			Step Angle		Steps per Rotation
	 * 		0	0	Full step (2 phase)	1.8 degrees		200
	 * 		1	0	Half step			0.9 degrees		400
	 * 		0	1	Quarter step		0.45 degrees	800
	 * 		1	1	Eighth step			0.225 degrees 	1600	*/
	digitalWrite(this->pinMS1, HIGH);
	digitalWrite(this->pinMS2, HIGH);

	digitalWrite(this->pinSleep, HIGH);//Wake up

	// //Counter-act startup "twitch"
	// this->setDesiredPosition(1500);
	// this->runToDesiredPosition();
	// this->currentPosition = NEMA17_STEPPER_MIN_POSITION;
	// this->setDesiredPosition(NEMA17_STEPPER_MIN_POSITION);
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
	this->setDesiredPosition(this->currentPosition + desiredRelativePosition);
}

bool NEMA17StepperMotor::runStepperIfNecessary() {

	if (this->currentPosition == this->desiredPosition) {
		return false;
	} else {
		unsigned long currentTime = micros();
		if ( (currentTime - this->motorLastStepped ) > NEMA17_STEPPER_MIN_TIME_INTERVAL_BETWEEN_STEPS_IN_MICROSECONDS ) {
			this->motorLastStepped = currentTime;
			this->takeStep();
		}
		return true;
	}
}

void NEMA17StepperMotor::takeStep() {

	if (this->currentPosition == this->desiredPosition) {
		return;
	}

	//Update current position; Set direction if it needs to change
	if (this->desiredPosition > this->currentPosition) {
		if (this->desiredPosition - this->currentPosition > NEMA17_STEPPER_HALF_OF_POSITIONS) {
			this->setDirectionCWAndUpdateCurrentPosition();
		} else {
			this->setDirectionCCWAndUpdateCurrentPosition();
		}
	} else {
		if (this->currentPosition - this->desiredPosition > NEMA17_STEPPER_HALF_OF_POSITIONS) {
			this->setDirectionCCWAndUpdateCurrentPosition();
		} else {
			this->setDirectionCWAndUpdateCurrentPosition();
		}
	}

	//Step
	digitalWrite(this->pinStep, HIGH); //Note: transition from LOW to HIGH causes step
	delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
	digitalWrite(this->pinStep, LOW); //Reset for future steps
	delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
}

//Spin disc CCW (e.g., North > East)
void NEMA17StepperMotor::setDirectionCCWAndUpdateCurrentPosition() {
	if (this->isDirectionPinInCWMode) {//Only update direction pin if the current mode is CW
		this->isDirectionPinInCWMode = false;
		digitalWrite(this->pinDirection, HIGH);
		delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
	}
	this->currentPosition++;
	if (this->currentPosition > NEMA17_STEPPER_MAX_POSITION) {
		this->currentPosition = NEMA17_STEPPER_MIN_POSITION;
	}
}

//Spin disc CW (e.g., North > West)
void NEMA17StepperMotor::setDirectionCWAndUpdateCurrentPosition() {
	if (!this->isDirectionPinInCWMode) {//Only update direction pin if the current mode is CCW
		this->isDirectionPinInCWMode = true;
		digitalWrite(this->pinDirection, LOW);
		delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
	}
	this->currentPosition--;
	if (this->currentPosition < NEMA17_STEPPER_MIN_POSITION) {
		this->currentPosition = NEMA17_STEPPER_MAX_POSITION;
	}
}

void NEMA17StepperMotor::runToDesiredPosition() {

	while (this->runStepperIfNecessary()) {
		delayMicroseconds(50);
	}
}

int NEMA17StepperMotor::getCurrentPosition() {
	return this->currentPosition;
}