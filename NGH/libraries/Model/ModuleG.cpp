#include <Arduino.h>
#include <ModuleG.h>
#include "..\..\configuration.h"



ModuleG::ModuleG()
	: stepper_Mach	(PIN_VID6606_1_FREQUENCY_MACH, 	PIN_VID6606_1_DIRECTION_MACH,  true, STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
	, stepper_Pitch	(PIN_VID6606_1_FREQUENCY_PITCH, PIN_VID6606_1_DIRECTION_PITCH, true, STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
{
	
}

bool ModuleG::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	isAMotorStillInMotion = this->stepper_Mach.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->stepper_Pitch.runStepperIfNecessary() || isAMotorStillInMotion;
	return isAMotorStillInMotion;
}