package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleF implements LEDAggregator {

	public SwitchSP2T trimPrimarySwitch = null;
	public SwitchSP2T sp4t_AB = null;
	public SwitchSP2T sp4t_CD = null;
	
	public ModuleF() {
		
		this.trimPrimarySwitch = new SwitchSP2T(IP.TrimPrimarySwitch);
		this.sp4t_AB = new SwitchSP2T(IP.SP4T_AB);
		this.sp4t_CD = new SwitchSP2T(IP.SP4T_CD);
		
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
				this.sp4t_CD.toString();
	}
}
