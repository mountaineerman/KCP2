package mountaineerman.kcp2.kkim.app.model;

public class AnalogInput extends Part {

	private static int STANDARD_MIN_VALUE = 0;
	private static int STANDARD_MAX_VALUE = 1023;

	/** Range: 0-1023 */
	private int calibratedValue;
	/** Range: 0-1023 */
	private int lowerValueCalibrationLimit;
	/** Range: 0-1023 */
	private int upperValueCalibrationLimit;
	
	/**
	 * @param name
	 * @param lowerValueLimit - Lower physical calibration offset for raw value
	 * @param upperValueLimit - Upper physical calibration offset for raw value
	 */
	public AnalogInput(String name, int lowerValueCalibrationLimit, int upperValueCalibrationLimit) {
		super(name);
		
		validateAnalogValue(lowerValueCalibrationLimit, "lowerValueCalibrationLimit");
		validateAnalogValue(upperValueCalibrationLimit, "upperValueCalibrationLimit");
		this.lowerValueCalibrationLimit = lowerValueCalibrationLimit;
		this.upperValueCalibrationLimit = upperValueCalibrationLimit;
	}
	
	private void validateAnalogValue(int analogValue, String valueType) {
		if( analogValue < STANDARD_MIN_VALUE || analogValue > STANDARD_MAX_VALUE) {
			String message = String.format("%s (%d) is out of allowed standard range (%d-%d).", valueType, analogValue, STANDARD_MIN_VALUE, STANDARD_MAX_VALUE);
			throw new IllegalArgumentException(message);
		}
	}
}