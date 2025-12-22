#include <Arduino.h>
#include <string.h>

#include <ControlPanel.h>
#include "../../configuration.h"


ControlPanel::ControlPanel()
	: moduleC()	//[GaugePacketA]
	, moduleG()	//[GaugePacketA]
	, moduleIa()//[GaugePacketA]
	, moduleIb()//[GaugePacketB]
	, moduleGT()//[GaugePacketB]
{
	
}

bool ControlPanel::runStepperIfNecessary() {

	bool isAMotorStillInMotion = false;
	
	if (NGH_A) {
		// [GaugePacketA]
		isAMotorStillInMotion = this->moduleC.stepper_HeatLife.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleC.stepper_Gforce.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleG.stepper_Mach.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleG.stepper_Pitch.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleG.stepper_Heading.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleIa.stepper_Fuel.runStepperIfNecessary() || isAMotorStillInMotion;
	} else {
		// [GaugePacketB]
		isAMotorStillInMotion = this->moduleIb.stepper_Charge.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleIb.stepper_MonopropellantIntake.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleGT.stepper_Density.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleGT.stepper_Speed.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleGT.stepper_VertSpeed.runStepperIfNecessary() || isAMotorStillInMotion;
		isAMotorStillInMotion = this->moduleGT.stepper_RadarAlt.runStepperIfNecessary() || isAMotorStillInMotion;
	}

	return isAMotorStillInMotion;
}

void ControlPanel::blockRunAllGearedSteppersToPosition(int position, unsigned long stepTimeInMicroseconds) {

	if (NGH_A) {
		// [GaugePacketA]
		this->moduleC.stepper_HeatLife.setDesiredPositionAndTravelStatus(position);
		this->moduleC.stepper_Gforce.setDesiredPositionAndTravelStatus(position);
		this->moduleG.stepper_Mach.setDesiredPositionAndTravelStatus(position);
		this->moduleG.stepper_Pitch.setDesiredPositionAndTravelStatus(position);
		this->moduleIa.stepper_Fuel.setDesiredPositionAndTravelStatus(position);
	} else {
		// [GaugePacketB]
		this->moduleIb.stepper_Charge.setDesiredPositionAndTravelStatus(position);
		this->moduleIb.stepper_MonopropellantIntake.setDesiredPositionAndTravelStatus(position);
		this->moduleGT.stepper_Density.setDesiredPositionAndTravelStatus(position);
		this->moduleGT.stepper_Speed.setDesiredPositionAndTravelStatus(position);
		this->moduleGT.stepper_VertSpeed.setDesiredPositionAndTravelStatus(position);
		this->moduleGT.stepper_RadarAlt.setDesiredPositionAndTravelStatus(position);
	}
	
	unsigned long startTime = 0;
	unsigned long currentTime = 0;
	unsigned long timeSpentRunningSteppers = 0;
	while(true) {
		startTime = micros();
		if (this->runStepperIfNecessary() == false) {break;}
		currentTime = micros();	
		timeSpentRunningSteppers = currentTime - startTime;
		if (timeSpentRunningSteppers > stepTimeInMicroseconds) {
			continue;
		} else {
			delayMicroseconds(stepTimeInMicroseconds - timeSpentRunningSteppers);
		}
	}
}