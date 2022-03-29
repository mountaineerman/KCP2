package mountaineerman.kcp2.kkim.model;

public class SwitchSP4T extends Part {

	private SwitchSP2T sensorAB; //See electrical diagram for details
	private SwitchSP2T sensorCD; //See electrical diagram for details
	private SwitchSP4TPosition position;

	public SwitchSP4T(String name, ModuleID moduleID, SwitchSP2T sensorAB, SwitchSP2T sensorCD) {
		super(name, moduleID);
		this.sensorAB = sensorAB; //TODO confirm format
		this.sensorCD = sensorCD; //TODO confirm format
	}

	public SwitchSP4TPosition getPosition() {
		return position;
	}
	
	public void setsensorABStatus(boolean status) {
		this.sensorAB.setStatus(status);
	}
	
	public void setsensorCDStatus(boolean status) {
		this.sensorCD.setStatus(status);
	}
	
	public void updatePosition() {
		if(this.sensorAB.getStatus() == true) {
			if(this.sensorCD.getStatus() == true) {
				this.position = SwitchSP4TPosition.TWO;
			} else {
				this.position = SwitchSP4TPosition.ONE;
			}
		} else {
			if(this.sensorCD.getStatus() == true) {
				this.position = SwitchSP4TPosition.FOUR;
			} else {
				this.position = SwitchSP4TPosition.THREE;
			}
		}	
	}
}
