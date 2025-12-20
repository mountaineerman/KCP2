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
	
	// Speed calibration settings:
	//                                                     Previous
	//                                           Stepper   Stepper
	//                             Speed (m/s)   Position  Position
	   private final int speedGauge_position_0 = 50;	// 0
	 private final int speedGauge_position_100 = 1120;	// 1060
	 private final int speedGauge_position_500 = 2180;	// 2130
	private final int speedGauge_position_3000 = 3590;	// 3540
	 private final int speedGauge_position_TRB = 3779;	// 3779
	
	// Vertical Speed calibration settings:
	//                                                             Previous
	//                                                   Stepper   Stepper
	//                            Vertical Speed (m/s)   Position  Position
	private final int vertSpeedGauge_position_minus200 = 0;		// 0       //NOTE: Calibration cannot quite reach the exact -200 mark (but very close)
	 private final int vertSpeedGauge_position_minus50 = 810;	// 835
	  private final int vertSpeedGauge_position_plus50 = 2940;	// 2965
	 private final int vertSpeedGauge_position_plus200 = 3755;	// 3770
	
	// Radar Altitude calibration settings:
	//                                                        Previous
	//                                              Stepper   Stepper
	//                         Radar Altitude (m)   Position  Position: 2025-12-17: No change since last calibration.
	   private final int radarAltGauge_position_0 = 110;	// 110
	 private final int radarAltGauge_position_100 = 1210;	// 1210
	 private final int radarAltGauge_position_500 = 2300;	// 2300
	private final int radarAltGauge_position_5000 = 3365;	// 3365
	 private final int radarAltGauge_position_ATM = 3570;	// 3570
	 private final int radarAltGauge_position_SPC = 3720;	// 3720

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
		
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {CommonUtilities.clearScreen();}

		//Update inputs that are depended on by other Modules
		this.moduleE.sp3tSpeedModeSwitch.updatePosition();
		this.moduleE.sp3tVehicleModeSwitch.updatePosition();
		this.moduleE.sp3tPitchSwitch.updatePosition();
		this.moduleF.sensitivitySwitch.updatePosition();
		
		//Module A (+D+F) =====================================================
		if (this.moduleA.brakeButton.getStatus() ^ this.moduleD.brakeSwitch.getStatus()) {//XOR
			this.brake.setStatus(true);
			this.moduleA.brakeLED.setPWM(KKIMProp.kmegaLEDOnPWM);
			this.moduleD.brakeLED.setPWM(KKIMProp.kmegaLEDOnPWM);
		} else {
			this.brake.setStatus(false);
			this.moduleA.brakeLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.brakeLED.setPWM(KKIMProp.kmegaLEDMinPWM);
		}

		if ( KKIMProp.kMegaSendPacketType.equals("outputRefreshPacket") ) {
			if (this.moduleA.analogInput_Throttle.getRawValue() > 925) {//TODO add configuration
				throttleLever = (float) 0;
			} else {
				throttleLever = ((this.moduleA.analogInput_Throttle.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Throttle.maxRescaleLim;
			}
		} else if ( KKIMProp.kMegaSendPacketType.equals("gaugePacketA") ||
					KKIMProp.kMegaSendPacketType.equals("gaugePacketB") ) {
			throttleLever = 1;
		} else {
			throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.kMegaSendPacketType);
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
		
		//The following logic is disabled because kRPC support for (OVER)HEAT has been disabled, due to performance problems.
		// if (this.moduleG.heatLifeSwitch.getStatus()) {//Life Support selected
		// 	refreshPercentRGBLED(this.moduleC.stepperLED_Heat, LED_RGB_Brightness.DIM, this.percentTemperatureHealth);
		// 	refreshPercentRGBLED(this.moduleC.stepperLED_LifeSupport, LED_RGB_Brightness.BRIGHT, this.percentLifeSupport);
		// 	if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("percentLifeSupport: " + this.percentLifeSupport);}
		// 	this.moduleC.stepper_HeatLife.setDesiredPositionUsingCalibrationLimits(this.percentLifeSupport, (float) 0, (float) 100);
		// } else {//Heat selected
		// 	refreshPercentRGBLED(this.moduleC.stepperLED_Heat, LED_RGB_Brightness.BRIGHT, this.percentTemperatureHealth);
		// 	refreshPercentRGBLED(this.moduleC.stepperLED_LifeSupport, LED_RGB_Brightness.DIM, this.percentLifeSupport);
		// 	if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("percentTemperatureHealth: " + this.percentTemperatureHealth);}
		// 	this.moduleC.stepper_HeatLife.setDesiredPositionUsingCalibrationLimits(this.percentTemperatureHealth, (float) 0, (float) 100);
		// }
		//Temporary:
		refreshPercentRGBLED(this.moduleC.stepperLED_LifeSupport, LED_RGB_Brightness.BRIGHT, this.percentLifeSupport);
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("percentLifeSupport: " + this.percentLifeSupport);}
		this.moduleC.stepper_HeatLife.setDesiredPositionUsingCalibrationLimits(this.percentLifeSupport, (float) 0, (float) 100);
		
		// ----- G-Force ----------------------
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("gforce: " + this.gforce);}
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
		//MUTE: see end of this method.

		//Autopilot modes:
		if (this.moduleD.sasSwitch.statusChanged() && !this.moduleD.sasSwitch.getStatus()) {
			this.moduleD.autoHoldLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoProgradeLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoRetrogradeLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoNormalBluLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoNormalRedLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoAntiNormalBluLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoAntiNormalRedLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoRadialInBluLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoRadialInGrnLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoRadialOutBluLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoRadialOutGrnLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoTargetBluLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoTargetRedLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoAntiTargetBluLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoAntiTargetRedLED.setPWM(KKIMProp.kmegaLEDMinPWM);
			this.moduleD.autoManeuverLED.setPWM(KKIMProp.kmegaLEDMinPWM);
		}

		if (this.moduleD.sasSwitch.getStatus()) {
			
			// Dim all autopilot LEDs:
			this.moduleD.autoHoldLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoProgradeLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoRetrogradeLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoNormalBluLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoNormalRedLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoAntiNormalBluLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoAntiNormalRedLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoRadialInBluLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoRadialInGrnLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoRadialOutBluLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoRadialOutGrnLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoTargetBluLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoTargetRedLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoAntiTargetBluLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoAntiTargetRedLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);
			this.moduleD.autoManeuverLED.setPWM(KKIMProp.kmegaLEDDimPWM / 5);

			// Brighten the LED corresponding to the current SASMode:
			switch (this.currentSASMode) {
				case STABILITY_ASSIST:
					this.moduleD.autoHoldLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case PROGRADE:
					this.moduleD.autoProgradeLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case RETROGRADE:
					this.moduleD.autoRetrogradeLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case NORMAL:
					this.moduleD.autoNormalBluLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					this.moduleD.autoNormalRedLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case ANTI_NORMAL:
					this.moduleD.autoAntiNormalBluLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					this.moduleD.autoAntiNormalRedLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case RADIAL:
					this.moduleD.autoRadialOutBluLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					this.moduleD.autoRadialOutGrnLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case ANTI_RADIAL:
					this.moduleD.autoRadialInBluLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					this.moduleD.autoRadialInGrnLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case TARGET:
					this.moduleD.autoTargetBluLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					this.moduleD.autoTargetRedLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case ANTI_TARGET:
					this.moduleD.autoAntiTargetBluLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					this.moduleD.autoAntiTargetRedLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				case MANEUVER:
					this.moduleD.autoManeuverLED.setPWM(KKIMProp.kmegaLEDOnPWM);
					break;
				default:
					break;
			}
		}
		
		
		//Module E (+G +GT) ===================================================
		if (this.moduleE.fairingButton.getRawStatus() == true) {
			this.moduleE.fairingLED.setPWM(KKIMProp.kmegaLEDMinPWM);
		} else {
			this.moduleE.fairingLED.setPWM(KKIMProp.kmegaLEDMaxPWM);
		}
		
		if (this.moduleE.chuteButton.getRawStatus() == true) {
			this.moduleE.parachuteLED.setPWM(KKIMProp.kmegaLEDMinPWM);
		} else {
			this.moduleE.parachuteLED.setPWM(KKIMProp.kmegaLEDMaxPWM);
		}
		
		if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.TOP) {//SFC
			this.altitudeToDisplay = (float) this.altitudeAboveSurface;
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.CENTER) {//ORB
			this.altitudeToDisplay = (float) this.altitudeAboveSeaLevel;
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.BOTTOM) {//TGT
			this.altitudeToDisplay = (float) this.altitudeAboveSeaLevel;
		} else {//INVALID
			this.altitudeToDisplay = KKIMProp.kmegaAltitudeGaugeErrorAltitude;
		}
		
		//TODO Move e.g., vehicle mode logic from KRPCCommunicator::sendInfoFromModelToKSP() to here?
		
		//Pitch: see Module G
		
		//Module F ============================================================
		//System.out.println("Current draw (mA): " + this.moduleF.analogInput_Current.getRescaledValue());
		//Trim: see Module B
		//Sensitivity Switch: See Modules A, B
		
		//Module G (+E) =======================================================
		// ----- Mach ----------------------
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("mach: " + this.mach);}
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
			if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("pitch (90 degree mode): " + this.pitch);}
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
			if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("pitch (30 degree mode): " + this.pitch);}
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
			if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("pitch (9 degree mode): " + this.pitch);}
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
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("heading: " + this.heading);}
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
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("percentFuel: " + this.percentFuel);}
		this.moduleI.stepper_Fuel.setDesiredPositionUsingCalibrationLimits(this.percentFuel, (float) 0, (float) 100);
		refreshPercentRGBLED(this.moduleI.stepperLED_Fuel, LED_RGB_Brightness.BRIGHT, this.percentFuel);
		
		// ----- Charge ----------------------
		if (this.maxElectricCharge > 0) {
			this.percentElectricCharge = (int) (this.currentElectricCharge / this.maxElectricCharge * 100);
		} else {
			this.percentElectricCharge = -1;
		}
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("percentElectricCharge: " + this.percentElectricCharge);}
		this.moduleI.stepper_Charge.setDesiredPositionUsingCalibrationLimits(this.percentElectricCharge, (float) 0, (float) 100);
		refreshPercentRGBLED(this.moduleI.stepperLED_Charge, LED_RGB_Brightness.BRIGHT, this.percentElectricCharge);
		
		if (this.currentElectricCharge > this.previousElectricCharge) {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.DIM_GREEN);
		} else if (this.currentElectricCharge == this.previousElectricCharge) {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.DIM_WHITE);
		} else {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.DIM_RED);
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
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("percentMonopropellant: " + this.percentMonopropellant);}
		this.moduleI.stepper_MonopropellantIntake.setDesiredPositionUsingCalibrationLimits(this.percentMonopropellant, (float) 0, (float) 100);
		refreshPercentRGBLED(this.moduleI.stepperLED_Monopropellant, LED_RGB_Brightness.BRIGHT, this.percentMonopropellant);
		
		//Module GT ===========================================================
		// ----- Air Density ----------------------
		if (this.maxAirDensity > 0) {
			this.invertedPercentAirDensity = 100 - (int) (this.currentAirDensity / this.maxAirDensity * 100);
		} else {
			this.invertedPercentAirDensity = -1;
		}
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("invertedPercentAirDensity: " + this.invertedPercentAirDensity);}
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
		
		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("speed: " + speed);}
		if (speed > 3000.0) {
			this.moduleGT.stepper_Speed.setDesiredPosition(speedGauge_position_TRB);
		} else if (speed > 500.0) {
			this.moduleGT.stepper_Speed.setDesiredPositionUsingCustomLimits(speed, (float) 500.0, (float) 3000.0, speedGauge_position_500, speedGauge_position_3000);
		} else if (speed > 100.0) {
			this.moduleGT.stepper_Speed.setDesiredPositionUsingCustomLimits(speed, (float) 100.0, (float) 500.0, speedGauge_position_100, speedGauge_position_500);
		} else if (speed > 0.0) {
			this.moduleGT.stepper_Speed.setDesiredPositionUsingCustomLimits(speed, (float) 0.0, (float) 100.0, speedGauge_position_0, speedGauge_position_100);
		} else {
			this.moduleGT.stepper_Speed.setDesiredPosition(0);//0
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

		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("verticalSpeed: " + verticalSpeed);}
		if (verticalSpeed > 50.0) {
			this.moduleGT.stepper_VerticalSpeed.setDesiredPositionUsingCustomLimits(verticalSpeed, (float) 50.0, (float) 200.0, vertSpeedGauge_position_plus50, vertSpeedGauge_position_plus200);
		} else if (verticalSpeed > -50.0) {
			this.moduleGT.stepper_VerticalSpeed.setDesiredPositionUsingCustomLimits(verticalSpeed, (float) -50.0, (float) 50.0, vertSpeedGauge_position_minus50, vertSpeedGauge_position_plus50);
		} else {
			this.moduleGT.stepper_VerticalSpeed.setDesiredPositionUsingCustomLimits(verticalSpeed, (float) -200.0, (float) -50.0, vertSpeedGauge_position_minus200, vertSpeedGauge_position_minus50);
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

		if (KKIMProp.kkimDisplayStepperMotorDigitalValues) {System.out.println("altitudeAboveSurface: " + this.altitudeAboveSurface);}
		if (this.altitudeAboveSurface > 5000.0) {
			if (this.vesselSituation == VesselSituation.FLYING) {
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.CYAN);
				this.moduleGT.stepper_RadarAltitude.setDesiredPosition(radarAltGauge_position_ATM);
			} else { //Some form of "in space"
				this.moduleGT.stepperLED_RadarAltitude.setMode(LED_RGB_Mode.BLUE);
				this.moduleGT.stepper_RadarAltitude.setDesiredPosition(radarAltGauge_position_SPC);
			}
		} else {
			if (this.altitudeAboveSurface > 500.0) {
				this.moduleGT.stepper_RadarAltitude.setDesiredPositionUsingCustomLimits((float) altitudeAboveSurface, (float) 500.0, (float) 5000.0, radarAltGauge_position_500, radarAltGauge_position_5000);
			} else if (this.altitudeAboveSurface > 100.0) {
				this.moduleGT.stepper_RadarAltitude.setDesiredPositionUsingCustomLimits((float) altitudeAboveSurface, (float) 100.0, (float) 500.0, radarAltGauge_position_100, radarAltGauge_position_500);
			} else {
				this.moduleGT.stepper_RadarAltitude.setDesiredPositionUsingCustomLimits((float) altitudeAboveSurface, (float) 0.0, (float) 100.0, radarAltGauge_position_0, radarAltGauge_position_100);
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

		//Module D: MUTE Switch
		if(this.moduleD.muteSwitch.getStatus()) {
			this.setAllLEDsOff();
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
	
	/**
	 * Update LED status for all LEDs in model to OFF. Does not send an OutputRefreshPacket.
	 * Exceptions:
	 *    1) Electrical Wiring: ...
	 *    2) COMMS LED (controlled by KMega).
	 */
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
	
	/**
	 * Set all stepper motors to the first "tick" near CCW. Set the HEADING gauge to the NORTH position.
	 */
	public void setAllSteppersToCCWTick() {
		this.moduleC.stepper_HeatLife.setDesiredPosition(this.moduleC.stepper_HeatLife.getCalibrationCCWLimit());
		this.moduleC.stepper_Gforce.setDesiredPosition(this.moduleC.stepper_Gforce.getCalibrationCCWLimit());
		
		this.moduleG.stepper_Mach.setDesiredPosition(this.moduleG.stepper_Mach.getCalibrationCCWLimit());
		this.moduleG.stepper_Pitch.setDesiredPosition(this.moduleG.stepper_Pitch.getCalibrationCCWLimit());
		this.moduleG.stepper_Heading.setDesiredPosition(0);

		this.moduleI.stepper_Fuel.setDesiredPosition(this.moduleI.stepper_Fuel.getCalibrationCCWLimit());
		this.moduleI.stepper_Charge.setDesiredPosition(this.moduleI.stepper_Charge.getCalibrationCCWLimit());
		this.moduleI.stepper_MonopropellantIntake.setDesiredPosition(this.moduleI.stepper_MonopropellantIntake.getCalibrationCCWLimit());

		this.moduleGT.stepper_AirDensity.setDesiredPosition(this.moduleGT.stepper_AirDensity.getCalibrationCCWLimit());
		this.moduleGT.stepper_Speed.setDesiredPosition(this.speedGauge_position_0);
		this.moduleGT.stepper_VerticalSpeed.setDesiredPosition(this.vertSpeedGauge_position_minus200);
		this.moduleGT.stepper_RadarAltitude.setDesiredPosition(this.radarAltGauge_position_0);
	}

	/**
	 * Set all stepper motors to the last "tick" near CW. Set the HEADING gauge to the NORTH position.
	 */
	public void setAllSteppersToCWTick() {
		this.moduleC.stepper_HeatLife.setDesiredPosition(this.moduleC.stepper_HeatLife.getCalibrationCWLimit());
		this.moduleC.stepper_Gforce.setDesiredPosition(this.moduleC.stepper_Gforce.getCalibrationCWLimit());
		
		this.moduleG.stepper_Mach.setDesiredPosition(this.moduleG.stepper_Mach.getCalibrationCWLimit());
		this.moduleG.stepper_Pitch.setDesiredPosition(this.moduleG.stepper_Pitch.getCalibrationCWLimit());
		this.moduleG.stepper_Heading.setDesiredPosition(0);

		this.moduleI.stepper_Fuel.setDesiredPosition(this.moduleI.stepper_Fuel.getCalibrationCWLimit());
		this.moduleI.stepper_Charge.setDesiredPosition(this.moduleI.stepper_Charge.getCalibrationCWLimit());
		this.moduleI.stepper_MonopropellantIntake.setDesiredPosition(this.moduleI.stepper_MonopropellantIntake.getCalibrationCWLimit());

		this.moduleGT.stepper_AirDensity.setDesiredPosition(this.moduleGT.stepper_AirDensity.getCalibrationCWLimit());
		this.moduleGT.stepper_Speed.setDesiredPosition(this.speedGauge_position_3000);
		this.moduleGT.stepper_VerticalSpeed.setDesiredPosition(this.vertSpeedGauge_position_plus200);
		this.moduleGT.stepper_RadarAltitude.setDesiredPosition(this.radarAltGauge_position_5000);
	}

	/**
	 * Set all stepper motors to their most counter-clockwise travel position, as defined in KKIMProp.
	 */
	public void setAllSteppersToMaxCCW() {
		this.moduleC.stepper_HeatLife.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleC.stepper_Gforce.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		
		this.moduleG.stepper_Mach.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleG.stepper_Pitch.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleG.stepper_Heading.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);

		this.moduleI.stepper_Fuel.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleI.stepper_Charge.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleI.stepper_MonopropellantIntake.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);

		this.moduleGT.stepper_AirDensity.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleGT.stepper_VerticalSpeed.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
		this.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.kmegaSteppersCCWLimit);
	}

	/**
	 * Set all stepper motors to their most clockwise travel position, as defined in KKIMProp.
	 */
	public void setAllSteppersToMaxCW() {
		this.moduleC.stepper_HeatLife.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleC.stepper_Gforce.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		
		this.moduleG.stepper_Mach.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleG.stepper_Pitch.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleG.stepper_Heading.setDesiredPosition(KKIMProp.kmegaNEMA17SteppersCWLimit);

		this.moduleI.stepper_Fuel.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleI.stepper_Charge.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleI.stepper_MonopropellantIntake.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);

		this.moduleGT.stepper_AirDensity.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleGT.stepper_Speed.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleGT.stepper_VerticalSpeed.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
		this.moduleGT.stepper_RadarAltitude.setDesiredPosition(KKIMProp.kmegaSteppersCWLimit);
	}

	public void activateLEDOverride() {//TODO
		
	}
	
	public void disableLEDOverride() {//TODO
		
	}
}

































