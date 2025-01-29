#ifndef NEMA17StepperMotor_h
#define NEMA17StepperMotor_h

#include <Arduino.h>

/* NEMA17 Stepper Motor controlled via the Sparkfun EasyDriver.
 *
 * Maximum Rotation Angle = Unlimited.
 * Minimum Step Angle (with 1/8th Microstep Resolution) = 0.225 degrees.
 * Steps per full rotation = 1600.
 *  See:
 *     -Motor: https://www.sparkfun.com/products/9238
 *     -Driver: https://www.sparkfun.com/products/12779
 *
 * PRE-REQUISITE: Motor begins in STEPPER_CCW_LIMIT position (pointing "North") */
class NEMA17StepperMotor
{
public:
	//Define a stepper motor and set its maximum speed/acceleration
	NEMA17StepperMotor();
	
	//Set the desired position. Does not move the stepper, for that you must call runStepperIfNecessary()
	void setDesiredPosition(int desiredPosition);
	
	//Set the desired position, relative to the current position
	void setDesiredRelativePosition(int desiredRelativePosition);
	
	//Returns the current position of the motor, according to the driver (not equal to desiredPosition)
	int getCurrentPosition();
	
	//Returns the desired position of the motor (not equal to currentPosition)
	int getDesiredPosition();

private:

	/* A number describing KKIM's desired stepper motor position, in steps.
	 * Range: [0-1599] [STEPPER_CCW_LIMIT-NEMA17_CW_LIMIT]
	 * e.g., desiredPosition of 0 means the heading gauge is pointing North.
	 * e.g., desiredPosition of 399 means the heading gauge is pointing East.
	 * e.g., desiredPosition of 799 means the heading gauge is pointing South.
	 * e.g., desiredPosition of 1199 means the heading gauge is pointing West. */
	int desiredPosition;

	//A number describing the current stepper motor position, in steps.
	int currentPosition;

	//The time the motor was last stepped, in microseconds
	unsigned long motorLastStepped;
};

#endif