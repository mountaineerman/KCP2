package mountaineerman.kcp2.kkim.model;

/** Momentarily-ON SP2T Switch (Adds debouncing logic to SP2T Switch) */
public class SwitchMom {

	static private int IGNORE_TIME_IN_MILLISECONDS = 500;
	
	private SwitchSP2T sp2t;
	private Boolean previousSP2TStatus;
	private Boolean debouncedStatus;
	private boolean ignoreTimerIsActive;
	private int ignoreTimer;
	
	public SwitchMom(String name, ModuleID moduleID) {
		super();
		this.sp2t = new SwitchSP2T(name, moduleID);
		this.ignoreTimerIsActive = false;
	}

	public Boolean getDebouncedStatus() {
		return this.debouncedStatus;
	}
	
	public void setSP2TStatus(Boolean status) {
		
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
	
	private void activateIgnoreTimer() {
		//TODO reset timer and begin countdown
	}
}
