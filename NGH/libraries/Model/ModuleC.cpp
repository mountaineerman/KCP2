// //[GaugePacketA] (does not apply to [GaugePacketB])
// #include <Arduino.h>
// #include <ModuleC.h>
// #include "..\..\configuration.h"



// ModuleC::ModuleC()
// 	: stepper_HeatLife		(PIN_VID6606_1_FREQUENCY_HEATLIFE,	PIN_VID6606_1_DIRECTION_HEATLIFE,	false,	STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
// 	, stepper_Gforce		(PIN_VID6606_1_FREQUENCY_GFORCE,	PIN_VID6606_1_DIRECTION_GFORCE, 	false,	STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
// {
	
// }

// bool ModuleC::runStepperIfNecessary() {
// 	bool isAMotorStillInMotion = false;
// 	isAMotorStillInMotion = this->stepper_HeatLife.runStepperIfNecessary() || isAMotorStillInMotion;
// 	isAMotorStillInMotion = this->stepper_Gforce.runStepperIfNecessary() || isAMotorStillInMotion;
// 	return isAMotorStillInMotion;
// }