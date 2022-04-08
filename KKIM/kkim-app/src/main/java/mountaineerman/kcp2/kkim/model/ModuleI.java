package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.OP;

public class ModuleI implements LEDAggregator, StepperMotorAggregator {
	
	public LED_PWM stepperLED_Fuel_Green = null;
	
	public ModuleI() {
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
		return "Module I: TBD";
	}
}
