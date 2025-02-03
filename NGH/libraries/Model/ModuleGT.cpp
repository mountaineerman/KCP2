// //[GaugePacketB] (does not apply to [GaugePacketA])
// #include <Arduino.h>
// #include <ModuleGT.h>
// #include "..\..\configuration.h"


// ModuleGT::ModuleGT()
// : stepper_Density	(PIN_VID6606_3_FREQUENCY_DENSITY,		PIN_VID6606_3_DIRECTION_DENSITY,	   false, STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
// , stepper_Speed		(PIN_VID6606_3_FREQUENCY_SPEED,			PIN_VID6606_3_DIRECTION_SPEED,		   false, STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
// , stepper_VertSpeed	(PIN_VID6606_3_FREQUENCY_VERTICALSPEED,	PIN_VID6606_3_DIRECTION_VERTICALSPEED, false, STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
// , stepper_RadarAlt	(PIN_VID6606_3_FREQUENCY_RADARALTITUDE,	PIN_VID6606_3_DIRECTION_RADARALTITUDE, false, STEPPER_SPEED, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT)
// {
	
// }

// bool ModuleGT::runStepperIfNecessary() {
// 	bool isAMotorStillInMotion = false;
// 	isAMotorStillInMotion = this->stepper_Density.runStepperIfNecessary() || isAMotorStillInMotion;
// 	isAMotorStillInMotion = this->stepper_Speed.runStepperIfNecessary() || isAMotorStillInMotion;
// 	isAMotorStillInMotion = this->stepper_VertSpeed.runStepperIfNecessary() || isAMotorStillInMotion;
// 	isAMotorStillInMotion = this->stepper_RadarAlt.runStepperIfNecessary() || isAMotorStillInMotion;
// 	return isAMotorStillInMotion;	
// }