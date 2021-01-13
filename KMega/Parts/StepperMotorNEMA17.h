#ifndef StepperMotorNEMA17_h
#define StepperMotorNEMA17_h

#include "Arduino.h"
#include "AccelStepper.h"

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
class StepperMotorNEMA17
{
public:
	//Define a stepper motor and set its maximum speed/acceleration
	StepperMotorNEMA17(uint8_t pinStep, uint8_t pinDirection, uint8_t pinSleep, uint8_t pinMS1, uint8_t pinMS2);
	void setKkimDesiredPosition(long desiredPosition);
	void run();
	//Manually position the motor by controlling it with the Joystick.
	void manualCalibration();
	//Move the motor to its home (north-up) position.
	void gracefulShutdown();
protected:
	//Used for debugging only:
	long getKkimDesiredPosition();
	long getCurrentPosition();
private:
	uint8_t pinStep;	 //The Step input to the driver. Low to High transition means to step.
	uint8_t pinDirection;//The Direction input the driver. High means forward, Low means reverse.
	uint8_t pinSleep;	 //Sleep override. Bring LOW to disable outputs and minimize power consumption.
	uint8_t pinMS1;		 //MicroStep Input 1 (see constructor)
	uint8_t pinMS2;		 //MicroStep Input 2 (see constructor)

	/* A number describing KKIM's desired stepper motor position, in steps.
	 * Range: [0-1599] [STEPPER_CW_LIMIT-STEPPER_CCW_LIMIT]
	 * e.g., kkimDesiredPosition of 0 corresponds to North.
	 * e.g., kkimDesiredPosition of 1 corresponds to one step East of North.
	 * e.g., kkimDesiredPosition of 1599 corresponds to one step West of North. */
	long kkimDesiredPosition;

	AccelStepper stepper;
};

#endif
