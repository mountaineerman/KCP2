package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;

/** Momentarily-ON SP2T Switch (Adds debouncing logic to SP2T Switch) */
public class SwitchMom {

	private SwitchSP2T sp2t = null;
	private DebounceTimer debounceTimer = null;
	private boolean previousSP2TStatus = false;
	private boolean debouncedStatus = false;
	
	public SwitchMom(IP ip) {
		this.sp2t = new SwitchSP2T(ip);
		this.debounceTimer = new DebounceTimer();
	}

	public boolean getRawStatus() {
		return this.sp2t.getStatus();
	}
	
	public boolean getDebouncedStatus() {
		return this.debouncedStatus;
	}
	
	public void setSP2TStatus(boolean status) {
		
		this.previousSP2TStatus = this.sp2t.getStatus();
		this.sp2t.setStatus(status);

		// Debouncing Logic
		if (this.debounceTimer.isActive()) {
			this.debouncedStatus = false;
		} else {
			if ((this.sp2t.getStatus() == true) && (this.previousSP2TStatus == false)) { // Rising edge
				debouncedStatus = true;
				this.debounceTimer.activate();
			} else {
				debouncedStatus = false;
			}
		}
	}
	
	@Override
	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": RAW_STATUS:[" + this.sp2t.getStatus() + "] DEBOUNCED_STATUS: [" + this.getDebouncedStatus() + "], DEBOUNCED_TIMER: [" + this.debounceTimer.getElapsedTime() + "]\n";
	}
	
	public String getName() {
		return this.sp2t.getName();
	}
	
	public ModuleID getModuleID() {
		return this.sp2t.getModuleID();
	}	
}
