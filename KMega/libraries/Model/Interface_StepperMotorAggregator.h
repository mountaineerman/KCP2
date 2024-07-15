#ifndef INTERFACE_STEPPERMOTORAGGREGATOR_h
#define INTERFACE_STEPPERMOTORAGGREGATOR_h


/* Common functions for Geared and NEMA 17 stepper motors
 *
 * */
class Interface_StepperMotorAggregator
{
public:
	virtual void runStepperIfNecessary() = 0;
	//bool isMoving(); TODO?
	//void blockRunToDesiredPosition(); TODO?
};

#endif