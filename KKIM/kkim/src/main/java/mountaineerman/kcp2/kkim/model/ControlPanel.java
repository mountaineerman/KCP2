package mountaineerman.kcp2.kkim.model;

import krpc.client.services.SpaceCenter.SASMode;
import krpc.client.services.SpaceCenter.VesselSituation;
import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.KKIMProp;

/* MkII Control Panel
 */
public class ControlPanel implements LEDAggregator, StepperMotorAggregator {

	public ModuleA moduleA = null;
	public ModuleB moduleB = null;
	public ModuleC moduleC = null;
	public ModuleD moduleD = null;
	public ModuleE moduleE = null;
	public ModuleF moduleF = null;
	public ModuleG moduleG = null;
	public ModuleH moduleH = null;
	public ModuleI moduleI = null;
	public ModuleGT moduleGT = null;
	
	//TODO WRAP IN KMEGA class:
	public SwitchSP2T brake = null;
	public float throttleLever = 0;	 //Range: 0(OFF) to 1(Max Thrust)
	public float joystick_FwdBck = 0;//Range: -1(Back) to 1(Forward)
	public float joystick_LftRgh = 0;//Range: -1(Right) to 1(Left)
	public float joystick_Twist = 0; //Range: -1(CCW) to 1(CW)
	
	//TODO WRAP IN KSP class:
	//TODO double-check variable types in kRPC...
	
	//TODO change comments below to Javadoc format like percentTemperatureHealth
	//TODO convert int percentages to float like percentFuel
	
	/** Health of the hottest part on the vessel. Range: 0 to 100. 0=bad, 100=good. */
	public int percentTemperatureHealth = 0;

	public float currentFood = 0;
	public float maxFood = 0;
	public int percentFood = 0;//Range: 0 to 100
	public float currentWater = 0;
	public float maxWater = 0;
	public int percentWater = 0;//Range: 0 to 100
	public float currentOxygen = 0;
	public float maxOxygen = 0;
	public int percentOxygen = 0;//Range: 0 to 100
	public int percentLifeSupport = 0;//Range: 0 to 100. Worst of Food/Water/Oxygen. Does not include Electric Charge, since it has its own dedicated gauge
	public float gforce = 0;
	public float mach = 0;
	public float pitch = 0;	 //Units: degrees. Range: -90.0 to +90.0
	public float heading = 0;//Units: degrees. Range: 0.0 to 360.0

	public float currentLiquidFuel = 0;
	public float maxLiquidFuel = 0;
	public float currentSolidFuel = 0;
	public float maxSolidFuel = 0;
	/** a) If there is any capacity for Solid Fuel, based on Solid Fuel. Otherwise:
	 *  b) If there is any capacity for Liquid Fuel, based on Liquid Fuel. Otherwise:
	 *  c) Zero. 
	 * 
	 * Range: 0.0 to 100.0 */
	private float percentFuel = 0;

	public float currentElectricCharge = 0;
	private float previousElectricCharge = 0;
	public float maxElectricCharge = 0;
	private int percentElectricCharge = 0;//Range: 0 to 100
	public float currentMonopropellant = 0;
	public float maxMonopropellant = 0;
	private int percentMonopropellant = 0;//Range: 0 to 100
	public float currentIntakeAir = 0;
	public float currentAirDensity = 0;//Units: kg/m^3
	public float maxAirDensity = 0;//Units: kg/m^3. The maximum air density for the object around which the vessel is orbiting.
	private int invertedPercentAirDensity = 0;//Range: 0 to 100. 0 = you are in the thickest part of the atmosphere. 100 = you are in vacuum.
	public double surfaceReferenceFrame_speed = 0;//Units: meters/second.
	public double surfaceReferenceFrame_verticalSpeed = 0;//Units: meters/second.
	public double orbitalReferenceFrame_speed = 0;//Units: meters/second.
	public double orbitalReferenceFrame_verticalSpeed = 0;//Units: meters/second.
	public double altitudeAboveSurface = 0;//Units: meters. Measured from the center of mass of the vessel.
	public double altitudeAboveSeaLevel = 0;//Units: meters. Measured from the center of mass of the vessel.
	public float altitudeToDisplay = 0;//altitudeAboveSurface or altitudeAboveSeaLevel, depending on the position of the SpeedMode SP3T Switch
	public VesselSituation vesselSituation;
	public SASMode currentSASMode = null;
	
	public ControlPanel() {
		
		this.moduleA = new ModuleA();
		this.moduleB = new ModuleB();
		this.moduleC = new ModuleC();
		this.moduleD = new ModuleD();
		this.moduleE = new ModuleE();
		this.moduleF = new ModuleF();
		this.moduleG = new ModuleG();
		this.moduleH = new ModuleH();
		this.moduleI = new ModuleI();
		this.moduleGT = new ModuleGT();
		
		this.brake = new SwitchSP2T(IP.CombinedBrake);

		this.disableLEDOverride();
		this.setAllLEDsOff();
	}
	
	/**
	 * 1) Re-calculate state of higher-level members based on state of lower-level members.
	 * 2) Update outputs based on Control Panel state
	 * 		e.g., brake LEDs based on switch/button states.
	 * 		e.g., life support stepper gauge position based on food/water/oxygen reserves.
	 */
	public void refresh() {
		//TODO make use of SwitchSP2T:statusChanged()
		
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {CommonUtilities.clearScreen();}

		//Update inputs that are depended on by other Modules
		this.moduleE.sp3tSpeedModeSwitch.updatePosition();
		this.moduleE.sp3tVehicleModeSwitch.updatePosition();
		this.moduleE.sp3tPitchSwitch.updatePosition();
		this.moduleF.sensitivitySwitch.updatePosition();
		
		//Module A (+D+F) =====================================================
		if (this.moduleA.brakeButton.getStatus() ^ this.moduleD.brakeSwitch.getStatus()) {//XOR
			this.brake.setStatus(true);
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
		} else {
			this.brake.setStatus(false);
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
		}

		if ( KKIMProp.getkMegaSendPacketType().equals("outputRefreshPacket") ) {
			if (this.moduleA.analogInput_Throttle.getRawValue() > 925) {//TODO add configuration
				throttleLever = (float) 0;
			} else {
				throttleLever = ((this.moduleA.analogInput_Throttle.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Throttle.maxRescaleLim;
			}
		} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacketA") ||
					KKIMProp.getkMegaSendPacketType().equals("gaugePacketB") ) {
			throttleLever = 1;
		} else {
			throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.getkMegaSendPacketType());
		}
		//System.out.println("throttleLever: " + throttleLever);

		//Module B (+F) =======================================================
		joystick_FwdBck = ((this.moduleB.analogInput_Joystick_FwdBck.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_FwdBck.maxRescaleLim;
		joystick_LftRgh = ((this.moduleB.analogInput_Joystick_LftRgh.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_LftRgh.maxRescaleLim;
		joystick_Twist = ((this.moduleB.analogInput_Joystick_Twist.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_Twist.maxRescaleLim;
		
		//TODO Joystick button logic...
		
		//TODO Trim Logic (+Module F potentiometer)
		
		//Module C (+G) =======================================================
		// ----- Heat/Life Support ----------------------
		if (this.maxFood > 0) {
			this.percentFood = (int) (this.currentFood / this.maxFood * 100);
		} else {
			this.percentFood = -1;
		}
		if (this.maxWater > 0) {
			this.percentWater = (int) (this.currentWater / this.maxWater * 100);
		} else {
			this.percentWater = -1;
		}
		if (this.maxOxygen > 0) {
			this.percentOxygen = (int) (this.currentOxygen / this.maxOxygen * 100);
		} else {
			this.percentOxygen = -1;
		}
		this.percentLifeSupport = Math.min(this.percentFood, this.percentWater);
		this.percentLifeSupport = Math.min(this.percentLifeSupport, this.percentOxygen);
		
		if (this.moduleG.heatLifeSwitch.getStatus()) {//Life Support selected
			refreshPercentRGBLED(this.moduleC.stepperLED_Heat, LED_RGB_Brightness.DIM, this.percentTemperatureHealth);
			refreshPercentRGBLED(this.moduleC.stepperLED_LifeSupport, LED_RGB_Brightness.BRIGHT, this.percentLifeSupport);
			if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("percentLifeSupport: " + this.percentLifeSupport);}
			this.moduleC.stepper_HeatLife.setDesiredPositionUsingCalibrationLimits(this.percentLifeSupport, (float) 0, (float) 100);
		} else {//Heat selected
			refreshPercentRGBLED(this.moduleC.stepperLED_Heat, LED_RGB_Brightness.BRIGHT, this.percentTemperatureHealth);
			refreshPercentRGBLED(this.moduleC.stepperLED_LifeSupport, LED_RGB_Brightness.DIM, this.percentLifeSupport);
			if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("percentTemperatureHealth: " + this.percentTemperatureHealth);}
			this.moduleC.stepper_HeatLife.setDesiredPositionUsingCalibrationLimits(this.percentTemperatureHealth, (float) 0, (float) 100);
		}
		
		// ----- G-Force ----------------------
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("gforce: " + this.gforce);}
		this.moduleC.stepper_Gforce.setDesiredPositionUsingCalibrationLimits(this.gforce, (float) 0, (float) 15);
		if (this.gforce > 10.0) {
			this.moduleC.stepperLED_GForce.setMode(LED_RGB_Mode.RED);
		} else if (this.gforce > 8.0) {
			this.moduleC.stepperLED_GForce.setMode(LED_RGB_Mode.YELLOW);
		} else if (this.gforce > 3.0) {
			this.moduleC.stepperLED_GForce.setMode(LED_RGB_Mode.WHITE);
		} else if (this.gforce > 0.05) {
			this.moduleC.stepperLED_GForce.setMode(LED_RGB_Mode.GREEN);
		} else {
			this.moduleC.stepperLED_GForce.setMode(LED_RGB_Mode.BLUE);
		}
		
		//Module D ============================================================
		//Brake: see Module A

		//Autopilot modes:
		if (this.moduleD.sasSwitch.statusChanged() && !this.moduleD.sasSwitch.getStatus()) {
			this.moduleD.autoHoldLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoProgradeLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoRetrogradeLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoNormalBluLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoNormalRedLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoAntiNormalBluLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoAntiNormalRedLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoRadialInBluLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoRadialInGrnLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoRadialOutBluLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoRadialOutGrnLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoTargetBluLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoTargetRedLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoAntiTargetBluLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoAntiTargetRedLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.autoManeuverLED.setPWM(KKIMProp.getkmegaMinPWM());
		}

		if (this.moduleD.sasSwitch.getStatus()) {
			
			// Dim all autopilot LEDs:
			this.moduleD.autoHoldLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoProgradeLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoRetrogradeLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoNormalBluLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoNormalRedLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoAntiNormalBluLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoAntiNormalRedLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoRadialInBluLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoRadialInGrnLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoRadialOutBluLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoRadialOutGrnLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoTargetBluLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoTargetRedLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoAntiTargetBluLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoAntiTargetRedLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);
			this.moduleD.autoManeuverLED.setPWM(KKIMProp.getkmegaDimPWM() / 5);

			// Brighten the LED corresponding to the current SASMode:
			switch (this.currentSASMode) {
				case STABILITY_ASSIST:
					this.moduleD.autoHoldLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case PROGRADE:
					this.moduleD.autoProgradeLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case RETROGRADE:
					this.moduleD.autoRetrogradeLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case NORMAL:
					this.moduleD.autoNormalBluLED.setPWM(KKIMProp.getkmegaMaxPWM());
					this.moduleD.autoNormalRedLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case ANTI_NORMAL:
					this.moduleD.autoAntiNormalBluLED.setPWM(KKIMProp.getkmegaMaxPWM());
					this.moduleD.autoAntiNormalRedLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case RADIAL:
					this.moduleD.autoRadialOutBluLED.setPWM(KKIMProp.getkmegaMaxPWM());
					this.moduleD.autoRadialOutGrnLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case ANTI_RADIAL:
					this.moduleD.autoRadialInBluLED.setPWM(KKIMProp.getkmegaMaxPWM());
					this.moduleD.autoRadialInGrnLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case TARGET:
					this.moduleD.autoTargetBluLED.setPWM(KKIMProp.getkmegaMaxPWM());
					this.moduleD.autoTargetRedLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case ANTI_TARGET:
					this.moduleD.autoAntiTargetBluLED.setPWM(KKIMProp.getkmegaMaxPWM());
					this.moduleD.autoAntiTargetRedLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				case MANEUVER:
					this.moduleD.autoManeuverLED.setPWM(KKIMProp.getkmegaMaxPWM());
					break;
				default:
					break;
			}
		}
		
		
		//Module E (+G +GT) ===================================================
		if (this.moduleE.fairingButton.getRawStatus() == true) {
			this.moduleE.fairingLED.setPWM(KKIMProp.getkmegaMinPWM());
		} else {
			this.moduleE.fairingLED.setPWM(KKIMProp.getkmegaMaxPWM());
		}
		
		if (this.moduleE.chuteButton.getRawStatus() == true) {
			this.moduleE.parachuteLED.setPWM(KKIMProp.getkmegaMinPWM());
		} else {
			this.moduleE.parachuteLED.setPWM(KKIMProp.getkmegaMaxPWM());
		}
		
		if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.TOP) {//SFC
			this.altitudeToDisplay = (float) this.altitudeAboveSurface;
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.CENTER) {//ORB
			this.altitudeToDisplay = (float) this.altitudeAboveSeaLevel;
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.BOTTOM) {//TGT
			this.altitudeToDisplay = (float) this.altitudeAboveSeaLevel;
		} else {//INVALID
			this.altitudeToDisplay = KKIMProp.getkmegaAltitudeGaugeErrorAltitude();
		}
		
		//TODO Move e.g., vehicle mode logic from KRPCCommunicator::sendInfoFromModelToKSP() to here?
		
		//Pitch: see Module G
		
		//Module F ============================================================
		//System.out.println("Current draw (mA): " + this.moduleF.analogInput_Current.getRescaledValue());
		//Trim: see Module B
		//Sensitivity Switch: See Modules A, B
		
		//Module G (+E) =======================================================
		// ----- Mach ----------------------
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("mach: " + this.mach);}
		this.moduleG.stepper_Mach.setDesiredPositionUsingCalibrationLimits(this.mach, (float) 0, (float) 24);
		if (this.mach > 28.0) {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.VIOLET);
		} else if (this.mach > 16.0) {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.RED);
		} else if (this.mach > 8.0) {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.ORANGE);
		} else if (this.mach > 4.0) {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.YELLOW);
		} else if (this.mach > 1.0) {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.WHITE);
		} else if (this.mach > 0.001) {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.GREEN);
		} else if (this.mach > -0.001) {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.BLUE);
		} else {
			this.moduleG.stepperLED_Mach.setMode(LED_RGB_Mode.CYAN);
		}
		
		// ----- Pitch ----------------------
		if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.TOP) {//90 degrees
			if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("pitch (90 degree mode): " + this.pitch);}
			this.moduleG.stepper_Pitch.setDesiredPositionUsingCalibrationLimits(this.pitch, (float) -90, (float) 90);
			if (this.pitch > 60.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.BLUE);
			} else if (this.pitch > 30.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.CYAN);
			} else if (this.pitch > 1.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.GREEN);
			} else if (this.pitch > -1.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.WHITE);
			} else if (this.pitch > -30.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.YELLOW);
			} else if (this.pitch > -60.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.ORANGE);
			} else if (this.pitch >= -90.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.RED);
			} else {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.OFF);
			}
		} else if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.CENTER) {//30 degrees
			if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("pitch (30 degree mode): " + this.pitch);}
			this.moduleG.stepper_Pitch.setDesiredPositionUsingCalibrationLimits(this.pitch, (float) -30, (float) 30);
			if (this.pitch > 30.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.DIM_BLUE);
			} else if (this.pitch > 20.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.BLUE);
			} else if (this.pitch > 10.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.CYAN);
			} else if (this.pitch > 0.333) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.GREEN);
			} else if (this.pitch > -0.333) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.WHITE);
			} else if (this.pitch > -10.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.YELLOW);
			} else if (this.pitch > -20.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.ORANGE);
			} else if (this.pitch >= -30.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.RED);
			} else {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.DIM_RED);
			}
		} else if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.BOTTOM) {//9 degrees
			if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("pitch (9 degree mode): " + this.pitch);}
			this.moduleG.stepper_Pitch.setDesiredPositionUsingCalibrationLimits(this.pitch, (float) -9, (float) 9);
			if (this.pitch > 9.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.DIM_BLUE);
			} else if (this.pitch > 6.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.BLUE);
			} else if (this.pitch > 3.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.CYAN);
			} else if (this.pitch > 0.1) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.GREEN);
			} else if (this.pitch > -0.1) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.WHITE);
			} else if (this.pitch > -3.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.YELLOW);
			} else if (this.pitch > -6.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.ORANGE);
			} else if (this.pitch >= -9.0) {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.RED);
			} else {
				this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.DIM_RED);
			}
		} else {//INVALID
			this.moduleG.stepperLED_Pitch.setMode(LED_RGB_Mode.OFF);
		}
		
		// ----- Heading ----------------------
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("heading: " + this.heading);}
		this.moduleG.stepper_Heading.setDesiredPosition(this.heading, (float) 0, (float) 360);
		if ( (this.heading < 45.0) || (this.heading > 315.0) ) { //North quadrant
			this.moduleG.stepperLED_Heading.setMode(LED_RGB_Mode.DIM_BLUE);
		} else if (this.heading > 225.0) { //West quadrant
			this.moduleG.stepperLED_Heading.setMode(LED_RGB_Mode.DIM_ORANGE);
		} else if (this.heading > 135.0) { //South quadrant
			this.moduleG.stepperLED_Heading.setMode(LED_RGB_Mode.DIM_CYAN);
		} else { //East quadrant
			this.moduleG.stepperLED_Heading.setMode(LED_RGB_Mode.DIM_YELLOW);
		}
		
		//Module H ============================================================
		//TODO find Diagnostic Mode and Graceful Shutdown logic elsewhere...
		
		//Module I ============================================================
		// ----- Fuel ----------------------
		if (this.maxSolidFuel > 0) {
			this.percentFuel = this.currentSolidFuel / this.maxSolidFuel * (float) 100;
		} else if (this.maxLiquidFuel > 0) {
			this.percentFuel = this.currentLiquidFuel / this.maxLiquidFuel * (float) 100;
		} else {
			this.percentFuel = 0;
		}
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("percentFuel: " + this.percentFuel);}
		this.moduleI.stepper_Fuel.setDesiredPositionUsingCalibrationLimits(this.percentFuel, (float) 0, (float) 100);
		refreshPercentRGBLED(this.moduleI.stepperLED_Fuel, LED_RGB_Brightness.BRIGHT, this.percentFuel);
		
		// ----- Charge ----------------------
		if (this.maxElectricCharge > 0) {
			this.percentElectricCharge = (int) (this.currentElectricCharge / this.maxElectricCharge * 100);
		} else {
			this.percentElectricCharge = -1;
		}
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("percentElectricCharge: " + this.percentElectricCharge);}
		this.moduleI.stepper_Charge.setDesiredPositionUsingCalibrationLimits(this.percentElectricCharge, (float) 0, (float) 100);
		refreshPercentRGBLED(this.moduleI.stepperLED_Charge, LED_RGB_Brightness.BRIGHT, this.percentElectricCharge);
		
		if (this.currentElectricCharge > this.previousElectricCharge) {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.GREEN);
		} else if (this.currentElectricCharge == this.previousElectricCharge) {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.WHITE);
		} else {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.RED);
		}
		this.previousElectricCharge = this.currentElectricCharge;
		
		// ----- Monopropellant/Intake Air ----------------------
		if (this.maxMonopropellant > 0) {
			this.percentMonopropellant = (int) (this.currentMonopropellant / this.maxMonopropellant * 100);
		} else {
			this.percentMonopropellant = -1;
		}
		
		//FIXME always getting 0 current/max Intake Air. Must be different values (flow?)
//		if (this.maxIntakeAir > 0) {
//			this.percentIntakeAir = (int) (this.currentIntakeAir / this.maxIntakeAir * 100);
//		} else {
//			this.percentIntakeAir = 0;
//		}
		
		// if (this.moduleI.monopropIntakeSwitch.getStatus()) {//Intake Air selected
		// 	refreshPercentRGBLED(this.moduleI.stepperLED_Monopropellant, LED_RGB_Brightness.DIM, this.percentMonopropellant);
		// 	TODO percentIntakeAir:BRIGHT
		// 	
		// } else {//Monopropellant selected
		// 	refreshPercentRGBLED(this.moduleI.stepperLED_Monopropellant, LED_RGB_Brightness.BRIGHT, this.percentMonopropellant);
		// 	TODO percentIntakeAir:DIM
		// 	this.moduleI.stepper_MonopropellantIntake.setDesiredPositionUsingCalibrationLimits(this.percentElectricCharge, (float) 0, (float) 100);
		// }
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("percentMonopropellant: " + this.percentMonopropellant);}
		this.moduleI.stepper_MonopropellantIntake.setDesiredPositionUsingCalibrationLimits(this.percentMonopropellant, (float) 0, (float) 100);
		refreshPercentRGBLED(this.moduleI.stepperLED_Monopropellant, LED_RGB_Brightness.BRIGHT, this.percentMonopropellant);
		
		//Module GT ===========================================================
		// ----- Air Density ----------------------
		if (this.maxAirDensity > 0) {
			this.invertedPercentAirDensity = 100 - (int) (this.currentAirDensity / this.maxAirDensity * 100);
		} else {
			this.invertedPercentAirDensity = -1;
		}
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("invertedPercentAirDensity: " + this.invertedPercentAirDensity);}
		this.moduleGT.stepper_AirDensity.setDesiredPositionUsingCalibrationLimits(this.invertedPercentAirDensity, (float) 0, (float) 100);
		refreshPercentRGBLED(this.moduleGT.stepperLED_AirDensity, LED_RGB_Brightness.BRIGHT, this.invertedPercentAirDensity);
		
		// ----- Speed/Vertical Speed ----------------------
		float speed = (float) -1.0;
		float verticalSpeed = (float) -1.0;
		if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.TOP) {//SFC
			speed = (float) Math.abs((double) surfaceReferenceFrame_speed);
			verticalSpeed = (float) surfaceReferenceFrame_verticalSpeed;
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.CENTER) {//ORB
			speed = (float) Math.abs((double) orbitalReferenceFrame_speed);
			verticalSpeed = (float) orbitalReferenceFrame_verticalSpeed;
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.BOTTOM) {//TGT
			//TODO
			speed = (float) 0.0;
			verticalSpeed = (float) 0.0;
		} else {//INVALID
			speed = (float) 0.0;
			verticalSpeed = (float) 0.0;
		}
		
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("speed: " + speed);}
		if (speed > 3000.0) {
			this.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.getkmegaStepperSpeedPositionTRB());
		} else if (speed > 500.0) {
			this.moduleGT.stepper_Speed.setDesiredPositionUsingCustomLimits(speed, (float) 500.0, (float) 3000.0, KKIMProp.getkmegaStepperSpeedPositionFiveHundred(), KKIMProp.getkmegaStepperSpeedPositionThreeThousand());
		} else if (speed > 100.0) {
			this.moduleGT.stepper_Speed.setDesiredPositionUsingCustomLimits(speed, (float) 100.0, (float) 500.0, KKIMProp.getkmegaStepperSpeedPositionOneHundred(), KKIMProp.getkmegaStepperSpeedPositionFiveHundred());
		} else if (speed > 0.0) {
			this.moduleGT.stepper_Speed.setDesiredPositionUsingCustomLimits(speed, (float) 0.0, (float) 100.0, KKIMProp.getkmegaStepperSpeedPositionZero(), KKIMProp.getkmegaStepperSpeedPositionOneHundred());
		} else {
			this.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.getkmegaStepperSpeedPositionZero());
		}

		if (speed > 3000.0) {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.VIOLET);
		} else if (speed > 2000.0) {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.BLUE);
		} else if (speed > 1000.0) {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.CYAN);
		} else if (speed > 500.0) {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.GREEN);
		} else if (speed > 100.0) {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.ORANGE);
		} else if (speed > 0.1) {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.YELLOW);
		} else if (speed > -0.1) {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.WHITE);
		} else {
			this.moduleGT.stepperLED_Speed.setMode(LED_RGB_Mode.RED);
		}
		
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("verticalSpeed: " + verticalSpeed);}
		if (verticalSpeed > 50.0) {
			this.moduleGT.stepper_VerticalSpeed.setDesiredPositionUsingCustomLimits(verticalSpeed, (float) 50.0, (float) 200.0, KKIMProp.getkmegaStepperVerticalSpeedPositionPosFifty(), KKIMProp.getkmegaStepperVerticalSpeedPositionPosTwoHundred());
		} else if (verticalSpeed > -50.0) {
			this.moduleGT.stepper_VerticalSpeed.setDesiredPositionUsingCustomLimits(verticalSpeed, (float) -50.0, (float) 50.0, KKIMProp.getkmegaStepperVerticalSpeedPositionNegFifty(), KKIMProp.getkmegaStepperVerticalSpeedPositionPosFifty());
		} else {
			this.moduleGT.stepper_VerticalSpeed.setDesiredPositionUsingCustomLimits(verticalSpeed, (float) -200.0, (float) -50.0, KKIMProp.getkmegaStepperVerticalSpeedPositionNegTwoHundred(), KKIMProp.getkmegaStepperVerticalSpeedPositionNegFifty());
		}

		if (verticalSpeed > 50.0) {
			this.moduleGT.stepperLED_VerticalSpeed.setMode(LED_RGB_Mode.GREEN);
		} else if (verticalSpeed > 1.0) {
			this.moduleGT.stepperLED_VerticalSpeed.setMode(LED_RGB_Mode.DIM_GREEN);
		} else if (verticalSpeed > -1.0) {
			this.moduleGT.stepperLED_VerticalSpeed.setMode(LED_RGB_Mode.WHITE);
		} else if (verticalSpeed > -50.0) {
			this.moduleGT.stepperLED_VerticalSpeed.setMode(LED_RGB_Mode.DIM_RED);
		} else {
			this.moduleGT.stepperLED_VerticalSpeed.setMode(LED_RGB_Mode.RED);
		}
		
		// ----- Radar Altitude ----------------------
		if (KKIMProp.getkkimDisplayStepperMotorDigitalValues()) {System.out.println("altitudeAboveSurface: " + this.altitudeAboveSurface);}
		if (this.altitudeAboveSurface > 5000.0) {
			if (this.vesselSituation == VesselSituation.FLYING) {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.CYAN);
				this.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.getkmegaStepperRadarAltitudePositionATM());
			} else { //Some form of "in space"
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.BLUE);
				this.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.getkmegaStepperRadarAltitudePositionSPC());
			}
		} else {
			if (this.altitudeAboveSurface > 500.0) {
				this.moduleGT.stepper_RadarAltitude.setDesiredPositionUsingCustomLimits((float) altitudeAboveSurface, (float) 500.0, (float) 5000.0, KKIMProp.getkmegaStepperRadarAltitudePositionFiveHundred(), KKIMProp.getkmegaStepperRadarAltitudePositionFiveThousand());
			} else if (this.altitudeAboveSurface > 100.0) {
				this.moduleGT.stepper_RadarAltitude.setDesiredPositionUsingCustomLimits((float) altitudeAboveSurface, (float) 100.0, (float) 500.0, KKIMProp.getkmegaStepperRadarAltitudePositionOneHundred(), KKIMProp.getkmegaStepperRadarAltitudePositionFiveHundred());
			} else {
				this.moduleGT.stepper_RadarAltitude.setDesiredPositionUsingCustomLimits((float) altitudeAboveSurface, (float) 0.0, (float) 100.0, KKIMProp.getkmegaStepperRadarAltitudePositionZero(), KKIMProp.getkmegaStepperRadarAltitudePositionOneHundred());
			}

			if (this.vesselSituation == VesselSituation.PRE_LAUNCH ||
				this.vesselSituation == VesselSituation.LANDED ||
				this.vesselSituation == VesselSituation.SPLASHED) {
					this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.WHITE);
			} else if (this.altitudeAboveSurface > 500.0) {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.GREEN);
			} else if (this.altitudeAboveSurface > 100.0) {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.WHITE);
			} else if (this.altitudeAboveSurface > 50.0) {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.YELLOW);
			} else if (this.altitudeAboveSurface > 20.0) {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.ORANGE);
			} else if (this.altitudeAboveSurface > 10.0) {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.RED);
			} else {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.VIOLET);
			}
		}
	}
	
	public void refreshPercentRGBLED(LED_PWM_RGB led, LED_RGB_Brightness brightness, int percentage) { //TODO REMOVE (see below)
		if (brightness == LED_RGB_Brightness.BRIGHT) {
			if (percentage > 99) {
				led.setMode(LED_RGB_Mode.BLUE);
			} else if (percentage > 90) {
				led.setMode(LED_RGB_Mode.GREEN);
			} else if (percentage > 20) {
				led.setMode(LED_RGB_Mode.WHITE);
			} else if (percentage > 10) {
				led.setMode(LED_RGB_Mode.YELLOW);
			} else if (percentage > 0) {
				led.setMode(LED_RGB_Mode.RED);
			} else if (percentage == 0) {
				led.setMode(LED_RGB_Mode.VIOLET);
			} else {
				led.setMode(LED_RGB_Mode.CYAN);
			}
		} else if (brightness == LED_RGB_Brightness.DIM) {
			if (percentage > 99) {
				led.setMode(LED_RGB_Mode.DIM_BLUE);
			} else if (percentage > 90) {
				led.setMode(LED_RGB_Mode.DIM_GREEN);
			} else if (percentage > 20) {
				led.setMode(LED_RGB_Mode.DIM_WHITE);
			} else if (percentage > 10) {
				led.setMode(LED_RGB_Mode.DIM_YELLOW);
			} else if (percentage > 0) {
				led.setMode(LED_RGB_Mode.DIM_RED);
			} else if (percentage == 0) {
				led.setMode(LED_RGB_Mode.DIM_VIOLET);
			} else {
				led.setMode(LED_RGB_Mode.DIM_CYAN);
			}
		}
	}
	
	public void refreshPercentRGBLED(LED_PWM_RGB led, LED_RGB_Brightness brightness, float percentage) {
		if (brightness == LED_RGB_Brightness.BRIGHT) {
			if (percentage > 99.0) {
				led.setMode(LED_RGB_Mode.BLUE);
			} else if (percentage > 90.0) {
				led.setMode(LED_RGB_Mode.GREEN);
			} else if (percentage > 20.0) {
				led.setMode(LED_RGB_Mode.WHITE);
			} else if (percentage > 10.0) {
				led.setMode(LED_RGB_Mode.YELLOW);
			} else if (percentage > 0.0) {
				led.setMode(LED_RGB_Mode.RED);
			} else if (percentage == 0.0) {
				led.setMode(LED_RGB_Mode.VIOLET);
			} else {
				led.setMode(LED_RGB_Mode.CYAN);
			}
		} else if (brightness == LED_RGB_Brightness.DIM) {
			if (percentage > 99.0) {
				led.setMode(LED_RGB_Mode.DIM_BLUE);
			} else if (percentage > 90.0) {
				led.setMode(LED_RGB_Mode.DIM_GREEN);
			} else if (percentage > 20.0) {
				led.setMode(LED_RGB_Mode.DIM_WHITE);
			} else if (percentage > 10.0) {
				led.setMode(LED_RGB_Mode.DIM_YELLOW);
			} else if (percentage > 0.0) {
				led.setMode(LED_RGB_Mode.DIM_RED);
			} else if (percentage == 0.0) {
				led.setMode(LED_RGB_Mode.DIM_VIOLET);
			} else {
				led.setMode(LED_RGB_Mode.DIM_CYAN);
			}
		}
	}

	@Override
	public String toString() {
		
		return  //this.moduleA.toString(); //TODO compress to fit on 1 screen...
				//this.moduleB.toString();
				//this.moduleC.toString();
				//this.moduleD.toString();
				this.moduleE.toString();
				//this.moduleF.toString();
				//this.moduleG.toString();
				//this.moduleH.toString();
				//this.moduleI.toString();
				//this.moduleGT.toString();
				//this.brake.toString();
	}
	
	public void setAllLEDsOff() {
		this.moduleA.setAllLEDsOff();
		//Module B: N/A
		this.moduleC.setAllLEDsOff();
		this.moduleD.setAllLEDsOff();
		this.moduleE.setAllLEDsOff();
		this.moduleF.setAllLEDsOff();
		this.moduleG.setAllLEDsOff();
		this.moduleH.setAllLEDsOff();
		this.moduleI.setAllLEDsOff();
		this.moduleGT.setAllLEDsOff();
	}
	
	public void setAllLEDsOn() {//TODO
		
	}
	
	public void setAllSteppersCCW() {
		this.moduleC.stepper_HeatLife.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleC.stepper_Gforce.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		
		this.moduleG.stepper_Mach.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleG.stepper_Pitch.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleG.stepper_Heading.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());

		this.moduleI.stepper_Fuel.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleI.stepper_Charge.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleI.stepper_MonopropellantIntake.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());

		this.moduleGT.stepper_AirDensity.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleGT.stepper_VerticalSpeed.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
		this.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.getkmegaSteppersCCWLimit());
	}

	public void activateLEDOverride() {//TODO
		
	}
	
	public void disableLEDOverride() {//TODO
		
	}
}

































