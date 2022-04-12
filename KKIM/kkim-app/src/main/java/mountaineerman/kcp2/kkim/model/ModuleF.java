package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleF implements LEDAggregator {

	public SwitchSP2T trimPrimarySwitch = null;
	public SwitchSP2T sp4t_AB = null;
	public SwitchSP2T sp4t_CD = null;
	public AnalogInput analogInput_MultiPot = null;
	public AnalogInput analogInput_Current = null;
//	this.analogInput_Throttle.toString();
	
	public ModuleF() {
		
		this.trimPrimarySwitch = new SwitchSP2T(IP.TrimPrimarySwitch);
		this.sp4t_AB = new SwitchSP2T(IP.SP4T_AB);
		this.sp4t_CD = new SwitchSP2T(IP.SP4T_CD);
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
				this.sp4t_AB.toString() +
				this.sp4t_CD.toString() +
				this.analogInput_MultiPot.toString() +
				this.analogInput_Current.toString();
	}
}
