package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleI implements LEDAggregator, StepperMotorAggregator {
	
	public SwitchSP2T monopropIntakeSwitch = null;
	public StepperMotor stepper_Fuel = null;
	public StepperMotor stepper_Charge = null;
	public StepperMotor stepper_MonopropellantIntake = null;
	public LED_PWM_RGB stepperLED_Fuel = null;
	public LED_PWM_RGB stepperLED_deltaCharge = null;
	public LED_PWM_RGB stepperLED_Charge = null;
	public LED_PWM_RGB stepperLED_Monopropellant = null;
	public LED_PWM_RGB stepperLED_IntakeAir = null;
	
	public ModuleI() {
		
		this.monopropIntakeSwitch = new SwitchSP2T(IP.MonopropIntakeSwitch);
		
		this.stepper_Fuel = new StepperMotor(OP.Stepper_Fuel);
		this.stepper_Charge = new StepperMotor(OP.Stepper_Charge);
		this.stepper_MonopropellantIntake = new StepperMotor(OP.Stepper_MonopropellantIntake);
		
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
		this.stepperLED_Fuel.setMode(LED_RGB_Mode.OFF);
		this.stepperLED_deltaCharge.setMode(LED_RGB_Mode.OFF);
		this.stepperLED_Charge.setMode(LED_RGB_Mode.OFF);
		this.stepperLED_Monopropellant.setMode(LED_RGB_Mode.OFF);
		this.stepperLED_IntakeAir.setMode(LED_RGB_Mode.OFF);
	}

	@Override
	public void setAllLEDsOn() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return  this.monopropIntakeSwitch.toString() +
				this.stepper_Fuel.toString() +
				this.stepper_Charge.toString() +
				this.stepper_MonopropellantIntake.toString() +
				this.stepperLED_Fuel.toString() +		
				this.stepperLED_deltaCharge.toString() +
				this.stepperLED_Charge.toString() +
				this.stepperLED_Monopropellant.toString() +
				this.stepperLED_IntakeAir.toString();
	}
}
