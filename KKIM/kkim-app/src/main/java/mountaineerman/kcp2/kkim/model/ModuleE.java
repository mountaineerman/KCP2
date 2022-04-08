package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleE implements LEDAggregator{

	public SwitchSP2T sp3t_SFC_Switch = null;
	public SwitchSP2T sp3t_TGT_Switch = null;
	public SwitchSP2T sp3t_RKT_Switch = null;
	public SwitchSP2T sp3t_RVR_Switch = null;
	public SwitchSP2T sp3t_90degSwitch = null;
	public SwitchSP2T sp3t_9degSwitch = null;
	
	public SwitchMom ag1Switch = null;
	public SwitchMom ag2Switch = null;
	public SwitchMom ag3Switch = null;
	public SwitchMom scienceSwitch = null;
	public SwitchMom resetSwitch = null;
	public SwitchMom solarSwitch = null;
	public SwitchMom ladderSwitch = null;
	public SwitchMom atnvSwitch = null;
	public SwitchMom fairingButton = null;
	public SwitchMom chuteButton = null;
	
	public ModuleE() {
		
		this.sp3t_SFC_Switch = new SwitchSP2T(IP.SP3T_SFC_Switch);
		this.sp3t_TGT_Switch = new SwitchSP2T(IP.SP3T_TGT_Switch);
		this.sp3t_RKT_Switch = new SwitchSP2T(IP.SP3T_RKT_Switch);
		this.sp3t_RVR_Switch = new SwitchSP2T(IP.SP3T_RVR_Switch);
		this.sp3t_90degSwitch = new SwitchSP2T(IP.SP3T_90degSwitch);
		this.sp3t_9degSwitch = new SwitchSP2T(IP.SP3T_9degSwitch);
		
		this.ag1Switch = new SwitchMom(IP.Ag1Switch);
		this.ag2Switch = new SwitchMom(IP.Ag2Switch);
		this.ag3Switch = new SwitchMom(IP.Ag3Switch);
		this.scienceSwitch = new SwitchMom(IP.ScienceSwitch);
		this.resetSwitch = new SwitchMom(IP.ResetSwitch);
		this.solarSwitch = new SwitchMom(IP.SolarSwitch);
		this.ladderSwitch = new SwitchMom(IP.LadderSwitch);
		this.atnvSwitch = new SwitchMom(IP.ATNV_Switch);
		this.fairingButton = new SwitchMom(IP.FairingButton);
		this.chuteButton = new SwitchMom(IP.ChuteButton);
		
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
		return  this.ag1Switch.toString() +
				this.ag2Switch.toString() +
				this.ag3Switch.toString() +
				this.scienceSwitch.toString() +
				this.resetSwitch.toString() +
				this.solarSwitch.toString() +
				this.ladderSwitch.toString() +
				this.atnvSwitch.toString() +
				this.sp3t_SFC_Switch.toString() +
				this.sp3t_TGT_Switch.toString() +
				this.sp3t_RKT_Switch.toString() +
				this.sp3t_RVR_Switch.toString() +
				this.sp3t_90degSwitch.toString() +
				this.sp3t_9degSwitch.toString() +
				
				this.fairingButton.toString() +
				this.chuteButton.toString();
	}
}
