package mountaineerman.kcp2.kkim.model;

public class AltitudeGauge extends Part {
	
	/** The altitude sent to the Control Panel. Always measured from the center of mass. Depending on flight conditions, this can be above ground level, sea level, or sea floor. */
	private float altitude;
	
	public AltitudeGauge(String name, ModuleID moduleID) {
		super(name, moduleID);
		this.altitude = Float.NaN;
	}

	public float getAltitude() {
		return altitude;
	}

	/** Note that the altitude is sent to the control panel as a float */
	public void setAltitude(double altitude) {
		this.altitude = (float)altitude;
	}
}
