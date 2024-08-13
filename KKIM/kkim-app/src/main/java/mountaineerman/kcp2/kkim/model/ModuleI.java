package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleI implements LEDAggregator, StepperMotorAggregator {
	
	public SwitchSP2T monopropIntakeSwitch = null;
	
	//public StepperMotor stepper_Fuel = null;
	public LED_PWM_RGB stepperLED_Fuel = null;
	public LED_PWM_RGB stepperLED_deltaCharge = null;
	public LED_PWM_RGB stepperLED_Charge = null;
	public LED_PWM_RGB stepperLED_Monopropellant = null;
	public LED_PWM_RGB stepperLED_IntakeAir = null;
	
	public ModuleI() {
		
		this.monopropIntakeSwitch = new SwitchSP2T(IP.MonopropIntakeSwitch);
		
		//this.stepper_Fuel = new StepperMotor(OP.Stepper_Fuel);
		this.stepperLED_Fuel = 
				new LED_PWM_RGB(OP.StepperRGBLED_Fuel,
								OP.StepperLED_Fuel_Red,
								OP.StepperLED_Fuel_Green,
								OP.StepperLED_Fuel_Blue);		
		this.stepperLED_deltaCharge =
				new LED_PWM_RGB(OP.RGBLED_deltaCharge,
								OP.DeltaChargeLED_Red,
								OP.DeltaChargeLED_Green,
								OP.DeltaChargeLED_Blue);
		this.stepperLED_Charge =
				new LED_PWM_RGB(OP.StepperRGBLED_Charge,
								OP.StepperLED_Charge_Red,
								OP.StepperLED_Charge_Green,
								OP.StepperLED_Charge_Blue);
		this.stepperLED_Monopropellant =
				new LED_PWM_RGB(OP.StepperRGBLED_Monopropellant,
								OP.StepperLED_Monopropellant_Red,
								OP.StepperLED_Monopropellant_Green,
								OP.StepperLED_Monopropellant_Blue);
		this.stepperLED_IntakeAir =
				new LED_PWM_RGB(OP.StepperRGBLED_IntakeAir,
								OP.StepperLED_IntakeAir_Red,
								OP.StepperLED_IntakeAir_Green,
								OP.StepperLED_IntakeAir_Blue);
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
				
				this.stepperLED_Fuel.toString() +		
				this.stepperLED_deltaCharge.toString() +
				this.stepperLED_Charge.toString() +
				this.stepperLED_Monopropellant.toString() +
				this.stepperLED_IntakeAir.toString();
	}
}
