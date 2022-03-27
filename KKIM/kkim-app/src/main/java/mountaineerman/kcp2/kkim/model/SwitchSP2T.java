package mountaineerman.kcp2.kkim.model;

public class SwitchSP2T extends Part implements InputAggregator {

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

	public void displayInputStatus() {
		System.out.println(this.getModuleID() + ": " + this.getName() + ": " + this.getStatus());
	}
}