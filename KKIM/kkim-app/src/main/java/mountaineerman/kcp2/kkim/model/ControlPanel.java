package mountaineerman.kcp2.kkim.model;

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
	public float throttleLever = 0;	 //Range: 0 to 1
	public float joystick_FwdBck = 0;//Range: -1(TBD) to 1(TBD)
	public float joystick_LftRgh = 0;//Range: -1(TBD) to 1(TBD)
	public float joystick_Twist = 0; //Range: -1(TBD) to 1(TBD)
	
	//TODO WRAP IN KSP class:
	public float fuel = 0;//TODO replace
	public float gforce = 0;//TODO replace
	private int milliGforce = 0;//TODO replace
	
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
			throttleLever = 0.0;
		} else {
			throttleLever = ((this.moduleA.analogInput_Throttle.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / 1000.0;//TODO add configuration
		}
		joystick_FwdBck = ((this.moduleB.analogInput_Joystick_FwdBck.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / 1000.0; //TODO add configuration	//TODO: Add middle deadzone
		joystick_LftRgh = ((this.moduleB.analogInput_Joystick_LftRgh.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / 1000.0; //TODO add configuration	//TODO: Add middle deadzone
		joystick_Twist = ((this.moduleB.analogInput_Joystick_Twist.getRescaledValue() * this.moduleF.sensitivitySwitch.getPercentSensitivity()) / 100) / 1000.0;   //TODO add configuration	//TODO: Add middle deadzone
		
		//TODO add remaining parts
	}
	
	@Override
	public String toString() {
		
		return  this.moduleA.toString() + //TODO compress to fit on 1 screen...
				this.moduleB.toString() +
				//this.moduleC.toString() +
				//this.moduleD.toString() +
				//this.moduleE.toString() +
				this.moduleF.toString();// +
				//this.moduleG.toString() +
				//this.moduleH.toString() +
				//this.moduleI.toString() +
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
	
	private int scaleIntegerToNewRange(int number, int oldRangeMin, int oldRangeMax, int newRangeMin, int newRangeMax) {//TODO VERIFY
		return (number - oldRangeMin) * (newRangeMax - newRangeMin) / (oldRangeMax - oldRangeMin) + newRangeMin;
	}
}

































