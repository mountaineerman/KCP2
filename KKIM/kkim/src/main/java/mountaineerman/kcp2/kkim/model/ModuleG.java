package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

public class ModuleG implements LEDAggregator, StepperMotorAggregator {

	public SwitchSP2T heatLifeSwitch = null;
	public StepperMotor stepper_Mach = null;
	public StepperMotor stepper_Pitch = null;
	public NEMA17Stepper stepper_Heading = null;
	public LED_PWM_RGB stepperLED_Mach = null;
	public LED_PWM_RGB stepperLED_Pitch = null;
	public LED_PWM_RGB stepperLED_Heading = null;
	
	public ModuleG() {
		
		this.heatLifeSwitch = new SwitchSP2T(IP.HeatLifeSwitch);
		
		this.stepper_Mach = new StepperMotor(OP.Stepper_Mach);
		this.stepper_Pitch = new StepperMotor(OP.Stepper_Pitch);
		this.stepper_Heading = new NEMA17Stepper(OP.Stepper_Heading);

		this.stepperLED_Mach = 
				new LED_PWM_RGB(OP.StepperRGBLED_Mach,
								OP.StepperLED_Mach_Red,
								OP.StepperLED_Mach_Green,
								OP.StepperLED_Mach_Blue);
		this.stepperLED_Pitch = 
				new LED_PWM_RGB(OP.StepperRGBLED_Pitch,
								OP.StepperLED_Pitch_Red,
								OP.StepperLED_Pitch_Green,
								OP.StepperLED_Pitch_Blue);
		this.stepperLED_Heading = 
				new LED_PWM_RGB(OP.StepperRGBLED_Heading,
								OP.StepperLED_Heading_Red,
								OP.StepperLED_Heading_Green,
								OP.StepperLED_Heading_Blue);
	}
	
	@Override
	public void setAllLEDsOff() {
		this.stepperLED_Mach.setMode(LED_RGB_Mode.OFF);
		this.stepperLED_Pitch.setMode(LED_RGB_Mode.OFF);
		this.stepperLED_Heading.setMode(LED_RGB_Mode.OFF);
	}

	@Override
	public void setAllLEDsOn() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return  this.heatLifeSwitch.toString() +
				this.stepper_Mach.toString() +
				this.stepper_Pitch.toString() +
				this.stepper_Heading.toString() +
				this.stepperLED_Mach.toString() +
				this.stepperLED_Pitch.toString() +
				this.stepperLED_Heading.toString();
	}
}
