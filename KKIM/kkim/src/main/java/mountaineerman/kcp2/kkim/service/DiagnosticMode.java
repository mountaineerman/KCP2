package mountaineerman.kcp2.kkim.service;

import java.util.InputMismatchException;
import java.util.Scanner;
import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.KKIMProp;
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
		
		//Sleep while waiting for KMega and NGHes to prepare for diagnostic mode
		this.sleepForMilliseconds(5000);
		kkimService.controlPanel.setAllLEDsOff();
		kkimService.controlPanel.moduleH.glassCR_LED.setPWM(KKIMProp.kmegaLEDOnPWM);//KKIM Diagnostic Mode
		kkimService.controlPanel.setAllSteppersToMaxCCW();
		byte[] packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
		kkimService.serialCommunicator.flushInputOutputBuffers();
		kkimService.serialCommunicator.sendPacket(packet);

		this.mainMenu(kkimService);
		this.scanner.close();
        kkimService.setCurrentOperatingMode(ShutdownMode.getInstance());
    }

	private void sleepForMilliseconds(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException i_e) {i_e.printStackTrace();}
	}

	private void mainMenu(KKIMService kkimService) {

		int userInput = -1;
		while (userInput != 0) {
			CommonUtilities.clearScreen();
			System.out.println("Diagnostic Mode activated. Select one of the following:");
			System.out.println("[0] Shut down (KKIM only)");
			System.out.println("[1] Test Stepper Motors");

			try {
				userInput = this.scanner.nextInt();
				switch (userInput) {
					case 0:
						System.out.println("!!! REMINDER: Power cycle the Nano Gauge Helpers to clear their \"Coffee Mode\" !!!");
						kkimService.controlPanel.setAllLEDsOff();
						kkimService.controlPanel.setAllSteppersToMaxCCW();
						byte[] packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
						kkimService.serialCommunicator.flushInputOutputBuffers();
						kkimService.serialCommunicator.sendPacket(packet);
						break;
					case 1:
						testStepperMotors(kkimService);
						break;
					default:
						break;
				}
			} catch (InputMismatchException | IllegalStateException e) {
				System.out.println("Unexpected input caused exception:");
				e.printStackTrace();
			}
		}
	}

	private void testStepperMotors(KKIMService kkimService) {

		int userInput = -1;
		while (userInput != 0) {
			CommonUtilities.clearScreen();
			System.out.println("Testing Stepper Motors. Select an option:");
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
			System.out.println("[13] Move all stepper motors to maximum CCW position");
			System.out.println("[14] Move all stepper motors to first (CCW) tick");
			System.out.println("[15] Move all stepper motors to last (CW) tick");
			System.out.println("[16] Move all stepper motors to maximum CW position");

			try{
				userInput = this.scanner.nextInt();
				byte[] packet = null;
				switch (userInput) {
					case 1:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleC.stepper_HeatLife); break;
					case 2:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleC.stepper_Gforce); break;
					case 3:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleG.stepper_Mach); break;
					case 4:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleG.stepper_Pitch); break;
					case 5:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleG.stepper_Heading); break;
					case 6:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleI.stepper_Fuel); break;
					case 7:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleI.stepper_Charge); break;
					case 8:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleI.stepper_MonopropellantIntake); break;
					case 9:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleGT.stepper_AirDensity); break;
					case 10:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleGT.stepper_Speed); break;
					case 11:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleGT.stepper_VerticalSpeed); break;
					case 12:
						controlStepperMotor(kkimService, kkimService.controlPanel.moduleGT.stepper_RadarAltitude); break;
					case 13:
						kkimService.controlPanel.setAllSteppersToMaxCCW();
						packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
						kkimService.serialCommunicator.flushInputOutputBuffers();
						kkimService.serialCommunicator.sendPacket(packet);
					case 14:
						kkimService.controlPanel.setAllSteppersToCCWTick();
						packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
						kkimService.serialCommunicator.flushInputOutputBuffers();
						kkimService.serialCommunicator.sendPacket(packet);
						break;
					case 15:
						kkimService.controlPanel.setAllSteppersToCWTick();
						packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
						kkimService.serialCommunicator.flushInputOutputBuffers();
						kkimService.serialCommunicator.sendPacket(packet);
						break;
					case 16:
						kkimService.controlPanel.setAllSteppersToMaxCW();
						packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
						kkimService.serialCommunicator.flushInputOutputBuffers();
						kkimService.serialCommunicator.sendPacket(packet);
					default:
						break;
				}
			} catch (InputMismatchException | IllegalStateException e) {
				System.out.println("Unexpected input caused exception:");
				e.printStackTrace();
			}
		}
	}

	private void controlStepperMotor(KKIMService kkimService, StepperMotor motor) {

		int userInput = 0;
		while (userInput != -1) {
			CommonUtilities.clearScreen();
			System.out.println(motor.getName() + " selected.");
			System.out.println("  calibrationCCWLimit: " + motor.getCalibrationCCWLimit());
			System.out.println("      desiredPosition: " + motor.getDesiredPosition());
			System.out.println("   calibrationCWLimit: " + motor.getCalibrationCWLimit());
			System.out.println();
			System.out.println("Note: Stepper Calibration Limits are defined in OP.java. To quickly open file, use CTRL+P.");
			System.out.println();
			System.out.println("Select one of the following options:");
			System.out.println("[-1] Return to previous menu");
			System.out.println("[-2] Perform \"decalibration\" test (cycle through 90% > 10% > 90% > ... several times)");
			System.out.println("[-3] Perform \"decalibration\" thrashing test (same as decalibration, but with less sleep time, causing sudden direction changes)");
			System.out.println("[" + KKIMProp.kmegaSteppersCCWLimit + "-" + KKIMProp.kmegaSteppersCWLimit + "] Select desired position of the motor");

			try {
				userInput = this.scanner.nextInt();
				
				if (userInput >= KKIMProp.kmegaSteppersCCWLimit && userInput <= KKIMProp.kmegaSteppersCWLimit) {
					motor.setDesiredPosition(userInput);
					byte[] packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
				} else if (userInput == -2) {
					byte[] packet = null;
					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(3000);

					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);

					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);
					
					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);
					
					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);
					
					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(1500);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
				} else if (userInput == -3) {
					byte[] packet = null;
					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(3000);

					int sleepTime = 500;

					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);

					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);
					
					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);
					
					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);
					
					motor.setDesiredPositionUsingCalibrationLimits((float) 90.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					this.sleepForMilliseconds(sleepTime);

					motor.setDesiredPositionUsingCalibrationLimits((float) 10.0, (float) 0, (float) 100);
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
				}
			} catch (InputMismatchException | IllegalStateException e) {
				System.out.println("Unexpected input caused exception:");
				e.printStackTrace();
			}
		}
	}

	private void controlStepperMotor(KKIMService kkimService, NEMA17Stepper motor) {

		int userInput = 0;
		while (userInput != -1) {
			CommonUtilities.clearScreen();
			System.out.println(motor.getName() + " selected.");
			System.out.println("  desiredPosition: " + motor.getDesiredPosition());
			System.out.println();
			System.out.println("Select one of the following options:");
			System.out.println("[-1] Return to previous menu");
			System.out.println("[0-1599] Select desired position of the motor");
			
			try {
				userInput = this.scanner.nextInt();
				
				if (userInput >= 0 && userInput <= 1599) {
					motor.setDesiredPosition(userInput);
					byte[] packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
				}
			} catch (InputMismatchException | IllegalStateException e) {
				System.out.println("Unexpected input caused exception:");
				e.printStackTrace();
			}
		}
	}
}