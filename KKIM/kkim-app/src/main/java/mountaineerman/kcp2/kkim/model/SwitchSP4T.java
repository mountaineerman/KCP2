package mountaineerman.kcp2.kkim.model;

public class SensitivitySwitch extends Part {//FIXME

	private SwitchSP2T sensorAB; //See electrical diagram for details
	private SwitchSP2T sensorCD; //See electrical diagram for details
	private int senstivityPercent = 100;

	public SensitivitySwitch(String name, ModuleID moduleID, SwitchSP2T sensorAB, SwitchSP2T sensorCD) {
		super(name, moduleID);
		this.sensorAB = sensorAB; //TODO confirm format
		this.sensorCD = sensorCD; //TODO confirm format
	}

	public int getPercentSensitivity() {
		return this.senstivityPercent;
	}
	
	public void setsensorABStatus(boolean status) {
		this.sensorAB.setStatus(status);
	}
	
	public void setsensorCDStatus(boolean status) {
		this.sensorCD.setStatus(status);
	}
	
	public void updatePosition() {
		if(this.sensorAB.getStatus() == true) {
			if(this.sensorCD.getStatus() != true) {
				this.sensitivityPercent = 25;//TODO move to config file
			} else {
				this.sensitivityPercent = 50;//TODO move to config file
			}
		} else {
			if(this.sensorCD.getStatus() == true) {
				this.sensitivityPercent = 75;//TODO move to config file
			} else {
				this.sensitivityPercent = 100;//TODO move to config file
			}
		}	
	}
}
