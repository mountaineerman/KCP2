package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.OP;

public class ModuleGT implements LEDAggregator, StepperMotorAggregator {

	public StepperMotor stepper_AirDensity = null;
	public StepperMotor stepper_Speed = null;
	public StepperMotor stepper_VerticalSpeed = null;
	public StepperMotor stepper_RadarAltitude = null;
	public LED_PWM_RGB stepperLED_AirDensity = null;
	public LED_PWM_RGB stepperLED_Speed = null;
	public LED_PWM_RGB stepperLED_VerticalSpeed = null;
	public LED_PWM_RGB stepperLED_RadarAltitude = null;
	
	public ModuleGT() {

		this.stepper_AirDensity = new StepperMotor(OP.Stepper_AirDensity);
		this.stepper_Speed = new StepperMotor(OP.Stepper_Speed);
		this.stepper_VerticalSpeed = new StepperMotor(OP.Stepper_VerticalSpeed);
		this.stepper_RadarAltitude = new StepperMotor(OP.Stepper_RadarAltitude);

		this.stepperLED_AirDensity = 
				new LED_PWM_RGB(OP.StepperRGBLED_AirDensity,
								OP.StepperLED_AirDensity_Red,
								OP.StepperLED_AirDensity_Green,
								OP.StepperLED_AirDensity_Blue);
		this.stepperLED_Speed = 
				new LED_PWM_RGB(OP.StepperRGBLED_Speed,
								OP.StepperLED_Speed_Red,
								OP.StepperLED_Speed_Green,
								OP.StepperLED_Speed_Blue);
		this.stepperLED_VerticalSpeed = 
				new LED_PWM_RGB(OP.StepperRGBLED_VerticalSpeed,
								OP.StepperLED_VerticalSpeed_Red,
								OP.StepperLED_VerticalSpeed_Green,
								OP.StepperLED_VerticalSpeed_Blue);
		this.stepperLED_RadarAltitude = 
				new LED_PWM_RGB(OP.StepperRGBLED_RadarAltitude,
								OP.StepperLED_RadarAltitude_Red,
								OP.StepperLED_RadarAltitude_Green,
								OP.StepperLED_RadarAltitude_Blue);
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
		return 	this.stepper_AirDensity.toString() +
				this.stepper_Speed.toString() +
				this.stepper_VerticalSpeed.toString() +
				this.stepper_RadarAltitude.toString() +
				this.stepperLED_AirDensity.toString() +
				this.stepperLED_Speed.toString() +
				this.stepperLED_VerticalSpeed.toString() +
				this.stepperLED_RadarAltitude.toString();
	}
}