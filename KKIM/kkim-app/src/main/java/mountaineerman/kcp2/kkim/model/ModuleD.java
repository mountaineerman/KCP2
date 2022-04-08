package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
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
				
				this.brakeLED.toString();
	}
}
