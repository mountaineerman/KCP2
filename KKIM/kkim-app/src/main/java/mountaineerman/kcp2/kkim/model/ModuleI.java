package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleI implements LEDAggregator, StepperMotorAggregator {
	
	public SwitchSP2T monopropIntakeSwitch = null;
	
	public StepperMotor stepper_Fuel = null;
	
	public LED_PWM stepperLED_Fuel_Red = null;
	public LED_PWM stepperLED_Fuel_Green = null;
	public LED_PWM stepperLED_Fuel_Blue = null;
	
	public ModuleI() {
		
		this.monopropIntakeSwitch = new SwitchSP2T(IP.MonopropIntakeSwitch);
		
		this.stepper_Fuel = new StepperMotor(OP.Stepper_Fuel);
		
		this.stepperLED_Fuel_Red = new LED_PWM(OP.StepperLED_Fuel_Red);
		this.stepperLED_Fuel_Green = new LED_PWM(OP.StepperLED_Fuel_Green);
		this.stepperLED_Fuel_Blue = new LED_PWM(OP.StepperLED_Fuel_Blue);
		
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
				
				this.stepperLED_Fuel_Red.toString() +
				this.stepperLED_Fuel_Green.toString() +
				this.stepperLED_Fuel_Blue.toString();
	}
}
