package mountaineerman.kcp2.kkim.service;

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
		kkimService.controlPanel.moduleH.glassCR_LED.setPWM(KKIMProp.getkmegaMaxPWM());//KKIM Diagnostic Mode
		kkimService.controlPanel.setAllSteppersCCW();
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
			userInput = this.scanner.nextInt();
			switch (userInput) {
				case 0:
					System.out.println("!!! REMINDER: Power cycle the Nano Gauge Helpers to clear their \"Coffee Mode\" !!!");
					kkimService.controlPanel.setAllLEDsOff();
					kkimService.controlPanel.setAllSteppersCCW();
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
			System.out.println("====================================================================");
			System.out.println("[21] Set all geared stepper motors to " + KKIMProp.getkmegaSteppersCCWLimit());
			System.out.println("[22] Set all geared stepper motors to CCW edge mark");
			System.out.println("[23] Set all geared stepper motors to CW edge mark");
			System.out.println("[24] Set all geared stepper motors to " + KKIMProp.getkmegaGearedStepperCWLimit());
			
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
				case 21:
					kkimService.controlPanel.moduleC.stepper_HeatLife.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleC.stepper_Gforce.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleG.stepper_Mach.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleG.stepper_Pitch.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleI.stepper_Fuel.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleI.stepper_Charge.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleGT.stepper_AirDensity.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleGT.stepper_VerticalSpeed.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					kkimService.controlPanel.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					break;
				case 22:
					kkimService.controlPanel.moduleC.stepper_HeatLife.setDesiredPositionUsingCalibrationLimits((float) 0,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleC.stepper_Gforce.setDesiredPositionUsingCalibrationLimits((float) 0,  (float) 0, (float) 15);
					kkimService.controlPanel.moduleG.stepper_Mach.setDesiredPositionUsingCalibrationLimits((float) 0,  (float) 0, (float) 24);
					kkimService.controlPanel.moduleG.stepper_Pitch.setDesiredPositionUsingCalibrationLimits((float) -90, (float) -90, (float) 90);
					kkimService.controlPanel.moduleI.stepper_Fuel.setDesiredPositionUsingCalibrationLimits((float) 0,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleI.stepper_Charge.setDesiredPositionUsingCalibrationLimits((float) 0,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPositionUsingCalibrationLimits((float) 0,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleGT.stepper_AirDensity.setDesiredPositionUsingCalibrationLimits((float) 0,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.getkmegaStepperSpeedPositionZero());
					kkimService.controlPanel.moduleGT.stepper_VerticalSpeed.setDesiredPosition(KKIMProp.getkmegaStepperVerticalSpeedPositionNegTwoHundred());
					kkimService.controlPanel.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.getkmegaStepperRadarAltitudePositionZero());
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					break;
				case 23:
					kkimService.controlPanel.moduleC.stepper_HeatLife.setDesiredPositionUsingCalibrationLimits((float) 100,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleC.stepper_Gforce.setDesiredPositionUsingCalibrationLimits((float) 15,  (float) 0, (float) 15);
					kkimService.controlPanel.moduleG.stepper_Mach.setDesiredPositionUsingCalibrationLimits((float) 24,  (float) 0, (float) 24);
					kkimService.controlPanel.moduleG.stepper_Pitch.setDesiredPositionUsingCalibrationLimits((float) 90, (float) -90, (float) 90);
					kkimService.controlPanel.moduleI.stepper_Fuel.setDesiredPositionUsingCalibrationLimits((float) 100,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleI.stepper_Charge.setDesiredPositionUsingCalibrationLimits((float) 100,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPositionUsingCalibrationLimits((float) 100,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleGT.stepper_AirDensity.setDesiredPositionUsingCalibrationLimits((float) 100,  (float) 0, (float) 100);
					kkimService.controlPanel.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.getkmegaStepperSpeedPositionThreeThousand());
					kkimService.controlPanel.moduleGT.stepper_VerticalSpeed.setDesiredPosition(KKIMProp.getkmegaStepperVerticalSpeedPositionPosTwoHundred());
					kkimService.controlPanel.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.getkmegaStepperRadarAltitudePositionFiveThousand());
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					break;
				case 24:
					kkimService.controlPanel.moduleC.stepper_HeatLife.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleC.stepper_Gforce.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleG.stepper_Mach.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleG.stepper_Pitch.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleI.stepper_Fuel.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleI.stepper_Charge.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleI.stepper_MonopropellantIntake.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleGT.stepper_AirDensity.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleGT.stepper_VerticalSpeed.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					kkimService.controlPanel.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.getkmegaGearedStepperCWLimit());
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
					kkimService.serialCommunicator.flushInputOutputBuffers();
					kkimService.serialCommunicator.sendPacket(packet);
					break;
				default:
					break;
			}
		}
	}

	private void controlStepperMotor(KKIMService kkimService, StepperMotor motor) {

		int userInput = 0;
		while (userInput != -1) {
			CommonUtilities.clearScreen();
			System.out.println(motor.getName() + " selected.");
			System.out.println("  desiredPosition: " + motor.getDesiredPosition());
			System.out.println();
			System.out.println("Select one of the following options:");
			System.out.println("[-1] Return to previous menu");
			System.out.println("[-2] Perform \"decalibration\" test (cycle through 90% > 10% > 90% > ... several times)");
			System.out.println("[" + KKIMProp.getkmegaSteppersCCWLimit() + "-" + KKIMProp.getkmegaGearedStepperCWLimit() + "] Select desired position of the motor");
			userInput = this.scanner.nextInt();
			
			if (userInput >= KKIMProp.getkmegaSteppersCCWLimit() && userInput <= KKIMProp.getkmegaGearedStepperCWLimit()) {
				motor.setDesiredPosition(userInput);
				byte[] packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
				kkimService.serialCommunicator.flushInputOutputBuffers();
				kkimService.serialCommunicator.sendPacket(packet);
			} else if (userInput == -2) {
				byte[] packet = null;
				motor.setDesiredPositionUsingCalibrationLimits((float) 0.0, (float) 0, (float) 100);
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
			System.out.println("[" + KKIMProp.getkmegaSteppersCCWLimit() + "-" + KKIMProp.getkmegaNEMA17StepperMaxLimit() + "] Select desired position of the motor");
			userInput = this.scanner.nextInt();
			
			if (userInput >= KKIMProp.getkmegaSteppersCCWLimit() && userInput <= KKIMProp.getkmegaNEMA17StepperMaxLimit()) {
				motor.setDesiredPosition(userInput);
				byte[] packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
				kkimService.serialCommunicator.flushInputOutputBuffers();
				kkimService.serialCommunicator.sendPacket(packet);
			}
		}
	}
}