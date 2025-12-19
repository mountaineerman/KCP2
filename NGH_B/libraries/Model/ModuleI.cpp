#include <Arduino.h>
#include <ModuleI.h>
#include "../../configuration.h"



ModuleI::ModuleI()
	// //[GaugePacketA]
	// : stepper_Fuel				   (PIN_VID6606_2_FREQUENCY_FUEL,	PIN_VID6606_2_DIRECTION_FUEL,	true,  STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
	//[GaugePacketB]
	: stepper_Charge			   (PIN_VID6606_2_FREQUENCY_CHARGE,	PIN_VID6606_2_DIRECTION_CHARGE,	true,  STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
	, stepper_MonopropellantIntake (PIN_VID6606_2_FREQUENCY_MNPINT,	PIN_VID6606_2_DIRECTION_MNPINT,	false, STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
{
	
}

bool ModuleI::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	
	// //[GaugePacketA]
	// isAMotorStillInMotion = this->stepper_Fuel.runStepperIfNecessary() || isAMotorStillInMotion;
	
	//[GaugePacketB]
	isAMotorStillInMotion = this->stepper_Charge.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->stepper_MonopropellantIntake.runStepperIfNecessary() || isAMotorStillInMotion;

	return isAMotorStillInMotion;
}