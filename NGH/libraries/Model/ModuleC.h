// //[GaugePacketA] (does not apply to [GaugePacketB])
// #ifndef MODULE_C_h
// #define MODULE_C_h

// #include <Arduino.h>
// #include <Interface_StepperMotorAggregator.h>
// #include <StepperMotor2.h>


// /* Module C
//  *
//  * Heat, Life Support, G-Force */
// class ModuleC : public Interface_StepperMotorAggregator
// {
// public:
// 	ModuleC();
	
// 	//Check if any stepper in the Module needs to move. Move them one step if they do. Returns true if any motor is still running to its desired position.
// 	bool runStepperIfNecessary();
	
// 	//Parts:
// 	StepperMotor2 stepper_HeatLife;
// 	StepperMotor2 stepper_Gforce;
// };

// #endif