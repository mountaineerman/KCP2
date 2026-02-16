
#ifndef StepperMotor2_h
#define StepperMotor2_h

#include <Arduino.h>
#include "../../configuration.h"
#include <Interface_StepperMotorAggregator.h>

enum ClockTravelDirection {CW, CCW};

/* x27.168 Geared Stepper Motor controlled via VID6606 Driver Chip.
 *
 * Maximum Rotation Angle = 315 degrees.
 * Steps per degree = 12.
 * Steps per full rotation = 3780.
 * See:
 *    -Stepper: https://www.adafruit.com/product/2424
 *    -Driver: https://www.tindie.com/products/propwashsim/vid6606-sti6606-4x-stepper-driver-board-kit/
 *
 * PRE-REQUISITE: Motor begins in STEPPER_CCW_LIMIT position
 */
class StepperMotor2 : public Interface_StepperMotorAggregator
{
public:
	StepperMotor2(uint8_t pinStep, uint8_t pinDirection, bool arePinsInverted, int maxStepperSpeed, int ccwLimit, int cwLimit);
	
	//Set the desiredPosition (and travelStatus). Does not move the stepper, for that you must call runStepperIfNecessary() or blockRunToDesiredPosition().
	void setDesiredPositionAndTravelStatus(int desiredPosition);

	//Set the activeTravelDirection (and pinDirection) based on currentPosition and desiredPosition.
	void setActiveTravelDirectionAndPin();

	//Check if the stepper needs to move. Move it one step if it does. Returns true if the motor is still running to the desired position.
	bool runStepperIfNecessary();
	
	//Returns the current position of the motor, according to the driver (not equal to desiredPosition)
	int getCurrentPosition();
	
private:

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//CONFIGURATION PARAMETERS
	//The Step input to the driver. Low to High transition means to step.
	uint8_t pinStep;

	//The Direction input to the driver. High means forward, Low means reverse.
	uint8_t pinDirection;
	
	//The configuration of the motor. Some motors are inverted, others are not. I think this depends on the wiring.
	bool arePinsInverted;
	
	//See MAX_GEARED_STEPPER_SPEED.
	int maxStepperSpeed;
	
	//The step number associated with the counter-clockwise limit of the stepper motor.
	int ccwLimit;
	
	//The step number associated with the clockwise limit of the stepper motor.
	int cwLimit;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//DYNAMIC PARAMETERS

	/* A number describing the desired stepper motor position, in steps.
	 * Range: [0-3779] [STEPPER_CCW_LIMIT-STEPPER_CW_LIMIT]
	 * e.g., desiredPosition of 0 is the farthest CCW position possible.
	 * e.g., desiredPosition of 3779 is the farthest CW position possible. */
	int desiredPosition;
	
	//A number describing the current stepper motor position, in steps.
	int currentPosition;

	//The current speed of the stepper motor, in steps per second.
	int currentSpeed;

	//The variable minimum time required between motor steps to achieve "currentSpeed". Directly proportional to it.
	long minTimeBetweenStepsInMicroseconds;

	//The direction the stepper motor is moving in. Note: Pitch and Radar Altitude gauges are reversed. StepperMotor2 does not account for this, because the majority of gauges are not like this.
	ClockTravelDirection activeTravelDirection;

	//Is the stepper MOVING or STOPPED?
	TravelStatus travelStatus;

	//Used by runStepperIfNecessary() to decide when to step. Also updated by it.
	unsigned long lastStepTimeInMicroseconds;

	//A mode of the geared stepper motor that is triggered by a mid-motion direction change. See runStepperIfNecessary() for more information.
	bool brakeUntilSafeToChangeTravelDirection;
};

#endif
