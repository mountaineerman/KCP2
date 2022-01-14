#include <Arduino.h>
#include "..\..\configuration.h"
#include <StepperMotorNEMA17.h>
#include <AccelStepper.h>

StepperMotorNEMA17::StepperMotorNEMA17(uint8_t pinStep, uint8_t pinDirection, uint8_t pinSleep, uint8_t pinMS1, uint8_t pinMS2)
	: stepper(AccelStepper::DRIVER, this->pinStep, this->pinDirection) {

	this->pinStep = pinStep;
	this->pinDirection = pinDirection;
	this->pinSleep = pinSleep;
	this->pinMS1 = pinMS1;
	this->pinMS2 = pinMS2;
	this->kkimDesiredPosition = 0;

	/* Set MicroStep Resolution. Options:
	 * 		MS1	MS2	Resolution			Step Angle		Steps per Rotation
	 * 		0	0	Full step (2 phase)	1.8 degrees		200
	 * 		1	0	Half step			0.9 degrees		400
	 * 		0	1	Quarter step		0.45 degrees	800
	 * 		1	1	Eighth step			0.225 degrees 	1600	*/
	pinMode(this->pinMS1, OUTPUT);
	pinMode(this->pinMS2, OUTPUT);
	digitalWrite(this->pinMS1, HIGH);
	digitalWrite(this->pinMS2, HIGH);

	//Finish stepper setup:
	this->stepper.setMaxSpeed(NEMA17_MAX_SPEED);
	this->stepper.setAcceleration(NEMA17_MAX_ACCELERATION);
	this->stepper.setEnablePin(this->pinSleep);
	this->stepper.enableOutputs();
}

void StepperMotorNEMA17::setKkimDesiredPosition(long desiredPosition) {
	this->kkimDesiredPosition = desiredPosition;
	this->stepper.moveTo(this->kkimDesiredPosition);
}

void StepperMotorNEMA17::run() {
	this->stepper.run();
	//TODO Try adding hibernation later (AccelStepper:  disableOutputs(); enableOutputs();)
}

void StepperMotorNEMA17::manualCalibration() {
	//TODO
}

void StepperMotorNEMA17::gracefulShutdown() {
	this->stepper.runToNewPosition(NEMA17_CCW_LIMIT); //TODO replace with non-blocking mechanism
	//TODO ignore kkimDesiredPosition;
}

long StepperMotorNEMA17::getKkimDesiredPosition() {
	return this->kkimDesiredPosition;
}

long StepperMotorNEMA17::getCurrentPosition() {
	return this->stepper.currentPosition();
}
