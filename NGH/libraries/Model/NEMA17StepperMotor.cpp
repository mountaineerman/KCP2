//[GaugePacketA] (does not apply to [GaugePacketB])
#include <Arduino.h>
#include "../../configuration.h"
#include <NEMA17StepperMotor.h>


NEMA17StepperMotor::NEMA17StepperMotor(uint8_t pinStep, uint8_t pinDirection, uint8_t pinSleep, uint8_t pinMS1, uint8_t pinMS2) {

	this->pinStep = pinStep;
	pinMode(this->pinStep, OUTPUT);
	digitalWrite(this->pinStep, LOW);

	this->currentPosition = 0;
	this->setDesiredPositionAndTravelStatus(0);

	this->activeTravelDirection = HeadingTravelDirection::ASCENDING;
	this->pinDirection = pinDirection;
	pinMode(this->pinDirection, OUTPUT);
	digitalWrite(this->pinDirection, HIGH);
	delayMicroseconds(STEPPER_MINIMUM_WAIT_FOR_DIRECTION_CHANGE_IN_MICROSECONDS);	

	/* Set MicroStep Resolution. Options:
	 * 		MS1	MS2	Resolution			Step Angle		Steps per Rotation
	 * 		0	0	Full step (2 phase)	1.8 degrees		200
	 * 		1	0	Half step			0.9 degrees		400
	 * 		0	1	Quarter step		0.45 degrees	800
	 * 		1	1	Eighth step			0.225 degrees 	1600	*/
	this->pinMS1 = pinMS1;
	this->pinMS2 = pinMS2;
	pinMode(this->pinMS1, OUTPUT);
	pinMode(this->pinMS2, OUTPUT);
	digitalWrite(this->pinMS1, HIGH);
	digitalWrite(this->pinMS2, HIGH);

	this->pinSleep = pinSleep;
	pinMode(this->pinSleep, OUTPUT);
	digitalWrite(this->pinSleep, HIGH);//Wake up

	this->lastStepTimeInMicroseconds = micros();
    this->currentSpeed = NEMA17_MIN_SPEED;
	this->minTimeBetweenStepsInMicroseconds = 1000000 / currentSpeed;
	this->brakeUntilSafeToChangeTravelDirection = false;
}

void NEMA17StepperMotor::setDesiredPositionAndTravelStatus(int desiredPosition) {

	if (desiredPosition < NEMA17_STEPPER_MIN_POSITION) {
		this->desiredPosition = NEMA17_STEPPER_MIN_POSITION;
	} else if (desiredPosition > NEMA17_STEPPER_MAX_POSITION) {
		this->desiredPosition = NEMA17_STEPPER_MAX_POSITION;
	} else {
		this->desiredPosition = desiredPosition;
	}

	if (this->desiredPosition == this->currentPosition) {
		this->travelStatus = TravelStatus::STOPPED;
	} else {
		this->travelStatus = TravelStatus::MOVING;
	}
}

HeadingTravelDirection NEMA17StepperMotor::identifyRequiredTravelDirection() {

	if (this->desiredPosition > this->currentPosition) {//TBD_DOME
		if (this->desiredPosition - this->currentPosition > NEMA17_STEPPER_HALF_OF_POSITIONS) {
			return HeadingTravelDirection::DESCENDING;
		} else {
			return HeadingTravelDirection::ASCENDING;
		}
	} else {//TBD_DOME
		if (this->currentPosition - this->desiredPosition > NEMA17_STEPPER_HALF_OF_POSITIONS) {
			return HeadingTravelDirection::ASCENDING;
		} else {
			return HeadingTravelDirection::DESCENDING;
		}
	}
}

int NEMA17StepperMotor::identifyDistanceToGo() {

	int rawDisplacement = abs(this->desiredPosition - this->currentPosition);
	
	if (rawDisplacement > NEMA17_STEPPER_HALF_OF_POSITIONS) {
		return NEMA17_TOTAL_NUMBER_OF_POSITIONS - rawDisplacement;
	} else {
		return rawDisplacement;
	}
}

void NEMA17StepperMotor::setActiveTravelDirectionAndPin() {
	
	if (this->currentPosition == this->desiredPosition) {
		return;
	}

	HeadingTravelDirection requiredTravelDirection = this->identifyRequiredTravelDirection();
	if (requiredTravelDirection == this->activeTravelDirection) {
		return;
	}

	if (requiredTravelDirection == HeadingTravelDirection::ASCENDING) {
		digitalWrite(this->pinDirection, HIGH);
	} else {
		digitalWrite(this->pinDirection, LOW);
	}
	delayMicroseconds(STEPPER_MINIMUM_WAIT_FOR_DIRECTION_CHANGE_IN_MICROSECONDS);
	this->activeTravelDirection = requiredTravelDirection;
}

bool NEMA17StepperMotor::runStepperIfNecessary() {

	// 1. Check if we are already there
	if (this->currentPosition == this->desiredPosition) {
		this->travelStatus = TravelStatus::STOPPED;
		this->currentSpeed = NEMA17_MIN_SPEED; // Reset for next move
		return false;
	}

	// 2. Timing Gate: Has enough time passed since the last step to do another step?
	unsigned long now = micros();
    if (now - this->lastStepTimeInMicroseconds < this->minTimeBetweenStepsInMicroseconds) {
        return true; 
    }

	// 3. Set travelDirection if starting movement
	if (this->travelStatus == TravelStatus::STOPPED) {
		this->setActiveTravelDirectionAndPin();

	// 4. Detect if a mid-motion direction change has occurred
	} else {//travelStatus == MOVING
		if (this->brakeUntilSafeToChangeTravelDirection == false) {
			HeadingTravelDirection requiredTravelDirection = this->identifyRequiredTravelDirection();
			if (requiredTravelDirection == HeadingTravelDirection::ASCENDING && this->activeTravelDirection == HeadingTravelDirection::DESCENDING) {
				this->brakeUntilSafeToChangeTravelDirection = true;
			} else if (requiredTravelDirection == HeadingTravelDirection::DESCENDING && this->activeTravelDirection == HeadingTravelDirection::ASCENDING) {
				this->brakeUntilSafeToChangeTravelDirection = true;
			}
		}
	}
	
	// 5. Acceleration / Deceleration logic
	if (this->brakeUntilSafeToChangeTravelDirection == true) {//deceleration condition 1
		this->currentSpeed -= NEMA17_ACCELERATION_RATE;
		if (this->currentSpeed <= NEMA17_MIN_SPEED) {//safe to change direction
			this->brakeUntilSafeToChangeTravelDirection = false;
			this->setActiveTravelDirectionAndPin();
		}
	} else if (this->identifyDistanceToGo() < (this->currentSpeed / NEMA17_DECELERATION_PARAM)) {//deceleration condition 2
		this->currentSpeed -= NEMA17_ACCELERATION_RATE;
	} else if (this->currentSpeed < NEMA17_MAX_SPEED) {//acceleration condition
        this->currentSpeed += NEMA17_ACCELERATION_RATE;
    }

    if (this->currentSpeed > NEMA17_MAX_SPEED) {
		this->currentSpeed = NEMA17_MAX_SPEED;
	} else if (this->currentSpeed < NEMA17_MIN_SPEED) {
		this->currentSpeed = NEMA17_MIN_SPEED;
	}

    this->minTimeBetweenStepsInMicroseconds = 1000000 / this->currentSpeed;
	
    // 6. Take Step
    digitalWrite(this->pinStep, HIGH); //Note: transition from LOW to HIGH causes step
    delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
    digitalWrite(this->pinStep, LOW); //Reset for future steps
	
	// 7. Update currentPosition
	if (this->activeTravelDirection == HeadingTravelDirection::ASCENDING) {
		if (this->currentPosition == NEMA17_STEPPER_MAX_POSITION) {
			this->currentPosition = NEMA17_STEPPER_MIN_POSITION;
		} else {
			this->currentPosition++;
		}
	} else {//activeTravelDirection == DESCENDING
		if (this->currentPosition == NEMA17_STEPPER_MIN_POSITION) {
			this->currentPosition = NEMA17_STEPPER_MAX_POSITION;
		} else {
			this->currentPosition--;
		}
	}

    this->lastStepTimeInMicroseconds = now;
    return true;
}


/*
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
*/

int NEMA17StepperMotor::getCurrentPosition() {
	return this->currentPosition;
}