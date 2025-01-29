#ifndef StepperMotor2_h
#define StepperMotor2_h

#include <Arduino.h>

/* x27.168 Geared Stepper Motor controlled via VID6606 Driver Chip.
 *
 * Maximum Rotation Angle = 315 degrees.
 * Steps per degree = 12.
 * Steps per full rotation = 3780.
 * See:
 *    -Stepper: https://www.adafruit.com/product/2424
 *    -Driver: https://www.tindie.com/products/propwashsim/vid6606-sti6606-4x-stepper-driver-board-kit/
 *
 * PRE-REQUISITE: Motor begins in ..._CCW_LIMIT position
 */
class StepperMotor2
{
public:
	StepperMotor2(int speed, int ccwLimit, int cwLimit);
	
	//Set the desired position. Does not move the stepper, for that you must call runStepperIfNecessary() or blockRunToDesiredPosition()
	void setDesiredPosition(int desiredPosition);
	
	//Set the desired position, relative to the current position (negative == CCW, positive == CW).
	void setDesiredRelativePosition(int desiredRelativePosition);
	
	//Check if the stepper needs to move. Move it one step if it does. Returns true if the motor is still running to the desired position.
	bool runStepperIfNecessary();
	
	//Move the stepper. Block until it is in position.
	void blockRunToDesiredPosition();
	
	//Returns the current position of the motor, according to the driver (not equal to desiredPosition)
	int getCurrentPosition();

	//Returns the desired position of the motor (not equal to currentPosition)
	int getDesiredPosition();
	
	void setSpeed(int speed);
	int getSpeed();
	long getTimeBetweenSteps();
	int get_ccwLimit();
	int get_cwLimit();
	
private:

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CONFIGURATION PARAMETERS
	
	//Stepper Motor Speed (steps per second).
	int speed;
	
	//Time (in microseconds) between motor steps. Directly proportional to "speed".
	long timeBetweenSteps;
	
	//The step number associated with the counter-clockwise limit of the stepper motor.
	int ccwLimit;
	
	//The step number associated with the clockwise limit of the stepper motor.
	int cwLimit;
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//DYNAMIC PARAMETERS
	/* A number describing the desired stepper motor position, in steps.
	 * Range: [0-3779] [STEPPER_CCW_LIMIT-GEARED_STEPPER_CW_LIMIT]
	 * e.g., desiredPosition of 0 is the farthest CCW position possible.
	 * e.g., desiredPosition of 3779 is the farthest CW position possible. */
	int desiredPosition;
	
	// A number describing the current stepper motor position, in steps.
	int currentPosition;
};

#endif
