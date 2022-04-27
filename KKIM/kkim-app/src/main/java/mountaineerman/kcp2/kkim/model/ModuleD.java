package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;

public class ModuleD implements LEDAggregator {

	public SwitchSP2T sasSwitch = null;
	public SwitchSP2T rcsSwitch = null;
	public SwitchSP2T lightsSwitch = null;
	public SwitchSP2T gearSwitch = null;
	public SwitchSP2T brakeSwitch = null;
	public SwitchSP2T mapSwitch = null;
	public SwitchSP2T muteSwitch = null;
	
	public SwitchMom autoHoldButton = null;
	public SwitchMom autoProgradeButton = null;
	public SwitchMom autoRetrogradeButton = null;
	public SwitchMom autoNormalButton = null;
	public SwitchMom autoAntiNormalButton = null;
	public SwitchMom autoRadialInButton = null;
	public SwitchMom autoRadialOutButton = null;
	public SwitchMom autoTargetButton = null;
	public SwitchMom autoAntiTargetButton = null;
	public SwitchMom autoManeuverButton = null;
	
	public LED_PWM brakeLED = null;
	public LED_PWM autoHoldLED = null;
	public LED_PWM autoProgradeLED = null;
	public LED_PWM autoRetrogradeLED = null;
	public LED_PWM autoManeuverLED = null;
	public LED_PWM autoNormalRedLED = null;
	public LED_PWM autoNormalBluLED = null;
	public LED_PWM autoAntiNormalRedLED = null;
	public LED_PWM autoAntiNormalBluLED = null;
	public LED_PWM autoRadialInGrnLED = null;
	public LED_PWM autoRadialInBluLED = null;
	public LED_PWM autoRadialOutGrnLED = null;
	public LED_PWM autoRadialOutBluLED = null;
	public LED_PWM autoTargetRedLED = null;
	public LED_PWM autoTargetBluLED = null;
	public LED_PWM autoAntiTargetRedLED = null;
	public LED_PWM autoAntiTargetBluLED = null;
	
	public ModuleD() {
		
		this.sasSwitch = new SwitchSP2T(IP.SAS_Switch);
		this.rcsSwitch = new SwitchSP2T(IP.RCS_Switch);
		this.lightsSwitch = new SwitchSP2T(IP.LightsSwitch);
		this.gearSwitch = new SwitchSP2T(IP.GearSwitch);
		this.brakeSwitch = new SwitchSP2T(IP.BrakeSwitch);
		this.mapSwitch = new SwitchSP2T(IP.MapSwitch);
		this.muteSwitch = new SwitchSP2T(IP.MuteSwitch);
		
		this.autoHoldButton = new SwitchMom(IP.AutoHoldButton);
		this.autoProgradeButton = new SwitchMom(IP.AutoProgradeButton);
		this.autoRetrogradeButton = new SwitchMom(IP.AutoRetrogradeButton);
		this.autoNormalButton = new SwitchMom(IP.AutoNormalButton);
		this.autoAntiNormalButton = new SwitchMom(IP.AutoAntiNormalButton);
		this.autoRadialInButton = new SwitchMom(IP.AutoRadialInButton);
		this.autoRadialOutButton = new SwitchMom(IP.AutoRadialOutButton);
		this.autoTargetButton = new SwitchMom(IP.AutoTargetButton);
		this.autoAntiTargetButton = new SwitchMom(IP.AutoAntiTargetButton);
		this.autoManeuverButton = new SwitchMom(IP.AutoManeuverButton);
		
		this.brakeLED = new LED_PWM(OP.ModuleDBrakeLED);
		this.autoHoldLED = new LED_PWM(OP.AutoHoldLED);
		this.autoProgradeLED = new LED_PWM(OP.AutoProgradeLED);
		this.autoRetrogradeLED = new LED_PWM(OP.AutoRetrogradeLED);
		this.autoNormalRedLED = new LED_PWM(OP.AutoNormalRedLED);
		this.autoNormalBluLED = new LED_PWM(OP.AutoNormalBluLED);
		this.autoAntiNormalRedLED = new LED_PWM(OP.AutoAntiNormalRedLED);
		this.autoAntiNormalBluLED = new LED_PWM(OP.AutoAntiNormalBluLED);
		this.autoRadialInGrnLED = new LED_PWM(OP.AutoRadialInGrnLED);
		this.autoRadialInBluLED = new LED_PWM(OP.AutoRadialInBluLED);
		this.autoRadialOutGrnLED = new LED_PWM(OP.AutoRadialOutGrnLED);
		this.autoRadialOutBluLED = new LED_PWM(OP.AutoRadialOutBluLED);
		this.autoTargetRedLED = new LED_PWM(OP.AutoTargetRedLED);
		this.autoTargetBluLED = new LED_PWM(OP.AutoTargetBluLED);
		this.autoAntiTargetRedLED = new LED_PWM(OP.AutoAntiTargetRedLED);
		this.autoAntiTargetBluLED = new LED_PWM(OP.AutoAntiTargetBluLED);
		this.autoManeuverLED = new LED_PWM(OP.AutoManeuverLED);
		
		this.autoHoldLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoProgradeLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoRetrogradeLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoNormalRedLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoNormalBluLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoAntiNormalRedLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoAntiNormalBluLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoRadialInGrnLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoRadialInBluLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoRadialOutGrnLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoRadialOutBluLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoTargetRedLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoTargetBluLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoAntiTargetRedLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoAntiTargetBluLED.setPWM(KKIMProp.getkmegaDimPWM());
		this.autoManeuverLED.setPWM(KKIMProp.getkmegaDimPWM());
	}

	@Override
	public void setAllLEDsOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAllLEDsOn() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return  this.sasSwitch.toString() +
				this.rcsSwitch.toString() +
				this.lightsSwitch.toString() +
				this.gearSwitch.toString() +
				this.brakeSwitch.toString() +
				this.mapSwitch.toString() +
				this.muteSwitch.toString() +
				
				this.autoHoldButton.toString() +
				this.autoProgradeButton.toString() +
				this.autoRetrogradeButton.toString() +
				this.autoNormalButton.toString() +
				this.autoAntiNormalButton.toString() +
				this.autoRadialInButton.toString() +
				this.autoRadialOutButton.toString() +
				this.autoTargetButton.toString() +
				this.autoAntiTargetButton.toString() +
				this.autoManeuverButton.toString() +
				
				this.brakeLED.toString() +
				this.autoHoldLED.toString() +
				this.autoProgradeLED.toString() +
				this.autoRetrogradeLED.toString() +
				this.autoNormalRedLED.toString() +
				this.autoNormalBluLED.toString() +
				this.autoAntiNormalRedLED.toString() +
				this.autoAntiNormalBluLED.toString() +
				this.autoRadialInGrnLED.toString() +
				this.autoRadialInBluLED.toString() +
				this.autoRadialOutGrnLED.toString() +
				this.autoRadialOutBluLED.toString() +
				this.autoTargetRedLED.toString() +
				this.autoTargetBluLED.toString() +
				this.autoAntiTargetRedLED.toString() +
				this.autoAntiTargetBluLED.toString() +
				this.autoManeuverLED.toString();
	}
}
