package mountaineerman.kcp2.kkim.model;

public class LED extends Part {

	private boolean isOn;

	public LED(String name, ModuleID moduleID) {
		super(name, moduleID);
		this.isOn = false;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
}