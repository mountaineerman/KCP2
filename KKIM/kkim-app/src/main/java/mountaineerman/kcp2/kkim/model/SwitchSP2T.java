package mountaineerman.kcp2.kkim.model;

public class SwitchSP2T extends Part implements InputAggregator {

	/** true = HIGH/ON, false = LOW/OFF */
	private boolean status;
	
	public SwitchSP2T(String name, ModuleID moduleID) {
		super(name, moduleID);
		status = false;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": " + this.getStatus() + "\n";
	}
}