package mountaineerman.kcp2.kkim.app.model;

public class SwitchSP3T extends Part {

	private SwitchSP2T topSensor;
	private SwitchSP2T bottomSensor;
	private SwitchSP3TPosition position;
	
	public SwitchSP3T(String name, ModuleID moduleID, SwitchSP2T topSensor, SwitchSP2T bottomSensor) {
		super(name, moduleID);
		this.topSensor = topSensor; //TODO confirm format
		this.bottomSensor = bottomSensor; //TODO confirm format
	}

	public SwitchSP3TPosition getPosition() {
		return position;
	}

	public void setTopSensorStatus(boolean status) {
		this.topSensor.setStatus(status);
	}
	
	public void setBottomSensorStatus(boolean status) {
		this.bottomSensor.setStatus(status);
	}

	public void updatePosition() {
		if(this.topSensor.getStatus() == true) {
			if(this.bottomSensor.getStatus() == true) {
				this.position = SwitchSP3TPosition.INVALID;
				String message = String.format("%s is in an invalid position. Top and Bottom sensors are both ON.", this.name);
				throw new IllegalArgumentException(message); //TODO Replace with better exception type; Handle exception gracefully...
			} else {
				this.position = SwitchSP3TPosition.TOP;
			}
		} else {
			if(this.bottomSensor.getStatus() == true) {
				this.position = SwitchSP3TPosition.BOTTOM;
			} else {
				this.position = SwitchSP3TPosition.CENTER;
			}
		}	
	}
}
