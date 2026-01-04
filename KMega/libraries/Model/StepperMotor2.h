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
	StepperMotor2(int maxStepperSpeed, int ccwLimit, int cwLimit);
	
	//Set the desired position
	void setDesiredPosition(int desiredPosition);
	
	//Returns the desired position of the motor
	int getDesiredPosition();
	
	int get_ccwLimit();
	int get_cwLimit();
	
private:

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CONFIGURATION PARAMETERS
	
	//Stepper Motor Speed (steps per second).
	int maxStepperSpeed;
	
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
};

#endif
