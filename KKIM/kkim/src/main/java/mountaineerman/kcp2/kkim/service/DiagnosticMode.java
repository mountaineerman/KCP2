package mountaineerman.kcp2.kkim.service;

public final class DiagnosticMode implements OperatingMode { //SINGLETON

	private static DiagnosticMode INSTANCE;
    
	private DiagnosticMode() {}
	
	public static DiagnosticMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DiagnosticMode();
        }
        return INSTANCE;
    }
	
	public void run(KKIMService kkimService) {
		//Display:
		kkimService.controlPanel.toString();
		kkimService.controlPanel.moduleF.analogInput_Current.getRescaledValue();//Current Draw in mA...
		
        //kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
    }
}

/*

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

 */