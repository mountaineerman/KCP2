package mountaineerman.kcp2.kkim.service;

import java.util.Scanner;
import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.model.NEMA17Stepper;
import mountaineerman.kcp2.kkim.model.StepperMotor;

public final class DiagnosticMode implements OperatingMode { //SINGLETON

	private static DiagnosticMode INSTANCE;
	private Scanner scanner = null; 
    
	private DiagnosticMode() {
		this.scanner = new Scanner(System.in);
	}
	
	public static DiagnosticMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DiagnosticMode();
        }
        return INSTANCE;
    }
	
	public void run(KKIMService kkimService) {
		
		this.mainMenu(kkimService);
		this.scanner.close();
        kkimService.setCurrentOperatingMode(ShutdownMode.getInstance());
    }

	private void mainMenu(KKIMService kkimService) {

		int userInput = -1;
		while (userInput != 0) {
			CommonUtilities.clearScreen();
			System.out.println("Diagnostic Mode activated. Select one of the following:");
			System.out.println("[0] Shut down");
			System.out.println("[1] Test Stepper Motors");
			userInput = this.scanner.nextInt();
			switch (userInput) {
				case 1:
					testStepperMotors(kkimService);
					break;
				default:
					break;
			}
		}
	}

	private void testStepperMotors(KKIMService kkimService) {

		int userInput = -1;
		while (userInput != 0) {
			CommonUtilities.clearScreen();
			System.out.println("Testing Stepper Motors. Select a motor:");
			System.out.println("[0] Return to previous menu");
			System.out.println("[1] Heat/Life");
			System.out.println("[2] G-Force");
			System.out.println("[3] Mach Number");
			System.out.println("[4] Pitch");
			System.out.println("[5] Heading");
			System.out.println("[6] Fuel");
			System.out.println("[7] Charge");
			System.out.println("[8] Monopropellant/Intake Air");
			System.out.println("[9] Air Density");
			System.out.println("[10] Speed");
			System.out.println("[11] Vertical Speed");
			System.out.println("[12] Radar Altitude");
			userInput = this.scanner.nextInt();
			switch (userInput) {
				case 1:
					controlStepperMotor(kkimService.controlPanel.moduleC.stepper_HeatLife); break;
				case 2:
					controlStepperMotor(kkimService.controlPanel.moduleC.stepper_Gforce); break;
				case 3:
					controlStepperMotor(kkimService.controlPanel.moduleG.stepper_Mach); break;
				case 4:
					controlStepperMotor(kkimService.controlPanel.moduleG.stepper_Pitch); break;
				case 5:
					controlStepperMotor(kkimService.controlPanel.moduleG.stepper_Heading); break;
				case 6:
					controlStepperMotor(kkimService.controlPanel.moduleI.stepper_Fuel); break;
				case 7:
					controlStepperMotor(kkimService.controlPanel.moduleI.stepper_Charge); break;
				case 8:
					controlStepperMotor(kkimService.controlPanel.moduleI.stepper_MonopropellantIntake); break;
				case 9:
					controlStepperMotor(kkimService.controlPanel.moduleGT.stepper_AirDensity); break;
				case 10:
					controlStepperMotor(kkimService.controlPanel.moduleGT.stepper_Speed); break;
				case 11:
					controlStepperMotor(kkimService.controlPanel.moduleGT.stepper_VerticalSpeed); break;
				case 12:
					controlStepperMotor(kkimService.controlPanel.moduleGT.stepper_RadarAltitude); break;
				default:
					break;
			}
		}
	}

	private void controlStepperMotor(StepperMotor motor) {

		int userInput = 0;
		while (userInput != -1) {
			CommonUtilities.clearScreen();
			System.out.println(motor.getName() + " selected.");
			System.out.println("  desiredPosition: " + motor.getDesiredPosition());
			System.out.println();
			System.out.println("Select one of the following options:");
			System.out.println("[-1] Return to previous menu");
			System.out.println("[0-3779] Select desired position of the motor");
			userInput = this.scanner.nextInt();
			
			if (userInput >= 0 && userInput <= 3779) {
				motor.setDesiredPosition(userInput);
			}
		}
	}

	private void controlStepperMotor(NEMA17Stepper motor) {

		int userInput = 0;
		while (userInput != -1) {
			CommonUtilities.clearScreen();
			System.out.println(motor.getName() + " selected.");
			System.out.println("  desiredPosition: " + motor.getDesiredPosition());
			System.out.println();
			System.out.println("Select one of the following options:");
			System.out.println("[-1] Return to previous menu");
			System.out.println("[0-1599] Select desired position of the motor");
			userInput = this.scanner.nextInt();
			
			if (userInput >= 0 && userInput <= 1599) {
				motor.setDesiredPosition(userInput);
			}
		}
	}
}