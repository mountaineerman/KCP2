package mountaineerman.kcp2.kkim.model;

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
	//life support: TBD
	public float gforce = 0;//TODO replace
	private int milliGforce = 0;//TODO replace
	public float mach = 0;
	public float pitch = 0;	 //Units: degrees. Range: -90.0 to +90.0
	public float heading = 0;//Units: degrees. Range: 0.0 to 360.0
	public float fuel = 0;//TODO replace
	public float charge = 0;//TODO replace
	public float monopropellant = 0;//TODO replace
	public float intakeAir = 0;//TODO replace
	public float airDensity = 0;
	public double speed = 0;//Units: meters/second.
	public double verticalSpeed = 0;//Units: meters/second.
	public double altitudeAboveSurface = 0;//Units: meters. Measured from the center of mass of the vessel.
	public double altitudeAboveSeaLevel = 0;//Units: meters. Measured from the center of mass of the vessel.
	
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
	public void refresh() {//TODO re-write...
		
		if (this.moduleA.brakeButton.getStatus() ^ this.moduleD.brakeSwitch.getStatus()) {//XOR
			this.brake = true;
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
		} else {
			this.brake = false;
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
		}
		
		this.moduleE.sp3tSpeedModeSwitch.updatePosition();
		if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.TOP) {//SFC
			//double altitudeAboveSurface
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.CENTER) {//ORB
			//double altitudeAboveSeaLevel
		} else if (this.moduleE.sp3tSpeedModeSwitch.getPosition() == SP3TPosition.BOTTOM) {//TGT
			//double altitudeAboveSeaLevel
		} else {//INVALID
			//999 Gm
		}
		
		this.moduleE.sp3tVehicleModeSwitch.updatePosition();
		
		this.moduleE.sp3tPitchSwitch.updatePosition();
		if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.TOP) {//90 degrees
			//TODO Pitch Gauge...
		} else if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.CENTER) {//30 degrees
			//TODO Pitch Gauge...
		} else if (this.moduleE.sp3tPitchSwitch.getPosition() == SP3TPosition.BOTTOM) {//9 degrees
			//TODO Pitch Gauge...
		} else {//INVALID
			//TODO Pitch Gauge...
		}
		
		this.moduleF.sensitivitySwitch.updatePosition();
		
		if (this.fuel > 0) { //TODO replace
			this.moduleI.stepperLED_Fuel_Green.setPWM(KKIMProp.getkmegaMaxPWM());
		} else {
			this.moduleI.stepperLED_Fuel_Green.setPWM(KKIMProp.getkmegaMinPWM());
		}
		
		this.milliGforce = Math.round(this.gforce * 1000);
		if (this.milliGforce < 0) {
			this.milliGforce = 0;
		} else if (this.milliGforce > 15000) {
			this.milliGforce = 15000;
		}
		int temp = this.scaleIntegerToNewRange(this.milliGforce, 0, 15000, OP.Stepper_Gforce.calibrationCCWLimit, OP.Stepper_Gforce.calibrationCWLimit);
//		System.out.println();
//		System.out.println("G-Force (float): " + this.gforce);
//		System.out.println("Milli G-Force (int): " + this.milliGforce);
//		System.out.println("desiredPosition (scaled): " + temp);
		this.moduleC.stepper_Gforce.setDesiredPosition(temp);
		
		if (this.moduleA.analogInput_Throttle.getRawValue() > 925) {//TODO add configuration
			throttleLever = (float) 0;
		} else {
			throttleLever = ((this.moduleA.analogInput_Throttle.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Throttle.maxRescaleLim;
		}
		joystick_FwdBck = ((this.moduleB.analogInput_Joystick_FwdBck.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_FwdBck.maxRescaleLim;
		joystick_LftRgh = ((this.moduleB.analogInput_Joystick_LftRgh.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) -IP.AnalogInput_Joystick_LftRgh.maxRescaleLim;
		joystick_Twist = ((this.moduleB.analogInput_Joystick_Twist.getCenterDeadzonedValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / (float) IP.AnalogInput_Joystick_Twist.maxRescaleLim;
		
		
		//TODO add remaining parts
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

































