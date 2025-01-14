#include <Arduino.h>
#include <string.h>

#include <ControlPanel.h>
#include <CommonUtilities.h>
#include "../../configuration.h"


ControlPanel::ControlPanel()
	: moduleC()
	, moduleG()
	, moduleI()
	, moduleGT()
{
	
}

//TODO: Stepper Logic disabled until performance is fixed (do not modify)
void ControlPanel::stepMotorUpTo(StepperMotor2& stepper, int maxNumberOfSteps) {
	int counter = 0;
	while (true) {
		counter++;
		if(counter > maxNumberOfSteps) {
			return;
		}
		if (!stepper.runStepperIfNecessary()) {
			return;
		}
	}
}

//TODO: Stepper Logic disabled until performance is fixed (do not modify)
void ControlPanel::burstRunSteppers() {
	
	// int FAST = 300;
	// int MEDIUM = 200;
	// int SLOW = 100;

	// //Step "Fast" motors, up to FAST steps
	// this->stepMotorUpTo(this->moduleC.stepper_Gforce, FAST);
	// this->stepMotorUpTo(this->moduleG.stepper_Mach, FAST);
	// this->stepMotorUpTo(this->moduleG.stepper_Pitch, FAST);
	// // this->moduleG.stepper_Heading
	// this->stepMotorUpTo(this->moduleGT.stepper_Speed, FAST);
	// this->stepMotorUpTo(this->moduleGT.stepper_VertSpeed, FAST);
	// this->stepMotorUpTo(this->moduleGT.stepper_RadarAlt, FAST);
	
	// //Step "Medium" motors, up to MEDIUM steps
	// this->stepMotorUpTo(this->moduleI.stepper_Fuel, MEDIUM);
	// this->stepMotorUpTo(this->moduleI.stepper_Charge, MEDIUM);
	// this->stepMotorUpTo(this->moduleI.stepper_MonopropellantIntake, MEDIUM);
	
	// //Step "Slow" motors, up to SLOW steps
	// this->stepMotorUpTo(this->moduleC.stepper_HeatLife, SLOW);
	// this->stepMotorUpTo(this->moduleGT.stepper_Density, SLOW);

	//New:
	unsigned long startTime = 0;
	unsigned long currentTime = 0;
	unsigned long timeSpentRunningSteppers = 0;
	for (int i = 0; i < 20; i++) {// TODO parameterize loop amount
		startTime = micros();
		if (this->runStepperIfNecessary() == false) {return;}
		currentTime = micros();
		timeSpentRunningSteppers = currentTime - startTime;
		if (timeSpentRunningSteppers > 500) {//TODO Replace hardcoding with variable
			continue;
		} else {
			delayMicroseconds(500 - timeSpentRunningSteppers);//TODO Replace hardcoding with variable
		}
	}
}

bool ControlPanel::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	//TODO: Stepper Logic disabled until performance is fixed (do not modify)
	isAMotorStillInMotion = this->moduleC.stepper_HeatLife.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleC.stepper_Gforce.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleG.stepper_Mach.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleG.stepper_Pitch.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleI.stepper_Fuel.runStepperIfNecessary() || isAMotorStillInMotion;
	isAMotorStillInMotion = this->moduleI.stepper_Charge.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleI.stepper_MonopropellantIntake.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleGT.stepper_Density.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleGT.stepper_Speed.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleGT.stepper_VertSpeed.runStepperIfNecessary() || isAMotorStillInMotion;
	// isAMotorStillInMotion = this->moduleGT.stepper_RadarAlt.runStepperIfNecessary() || isAMotorStillInMotion;
	return isAMotorStillInMotion;
}

void ControlPanel::blockRunAllSteppersToPosition(int position, unsigned long stepTimeInMicroseconds) {
	//TODO: Stepper Logic disabled until performance is fixed (do not modify)
	this->moduleC.stepper_HeatLife.setDesiredPosition(position);
	this->moduleC.stepper_Gforce.setDesiredPosition(position);
	this->moduleG.stepper_Mach.setDesiredPosition(position);
	this->moduleG.stepper_Pitch.setDesiredPosition(position);
	this->moduleI.stepper_Fuel.setDesiredPosition(position);
	this->moduleI.stepper_Charge.setDesiredPosition(position);
	// this->moduleI.stepper_MonopropellantIntake.setDesiredPosition(position);
	// this->moduleGT.stepper_Density.setDesiredPosition(position);
	// this->moduleGT.stepper_Speed.setDesiredPosition(position);
	// this->moduleGT.stepper_VertSpeed.setDesiredPosition(position);
	// this->moduleGT.stepper_RadarAlt.setDesiredPosition(position);
	
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

void ControlPanel::sweepStepperMotorsThroughMaxMin() {
	this->blockRunAllSteppersToPosition(STEPPER_CW_LIMIT, 500);
	delay(200);
	this->blockRunAllSteppersToPosition(STEPPER_CCW_LIMIT, 500);
}