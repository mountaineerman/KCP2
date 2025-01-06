package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.OP;

public class ModuleC implements LEDAggregator, StepperMotorAggregator {

	//TODO: EnableSteppers (review entire class)
	public StepperMotor stepper_Gforce = null;                    //NEW
	public LED_PWM_RGB stepperLED_Heat = null;
	public LED_PWM_RGB stepperLED_LifeSupport = null;
	public LED_PWM_RGB stepperLED_GForce = null;
	
	public ModuleC() {
		this.stepper_Gforce = new StepperMotor(OP.Stepper_Gforce); //NEW
		this.stepperLED_Heat = 
				new LED_PWM_RGB(OP.StepperRGBLED_Heat,
								OP.StepperLED_Heat_Red,
								OP.StepperLED_Heat_Green,
								OP.StepperLED_Heat_Blue);
		
		this.stepperLED_LifeSupport = 
				new LED_PWM_RGB(OP.StepperRGBLED_LifeSupport,
								OP.StepperLED_LifeSupport_Red,
								OP.StepperLED_LifeSupport_Green,
								OP.StepperLED_LifeSupport_Blue);
		
		this.stepperLED_GForce = 
				new LED_PWM_RGB(OP.StepperRGBLED_GForce,
								OP.StepperLED_GForce_Red,
								OP.StepperLED_GForce_Green,
								OP.StepperLED_GForce_Blue);
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
		return  this.stepper_Gforce.toString() +          //NEW
				this.stepperLED_Heat.toString() +
				this.stepperLED_LifeSupport.toString() +
				this.stepperLED_GForce.toString();
	}
}
