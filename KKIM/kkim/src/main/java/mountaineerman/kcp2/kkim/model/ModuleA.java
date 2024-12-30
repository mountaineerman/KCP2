package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleA implements LEDAggregator {

	public SwitchMom stagingButton = null;
	public SwitchSP2T brakeButton = null;
	public LED_PWM brakeLED = null;
	public AnalogInput analogInput_Throttle = null;
	
	public ModuleA() {
		this.stagingButton = new SwitchMom(IP.StagingButton);
		this.brakeButton = new SwitchSP2T(IP.BrakeButton);
		this.brakeLED = new LED_PWM(OP.ModuleABrakeLED);
		this.analogInput_Throttle = new AnalogInput(IP.AnalogInput_Throttle);
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
		return  this.stagingButton.toString() +
				this.brakeButton.toString() +
				this.brakeLED.toString() +
				this.analogInput_Throttle.toString();
	}
}
