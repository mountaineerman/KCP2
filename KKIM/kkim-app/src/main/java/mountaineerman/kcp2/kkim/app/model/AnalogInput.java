package mountaineerman.kcp2.kkim.app.model;

public class AnalogInput extends Part {

	private static int ANALOGREAD_MIN_VALUE = 0;
	private static int ANALOGREAD_MAX_VALUE = 1023;

	/** Range: 0-1023 */
	private int calibratedValue;
	/** Range: 0-1023 */
	private int lowerCalibrationLimit;
	/** Range: 0-1023 */
	private int upperCalibrationLimit;
	
	/**
	 * @param name
	 * @param moduleID
	 * @param lowerCalibrationLimit - Lower physical calibration offset for raw value. Minimum: 0
	 * @param upperCalibrationLimit - Upper physical calibration offset for raw value. Maximum: 1023
	 */
	public AnalogInput(String name, ModuleID moduleID, int lowerCalibrationLimit, int upperCalibrationLimit) {
		super(name, moduleID);
		
		validateAnalogValue(lowerCalibrationLimit, "lowerCalibrationLimit");
		validateAnalogValue(upperCalibrationLimit, "upperCalibrationLimit");
		validateCalibrationLimits(lowerCalibrationLimit, upperCalibrationLimit);
		this.lowerCalibrationLimit = lowerCalibrationLimit;
		this.upperCalibrationLimit = upperCalibrationLimit;
	}
	
	//TODO Ensure this is: A) hard crash for calibration limits, B) triggers WARNING flag for rawValues
	private void validateAnalogValue(int analogValue, String valueType) {
		if(analogValue < ANALOGREAD_MIN_VALUE || analogValue > ANALOGREAD_MAX_VALUE) {
			String message = String.format("%s '%s' (%d) is outside of allowed range [%d-%d].", this.name, valueType, analogValue, ANALOGREAD_MIN_VALUE, ANALOGREAD_MAX_VALUE);
			throw new IllegalArgumentException(message);
		}
	}

	private void validateCalibrationLimits(int lowerCalibrationLimit, int upperCalibrationLimit) {
		if(lowerCalibrationLimit >= upperCalibrationLimit) {
			String message = String.format("The %s lowerCalibrationLimit (%d) is greater than or equal to the upperCalibrationLimit (%d).", this.name, lowerCalibrationLimit, upperCalibrationLimit);
			throw new IllegalArgumentException(message);
		}
	}
	
	public int getCalibratedValue() {
		return calibratedValue;
	}

	public void setCalibratedValue(int rawValue) {
		
		validateAnalogValue(rawValue, "rawValue");
		
		if(rawValue < this.lowerCalibrationLimit) {
			rawValue = this.lowerCalibrationLimit;
		}
		if(rawValue > this.upperCalibrationLimit) {
			rawValue = this.upperCalibrationLimit;
		}
		
		this.calibratedValue = (rawValue - this.lowerCalibrationLimit) * (ANALOGREAD_MAX_VALUE - ANALOGREAD_MIN_VALUE) / (this.upperCalibrationLimit - this.lowerCalibrationLimit);
	}
	
	
}