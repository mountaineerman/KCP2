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
	
	altitude = STARTING_ALTITUDE;
	
	this->setAllLEDsOff();
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

void ControlPanel::setAllLEDsOff() {
	this->moduleA.setAllLEDsOff();
	this->moduleC.setAllLEDsOff();
	this->moduleD.setAllLEDsOff();
	this->moduleE.setAllLEDsOff();
	this->moduleF.setAllLEDsOff();
	this->moduleG.setAllLEDsOff();
	this->moduleH.setAllLEDsOff();
	this->moduleI.setAllLEDsOff();
	this->moduleGT.setAllLEDsOff();
	this->writeLEDStatusToLEDDriverBoards();
}

void ControlPanel::setAllLEDsOn() {
	this->moduleA.setAllLEDsOn();
	this->moduleC.setAllLEDsOn();
	this->moduleD.setAllLEDsOn();
	this->moduleE.setAllLEDsOn();
	this->moduleF.setAllLEDsOn();
	this->moduleG.setAllLEDsOn();
	this->moduleH.setAllLEDsOn();
	this->moduleI.setAllLEDsOn();
	this->moduleGT.setAllLEDsOn();
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

bool ControlPanel::runStepperIfNecessary() {
	bool isAMotorStillInMotion = false;
	isAMotorStillInMotion = this->moduleC.runStepperIfNecessary() || isAMotorStillInMotion;
//	isAMotorStillInMotion = this->moduleG.runStepperIfNecessary() || isAMotorStillInMotion;
//	isAMotorStillInMotion = this->moduleI.runStepperIfNecessary() || isAMotorStillInMotion;
//	isAMotorStillInMotion = this->moduleGT.runStepperIfNecessary() || isAMotorStillInMotion;
	return isAMotorStillInMotion;
}

void ControlPanel::blockRunAllSteppersToPosition(long position) {
	
//	this->moduleC.stepper_HeatLife.setDesiredPosition(position);
	this->moduleC.stepper_Gforce.setDesiredPosition(position);
//	this->moduleG.stepper_Mach.setDesiredPosition(position);
//	this->moduleG.stepper_Pitch.setDesiredPosition(position);
//	//this->moduleG.stepper_Heading...
//	this->moduleI.stepper_Fuel.setDesiredPosition(position);
//	this->moduleI.stepper_Charge.setDesiredPosition(position);
//	this->moduleI.stepper_MonopropellantIntake.setDesiredPosition(position);
//	this->moduleGT.stepper_Density.setDesiredPosition(position);
//	this->moduleGT.stepper_Speed.setDesiredPosition(position);
//	this->moduleGT.stepper_VertSpeed.setDesiredPosition(position);
//	this->moduleGT.stepper_RadarAlt.setDesiredPosition(position);
		
	//long startTime = millis();
	while(this->runStepperIfNecessary()) {
		//Serial.println(this->moduleC.stepper_HeatLife.getCurrentPosition());
		//delay(STEPPER_STEP_MINIMUM_TIME_INTERVAL);
	}
	//long endTime = millis();
	//Serial.print("Time to CW (ms): "); Serial.println(endTime - startTime);
}

void ControlPanel::sweepStepperMotorsThroughMaxMinToCalibrate() {
	this->blockRunAllSteppersToPosition(STEPPER_CW_LIMIT);
	this->blockRunAllSteppersToPosition(STEPPER_CCW_LIMIT);
}

void ControlPanel::runDiagnosticMode() {
	
	String userInput;
	
	while(true) {
	
		clearScreen();
		Serial.println("Diagnostic Mode activated. Select one of the following:");
		Serial.println("[0] Exit");
		Serial.println("[1] Test all inputs");
		Serial.println("[2] Test all LEDs (simultaneously)");
		Serial.println("[3] Test LEDs sequentially");
		Serial.println("[4] Test Stepper Motors");
		
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
		Serial.println("Testing All Inputs. Enter '0' at any time to return to the main menu.");
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
	Serial.println("Testing All LEDs. Enter '0' at any time to return to the main menu.");
	this->setAllLEDsOn();
	
	String userInput;
	
	while(true) {
		
		userInput = Serial.readStringUntil('\n');
		//Serial.println(userInput);
		//delay(3000);
		
		if(userInput == "0") {
			this->setAllLEDsOff();
			return;
		}
	}
}

void ControlPanel::diagnosticMode_testLEDsSequentially() {

	String userInput;
	
	while(true) {
	
		clearScreen();
		Serial.println("Testing LEDs Sequentially. LEDs will be lit top to bottom, left to right.");
		Serial.println("RGB LEDs will be lit in R>G>B order. Select which module(s) to test:");
		Serial.println("[0] Return to main menu");
		Serial.println("[all] All");
		Serial.println("[a] Module A");
		Serial.println("[c] Module C");
		Serial.println("[d] Module D");
		Serial.println("[e] Module E");
		Serial.println("[f] Module F");
		Serial.println("[g] Module G");
		Serial.println("[h] Module H");
		Serial.println("[i] Module I");
		Serial.println("[gt] Gauge Tower");
		
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
	
	String userInput;
	
	while(true) {
		clearScreen();
		Serial.println("Testing Stepper Motors. Select a stepper motor to test:");
		Serial.println("[0] Return to main menu");
		Serial.println("[1] Heat/Life");
		Serial.println("[2] G-Force");
		Serial.println("[3] Mach Number");
		Serial.println("[4] Pitch");
		Serial.println("[5] Heading");
		Serial.println("[6] Fuel");
		Serial.println("[7] Charge");
		Serial.println("[8] Monopropellant/Intake Air");
		Serial.println("[9] Air Density");
		Serial.println("[10] Speed");
		Serial.println("[11] Vertical Speed");
		Serial.println("[12] Radar Altitude");
		
		userInput = Serial.readStringUntil('\n');
		
		if(userInput == "0") {
			return;
		} else if(userInput == "1") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleC.stepper_HeatLife);
		} else if(userInput == "2") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleC.stepper_Gforce);
		} else if(userInput == "3") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleG.stepper_Mach);
		} else if(userInput == "4") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleG.stepper_Pitch);
		} else if(userInput == "5") {
			this->diagnosticMode_testNEMA17StepperMotor(this->moduleG.stepper_Heading);//TODO: Combine StepperMotor with StepperMotorNEMA17?
		} else if(userInput == "6") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleI.stepper_Fuel);
		} else if(userInput == "7") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleI.stepper_Charge);
		} else if(userInput == "8") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleI.stepper_MonopropellantIntake);
		} else if(userInput == "9") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleGT.stepper_Density);
		} else if(userInput == "10") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleGT.stepper_Speed);
		} else if(userInput == "11") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleGT.stepper_VertSpeed);
		} else if(userInput == "12") {
			this->diagnosticMode_testGearedStepperMotor(this->moduleGT.stepper_RadarAlt);
		}
	}
}

void ControlPanel::diagnosticMode_testGearedStepperMotor(/*TODO const?*/StepperMotor& stepperMotorUnderTest) { 
	
	String userInput;
	
	while(true) {
		clearScreen();
		Serial.print("Stepper Motor Position ["); Serial.print(STEPPER_CCW_LIMIT); Serial.print("-"); Serial.print(STEPPER_CW_LIMIT); Serial.print("]: "); Serial.println(stepperMotorUnderTest.getCurrentPosition());
		Serial.println("");
		Serial.println("Select one of the following options:");
		Serial.println("[0] Return to previous menu");
		Serial.println("[1] Move to maximum CCW position");
		Serial.println("[2] Move to maximum CW position");
		Serial.println("[3] Manual Control Mode (via Joystick) [TODO]");
		Serial.println("[4] Move 1000 steps CCW");
		Serial.println("[5] Move 1000 steps CW");
		Serial.println("[6] Move 100 steps CCW");
		Serial.println("[7] Move 100 steps CW");
		Serial.println("[8] Move 10 steps CCW");
		Serial.println("[9] Move 10 steps CW");
		
		userInput = Serial.readStringUntil('\n');
		
		if(userInput == "0") {
			return;
		} else if(userInput == "1") {
			stepperMotorUnderTest.setDesiredPosition(STEPPER_CCW_LIMIT);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "2") {
			stepperMotorUnderTest.setDesiredPosition(STEPPER_CW_LIMIT);
			stepperMotorUnderTest.runToDesiredPosition();
//		} else if(userInput == '3') {
//			//TODO
		} else if(userInput == "4") {
			stepperMotorUnderTest.setDesiredRelativePosition(-1000);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "5") {
			stepperMotorUnderTest.setDesiredRelativePosition(1000);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "6") {
			stepperMotorUnderTest.setDesiredRelativePosition(-100);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "7") {
			stepperMotorUnderTest.setDesiredRelativePosition(100);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "8") {
			stepperMotorUnderTest.setDesiredRelativePosition(-10);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "9") {
			stepperMotorUnderTest.setDesiredRelativePosition(10);
			stepperMotorUnderTest.runToDesiredPosition();			
		}
	}
}


void ControlPanel::diagnosticMode_testNEMA17StepperMotor(StepperMotorNEMA17& stepperMotorUnderTest) { //TODO remove
	
	String userInput;
	
	while(true) {
		clearScreen();
		Serial.print("Stepper Motor Position ["); Serial.print(NEMA17_CCW_LIMIT); Serial.print("-"); Serial.print(NEMA17_CW_LIMIT); Serial.print("]: "); Serial.println(stepperMotorUnderTest.getCurrentPosition());
		Serial.println("");
		Serial.println("Select one of the following options:");
		Serial.println("[0] Return to previous menu");
		Serial.println("[1] Move to maximum CCW position");
		Serial.println("[2] Move to maximum CW position");
		Serial.println("[3] Manual Control Mode (via Joystick) [TODO]");
		Serial.println("[4] Move 1000 steps CCW");
		Serial.println("[5] Move 1000 steps CW");
		Serial.println("[6] Move 100 steps CCW");
		Serial.println("[7] Move 100 steps CW");
		Serial.println("[8] Move 10 steps CCW");
		Serial.println("[9] Move 10 steps CW");
		
		userInput = Serial.readStringUntil('\n');
		
		if(userInput == "0") {
			return;
		} else if(userInput == "1") {
			stepperMotorUnderTest.setDesiredPosition(NEMA17_CCW_LIMIT);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "2") {
			stepperMotorUnderTest.setDesiredPosition(NEMA17_CW_LIMIT);
			stepperMotorUnderTest.runToDesiredPosition();
//		} else if(userInput == '3') {
//			//TODO
		} else if(userInput == "4") {
			stepperMotorUnderTest.setDesiredRelativePosition(-1000);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "5") {
			stepperMotorUnderTest.setDesiredRelativePosition(1000);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "6") {
			stepperMotorUnderTest.setDesiredRelativePosition(-100);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "7") {
			stepperMotorUnderTest.setDesiredRelativePosition(100);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "8") {
			stepperMotorUnderTest.setDesiredRelativePosition(-10);
			stepperMotorUnderTest.runToDesiredPosition();
		} else if(userInput == "9") {
			stepperMotorUnderTest.setDesiredRelativePosition(10);
			stepperMotorUnderTest.runToDesiredPosition();			
		}
	}
}

//Regardless of what status the LEDs driven by the LED Driver Boards are, turn them off
void ControlPanel::activateLEDOverride() {
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, HIGH);
}

//Allow the LEDs driven by the LED Driver Boards to be ON
void ControlPanel::disableLEDOverride() {
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, LOW);
}



































