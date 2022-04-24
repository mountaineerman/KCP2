package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;

public class SensitivitySwitch extends Part implements LEDAggregator {

	private SwitchSP2T sensorAB = null; //See electrical diagram for details
	private SwitchSP2T sensorCD = null; //See electrical diagram for details
	private int sensitivityPercent = 100;
	public LED_PWM sensitivity100PercentLED = null;
	public LED_PWM sensitivity75PercentLED = null;
	public LED_PWM sensitivity50PercentLED = null;
	public LED_PWM sensitivity25PercentLED = null;

	public SensitivitySwitch() {
		super(IP.SensitivitySwitch.partName, IP.SensitivitySwitch.moduleID);
		this.sensorAB = new SwitchSP2T(IP.SensitivitySwitch_AB);
		this.sensorCD = new SwitchSP2T(IP.SensitivitySwitch_CD);
		this.sensitivity100PercentLED = new LED_PWM(OP.Sensitivity100PercentLED);
		this.sensitivity75PercentLED = new LED_PWM(OP.Sensitivity75PercentLED);
		this.sensitivity50PercentLED = new LED_PWM(OP.Sensitivity50PercentLED);
		this.sensitivity25PercentLED = new LED_PWM(OP.Sensitivity25PercentLED);
	}
	
	public int getPercentSensitivity() {
		return this.sensitivityPercent;
	}
	
	public void setsensorABStatus(boolean status) {
		this.sensorAB.setStatus(status);
	}
	
	public void setsensorCDStatus(boolean status) {
		this.sensorCD.setStatus(status);
	}
	
	public void updatePosition() {
		if(this.sensorAB.getStatus() == true) {
			if(this.sensorCD.getStatus() != true) {
				this.sensitivityPercent = 25;
				this.sensitivity25PercentLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.sensitivity50PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity75PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity100PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
			} else {
				this.sensitivityPercent = 50;
				this.sensitivity25PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity50PercentLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.sensitivity75PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity100PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
			}
		} else {
			if(this.sensorCD.getStatus() != true) {
				this.sensitivityPercent = 75;
				this.sensitivity25PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity50PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity75PercentLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.sensitivity100PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
			} else {
				this.sensitivityPercent = 100;
				this.sensitivity25PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity50PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity75PercentLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.sensitivity100PercentLED.setPWM(KKIMProp.getkmegaMaxPWM());
			}
		}	
	}

	@Override
	public void setAllLEDsOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAllLEDsOn() {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": AB[" + this.sensorAB.getStatus() + "], CD[" + this.sensorCD.getStatus() + "], Percent: " + this.getPercentSensitivity() + "%\n";
	}
}
