
package mountaineerman.kcp2.kkim.app;

import java.util.Arrays;

import mountaineerman.kcp2.kkim.KKIMProperties;
import mountaineerman.kcp2.kkim.model.*;
import mountaineerman.kcp2.kkim.service.KKIMService;

//import krpc.client.Connection;
//import krpc.client.RPCException;
//import krpc.client.services.KRPC;

import com.fazecast.jSerialComm.*; // Serial Communication library

public class App
{
	public static void main(String[] args)
	{
		KKIMProperties.initializeProperties();
		//System.out.println(KKIMProperties.getkMegaPortBaudrate());
		
		KKIMService kkimService;
		System.exit(0);
		
		/*
		// =================================================================================================================
		// PARTS - Inputs
		// =================================================================================================================
		
		// ========================================== SwitchSP2T / SwitchMom ===============================================
		SwitchMom stagingButton = new SwitchMom("stagingButton", ModuleID.A);
		SwitchMom brakeButton = new SwitchMom("brakeButton", ModuleID.A);
		
		SwitchMom abortButton = new SwitchMom("abortButton", ModuleID.B);
		SwitchSP2T trimPitchSwitch = new SwitchSP2T("trimPitchSwitch", ModuleID.B);
		SwitchSP2T trimYawSwitch = new SwitchSP2T("trimYawSwitch", ModuleID.B);
		SwitchSP2T trimRollSwitch = new SwitchSP2T("trimRollSwitch", ModuleID.B);
		SwitchMom timeWarpUpButton = new SwitchMom("timeWarpUpButton", ModuleID.B);
		SwitchMom timeWarpDownButton = new SwitchMom("timeWarpDownButton", ModuleID.B);
		SwitchMom joystickButton = new SwitchMom("joystickButton", ModuleID.B);
		
		SwitchSP2T SAS_Switch = new SwitchSP2T("SAS_Switch", ModuleID.D);
		SwitchSP2T RCS_Switch = new SwitchSP2T("RCS_Switch", ModuleID.D);
		SwitchSP2T lightsSwitch = new SwitchSP2T("lightsSwitch", ModuleID.D);
		SwitchSP2T gearSwitch = new SwitchSP2T("gearSwitch", ModuleID.D);
		SwitchSP2T brakeSwitch = new SwitchSP2T("brakeSwitch", ModuleID.D);
		SwitchSP2T mapSwitch = new SwitchSP2T("mapSwitch", ModuleID.D);
		SwitchSP2T muteSwitch = new SwitchSP2T("muteSwitch", ModuleID.D);
		SwitchMom autoHoldButton = new SwitchMom("autoHoldButton", ModuleID.D);
		SwitchMom autoProgradeButton = new SwitchMom("autoProgradeButton", ModuleID.D);
		SwitchMom autoRetrogradeButton = new SwitchMom("autoRetrogradeButton", ModuleID.D);
		SwitchMom autoNormalButton = new SwitchMom("autoNormalButton", ModuleID.D);
		SwitchMom autoAntiNormalButton = new SwitchMom("autoAntiNormalButton", ModuleID.D);
		SwitchMom autoRadialInButton = new SwitchMom("autoRadialInButton", ModuleID.D);
		SwitchMom autoRadialOutButton = new SwitchMom("autoRadialOutButton", ModuleID.D);
		SwitchMom autoTargetButton = new SwitchMom("autoTargetButton", ModuleID.D);
		SwitchMom autoAntiTargetButton = new SwitchMom("autoAntiTargetButton", ModuleID.D);
		SwitchMom autoManeuverButton = new SwitchMom("autoManeuverButton", ModuleID.D);
		
		SwitchMom ag1Switch = new SwitchMom("ag1Switch", ModuleID.E);
		SwitchMom ag2Switch = new SwitchMom("ag2Switch", ModuleID.E);
		SwitchMom ag3Switch = new SwitchMom("ag3Switch", ModuleID.E);
		SwitchMom ag4SwitchScience = new SwitchMom("ag4SwitchScience", ModuleID.E);
		SwitchMom ag5SwitchReset = new SwitchMom("ag5SwitchReset", ModuleID.E);
		SwitchMom ag6SwitchSolar = new SwitchMom("ag6SwitchSolar", ModuleID.E);
		SwitchMom ag7SwitchLadder = new SwitchMom("ag7SwitchLadder", ModuleID.E);
		SwitchMom ag8SwitchATNV = new SwitchMom("ag8SwitchATNV", ModuleID.E);
		SwitchMom ag9SwitchFairing = new SwitchMom("ag9SwitchFairing", ModuleID.E);
		SwitchMom ag10SwitchChute = new SwitchMom("ag10SwitchChute", ModuleID.E);
		
		SwitchSP2T trimMasterSwitch = new SwitchSP2T("trimMasterSwitch", ModuleID.F);
		
		SwitchSP2T heatLifeSwitch = new SwitchSP2T("heatLifeSwitch", ModuleID.G);
		
		SwitchMom glassTLButton = new SwitchMom("glassTLButton", ModuleID.H);
		SwitchMom glassCLButton = new SwitchMom("glassCLButton", ModuleID.H);
		SwitchMom glassBLButton = new SwitchMom("glassBLButton", ModuleID.H);
		SwitchMom glassTRButton = new SwitchMom("glassTRButton", ModuleID.H);
		SwitchMom glassCRButton = new SwitchMom("glassCRButton", ModuleID.H);
		SwitchMom glassBRButton = new SwitchMom("glassBRButton", ModuleID.H);
		
		SwitchSP2T mnprpIntakeSwitch = new SwitchSP2T("mnprpIntakeSwitch", ModuleID.I);
		
		// ========================================== SwitchSP3T ===========================================================
		SwitchSP2T tempTopSensor = new SwitchSP2T("sp3tSpeedSurfaceSensor", ModuleID.E);
		SwitchSP2T tempBottomSensor = new SwitchSP2T("sp3tSpeedTargetSensor", ModuleID.E);
		SwitchSP3T sp3tSpeedSwitch = new SwitchSP3T("sp3tSpeedSwitch", ModuleID.E, tempTopSensor, tempBottomSensor);
		tempTopSensor = null; tempBottomSensor = null;
		
		tempTopSensor = new SwitchSP2T("sp3tMnvrRocketSensor", ModuleID.E);
		tempBottomSensor = new SwitchSP2T("sp3tMnvrRoverSensor", ModuleID.E);
		SwitchSP3T sp3tMnvrSwitch = new SwitchSP3T("sp3tMnvrSwitch", ModuleID.E, tempTopSensor, tempBottomSensor);
		tempTopSensor = null; tempBottomSensor = null;
		
		tempTopSensor = new SwitchSP2T("sp3tPitch90Sensor", ModuleID.E);
		tempBottomSensor = new SwitchSP2T("sp3tPitch9Sensor", ModuleID.E);
		SwitchSP3T sp3tPitchSwitch = new SwitchSP3T("sp3tPitchSwitch", ModuleID.E, tempTopSensor, tempBottomSensor);
		tempTopSensor = null; tempBottomSensor = null;
		
		// ========================================== SwitchSP4T ===========================================================
		SwitchSP2T tempSensorAB = new SwitchSP2T("inputRangeSensorAB", ModuleID.F);
		SwitchSP2T tempSensorCD = new SwitchSP2T("inputRangeSensorCD", ModuleID.F);
		SwitchSP4T inputRangeSP4TSwitch = new SwitchSP4T("inputRangeSP4TSwitch", ModuleID.F, tempSensorAB, tempSensorCD);
		tempSensorAB = null; tempSensorCD = null;
		
		// ========================================== AnalogInput ==========================================================
		AnalogInput throttlePot = new AnalogInput("throttlePot", ModuleID.A, 0, 1023);
		//TODO create joystick mapping between axis number and Rotation (Pitch/Yaw/Roll), Translation (Up-Down/Left-Right/In-Out):
		AnalogInput joystickAxis1Pot = new AnalogInput("joystickAxis1Pot", ModuleID.B, 0, 1023); 
		AnalogInput joystickAxis2Pot = new AnalogInput("joystickAxis2Pot", ModuleID.B, 0, 1023);
		AnalogInput joystickAxis3Pot = new AnalogInput("joystickAxis3Pot", ModuleID.B, 0, 1023);
		AnalogInput multiUsePot = new AnalogInput("multiUsePot", ModuleID.F, 0, 1023);
		AnalogInput currentSensor = new AnalogInput("currentSensor", ModuleID.F, 0, 1023);
		
		
		
		
		// =================================================================================================================
		// PARTS - Outputs
		// =================================================================================================================
		
		// ========================================== LED_PWM ==============================================================
		LED_PWM moduleABrakeLED = new LED_PWM("moduleABrakeLED", ModuleID.A);
		
		LED_PWM moduleDBrakeLED = new LED_PWM("moduleDBrakeLED", ModuleID.D);
		LED_PWM autoHoldLED = new LED_PWM("autoHoldLED", ModuleID.D);
		LED_PWM autoProgradeLED = new LED_PWM("autoProgradeLED", ModuleID.D);
		LED_PWM autoRetrogradeLED = new LED_PWM("autoRetrogradeLED", ModuleID.D);
		LED_PWM autoManeuverLED = new LED_PWM("autoManeuverLED", ModuleID.D);
		
		LED_PWM fairingLED = new LED_PWM("fairingLED", ModuleID.E);
		LED_PWM chuteLED = new LED_PWM("chuteLED", ModuleID.E);
		LED_PWM SP3T_Speed_ORB_LED = new LED_PWM("SP3T_Speed_ORB_LED", ModuleID.E);
		LED_PWM SP3T_Mode_PLN_LED = new LED_PWM("SP3T_Mode_PLN_LED", ModuleID.E);
		LED_PWM SP3T_Pitch_30_LED = new LED_PWM("SP3T_Pitch_30_LED", ModuleID.E);
		
		LED_PWM inputRange100LED = new LED_PWM("inputRange100LED", ModuleID.F);
		LED_PWM inputRange75LED = new LED_PWM("inputRange75LED", ModuleID.F);
		LED_PWM inputRange50LED = new LED_PWM("inputRange50LED", ModuleID.F);
		LED_PWM inputRange25LED = new LED_PWM("inputRange25LED", ModuleID.F);
		
		LED_PWM commsLED = new LED_PWM("commsLED", ModuleID.G);
		
		LED_PWM glassTL_LED = new LED_PWM("glassTL_LED", ModuleID.H);
		LED_PWM glassCL_LED = new LED_PWM("glassCL_LED", ModuleID.H);
		LED_PWM glassBL_LED = new LED_PWM("glassBL_LED", ModuleID.H);
		LED_PWM glassTR_LED = new LED_PWM("glassTR_LED", ModuleID.H);
		LED_PWM glassCR_LED = new LED_PWM("glassCR_LED", ModuleID.H);
		LED_PWM glassBR_LED = new LED_PWM("glassBR_LED", ModuleID.H);
		
		// ========================================== LED_PWM_RGB ==========================================================
		LED_PWM tempRedLED = new LED_PWM("heatRedLED", ModuleID.C);
		LED_PWM tempGrnLED = new LED_PWM("heatGrnLED", ModuleID.C);
		LED_PWM tempBluLED = new LED_PWM("heatBluLED", ModuleID.C);
		LED_PWM_RGB heatRGBLED = new LED_PWM_RGB("heatRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("lifeRedLED", ModuleID.C);
		tempGrnLED = new LED_PWM("lifeGrnLED", ModuleID.C);
		tempBluLED = new LED_PWM("lifeBluLED", ModuleID.C);
		LED_PWM_RGB lifeRGBLED = new LED_PWM_RGB("lifeRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("gforceRedLED", ModuleID.C);
		tempGrnLED = new LED_PWM("gforceGrnLED", ModuleID.C);
		tempBluLED = new LED_PWM("gforceBluLED", ModuleID.C);
		LED_PWM_RGB gforceRGBLED = new LED_PWM_RGB("gforceRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoNormalRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoNormalBluLED", ModuleID.D);
		LED_PWM_RGB autoNormalRGBLED = new LED_PWM_RGB("autoNormalRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoAntiNormalRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoAntiNormalBluLED", ModuleID.D);
		LED_PWM_RGB autoAntiNormalRGBLED = new LED_PWM_RGB("autoAntiNormalRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempGrnLED = new LED_PWM("autoRadialInGrnLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoRadialInBluLED", ModuleID.D);
		LED_PWM_RGB autoRadialInRGBLED = new LED_PWM_RGB("autoRadialInRGBLED", ModuleID.D, null, tempGrnLED, tempBluLED);
		tempGrnLED = null; tempBluLED = null;
		
		tempGrnLED = new LED_PWM("autoRadialOutGrnLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoRadialOutBluLED", ModuleID.D);
		LED_PWM_RGB autoRadialOutRGBLED = new LED_PWM_RGB("autoRadialOutRGBLED", ModuleID.D, null, tempGrnLED, tempBluLED);
		tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoTargetRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoTargetBluLED", ModuleID.D);
		LED_PWM_RGB autoTargetRGBLED = new LED_PWM_RGB("autoTargetRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoAntiTargetRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoAntiTargetBluLED", ModuleID.D);
		LED_PWM_RGB autoAntiTargetRGBLED = new LED_PWM_RGB("autoAntiTargetRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("machRedLED", ModuleID.G);
		tempGrnLED = new LED_PWM("machGrnLED", ModuleID.G);
		tempBluLED = new LED_PWM("machBluLED", ModuleID.G);
		LED_PWM_RGB machRGBLED = new LED_PWM_RGB("machRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("pitchRedLED", ModuleID.G);
		tempGrnLED = new LED_PWM("pitchGrnLED", ModuleID.G);
		tempBluLED = new LED_PWM("pitchBluLED", ModuleID.G);
		LED_PWM_RGB pitchRGBLED = new LED_PWM_RGB("pitchRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("headingRedLED", ModuleID.G);
		tempGrnLED = new LED_PWM("headingGrnLED", ModuleID.G);
		tempBluLED = new LED_PWM("headingBluLED", ModuleID.G);
		LED_PWM_RGB headingRGBLED = new LED_PWM_RGB("headingRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("fuelRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("fuelGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("fuelBluLED", ModuleID.I);
		LED_PWM_RGB fuelRGBLED = new LED_PWM_RGB("fuelRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("chargeRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("chargeGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("chargeBluLED", ModuleID.I);
		LED_PWM_RGB chargeRGBLED = new LED_PWM_RGB("chargeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("deltaChargeRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("deltaChargeGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("deltaChargeBluLED", ModuleID.I);
		LED_PWM_RGB deltaChargeRGBLED = new LED_PWM_RGB("deltaChargeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("mnprpRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("mnprpGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("mnprpBluLED", ModuleID.I);
		LED_PWM_RGB mnprpRGBLED = new LED_PWM_RGB("mnprpRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("intakeRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("intakeGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("intakeBluLED", ModuleID.I);
		LED_PWM_RGB intakeRGBLED = new LED_PWM_RGB("intakeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("densityRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("densityGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("densityBluLED", ModuleID.GT);
		LED_PWM_RGB densityRGBLED = new LED_PWM_RGB("densityRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("speedRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("speedGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("speedBluLED", ModuleID.GT);
		LED_PWM_RGB speedRGBLED = new LED_PWM_RGB("speedRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("vertSpeedRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("vertSpeedGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("vertSpeedBluLED", ModuleID.GT);
		LED_PWM_RGB vertSpeedRGBLED = new LED_PWM_RGB("vertSpeedRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("radAltRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("radAltGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("radAltBluLED", ModuleID.GT);
		LED_PWM_RGB radAltRGBLED = new LED_PWM_RGB("radAltRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		// ========================================== StepperMotor =========================================================
		StepperMotor heatLifeStepper = new StepperMotor("heatLifeStepper", ModuleID.C);
		StepperMotor gforceStepper = new StepperMotor("gforceStepper", ModuleID.C);
		StepperMotor machStepper = new StepperMotor("machStepper", ModuleID.G);
		StepperMotor pitchStepper = new StepperMotor("pitchStepper", ModuleID.G);
		StepperMotor fuelStepper = new StepperMotor("fuelStepper", ModuleID.I);
		StepperMotor chargeStepper = new StepperMotor("chargeStepper", ModuleID.I);
		StepperMotor mnprpIntakeStepper = new StepperMotor("mnprpIntakeStepper", ModuleID.I);
		StepperMotor densityStepper = new StepperMotor("densityStepper", ModuleID.GT);
		StepperMotor speedStepper = new StepperMotor("speedStepper", ModuleID.GT);
		StepperMotor vertSpeedStepper = new StepperMotor("vertSpeedStepper", ModuleID.GT);
		StepperMotor radAltStepper = new StepperMotor("radAltStepper", ModuleID.GT);
		
		// ========================================== NEMA17StepperMotor ===================================================
		StepperMotorNEMA17 headingStepper = new StepperMotorNEMA17("headingStepper", ModuleID.G);
		
		// ========================================== AltitudeGauge ========================================================
		AltitudeGauge altitudeGauge = new AltitudeGauge("altitudeGauge", ModuleID.GT);
		*/
		
		
		
		
		
		
		
		
		
		// =================================================================================================================
		
		/* 	High-level flow:
		 * 	
		 * 	Initial:
		 * 		-Establish connection to kRPC
		 * 		-Establish connection to kMega
		 * 		-Establish connection to kAndro
		 * 	Loop:
		 * 		-Confirm connection to kRPC
		 * 		-Confirm connection to kMega
		 * 		-Confirm connection between kMega and kNano 
		 * 		-Confirm connection to kAndro
		 *		 
		 * 		A-Fetch latest kRPC values
		 * 		B-Fetch latest kMega values
		 * 		C-Update KIM object states based on A and B
		 * 			-Send "packet" to kMega
		 * 			-Send controlling signals to kRPC
		 * 			-Send "packet" to kAndro
		 * 
		 * 		-Idle	
		 */
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*System.out.println("Starting KKIM-kRPC connection test...");
		try (Connection connection = Connection.newInstance())
		{
			KRPC krpc = KRPC.newInstance(connection);
			System.out.println("Connected to kRPC version " + krpc.getStatus().getVersion());
		}
		catch (RPCException e) {System.out.println("Caught RPCException: " + e.getMessage());}
		catch (IOException e)  {System.out.println("Caught IOException: " + e.getMessage());}
		*/
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//https://fazecast.github.io/jSerialComm/
		System.out.println("Starting KKIM-KMega connection test...");
		
		SerialPort comPort = SerialPort.getCommPort(KKIMProperties.getkMegaPortNumber());
		comPort.setBaudRate(KKIMProperties.getkMegaPortBaudrate());
		comPort.openPort();
		
		//(0) Initial Serial Read test
		comPort.addDataListener(new SerialPortDataListener()
			{
				@Override
				public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
				
				@Override
				public void serialEvent(SerialPortEvent event) {
					if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
						return;
					byte[] newData = new byte[comPort.bytesAvailable()];
					int numRead = comPort.readBytes(newData, newData.length);
					System.out.println("Read " + numRead + " bytes: " + Arrays.toString(newData));
				}
			}
		);
		//END(0) Initial Serial Read test
		
		
//		//(1) Byte- or Multibyte-Delimited Message Received
//		private final class MessageListener implements SerialPortMessageListener
//		{
//		   @Override
//		   public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }
//
//		   @Override
//		   public byte[] getMessageDelimiter() { return new byte[] { (byte)0x0B, (byte)0x65 }; }
//
//		   @Override
//		   public boolean delimiterIndicatesEndOfMessage() { return true; }
//
//		   @Override
//		   public void serialEvent(SerialPortEvent event)
//		   {
//		      byte[] delimitedMessage = event.getReceivedData();
//		      System.out.println("Received the following delimited message: " + delimitedMessage);
//		   }
//		}
//
//		static public void main(String[] args)
//		{
//		   SerialPort comPort = SerialPort.getCommPorts()[0];
//		   comPort.openPort();
//		   MessageListener listener = new MessageListener();
//		   comPort.addDataListener(listener);
//		   try { Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
//		   comPort.removeDataListener();
//		   comPort.closePort();
//		}
//		//END (1)
		
		comPort.closePort();
		System.out.println("Ending KKIM-KMega connection test...");
		
	}
	
	
	
}

