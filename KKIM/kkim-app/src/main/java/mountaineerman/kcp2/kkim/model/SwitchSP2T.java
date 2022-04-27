package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class SwitchSP2T extends Part {

	/** true = HIGH/ON, false = LOW/OFF */
	private boolean status;
	
	public SwitchSP2T(IP ip) {
		super(ip.partName, ip.moduleID);
		status = false;
	}
	
	public SwitchSP2T(String name, ModuleID moduleID) {//TODO scrap and cleanup unit tests
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