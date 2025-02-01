#include <Arduino.h>
#include <string.h>

#include <MuxShield.h>

#include <ControlPanel.h>
#include <CommonUtilities.h>
#include "../../configuration.h"


ControlPanel::ControlPanel()
	: mux()
	, ledDriverBoards(NUMBER_OF_LED_DRIVER_BOARDS, PIN_LED_DRIVER_BOARDS_CLOCK, PIN_LED_DRIVER_BOARDS_DATA_IN, PIN_LED_DRIVER_BOARDS_LATCH)
	, moduleA(mux, ledDriverBoards)
	, moduleB(mux)
	, moduleC(ledDriverBoards)
	, moduleD(mux, ledDriverBoards)
	, moduleE(mux, ledDriverBoards)
	, moduleF(mux, ledDriverBoards)
	, moduleG(ledDriverBoards)
	, moduleH(ledDriverBoards)
	, moduleI(ledDriverBoards)
	, moduleGT(ledDriverBoards)
{
	this->mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP); //VERIFIED
	this->mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);		   //VERIFIED
	this->mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);		   //VERIFIED
	
	//TODO: Remove://this->ledDriverBoards = new Adafruit_TLC5947(NUMBER_OF_LED_DRIVER_BOARDS, PIN_LED_DRIVER_BOARDS_CLOCK, PIN_LED_DRIVER_BOARDS_DATA_IN, PIN_LED_DRIVER_BOARDS_LATCH);
	this->ledDriverBoards.begin();
	//TODO: replace the following 2 lines with an LED OVERRIDE function... And add a bit to kkim>kmega interface...
	pinMode(PIN_LED_DRIVER_BOARDS_OVERRIDE, OUTPUT);
	this->disableLEDOverride();
	
	this->setAllLEDsTo(PWM_LED_MINIMUM);
}

void ControlPanel::refreshInputStatus() {
	this->moduleA.refreshInputStatus();
	this->moduleB.refreshInputStatus();
	this->moduleD.refreshInputStatus();
	this->moduleE.refreshInputStatus();
	this->moduleF.refreshInputStatus();
	this->moduleG.refreshInputStatus();
	this->moduleH.refreshInputStatus();
	this->moduleI.refreshInputStatus();
}

String ControlPanel::getInputStatusAsString() { //TODO: Fix (figure out overflow error (empty after adding Module H)???)
	//return this->moduleA.getInputStatusAsString() +
	//	   this->moduleB.getInputStatusAsString() +
	//	   this->moduleD.getInputStatusAsString() +
	//	   this->moduleE.getInputStatusAsString() +
	//	   this->moduleF.getInputStatusAsString() +
	//	   //this->moduleG.getInputStatusAsString() +
	//	   this->moduleH.getInputStatusAsString();// +
	//	   //this->moduleI.getInputStatusAsString();
	
	Serial.print(this->moduleA.getInputStatusAsString());
	Serial.print(this->moduleB.getInputStatusAsString());
	Serial.print(this->moduleD.getInputStatusAsString());
	Serial.print(this->moduleE.getInputStatusAsString());
	Serial.print(this->moduleF.getInputStatusAsString());
	Serial.print(this->moduleG.getInputStatusAsString());
	Serial.print(this->moduleH.getInputStatusAsString());
	Serial.print(this->moduleI.getInputStatusAsString());
	return(String(""));
}

void ControlPanel::setAllLEDsTo(int pwm_level) {
	this->moduleA.setAllLEDsTo(pwm_level);
	this->moduleC.setAllLEDsTo(pwm_level);
	this->moduleD.setAllLEDsTo(pwm_level);
	this->moduleE.setAllLEDsTo(pwm_level);
	this->moduleF.setAllLEDsTo(pwm_level);
	this->moduleG.setAllLEDsTo(pwm_level);
	this->moduleH.setAllLEDsTo(pwm_level);
	this->moduleI.setAllLEDsTo(pwm_level);
	this->moduleGT.setAllLEDsTo(pwm_level);
	this->writeLEDStatusToLEDDriverBoards();
}

void ControlPanel::writeLEDStatusToLEDDriverBoards() {
	this->ledDriverBoards.write();
}

void ControlPanel::testLEDsSequentially() {
	this->moduleA.testLEDsSequentially();
	this->moduleC.testLEDsSequentially();
	this->moduleD.testLEDsSequentially();
	this->moduleE.testLEDsSequentially();
	this->moduleF.testLEDsSequentially();
	this->moduleG.testLEDsSequentially();
	this->moduleH.testLEDsSequentially();
	this->moduleI.testLEDsSequentially();
	this->moduleGT.testLEDsSequentially();
}

void ControlPanel::runDiagnosticMode() {
	
	String userInput;
	
	while(true) {
	
		clearScreen();
		Serial.println(F("Diagnostic Mode activated. Select one of the following:"));
		Serial.println(F("[0] Exit"));
		Serial.println(F("[1] Test all inputs"));
		Serial.println(F("[2] Test all LEDs (simultaneously)"));
		Serial.println(F("[3] Test LEDs sequentially"));
		Serial.println(F("[4] Test Stepper Motors"));
		
		userInput = Serial.readStringUntil('\n');
		//Serial.println(userInput);
		delay(100);
		
		if(userInput == "0") {
			Serial.println("Exiting...");
			return;
		} else if(userInput == "1") {
			this->diagnosticMode_testAllInputs();
		} else if(userInput == "2") {
			this->diagnosticMode_testAllLEDs();
		} else if(userInput == "3") {
			this->diagnosticMode_testLEDsSequentially();
		} else if(userInput == "4") {
			this->diagnosticMode_testStepperMotors();
		}
	}
}

/*
MuxShield mux;

void setup() {
	
	mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP); //VERIFIED
	mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);		   //VERIFIED
	mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);		   //TODO is this right?
	
	Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	Serial.begin(COMPUTER_BAUD_RATE);
	delay(1000);
}

void loop() {
	Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();
	String X = String("Mux ============================================================================") +
	"\nROW1 (Input Pullup):" +
	"\n(0)  unassigned: " + mux.digitalReadMS(1,0) +
	"\n(1)  BRAKE_BUTTON: " + mux.digitalReadMS(1,1) +
	"\n(2)  JOYSTICK_BUTTON: " + mux.digitalReadMS(1,2) +
	"\n(3)  BRAKE_SWITCH: " + mux.digitalReadMS(1,3) +
	"\n(4)  AUTOPILOT_HOLD_BUTTON: " + mux.digitalReadMS(1,4) +
	"\n(5)  AUTOPILOT_PROGRADE_BUTTON: " + mux.digitalReadMS(1,5) +
	"\n(6)  AUTOPILOT_RETROGRADE_BUTTON: " + mux.digitalReadMS(1,6) +
	"\n(7)  AUTOPILOT_NORMAL_BUTTON: " + mux.digitalReadMS(1,7) +
	"\n(8)  AUTOPILOT_ANTINORMAL_BUTTON: " + mux.digitalReadMS(1,8) +
	"\n(9)  AUTOPILOT_RADIALIN_BUTTON: " + mux.digitalReadMS(1,9) +
	"\n(10) AUTOPILOT_RADIALOUT_BUTTON: " + mux.digitalReadMS(1,10) +
	"\n(11) AUTOPILOT_ANTITARGET_BUTTON: " + mux.digitalReadMS(1,11) +
	"\n(12) AUTOPILOT_MANEUVER: " + mux.digitalReadMS(1,12) +
	"\n(13) AUTOPILOT_TARGET_BUTTON: " + mux.digitalReadMS(1,13) +
	"\n(14) FAIRING_BUTTON: " + mux.digitalReadMS(1,14) +
	"\n(15) CHUTE_BUTTON: " + mux.digitalReadMS(1,15) +
	"\n" +
	"\nROW2 (Input):" +
	"\n(0)  STAGING_BUTTON: " + mux.digitalReadMS(2,0) +
	"\n(1)  ABORT_BUTTON: " + mux.digitalReadMS(2,1) +
	"\n(2)  PITCH_TRIM_SWITCH: " + mux.digitalReadMS(2,2) +
	"\n(3)  YAW_TRIM_SWITCH: " + mux.digitalReadMS(2,3) +
	"\n(4)  ROLL_TRIM_SWITCH: " + mux.digitalReadMS(2,4) +
	"\n(5)  SAS_SWITCH: " + mux.digitalReadMS(2,5) +
	"\n(6)  RCS_SWITCH: " + mux.digitalReadMS(2,6) +
	"\n(7)  LIGHTS_SWITCH: " + mux.digitalReadMS(2,7) +
	"\n(8)  GEAR_SWITCH: " + mux.digitalReadMS(2,8) +
	"\n(9)  MAP_SWITCH: " + mux.digitalReadMS(2,9) +
	"\n(10) MUTE_SWITCH: " + mux.digitalReadMS(2,10) +
	"\n(11) unassigned: " + mux.digitalReadMS(2,11) +
	"\n(12) unassigned: " + mux.digitalReadMS(2,12) +
	"\n(13) SFC_SWITCH: " + mux.digitalReadMS(2,13) +
	"\n(14) TGT_SWITCH: " + mux.digitalReadMS(2,14) +
	"\n(15) RKT_SWITCH: " + mux.digitalReadMS(2,15) +
	"\n" +
	"\nROW3 (Input):" +
	"\n(0)  RVR_SWITCH: " + mux.digitalReadMS(3,0) +
	"\n(1)  90_DEG_SWITCH: " + mux.digitalReadMS(3,1) +
	"\n(2)  9_DEG_SWITCH: " + mux.digitalReadMS(3,2) +
	"\n(3)  TRIM_SWITCH: " + mux.digitalReadMS(3,3) +
	"\n(4)  ACTION_GROUP_1_SWITCH: " + mux.digitalReadMS(3,4) +
	"\n(5)  ACTION_GROUP_2_SWITCH: " + mux.digitalReadMS(3,5) +
	"\n(6)  ACTION_GROUP_3_SWITCH: " + mux.digitalReadMS(3,6) +
	"\n(7)  SCIENCE_SWITCH: " + mux.digitalReadMS(3,7) +
	"\n(8)  RESET_SWITCH: " + mux.digitalReadMS(3,8) +
	"\n(9)  SOLAR_SWITCH: " + mux.digitalReadMS(3,9) +
	"\n(10) LADDER_SWITCH: " + mux.digitalReadMS(3,10) +
	"\n(11) AUTONAVIGATION_SWITCH: " + mux.digitalReadMS(3,11) +
	"\n(12) unassigned: " + mux.digitalReadMS(3,12) +
	"\n(13) unassigned: " + mux.digitalReadMS(3,13) +
	"\n(14) unassigned: " + mux.digitalReadMS(3,14) +
	"\n(15) unassigned: " + mux.digitalReadMS(3,15);
	
	Serial.println(X);
	delay(9000);
}
*/

void ControlPanel::diagnosticMode_testAllInputs() {
	
	Serial.setTimeout(2000);//Lower delay
	String userInput;
	
	while(true) {
		
		clearScreen();
		Serial.println(F("Testing All Inputs. Enter '0' at any time to return to the main menu."));
		this->refreshInputStatus();
		this->getInputStatusAsString();//TODO: Fix: //Serial.println(this->getInputStatusAsString());
		
		userInput = Serial.readStringUntil('\n');
		//Serial.println(userInput);
		
		if(userInput == "0") {
			Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);//Reset delay
			return;
		}
	}
}

void ControlPanel::diagnosticMode_testAllLEDs() {
	
	clearScreen();
	Serial.println(F("Testing All LEDs. Enter '0' at any time to return to the main menu."));
	this->setAllLEDsTo(PWM_LED_MAXIMUM);
	
	String userInput;
	
	while(true) {
		
		userInput = Serial.readStringUntil('\n');
		//Serial.println(userInput);
		//delay(3000);
		
		if(userInput == "0") {
			this->setAllLEDsTo(PWM_LED_MINIMUM);
			return;
		}
	}
}

void ControlPanel::diagnosticMode_testLEDsSequentially() {

	String userInput;
	
	while(true) {
	
		clearScreen();
		Serial.println(F("Testing LEDs Sequentially. LEDs will be lit top to bottom, left to right."));
		Serial.println(F("RGB LEDs will be lit in R>G>B order. Select which module(s) to test:"));
		Serial.println(F("[0] Return to main menu"));
		Serial.println(F("[all] All"));
		Serial.println(F("[a] Module A"));
		Serial.println(F("[c] Module C"));
		Serial.println(F("[d] Module D"));
		Serial.println(F("[e] Module E"));
		Serial.println(F("[f] Module F"));
		Serial.println(F("[g] Module G"));
		Serial.println(F("[h] Module H"));
		Serial.println(F("[i] Module I"));
		Serial.println(F("[gt] Gauge Tower"));
		
		userInput = Serial.readStringUntil('\n');
		
		if(userInput == "0") {
			return;
		} else if(userInput == "all") {
			this->testLEDsSequentially();
		} else if(userInput == "a") {
			this->moduleA.testLEDsSequentially();
		} else if(userInput == "c") {
			this-> moduleC.testLEDsSequentially();
		} else if(userInput == "d") {
			this->moduleD.testLEDsSequentially();
		} else if(userInput == "e") {
			this->moduleE.testLEDsSequentially();
		} else if(userInput == "f") {
			this->moduleF.testLEDsSequentially();
		} else if(userInput == "g") {
			this->moduleG.testLEDsSequentially();
		} else if(userInput == "h") {
			this->moduleH.testLEDsSequentially();
		} else if(userInput == "i") {
			this->moduleI.testLEDsSequentially();
		} else if(userInput == "gt") {
			this->moduleGT.testLEDsSequentially();
		}
	}
}

void ControlPanel::diagnosticMode_testStepperMotors() {
	
	// String userInput;
	
	// while(true) {
	// 	clearScreen();
	// 	Serial.println(F("Testing Stepper Motors. Select a stepper motor to test:"));
	// 	Serial.println(F("[0] Return to main menu"));
	// 	Serial.println(F("[1] Heat/Life"));
	// 	Serial.println(F("[2] G-Force"));
	// 	Serial.println(F("[3] Mach Number"));
	// 	Serial.println(F("[4] Pitch"));
	// 	Serial.println(F("[5] Heading"));
	// 	Serial.println(F("[6] Fuel"));
	// 	Serial.println(F("[7] Charge"));
	// 	Serial.println(F("[8] Monopropellant/Intake Air"));
	// 	Serial.println(F("[9] Air Density"));
	// 	Serial.println(F("[10] Speed"));
	// 	Serial.println(F("[11] Vertical Speed"));
	// 	Serial.println(F("[12] Radar Altitude"));
	// 	Serial.println(F("--------------------------------------"));
	// 	Serial.println(F("[21] Sweep & time: Heat/Life"));
	// 	Serial.println(F("[22] Sweep & time: G-Force"));
	// 	Serial.println(F("[23] Sweep & time: Mach Number"));
	// 	Serial.println(F("[24] Sweep & time: Pitch"));
	// 	Serial.println(F("[25] Sweep & time: Heading [N/A] ---"));
	// 	Serial.println(F("[26] Sweep & time: Fuel"));
	// 	Serial.println(F("[27] Sweep & time: Charge"));
	// 	Serial.println(F("[28] Sweep & time: Monopropellant/Intake Air"));
	// 	Serial.println(F("[29] Sweep & time: Air Density"));
	// 	Serial.println(F("[30] Sweep & time: Speed"));
	// 	Serial.println(F("[31] Sweep & time: Vertical Speed"));
	// 	Serial.println(F("[32] Sweep & time: Radar Altitude"));
	// 	Serial.println(F("--------------------------------------"));
	// 	Serial.println(F("[40] Sweep & time: All geared stepper Motors"));
	// 	Serial.println(F("--------------------------------------"));
	// 	Serial.println(F("[56] Mock Test of Fuel (in isolation)"));
		
	// 	userInput = Serial.readStringUntil('\n');
		
	// 	if(userInput == "0") {
	// 		return;
	// 	} else if(userInput == "1") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleC.stepper_HeatLife);
	// 	} else if(userInput == "2") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleC.stepper_Gforce);
	// 	} else if(userInput == "3") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleG.stepper_Mach);
	// 	} else if(userInput == "4") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleG.stepper_Pitch);
	// 	} else if(userInput == "5") {
	// 		this->diagnosticMode_testNEMA17StepperMotor(this->moduleG.stepper_Heading);
	// 	} else if(userInput == "6") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleI.stepper_Fuel);
	// 	} else if(userInput == "7") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleI.stepper_Charge);
	// 	} else if(userInput == "8") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleI.stepper_MonopropellantIntake);
	// 	} else if(userInput == "9") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleGT.stepper_Density);
	// 	} else if(userInput == "10") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleGT.stepper_Speed);
	// 	} else if(userInput == "11") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleGT.stepper_VertSpeed);
	// 	} else if(userInput == "12") {
	// 		this->diagnosticMode_testStepperMotor2(this->moduleGT.stepper_RadarAlt);
	// 	} else if(userInput == "21") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleC.stepper_HeatLife);
	// 	} else if(userInput == "22") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleC.stepper_Gforce);
	// 	} else if(userInput == "23") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleG.stepper_Mach);
	// 	} else if(userInput == "24") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleG.stepper_Pitch);
	// 	} else if(userInput == "26") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleI.stepper_Fuel);
	// 	} else if(userInput == "27") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleI.stepper_Charge);
	// 	} else if(userInput == "28") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleI.stepper_MonopropellantIntake);
	// 	} else if(userInput == "29") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleGT.stepper_Density);
	// 	} else if(userInput == "30") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleGT.stepper_Speed);
	// 	} else if(userInput == "31") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleGT.stepper_VertSpeed);
	// 	} else if(userInput == "32") {
	// 		this->diagnosticMode_sweepSingleStepperMotor(this->moduleGT.stepper_RadarAlt);
	// 	} else if(userInput == "40") {
	// 		this->diagnosticMode_sweepAllGearedStepperMotors();
	// 	} else if(userInput == "56") {
	// 		this->diagnosticMode_fuelTest();
	// 	}
	// }
}

void ControlPanel::diagnosticMode_testStepperMotor2(StepperMotor2& stepperMotorUnderTest) {
	
// 	String userInput;
	
// 	while(true) {
// 		clearScreen();
// 		Serial.print(F("Stepper Motor Speed (steps per second): ")); Serial.println(stepperMotorUnderTest.getSpeed());
// 		Serial.print(F("Time between steps (microseconds): ")); Serial.println(stepperMotorUnderTest.getTimeBetweenSteps());
// 		Serial.print(F("CCW Limit (steps): ")); Serial.println(stepperMotorUnderTest.get_ccwLimit());
// 		Serial.print(F("CW Limit (steps): ")); Serial.println(stepperMotorUnderTest.get_cwLimit());
// 		Serial.print(F("Stepper Motor Position (steps): ")); Serial.println(stepperMotorUnderTest.getCurrentPosition());
// 		Serial.println();
// 		Serial.println(F("Select one of the following options:"));
// 		Serial.println(F("[0] Return to previous menu"));
// 		Serial.println(F("[1] Move to maximum CCW position"));
// 		Serial.println(F("[2] Move to maximum CW position"));
// 		Serial.println(F("[3] Manual Control Mode (via Joystick) [TODO]"));
// 		Serial.println(F("[4] Move 1000 steps CCW"));
// 		Serial.println(F("[5] Move 1000 steps CW"));
// 		Serial.println(F("[6] Move 100 steps CCW"));
// 		Serial.println(F("[7] Move 100 steps CW"));
// 		Serial.println(F("[8] Move 10 steps CCW"));
// 		Serial.println(F("[9] Move 10 steps CW"));
		
// 		userInput = Serial.readStringUntil('\n');
		
// 		if(userInput == "0") {
// 			return;
// 		} else if(userInput == "1") {
// 			stepperMotorUnderTest.setDesiredPosition(stepperMotorUnderTest.get_ccwLimit());
// 			stepperMotorUnderTest.blockRunToDesiredPosition();
// 		} else if(userInput == "2") {
// 			stepperMotorUnderTest.setDesiredPosition(stepperMotorUnderTest.get_cwLimit());
// 			stepperMotorUnderTest.blockRunToDesiredPosition();
// //		} else if(userInput == '3') {
// //			//TODO
// 		} else if(userInput == "4") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(-1000);
// 			stepperMotorUnderTest.blockRunToDesiredPosition();
// 		} else if(userInput == "5") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(1000);
// 			stepperMotorUnderTest.blockRunToDesiredPosition();
// 		} else if(userInput == "6") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(-100);
// 			stepperMotorUnderTest.blockRunToDesiredPosition();
// 		} else if(userInput == "7") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(100);
// 			stepperMotorUnderTest.blockRunToDesiredPosition();
// 		} else if(userInput == "8") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(-10);
// 			stepperMotorUnderTest.blockRunToDesiredPosition();
// 		} else if(userInput == "9") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(10);
// 			stepperMotorUnderTest.blockRunToDesiredPosition();			
// 		}
// 	}
}

void ControlPanel::diagnosticMode_testNEMA17StepperMotor(NEMA17StepperMotor& stepperMotorUnderTest) {
	
// 	String userInput;
	
// 	while(true) {
// 		clearScreen();
// 		Serial.print(F("Stepper Motor Position [")); Serial.print(NEMA17_STEPPER_MIN_POSITION); Serial.print("-"); Serial.print(NEMA17_STEPPER_MAX_POSITION); Serial.print("]: "); Serial.println(stepperMotorUnderTest.getCurrentPosition());
// 		Serial.println();
// 		Serial.println(F("Select one of the following options:"));
// 		Serial.println(F("[0] Return to previous menu"));
// 		Serial.println(F("[1] Move disc to NEMA17_STEPPER_MIN_POSITION position"));
// 		Serial.println(F("[2] Move disc to NEMA17_STEPPER_MAX_POSITION position"));
// 		Serial.println(F("[3] Manual Control Mode (via Joystick) [TODO]"));
// 		Serial.println(F("[4] Move disc 1000 steps CW"));
// 		Serial.println(F("[5] Move disc 1000 steps CCW"));
// 		Serial.println(F("[6] Move disc 100 steps CW"));
// 		Serial.println(F("[7] Move disc 100 steps CCW"));
// 		Serial.println(F("[8] Move disc 10 steps CW"));
// 		Serial.println(F("[9] Move disc 10 steps CCW"));
// 		Serial.println(F("[10] Move disc 1 step CW"));
// 		Serial.println(F("[11] Move disc 1 step CCW"));

// 		userInput = Serial.readStringUntil('\n');
		
// 		if(userInput == "0") {
// 			return;
// 		} else if(userInput == "1") {
// 			stepperMotorUnderTest.setDesiredPosition(NEMA17_STEPPER_MIN_POSITION);
// 			stepperMotorUnderTest.runToDesiredPosition();
// 		} else if(userInput == "2") {
// 			stepperMotorUnderTest.setDesiredPosition(NEMA17_STEPPER_MAX_POSITION);
// 			stepperMotorUnderTest.runToDesiredPosition();
// //		} else if(userInput == '3') {
// //			//TODO
// 		} else if(userInput == "4") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(-1000);
// 			stepperMotorUnderTest.runToDesiredPosition();
// 		} else if(userInput == "5") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(1000);
// 			stepperMotorUnderTest.runToDesiredPosition();
// 		} else if(userInput == "6") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(-100);
// 			stepperMotorUnderTest.runToDesiredPosition();
// 		} else if(userInput == "7") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(100);
// 			stepperMotorUnderTest.runToDesiredPosition();
// 		} else if(userInput == "8") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(-10);
// 			stepperMotorUnderTest.runToDesiredPosition();
// 		} else if(userInput == "9") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(10);
// 			stepperMotorUnderTest.runToDesiredPosition();			
// 		} else if(userInput == "10") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(-1);
// 			stepperMotorUnderTest.runToDesiredPosition();			
// 		} else if(userInput == "11") {
// 			stepperMotorUnderTest.setDesiredRelativePosition(1);
// 			stepperMotorUnderTest.runToDesiredPosition();			
// 		}
// 	}
}

//Regardless of what status the LEDs driven by the LED Driver Boards are, turn them off
void ControlPanel::activateLEDOverride() {
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, HIGH);
}

//Allow the LEDs driven by the LED Driver Boards to be ON
void ControlPanel::disableLEDOverride() {
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, LOW);
}

void ControlPanel::diagnosticMode_sweepSingleStepperMotor(StepperMotor2& stepperMotorUnderTest) {
	
	// String userInput;
	// clearScreen();
	// Serial.println(F("Enter Stepper Motor Speed (steps per second) [Default:4000]..."));
	// userInput = Serial.readStringUntil('\n');
	// int speed = atoi(userInput.c_str());
	// stepperMotorUnderTest.setSpeed(speed);
	
	// //Sweep...
	// long startTime = millis();
	// stepperMotorUnderTest.setDesiredPosition(stepperMotorUnderTest.get_cwLimit());
	// stepperMotorUnderTest.blockRunToDesiredPosition();
	// delay(200);
	// stepperMotorUnderTest.setDesiredPosition(stepperMotorUnderTest.get_ccwLimit());
	// stepperMotorUnderTest.blockRunToDesiredPosition();
	// long endTime = millis();
	// stepperMotorUnderTest.setSpeed(GEARED_STEPPER_SPEED);

	// //Calculations
	// long numberOfTraversedSteps = 3780*2;
	// long expectedSweepTimeInMilliseconds = (numberOfTraversedSteps * 1000 / speed) + 200;
	// long actualSweepTimeInMilliseconds = endTime - startTime;

	// //Print results
	// while(true) {
	// 	clearScreen();
	// 	Serial.print(F("Stepper Motor Speed (steps per second): ")); Serial.println(stepperMotorUnderTest.getSpeed());
	// 	Serial.print(F("Time between steps (microseconds): ")); Serial.println(stepperMotorUnderTest.getTimeBetweenSteps());
	// 	Serial.print(F("CCW Limit (steps): ")); Serial.println(stepperMotorUnderTest.get_ccwLimit());
	// 	Serial.print(F("CW Limit (steps): ")); Serial.println(stepperMotorUnderTest.get_cwLimit());
	// 	Serial.println();
	// 	Serial.print(F("numberOfTraversedSteps: ")); Serial.println(numberOfTraversedSteps);
	// 	Serial.print(F("expectedSweepTimeInMilliseconds: ")); Serial.println(expectedSweepTimeInMilliseconds);
	// 	Serial.print(F("  actualSweepTimeInMilliseconds: ")); Serial.println(actualSweepTimeInMilliseconds);
	// 	Serial.println();
	// 	Serial.print(F("Enter '0' to continue..."));

	// 	userInput = Serial.readStringUntil('\n');
	// 	if(userInput == "0") {
	// 		return;
	// 	}
	// }
}

void ControlPanel::diagnosticMode_sweepAllGearedStepperMotors() {
	
	// //Sweep...
	// long startTime = millis();
	// this->sweepGearedStepperMotorsThroughMaxMin();
	// long endTime = millis();

	// //Calculations
	// long numberOfTraversedSteps = 3780*2;
	// long expectedSweepTimeInMilliseconds = (numberOfTraversedSteps * 1000 / GEARED_STEPPER_SPEED) + 200; //200 milliseconds for delay() in sweepGearedStepperMotorsThroughMaxMin()
	// long actualSweepTimeInMilliseconds = endTime - startTime;

	// //Print results
	// while(true) {
	// 	clearScreen();
	// 	Serial.println(F("Stepper Motor Speed (steps per second): 4000 (HARDCODED)"));
	// 	Serial.println(F("Time between steps (microseconds): 250 (HARDCODED)"));
	// 	Serial.println();
	// 	Serial.print(F("numberOfTraversedSteps: ")); Serial.println(numberOfTraversedSteps);
	// 	Serial.print(F("expectedSweepTimeInMilliseconds: ")); Serial.println(expectedSweepTimeInMilliseconds);
	// 	Serial.print(F("  actualSweepTimeInMilliseconds: ")); Serial.println(actualSweepTimeInMilliseconds);
	// 	Serial.println();
	// 	Serial.print(F("Enter '0' to continue..."));

	// 	String userInput = Serial.readStringUntil('\n');
	// 	if(userInput == "0") {
	// 		return;
	// 	}
	// }
}

void ControlPanel::diagnosticMode_fuelTest() {
	
	// this->moduleI.stepper_Fuel.setDesiredPosition(50);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(2000);
	// this->moduleI.stepper_Fuel.setDesiredPosition(3659);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(2000);
	// this->moduleI.stepper_Fuel.setDesiredPosition(50);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(250);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(500);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(750);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(1000);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(1250);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(1500);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(1750);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(2000);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(2250);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(2500);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(2750);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(3000);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(3250);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(3500);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// delay(100);
	// this->moduleI.stepper_Fuel.setDesiredPosition(3659);
	// this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	
	// // this->moduleI.stepper_Fuel.blockRunToDesiredPosition();
	// // delay();
}