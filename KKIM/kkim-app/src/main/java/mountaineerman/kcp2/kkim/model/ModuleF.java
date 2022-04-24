package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleF implements LEDAggregator {

	public SwitchSP2T trimPrimarySwitch = null;
	public SensitivitySwitch sensitivitySwitch = null;
	public AnalogInput analogInput_MultiPot = null;
	public AnalogInput analogInput_Current = null;
	
	public ModuleF() {
		this.trimPrimarySwitch = new SwitchSP2T(IP.TrimPrimarySwitch);
		this.sensitivitySwitch = new SensitivitySwitch();
		this.analogInput_MultiPot = new AnalogInput(IP.AnalogInput_MultiPot);
		this.analogInput_Current = new AnalogInput(IP.AnalogInput_Current);
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
		return  this.trimPrimarySwitch.toString() +
				this.sensitivitySwitch.toString() +
				this.analogInput_MultiPot.toString() +
				this.analogInput_Current.toString();
	}
}
