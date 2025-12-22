//[GaugePacketA] (does not apply to [GaugePacketB])
#include <Arduino.h>
#include <ModuleIa.h>
#include "../../configuration.h"



ModuleIa::ModuleIa()
	: stepper_Fuel (PIN_VID6606_2_FREQUENCY_FUEL, PIN_VID6606_2_DIRECTION_FUEL, true, MAX_GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
{
	
}

bool ModuleIa::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	isAMotorStillInMotion = this->stepper_Fuel.runStepperIfNecessary() || isAMotorStillInMotion;
	return isAMotorStillInMotion;
}