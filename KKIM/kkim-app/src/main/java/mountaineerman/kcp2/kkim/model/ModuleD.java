package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleD implements LEDAggregator {

	public SwitchSP2T brakeSwitch = null;
	public LED_PWM brakeLED = null;
	
	public ModuleD() {
		
		brakeSwitch = new SwitchSP2T(IP.BrakeSwitch);
		
		brakeLED = new LED_PWM(OP.ModuleDBrakeLED);
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
		return "Module D: TBD";
	}
}
