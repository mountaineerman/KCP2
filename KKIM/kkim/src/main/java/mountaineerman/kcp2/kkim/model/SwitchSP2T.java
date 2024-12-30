package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

public class SwitchSP2T extends Part {

	/** true = HIGH/ON, false = LOW/OFF */
	private boolean status;
	private boolean previousStatus;
	
	public SwitchSP2T(IP ip) {
		super(ip.partName, ip.moduleID);
		status = false;
		previousStatus = false;
	}

	public boolean getStatus() {
		return status;
	}

	public boolean statusChanged() {
		if (this.status != this.previousStatus) {
			return true;
		} else {
			return false;
		}
	}

	public void setStatus(boolean status) {
		this.previousStatus = this.status;
		this.status = status;
	}

	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": " + this.getStatus() + "\n";
	}
}