package mountaineerman.kcp2.kkim.model;

public class ModuleA implements LEDAggregator {

//	public AnalogInput analogInput_Throttle;
	public SwitchMom stagingButton = null;
	public SwitchSP2T brakeButton = null;
	public LED_PWM brakeLED = null;
	
	public ModuleA() {
		
		this.stagingButton = new SwitchMom("Staging Button", ModuleID.A);
		this.brakeButton = new SwitchSP2T("Brake Button", ModuleID.A);
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
