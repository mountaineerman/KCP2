package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleA implements LEDAggregator {

//	public AnalogInput analogInput_Throttle;
	public SwitchMom stagingButton = null;
	public SwitchSP2T brakeButton = null;
	public LED_PWM brakeLED = null;
	
	public ModuleA() {
		
		this.stagingButton = new SwitchMom(IP.StagingButton);
		this.brakeButton = new SwitchSP2T(IP.BrakeButton);
				
		this.brakeLED = new LED_PWM("Module A Brake PWM LED", ModuleID.A);
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
		return "Module A: TBD";
	}
}
