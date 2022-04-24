package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleE implements LEDAggregator{

	public SwitchSP3T sp3tSpeedModeSwitch = null;
	public SwitchSP3T sp3tVehicleModeSwitch = null;
	public SwitchSP3T sp3tPitchSwitch = null;
	
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
	
	public LED_PWM fairingLED = null;
	public LED_PWM parachuteLED = null;
	
	public ModuleE() {
		
		this.sp3tSpeedModeSwitch = new SwitchSP3T(IP.SP3T_SpeedMode_Switch, IP.SP3T_SpeedMode_SFC_Switch, IP.SP3T_SpeedMode_TGT_Switch, OP.SP3T_SpeedMode_ORB_LED);
		this.sp3tVehicleModeSwitch = new SwitchSP3T(IP.SP3T_VehicleMode_Switch, IP.SP3T_VehicleMode_RKT_Switch, IP.SP3T_VehicleMode_RVR_Switch, OP.SP3T_VehicleMode_PLN_LED);
		this.sp3tPitchSwitch = new SwitchSP3T(IP.SP3T_Pitch_Switch, IP.SP3T_Pitch_90degSwitch, IP.SP3T_Pitch_9degSwitch, OP.SP3T_Pitch_30degLED);
		
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
		
		this.fairingLED = new LED_PWM(OP.FairingLED);
		this.parachuteLED = new LED_PWM(OP.ParachuteLED);
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
				this.sp3tSpeedModeSwitch.toString() +
				this.sp3tVehicleModeSwitch.toString() +
				this.sp3tPitchSwitch.toString() +
				this.fairingButton.toString() +
				this.chuteButton.toString();
	}
}
