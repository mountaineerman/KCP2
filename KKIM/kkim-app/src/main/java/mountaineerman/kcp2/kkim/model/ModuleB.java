package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleB {
	
	public SwitchMom abortButton = null;
	public SwitchMom timeWarpUpButton = null;
	public SwitchMom timeWarpDownButton = null;
	public SwitchMom joystickButton = null;
	
	public SwitchSP2T trimPitchSwitch = null;
	public SwitchSP2T trimYawSwitch = null;
	public SwitchSP2T trimRollSwitch = null;
	
	public ModuleB() {
		
		this.abortButton = new SwitchMom(IP.AbortButton);
		this.timeWarpUpButton = new SwitchMom(IP.TimeWarpUpButton);
		this.timeWarpDownButton = new SwitchMom(IP.TimeWarpDownButton);
		this.joystickButton = new SwitchMom(IP.JoystickButton);
		
		this.trimPitchSwitch = new SwitchSP2T(IP.TrimPitchSwitch);
		this.trimYawSwitch = new SwitchSP2T(IP.TrimYawSwitch);
		this.trimRollSwitch = new SwitchSP2T(IP.TrimRollSwitch);
		
	}
	
	@Override
	public String toString() {
		return  this.abortButton.toString() +
				this.timeWarpUpButton.toString() +
				this.timeWarpDownButton.toString() +
				this.joystickButton.toString() +
				
				this.trimPitchSwitch.toString() +
				this.trimYawSwitch.toString() +
				this.trimRollSwitch.toString();
	}
}
