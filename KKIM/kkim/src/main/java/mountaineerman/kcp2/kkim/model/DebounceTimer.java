package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.KKIMProp;

public class DebounceTimer {

    private boolean isActive = false;
    private long startTime = -1;
    private long elapsedTime = -1;

    public DebounceTimer() {
        super();
        this.deactivate();
    }

    /**
     * Returns true if the timer has been activated and has not yet expired.
     * Resets the timer if it has expired.
     */
    public boolean isActive() {
        if (!this.isActive) {
            return false;
        } else {
            this.elapsedTime = System.currentTimeMillis() - this.startTime;
            if (this.elapsedTime > KKIMProp.kkimSwitchMomDebounceTimeInMilliseconds) {
                this.deactivate();
                return false;
            } else {
                return true;
            }
        }
    }

	public void activate() {
        this.isActive = true;
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = 0;
	}

    private void deactivate() {
        this.isActive = false;
        this.startTime = -1;
        this.elapsedTime = -1;
	}

    public long getElapsedTime() {
        return this.elapsedTime;
    }
}