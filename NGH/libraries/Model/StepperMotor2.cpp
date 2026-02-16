
#include <Arduino.h>
#include <StepperMotor2.h>
#include "../../configuration.h"


StepperMotor2::StepperMotor2(uint8_t pinStep, uint8_t pinDirection, bool arePinsInverted, int maxStepperSpeed, int ccwLimit, int cwLimit) {
	
	this->arePinsInverted = arePinsInverted;
	
	this->pinStep = pinStep;
	pinMode(this->pinStep, OUTPUT);
	digitalWrite(this->pinStep, LOW);
	
	this->maxStepperSpeed = maxStepperSpeed;
	this->ccwLimit = ccwLimit;
	this->cwLimit = cwLimit;
	this->currentPosition = 0;
	this->setDesiredPositionAndTravelStatus(0);
	
	this->activeTravelDirection = ClockTravelDirection::CW;
	this->pinDirection = pinDirection;
	pinMode(this->pinDirection, OUTPUT);
	if (this->arePinsInverted) {
		digitalWrite(this->pinDirection, HIGH);
	} else {
		digitalWrite(this->pinDirection, LOW);
	}
	delayMicroseconds(STEPPER_MINIMUM_WAIT_FOR_DIRECTION_CHANGE_IN_MICROSECONDS);

	this->lastStepTimeInMicroseconds = micros();
    this->currentSpeed = MIN_GEARED_STEPPER_SPEED;
	this->minTimeBetweenStepsInMicroseconds = 1000000 / currentSpeed;
	this->brakeUntilSafeToChangeTravelDirection = false;
}

void StepperMotor2::setDesiredPositionAndTravelStatus(int desiredPosition) {
	
	if (desiredPosition < this->ccwLimit) {
		this->desiredPosition = this->ccwLimit;
	} else if (desiredPosition > this->cwLimit) {
		this->desiredPosition = this->cwLimit;
	} else {
		this->desiredPosition = desiredPosition;
	}

	if (this->desiredPosition == this->currentPosition) {
		this->travelStatus = TravelStatus::STOPPED;
	} else {
		this->travelStatus = TravelStatus::MOVING;
	}
}

void StepperMotor2::setActiveTravelDirectionAndPin() {
	
	if (this->currentPosition == this->desiredPosition) {
		return;
	}

	ClockTravelDirection requiredTravelDirection;
	if (this->currentPosition < this->desiredPosition ) {//desiredPosition is CW of currentPosition
		requiredTravelDirection = ClockTravelDirection::CW;
	} else {//desiredPosition is CCW of currentPosition
		requiredTravelDirection = ClockTravelDirection::CCW;
	}

	if (requiredTravelDirection == this->activeTravelDirection) {
		return;
	}

	if (requiredTravelDirection == ClockTravelDirection::CW) {
		if (this->arePinsInverted) {
			digitalWrite(this->pinDirection, HIGH);
		} else {
			digitalWrite(this->pinDirection, LOW);
		}
	} else {
		if (this->arePinsInverted) {
			digitalWrite(this->pinDirection, LOW);
		} else {
			digitalWrite(this->pinDirection, HIGH);
		}
	}
	delayMicroseconds(STEPPER_MINIMUM_WAIT_FOR_DIRECTION_CHANGE_IN_MICROSECONDS);
	this->activeTravelDirection = requiredTravelDirection;
}

bool StepperMotor2::runStepperIfNecessary() {
	
	// 1. Check if we are already there
	if (this->currentPosition == this->desiredPosition) {
		this->travelStatus = TravelStatus::STOPPED;
		this->currentSpeed = MIN_GEARED_STEPPER_SPEED; // Reset for next move
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
			if (this->currentPosition < this->desiredPosition && this->activeTravelDirection == ClockTravelDirection::CCW) {
				this->brakeUntilSafeToChangeTravelDirection = true;
			} else if (this->currentPosition > this->desiredPosition && this->activeTravelDirection == ClockTravelDirection::CW) {
				this->brakeUntilSafeToChangeTravelDirection = true;
			}
		}
	}

	// 5. Acceleration / Deceleration logic
	int distanceToGo = abs(this->desiredPosition - this->currentPosition);

	if (this->brakeUntilSafeToChangeTravelDirection == true) {//deceleration condition 1
		this->currentSpeed -= GEARED_STEPPER_ACCELERATION_RATE;
		if (this->currentSpeed <= MIN_GEARED_STEPPER_SPEED || this->currentPosition == STEPPER_CCW_LIMIT || this->currentPosition == STEPPER_CW_LIMIT) {//safe to change direction
			this->brakeUntilSafeToChangeTravelDirection = false;
			this->setActiveTravelDirectionAndPin();
		}
	} else if (distanceToGo < (this->currentSpeed / GEARED_STEPPER_DECELERATION_PARAM)) {//deceleration condition 2
		this->currentSpeed -= GEARED_STEPPER_ACCELERATION_RATE;
	} else if (this->currentSpeed < this->maxStepperSpeed) {//acceleration condition
        this->currentSpeed += GEARED_STEPPER_ACCELERATION_RATE;
    }

    if (this->currentSpeed > this->maxStepperSpeed) {
		this->currentSpeed = this->maxStepperSpeed;
	} else if (this->currentSpeed < MIN_GEARED_STEPPER_SPEED) {
		this->currentSpeed = MIN_GEARED_STEPPER_SPEED;
	}

    this->minTimeBetweenStepsInMicroseconds = 1000000 / this->currentSpeed;

    // 6. Take Step
    digitalWrite(this->pinStep, HIGH); //Note: transition from LOW to HIGH causes step
    delayMicroseconds(STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS);
    digitalWrite(this->pinStep, LOW); //Reset for future steps

	// 7. Update currentPosition
	if (this->activeTravelDirection == ClockTravelDirection::CW) {
		this->currentPosition++;
	} else {
		this->currentPosition--;
	}

    this->lastStepTimeInMicroseconds = now;
    return true;
}

int StepperMotor2::getCurrentPosition() {
	return this->currentPosition;
}