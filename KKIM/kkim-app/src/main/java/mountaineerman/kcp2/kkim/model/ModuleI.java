package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleI implements LEDAggregator, StepperMotorAggregator {
	
	public SwitchSP2T monopropIntakeSwitch = null;
	
	public LED_PWM stepperLED_Fuel_Green = null;
	
	public ModuleI() {
		
		this.monopropIntakeSwitch = new SwitchSP2T(IP.MonopropIntakeSwitch);
		
		stepperLED_Fuel_Green = new LED_PWM(OP.StepperLED_Fuel_Green);
		
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
		return  this.monopropIntakeSwitch.toString() +
				
				this.stepperLED_Fuel_Green.toString();
	}
}
