package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleG implements LEDAggregator, StepperMotorAggregator {

	public SwitchSP2T heatLifeSwitch = null;
	
	public ModuleG() {
		
		this.heatLifeSwitch = new SwitchSP2T(IP.HeatLifeSwitch);
		
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
		return  this.heatLifeSwitch.toString();
	}
}
