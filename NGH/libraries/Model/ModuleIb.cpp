#include <Arduino.h>
#include <ModuleIb.h>
#include "../../configuration.h"



ModuleIb::ModuleIb()
	: stepper_Charge			   (PIN_VID6606_2_FREQUENCY_CHARGE,	PIN_VID6606_2_DIRECTION_CHARGE,	true,  MAX_GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT) //[GaugePacketB]
	, stepper_MonopropellantIntake (PIN_VID6606_2_FREQUENCY_MNPINT,	PIN_VID6606_2_DIRECTION_MNPINT,	false, MAX_GEARED_STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT) //[GaugePacketB]
{
	
}

bool ModuleIb::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	isAMotorStillInMotion = this->stepper_Charge.runStepperIfNecessary() || isAMotorStillInMotion;				//[GaugePacketB]
	isAMotorStillInMotion = this->stepper_MonopropellantIntake.runStepperIfNecessary() || isAMotorStillInMotion;//[GaugePacketB]
	return isAMotorStillInMotion;
}