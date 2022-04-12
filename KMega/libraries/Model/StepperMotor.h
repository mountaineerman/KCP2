#ifndef StepperMotor_h
#define StepperMotor_h

#include <Arduino.h>
#include <AccelStepper.h>
#include <Interface_StepperMotorAggregator.h>

/* x27.168 Geared Stepper Motor controlled via VID6606 Driver Chip.
 *
 * Note: this is basically a wrapper Class for AccelStepper, with KCP2-specific configuration and functionality added on.
 *
 * Maximum Rotation Angle = 315 degrees.
 * Steps per degree = 12.
 * Steps per full rotation = 3780.
 * See:
 *    -Stepper: https://www.adafruit.com/product/2424
 *    -Driver: https://www.tindie.com/products/propwashsim/vid6606-sti6606-4x-stepper-driver-board-kit/
 *
 * PRE-REQUISITE: Motor begins in STEPPER_CCW_LIMIT position
 * No input validation is performed: TODO remove*/
class StepperMotor : public Interface_StepperMotorAggregator
{
public:
	StepperMotor(uint8_t pinStep, uint8_t pinDirection, bool arePinsInverted);
	
	//Set the desired position. Does not move the stepper, for that you must call runStepperIfNecessary() or runToDesiredPosition()
	void setDesiredPosition(long desiredPosition);//TODO add error checking for out-of-bounds values.
	
	//Set the desired position, relative to the current position (negative == CCW, positive == CW).
	void setDesiredRelativePosition(long desiredRelativePosition);
	
	//Check if the stepper needs to move. Move it one step if it does. Returns true if the motor is still running to the desired position. See AccelStepper::run() for more info.
	bool runStepperIfNecessary();
	
	//Move the stepper. Block until it is in position. See AccelStepper::runToPosition() for more info.
	void runToDesiredPosition();
	
	//Returns the current position of the motor, according to the driver (not equal to desiredPosition)
	long getCurrentPosition();
	
private:
	//The Step input to the driver. Low to High transition means to step.
	uint8_t pinStep;

	//The Direction input the driver. High means forward, Low means reverse.
	uint8_t pinDirection;
	
	//The configuration of the motor. Some motors are inverted, others are not. I think this depends on the wiring.
	bool arePinsInverted;
	
	/* A number describing the desired stepper motor position, in steps.
	 * Range: [0-3779] [STEPPER_CCW_LIMIT-STEPPER_CW_LIMIT]
	 * e.g., desiredPosition of 0 is the farthest CCW position possible.
	 * e.g., desiredPosition of 3779 is the farthest CW position possible. */
	long desiredPosition;

	AccelStepper stepper;
};

#endif
