package mountaineerman.kcp2.kkim.model;

/** Momentarily-ON SP2T Switch (Adds debouncing logic to SP2T Switch) */
public class SwitchMom implements InputAggregator {

	static private int IGNORE_TIME_IN_MILLISECONDS = 500; //TODO move to config
	
	private SwitchSP2T sp2t;
	private boolean previousSP2TStatus = false;
	private boolean debouncedStatus = false;
	private boolean ignoreTimerIsActive = false;
	private int ignoreTimer = 0;
	
	public SwitchMom(String name, ModuleID moduleID) {
		super();
		this.sp2t = new SwitchSP2T(name, moduleID);
		this.ignoreTimerIsActive = false;
	}

	public boolean getDebouncedStatus() {
		return this.debouncedStatus;
	}
	
	public void setSP2TStatus(boolean status) {
		
		this.previousSP2TStatus = this.sp2t.getStatus();
		this.sp2t.setStatus(status);
		
		// Debouncing Logic
		if(this.ignoreTimerIsActive) {
			debouncedStatus = false;
			//TODO updateTimer();
			if(this.ignoreTimer < 0) { //Timer has expired
				this.ignoreTimerIsActive = false;
			}
		} else {
			if( (this.sp2t.getStatus() == true) && (this.previousSP2TStatus == false) ) { //Rising edge
				debouncedStatus = true;
				activateIgnoreTimer();
			} else {
				debouncedStatus = false;
			}
		}
	}
	
	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": RAW:[" + this.sp2t.getStatus() + "] DEBOUNCED: [" + this.getDebouncedStatus() + "], ...\n";
	}
	
	public String getName() {
		return this.sp2t.getName();
	}
	
	public ModuleID getModuleID() {
		return this.sp2t.getModuleID();
	}
	
	private void activateIgnoreTimer() {
		//TODO reset timer and begin countdown
	}	
}
