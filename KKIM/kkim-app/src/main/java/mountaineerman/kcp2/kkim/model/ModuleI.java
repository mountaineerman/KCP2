package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleI implements LEDAggregator, StepperMotorAggregator {
	
	public SwitchSP2T monopropIntakeSwitch = null;
	
	//public StepperMotor stepper_Fuel = null;
	public LED_PWM_RGB stepperLED_Fuel = null;
	
	public ModuleI() {
		
		this.monopropIntakeSwitch = new SwitchSP2T(IP.MonopropIntakeSwitch);
		
		//this.stepper_Fuel = new StepperMotor(OP.Stepper_Fuel);
		this.stepperLED_Fuel = new LED_PWM_RGB(OP.StepperRGBLED_Fuel,
											   OP.StepperLED_Fuel_Red,
											   OP.StepperLED_Fuel_Green,
											   OP.StepperLED_Fuel_Blue);		
		
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
				
				this.stepperLED_Fuel.toString();
	}
}
