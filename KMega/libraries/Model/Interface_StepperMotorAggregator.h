#ifndef INTERFACE_STEPPERMOTORAGGREGATOR_h
#define INTERFACE_STEPPERMOTORAGGREGATOR_h


/* Common functions for Geared and NEMA 17 stepper motors
 *
 * */
class Interface_StepperMotorAggregator
{
public:
	virtual void resetStepperToStartingPosition() = 0;
};

#endif