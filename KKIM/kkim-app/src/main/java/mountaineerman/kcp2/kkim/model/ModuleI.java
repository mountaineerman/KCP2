package mountaineerman.kcp2.kkim.model;

public class ModuleI implements LEDAggregator, StepperMotorAggregator {
	
	public LED_PWM stepperLED_Fuel_Green = null;
	
	public ModuleI() {
		stepperLED_Fuel_Green = new LED_PWM("Stepper PWM LED: Fuel: Green", ModuleID.I);
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
