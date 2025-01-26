#include <Arduino.h>
#include <string.h>

#include <ControlPanel.h>
#include "../../configuration.h"


ControlPanel::ControlPanel()
	// //[GaugePacketA]
	// : moduleC()
	// , moduleG()
	// , moduleI()

	//[GaugePacketB]
	: moduleI()
	, moduleGT()
{
	
}

bool ControlPanel::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	
	// [GaugePacketA]
	// isAMotorStillInMotion = this->moduleC.stepper_HeatLife.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleC.stepper_Gforce.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleG.stepper_Mach.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleG.stepper_Pitch.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleG.stepper_Heading.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleI.stepper_Fuel.runStepperIfNecessary() || isAMotorStillInMotion;
	
	// [GaugePacketB]
	isAMotorStillInMotion = this->moduleI.stepper_Charge.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleI.stepper_MonopropellantIntake.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleGT.stepper_Density.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleGT.stepper_Speed.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleGT.stepper_VertSpeed.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleGT.stepper_RadarAlt.runStepperIfNecessary() || isAMotorStillInMotion;

	return isAMotorStillInMotion;
}

void ControlPanel::blockRunAllGearedSteppersToPosition(int position, unsigned long stepTimeInMicroseconds) {

	// [GaugePacketA]
	// this->moduleC.stepper_HeatLife.setDesiredPosition(position);
	// this->moduleC.stepper_Gforce.setDesiredPosition(position);
	// this->moduleG.stepper_Mach.setDesiredPosition(position);
	// this->moduleG.stepper_Pitch.setDesiredPosition(position);
	// this->moduleI.stepper_Fuel.setDesiredPosition(position);

	// [GaugePacketB]
	this->moduleI.stepper_MonopropellantIntake.setDesiredPosition(position);
	this->moduleGT.stepper_Density.setDesiredPosition(position);
	this->moduleGT.stepper_Speed.setDesiredPosition(position);
	this->moduleGT.stepper_VertSpeed.setDesiredPosition(position);
	this->moduleGT.stepper_RadarAlt.setDesiredPosition(position);
	
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