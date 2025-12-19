// //[GaugePacketB] (does not apply to [GaugePacketA])
// #ifndef MODULE_GT_h
// #define MODULE_GT_h

// #include <Arduino.h>
// #include <Interface_StepperMotorAggregator.h>
// #include <StepperMotor2.h>


// /* Module GT (Gauge Tower)
//  * Stepper Motors: Air Density, Speed, Vertical Speed, Radar Altitude
//  */
// class ModuleGT : public Interface_StepperMotorAggregator
// {
// public:
// 	ModuleGT();
	
// 	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
// 	bool runStepperIfNecessary();
	
// 	//Parts:
// 	StepperMotor2 stepper_Density;
// 	StepperMotor2 stepper_Speed;
// 	StepperMotor2 stepper_VertSpeed;
// 	StepperMotor2 stepper_RadarAlt;
// };

// #endif