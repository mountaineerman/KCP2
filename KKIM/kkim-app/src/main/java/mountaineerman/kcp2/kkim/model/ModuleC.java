package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.OP;

public class ModuleC implements LEDAggregator, StepperMotorAggregator {

	public StepperMotor stepper_Gforce = null;
	
	public ModuleC() {
		this.stepper_Gforce = new StepperMotor(OP.Stepper_Gforce);
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
		return  this.stepper_Gforce.toString();
	}
}
