package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;

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
	public boolean brake = false;
	public float throttleLever = 0;	 //Range: 0(OFF) to 1(Max Thrust)
	public float joystick_FwdBck = 0;//Range: -1(Back) to 1(Forward)
	public float joystick_LftRgh = 0;//Range: -1(Right) to 1(Left)
	public float joystick_Twist = 0; //Range: -1(CCW) to 1(CW)
	
	//TODO WRAP IN KSP class:
	//TODO double-check variable types in kRPC...
	//heat: loop through all parts?: getTemperature()/getMaxTemperature(), getSkinTemperature()/getMaxSkinTemperature()
	public int percentTemperatureHealth = 0; //Health of the hottest part on the vessel. Range: 0 to 100. 0=bad, 100=good.
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
	private int percentFuel = 0;//Range: 0 to 100
	public float currentElectricCharge = 0;
	private float previousElectricCharge = 0;
	public float maxElectricCharge = 0;
	private int percentElectricCharge = 0;//Range: 0 to 100
	public float currentMonopropellant = 0;
	public float maxMonopropellant = 0;
	private int percentMonopropellant = 0;//Range: 0 to 100
	public float currentIntakeAir = 0;
	
	public float airDensity = 0;
	public double speed = 0;//Units: meters/second.
	public double verticalSpeed = 0;//Units: meters/second.
	public double altitudeAboveSurface = 0;//Units: meters. Measured from the center of mass of the vessel.
	public double altitudeAboveSeaLevel = 0;//Units: meters. Measured from the center of mass of the vessel.
	public float altitudeToDisplay = 0;//altitudeAboveSurface or altitudeAboveSeaLevel, depending on the position of the SpeedMode SP3T Switch
	
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
		
		this.disableLEDOverride();
		this.setAllLEDsOff();
	}
	
	//Re-calculate state of higher-level members based on state of lower-level members
	public void refresh() {
		//TODO Stepper Motors...
		
		//Update inputs that are depended on by other Modules
		this.moduleE.sp3tSpeedModeSwitch.updatePosition();
		this.moduleE.sp3tVehicleModeSwitch.updatePosition();
		this.moduleE.sp3tPitchSwitch.updatePosition();
		this.moduleF.sensitivitySwitch.updatePosition();
		
		//Module A (+D+F) =====================================================
		if (this.moduleA.brakeButton.getStatus() ^ this.moduleD.brakeSwitch.getStatus()) {//XOR
			this.brake = true;
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
		} else {
			this.brake = false;
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
		}
		
		if (this.moduleA.analogInput_Throttle.getRawValue() > 925) {//TODO add configuration
			throttleLever = (float) 0;
		} else {
			throttleLever = ((this.moduleA.analogInput_Throttle.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Throttle.maxRescaleLim;
		}
		
		//Module B (+F) =======================================================
		joystick_FwdBck = ((this.moduleB.analogInput_Joystick_FwdBck.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_FwdBck.maxRescaleLim;
		joystick_LftRgh = ((this.moduleB.analogInput_Joystick_LftRgh.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_LftRgh.maxRescaleLim;
		joystick_Twist = ((this.moduleB.analogInput_Joystick_Twist.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_Twist.maxRescaleLim;
		
		//TODO Joystick button logic...
		
		//TODO Trim Logic (+Module F potentiometer)
		
		//Module C (+G) =======================================================
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
		} else {//Heat selected
			refreshPercentRGBLED(this.moduleC.stepperLED_Heat, LED_RGB_Brightness.BRIGHT, this.percentTemperatureHealth);
			refreshPercentRGBLED(this.moduleC.stepperLED_LifeSupport, LED_RGB_Brightness.DIM, this.percentLifeSupport);
		}
		
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
		//TODO Autopilot modes
		//Brake: see Module A
		//TODO Map
		//TODO Mute
		
		//Module E (+G +GT) ===================================================
		//TODO Science
		//TODO Reset
		//TODO Solar Panels (PV)
		//TODO Ladder
		//TODO AutoNavigation (ATNV)
		//TODO Action Groups 1/2/3 (AG1/AG2/AG3)
		
		//TODO Activate Fairing
		if (this.moduleE.fairingButton.getRawStatus() == true) {
			this.moduleE.fairingLED.setPWM(KKIMProp.getkmegaMinPWM());
		} else {
			this.moduleE.fairingLED.setPWM(KKIMProp.getkmegaMaxPWM());
		}
		
		//TODO Activate Parachute
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
		//Trim: see Module B
		//Sensitivity Switch: See Modules A, B
		
		//Module G (+E) =======================================================
		//TODO MACH
		
		if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.TOP) {//90 degrees
			//blue
		} else if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.CENTER) {//30 degrees
			//green
			//TODO Pitch Gauge...
		} else if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.BOTTOM) {//9 degrees
			//red
			//TODO Pitch Gauge...
		} else {//INVALID
			//TODO Pitch Gauge...
		}
		
		//TODO HEADING
		
		//Module H ============================================================
		//TODO find Diagnostic Mode and Graceful Shutdown logic elsewhere...
		
		//Module I ============================================================
		if (this.maxSolidFuel > 0) {
			this.percentFuel = (int) (this.currentSolidFuel / this.maxSolidFuel * 100);
		} else if (this.maxLiquidFuel > 0) {
			this.percentFuel = (int) (this.currentLiquidFuel / this.maxLiquidFuel * 100);
		} else {
			this.percentFuel = -1;
		}
		refreshPercentRGBLED(this.moduleI.stepperLED_Fuel, LED_RGB_Brightness.BRIGHT, this.percentFuel);
		
		//int temp = this.scaleIntegerToNewRange(this.percentFuel, 0, 100, OP.Stepper_Fuel.calibrationCCWLimit, OP.Stepper_Fuel.calibrationCWLimit);
		//System.out.println();
		//System.out.println(" Solid Fuel: " + this.currentSolidFuel + " / " + this.maxSolidFuel);
		//System.out.println("Liquid Fuel: " + this.currentLiquidFuel + " / " + this.maxLiquidFuel);
		//System.out.println("percentFuel: " + this.percentFuel);
		//System.out.println("percentFuel (scaled): " + temp);
		//this.moduleI.stepper_Fuel.setDesiredPosition(temp);
		
		if (this.maxElectricCharge > 0) {
			this.percentElectricCharge = (int) (this.currentElectricCharge / this.maxElectricCharge * 100);
		} else {
			this.percentElectricCharge = -1;
		}
		refreshPercentRGBLED(this.moduleI.stepperLED_Charge, LED_RGB_Brightness.BRIGHT, this.percentElectricCharge);
		
		if (this.currentElectricCharge > this.previousElectricCharge) {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.GREEN);
		} else if (this.currentElectricCharge == this.previousElectricCharge) {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.WHITE);
		} else {
			this.moduleI.stepperLED_deltaCharge.setMode(LED_RGB_Mode.RED);
		}
		this.previousElectricCharge = this.currentElectricCharge;
		
		
		
		
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
		
		if (this.moduleI.monopropIntakeSwitch.getStatus()) {//Intake Air selected
			refreshPercentRGBLED(this.moduleI.stepperLED_Monopropellant, LED_RGB_Brightness.DIM, this.percentMonopropellant);
			//TODO percentIntakeAir:BRIGHT
		} else {//Monopropellant selected
			refreshPercentRGBLED(this.moduleI.stepperLED_Monopropellant, LED_RGB_Brightness.BRIGHT, this.percentMonopropellant);
			//TODO percentIntakeAir:DIM
		}
	}
	
	public void refreshPercentRGBLED(LED_PWM_RGB led, LED_RGB_Brightness brightness, int percentage) {
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
	}
	
	public void setAllLEDsOff() {//TODO
		
	}
	
	public void setAllLEDsOn() {//TODO
		
	}
	
	public void activateLEDOverride() {//TODO
		
	}
	
	public void disableLEDOverride() {//TODO
		
	}
	
	private int scaleIntegerToNewRange(int number, int oldRangeMin, int oldRangeMax, int newRangeMin, int newRangeMax) {//TODO remove and use CommonUtilities instead
		return (number - oldRangeMin) * (newRangeMax - newRangeMin) / (oldRangeMax - oldRangeMin) + newRangeMin;
	}
}

































