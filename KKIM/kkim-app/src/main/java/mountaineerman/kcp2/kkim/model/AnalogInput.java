package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.IP;

public class AnalogInput extends Part {

	private static int ANALOGREAD_MIN_VALUE = 0;
	private static int ANALOGREAD_MAX_VALUE = 1023;
	
	
	/** Range: ANALOGREAD_MIN_VALUE(0) - ANALOGREAD_MAX_VALUE(1023) */
	private int rawValue = 0;
	/** Range: lowerCalibrationLimit - upperCalibrationLimit */
	protected int boundValue = 0;
	/** Range: lowerRescaleLimit - upperRescaleLimit */
	protected int rescaledValue = 0;
	/** Range: ANALOGREAD_MIN_VALUE(0) - ANALOGREAD_MAX_VALUE(1023) */
	private int lowerCalibrationLimit;
	/** Range: ANALOGREAD_MIN_VALUE(0) - ANALOGREAD_MAX_VALUE(1023) */
	private int upperCalibrationLimit;
	protected int lowerRescaleLimit = 0;
	protected int upperRescaleLimit = 0;
	
	public AnalogInput(IP ip) {
		super(ip.partName, ip.moduleID);
		
		validateAnalogValue(ip.minCalibLim, "lowerCalibrationLimit");
		validateAnalogValue(ip.maxCalibLim, "upperCalibrationLimit");
		validateCalibrationLimits(ip.minCalibLim, ip.maxCalibLim);
		this.lowerCalibrationLimit = ip.minCalibLim;
		this.upperCalibrationLimit = ip.maxCalibLim;
		this.lowerRescaleLimit = ip.minRescaleLim;
		this.upperRescaleLimit = ip.maxRescaleLim;
	}
	
	/**
	 * @param name
	 * @param moduleID
	 * @param lowerCalibrationLimit - Lower physical calibration offset for raw value. Minimum: 0
	 * @param upperCalibrationLimit - Upper physical calibration offset for raw value. Maximum: 1023
	 */
	public AnalogInput(String name, ModuleID moduleID, int lowerCalibrationLimit, int upperCalibrationLimit) {//TODO SCRAP
		super(name, moduleID);
		
		validateAnalogValue(lowerCalibrationLimit, "lowerCalibrationLimit");
		validateAnalogValue(upperCalibrationLimit, "upperCalibrationLimit");
		validateCalibrationLimits(lowerCalibrationLimit, upperCalibrationLimit);
		this.lowerCalibrationLimit = lowerCalibrationLimit;
		this.upperCalibrationLimit = upperCalibrationLimit;
	}
	
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
	
	public int getRawValue() {
		return this.rawValue;
	}
	
	public int getBoundValue() {
		return this.boundValue;
	}
	
	public int getRescaledValue() {
		return this.rescaledValue;
	}

	public void setRawValueAndCalculateCalibratedValues(int rawValue) {
		
		try {
			validateAnalogValue(rawValue, "rawValue");
		} catch (IllegalArgumentException e) {e.printStackTrace();}
		this.rawValue = rawValue;
		
		if(this.rawValue < this.lowerCalibrationLimit) {
			this.boundValue = this.lowerCalibrationLimit;
		} else if (this.rawValue > this.upperCalibrationLimit) {
			this.boundValue = this.upperCalibrationLimit;
		} else {
			this.boundValue = this.rawValue;
		}
		
		this.rescaledValue = CommonUtilities.rescaleValue(this.boundValue,
														  this.lowerCalibrationLimit, this.upperCalibrationLimit,
														  this.lowerRescaleLimit, this.upperRescaleLimit);
	}
	
	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": RAW[" + this.getRawValue() + "],\tBOUND["+ this.getBoundValue() + "]\tRESCALED[" + this.getRescaledValue() + "]\n";
	}
	
}