//[GaugePacketA] (does not apply to [GaugePacketB])
#ifndef NEMA17StepperMotor_h
#define NEMA17StepperMotor_h

#include <Arduino.h>
#include "../../configuration.h"
#include <Interface_StepperMotorAggregator.h>

enum HeadingTravelDirection {ASCENDING, DESCENDING}; //ASCENDING: e.g., N to E;  DESCENDING: e.g., N to W

/* NEMA17 Stepper Motor controlled via the Sparkfun EasyDriver.
 *
 * Maximum Rotation Angle = Unlimited.
 * Minimum Step Angle (with 1/8th Microstep Resolution) = 0.225 degrees.
 * Steps per full rotation = 1600.
 *  See:
 *     -Motor: https://www.sparkfun.com/products/9238
 *     -Driver: https://www.sparkfun.com/products/12779
 *
 * PRE-REQUISITE: Motor begins in STEPPER_CCW_LIMIT position (pointing "North") TODOME */
class NEMA17StepperMotor : public Interface_StepperMotorAggregator
{
public:
	//Define a stepper motor and set its maximum speed/acceleration
	NEMA17StepperMotor(uint8_t pinStep, uint8_t pinDirection, uint8_t pinSleep, uint8_t pinMS1, uint8_t pinMS2);
	
	//Set the desired position. Does not move the stepper, for that you must call runStepperIfNecessary()
	void setDesiredPositionAndTravelStatus(int desiredPosition);
	
	//Returns the shortest-path required HeadingTravelDirection based on currentPosition and desiredPosition.
	HeadingTravelDirection identifyRequiredTravelDirection();

	//Returns the shortest-path distance between currentPosition and desiredPosition.
	int identifyDistanceToGo();

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

	//The Direction input to the driver. HIGH means TBD_DOME, LOW means TBD_DOME.
	uint8_t pinDirection;

	//Sleep override. Bring LOW to disable outputs and minimize power consumption.
	uint8_t pinSleep;

	//MicroStep Input 1 (see constructor)
	uint8_t pinMS1;

	//MicroStep Input 2 (see constructor)
	uint8_t pinMS2;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//DYNAMIC PARAMETERS

	/* A number describing the desired stepper motor position, in steps.
	 * Range: [0-1599] [NEMA17_STEPPER_MIN_POSITION-NEMA17_STEPPER_MAX_POSITION]
	 * e.g., desiredPosition of 0 means the heading gauge is pointing North. TODOME
	 * e.g., desiredPosition of 399 means the heading gauge is pointing East.
	 * e.g., desiredPosition of 799 means the heading gauge is pointing South.
	 * e.g., desiredPosition of 1199 means the heading gauge is pointing West. */
	int desiredPosition;

	//A number describing the current stepper motor position, in steps.
	int currentPosition;

	//The current speed of the stepper motor, in steps per second.
	int currentSpeed;

	//The variable minimum time required between motor steps to achieve "currentSpeed". Directly proportional to it.
	long minTimeBetweenStepsInMicroseconds;

	//The direction the stepper motor is moving in.
	HeadingTravelDirection activeTravelDirection;

	//Is the stepper MOVING or STOPPED?
	TravelStatus travelStatus;

	//Used by runStepperIfNecessary() to decide when to step. Also updated by it.
	unsigned long lastStepTimeInMicroseconds;

	//A mode of the geared stepper motor that is triggered by a mid-motion direction change. See runStepperIfNecessary() for more information.
	bool brakeUntilSafeToChangeTravelDirection;
};

#endif