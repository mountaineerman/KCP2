package mountaineerman.kcp2.kkim.model;

public class ModuleD implements LEDAggregator {

	public SwitchSP2T brakeSwitch = null;
	public LED_PWM brakeLED = null;
	
	public ModuleD() {
		brakeSwitch = new SwitchSP2T("Brake Switch", ModuleID.D);
		brakeLED = new LED_PWM("Module D Brake PWM LED", ModuleID.D);
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
