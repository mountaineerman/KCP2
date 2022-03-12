#ifndef StepperMotorNEMA17_h
#define StepperMotorNEMA17_h

#include <Arduino.h>
#include <AccelStepper.h>
#include <Interface_StepperMotorAggregator.h>

/* NEMA17 Stepper Motor controlled via the Sparkfun EasyDriver.
 *
 * Note: this is basically a wrapper Class for AccelStepper, with KCP2-specific configuration and functionality added on.
 *
 * Maximum Rotation Angle = Unlimited.
 * Minimum Step Angle (with 1/8th Microstep Resolution) = 0.225 degrees.
 * Steps per full rotation = 1600.
 *  See:
 *     -Motor: https://www.sparkfun.com/products/9238
 *     -Driver: https://www.sparkfun.com/products/12779
 *
 * No input validation is performed */
class StepperMotorNEMA17 : public Interface_StepperMotorAggregator
{
public:
	//Define a stepper motor and set its maximum speed/acceleration
	StepperMotorNEMA17(uint8_t pinStep, uint8_t pinDirection, uint8_t pinSleep, uint8_t pinMS1, uint8_t pinMS2);
	
	//Set the desired position. Does not move the stepper, for that you must call run() or runToDesiredPosition()
	void setDesiredPosition(long desiredPosition);//TODO add error checking for out-of-bounds values.
	
	//Set the desired position, relative to the current position (negative == CCW, positive == CW).
	void setDesiredRelativePosition(long desiredRelativePosition);
	
	//Check if the stepper needs to move. Move it one step if it does. See AccelStepper::run() for more info.
	void run();
	
	//Move the stepper. Block until it is in position. See AccelStepper::runToPosition() for more info.
	void runToDesiredPosition();
	
	//Move the motor to its home (north-up) position.
	void resetStepperToStartingPosition();
	
	//Returns the current position of the motor, according to the driver (not equal to desiredPosition)
	long getCurrentPosition();
	
private:
	uint8_t pinStep;	 //The Step input to the driver. Low to High transition means to step.
	uint8_t pinDirection;//The Direction input the driver. High means forward, Low means reverse.
	uint8_t pinSleep;	 //Sleep override. Bring LOW to disable outputs and minimize power consumption.
	uint8_t pinMS1;		 //MicroStep Input 1 (see constructor)
	uint8_t pinMS2;		 //MicroStep Input 2 (see constructor)

	/* A number describing KKIM's desired stepper motor position, in steps.
	 * Range: [0-1599] [NEMA17_CCW_LIMIT-NEMA17_CW_LIMIT]
	 * e.g., kkimDesiredPosition of 0 corresponds to North.
	 * e.g., kkimDesiredPosition of 1 corresponds to one step East of North.
	 * e.g., kkimDesiredPosition of 1599 corresponds to one step West of North. */
	long desiredPosition;

	AccelStepper stepper;
};

#endif
