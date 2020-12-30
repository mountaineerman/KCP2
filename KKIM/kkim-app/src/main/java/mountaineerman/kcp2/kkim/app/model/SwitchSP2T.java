package mountaineerman.kcp2.kkim.app.model;

public class SwitchSP2T extends Part {

	/** true = HIGH/ON, false = LOW/OFF */
	private Boolean status;
	
	public SwitchSP2T(String name, ModuleID moduleID) {
		super(name, moduleID);
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}