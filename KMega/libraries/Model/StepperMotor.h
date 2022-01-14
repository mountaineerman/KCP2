#ifndef StepperMotor_h
#define StepperMotor_h

#include <Arduino.h>
#include <AccelStepper.h>

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
 * No input validation is performed */
class StepperMotor
{
public:
	//Define a stepper motor, set its maximum speed and acceleration, and perform "soft" calibration
	StepperMotor(uint8_t pinStep, uint8_t pinDirection);
	void setKkimDesiredPosition(long desiredPosition);
	void run();
	//Reset the motor position by assuming it is near its CCW limit and turning CCW a little.
	void softResetMotorPosition();
	//Reset the motor position by turning to its CW limit, then CCW limit.
	void hardResetMotorPosition();
	//Manually position the motor at its CCW limit by controlling it with the Joystick.
	void manualCalibration();
	//Move the motor to its CCW limit.
	void gracefulShutdown();
protected:
	//Used for debugging only:
	long getKkimDesiredPosition();
	long getCurrentPosition();
private:
	//The Step input to the driver. Low to High transition means to step.
	uint8_t pinStep;

	//The Direction input the driver. High means forward, Low means reverse.
	uint8_t pinDirection;

	/* A number describing the stepper motor position, in steps.
	 * Range: [0-3779] [STEPPER_CCW_LIMIT-STEPPER_CW_LIMIT]
	 * e.g., desiredPosition of 0 is the farthest CCW position possible.
	 * e.g., desiredPosition of 3779 is the farthest CW position possible. */
	long kkimDesiredPosition; //The position desired by KKIM

	AccelStepper stepper;
};

#endif
