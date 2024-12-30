package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class ModuleH implements LEDAggregator {

	public SwitchMom glassTL_Button = null;
	public SwitchMom glassCL_Button = null;
	public SwitchMom glassBL_Button = null;
	public SwitchMom glassTR_Button = null;
	public SwitchMom glassCR_Button = null;
	public SwitchMom glassBR_Button = null;
	
	public ModuleH() {
		
		this.glassTL_Button = new SwitchMom(IP.GlassTL_Button);
		this.glassCL_Button = new SwitchMom(IP.GlassCL_Button);
		this.glassBL_Button = new SwitchMom(IP.GlassBL_Button);
		this.glassTR_Button = new SwitchMom(IP.GlassTR_Button);
		this.glassCR_Button = new SwitchMom(IP.GlassCR_Button);
		this.glassBR_Button = new SwitchMom(IP.GlassBR_Button);
		
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
		return  this.glassTL_Button.toString() +
				this.glassCL_Button.toString() +
				this.glassBL_Button.toString() +
				this.glassTR_Button.toString() +
				this.glassCR_Button.toString() +
				this.glassBR_Button.toString();
	}
}
