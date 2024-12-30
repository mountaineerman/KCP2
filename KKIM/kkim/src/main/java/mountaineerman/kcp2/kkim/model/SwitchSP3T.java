package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;

public class SwitchSP3T extends Part implements LEDAggregator {

	private SwitchSP2T topSensor = null;
	private SwitchSP2T bottomSensor = null;
	private SP3TPosition position = SP3TPosition.INVALID;
	public LED_PWM centerPositionLED = null;
	
	public SwitchSP3T(IP ipSP3T, IP ipTopSensor, IP ipBottomSensor, OP opCenterLED) {
		super(ipSP3T.partName, ipSP3T.moduleID);
		this.topSensor = new SwitchSP2T(ipTopSensor);
		this.bottomSensor = new SwitchSP2T(ipBottomSensor);
		this.centerPositionLED = new LED_PWM(opCenterLED);
	}
	
	public SwitchSP3T(String name, ModuleID moduleID, SwitchSP2T topSensor, SwitchSP2T bottomSensor) {//TODO SCRAP
		super(name, moduleID);
		this.topSensor = topSensor; //TODO confirm format
		this.bottomSensor = bottomSensor; //TODO confirm format
	}

	public SP3TPosition getPosition() {
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
				this.position = SP3TPosition.INVALID;
				this.centerPositionLED.setPWM(KKIMProp.getkmegaMinPWM());
				String message = String.format("%s is in an invalid position. Top and Bottom sensors are both ON.", this.name);
				throw new IllegalArgumentException(message); //TODO Replace with better exception type; Handle exception gracefully...
			} else {
				this.position = SP3TPosition.TOP;
				this.centerPositionLED.setPWM(KKIMProp.getkmegaMinPWM());
			}
		} else {
			if(this.bottomSensor.getStatus() == true) {
				this.position = SP3TPosition.BOTTOM;
				this.centerPositionLED.setPWM(KKIMProp.getkmegaMinPWM());
			} else {
				this.position = SP3TPosition.CENTER;
				this.centerPositionLED.setPWM(KKIMProp.getkmegaMaxPWM());
			}
		}
	}
	
	@Override
	public void setAllLEDsOff() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAllLEDsOn() {
		// TODO Auto-generated method stub
		
	}

	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": Top[" + this.topSensor.getStatus() + "], Bottom[" + this.bottomSensor.getStatus() + "], Position: " + this.getPosition() + "\n";
	}
}