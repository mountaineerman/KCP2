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
	this->desiredPosition = 0;

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
	//?this->stepper.setPinsInverted(this->arePinsInverted);
	this->stepper.setEnablePin(this->pinSleep);
	//this->stepper.enableOutputs(); //TODO what is this?
	this->stepper.disableOutputs();
}

void StepperMotorNEMA17::setDesiredPosition(long desiredPosition) {
	this->desiredPosition = desiredPosition;
	this->stepper.moveTo(this->desiredPosition);
}

void StepperMotorNEMA17::setDesiredRelativePosition(long desiredRelativePosition) {
	this->stepper.move(desiredRelativePosition);
}

void StepperMotorNEMA17::runStepperIfNecessary() {
	this->stepper.enableOutputs();
	this->stepper.run();
	this->stepper.disableOutputs();
	//TODO Try adding hibernation later (AccelStepper:  disableOutputs(); enableOutputs();)
}

void StepperMotorNEMA17::runToDesiredPosition() {
	this->stepper.enableOutputs();
	this->stepper.runToPosition();
	this->stepper.disableOutputs();
}

void StepperMotorNEMA17::resetStepperToStartingPosition() {
	this->setDesiredPosition(NEMA17_CCW_LIMIT);//TODO replace with non-blocking mechanism
	this->stepper.enableOutputs();
	this->runToDesiredPosition();
	this->stepper.disableOutputs();
}

long StepperMotorNEMA17::getCurrentPosition() {
	return this->stepper.currentPosition();
}
