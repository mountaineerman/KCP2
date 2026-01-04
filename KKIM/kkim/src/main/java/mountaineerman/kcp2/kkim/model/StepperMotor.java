package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.OP;
import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.KKIMProp;

/** x27.168 Geared Stepper Motor controlled via VID6606 Driver Chip.
 * Maximum Rotation Angle = 315 degrees.
 * Steps per degree = 12.
 * Steps per full rotation = 3780.
 * See:
 *    -Stepper: https://www.adafruit.com/product/2424
 *    -Driver: https://www.tindie.com/products/propwashsim/vid6606-sti6606-4x-stepper-driver-board-kit/ */
public class StepperMotor extends Part {

	private int calibrationCCWLimit;
	private int calibrationCWLimit;

	/** A number describing the desired stepper motor position, in steps.
	 * Range: [0-3779] (KKIMProp.kmegaSteppersCCWLimit - KKIMProp.kmegaSteppersCWLimit) */
	private int desiredPosition;
	
	public StepperMotor(OP op) {
		super(op.partName, op.moduleID);
		this.calibrationCCWLimit = op.calibrationCCWLimit;
		this.calibrationCWLimit = op.calibrationCWLimit;
		this.desiredPosition = 0;
	}

	/**
	 * This constructor should only be used for unit testing
	 */
	public StepperMotor(String name, ModuleID moduleID, int calibrationCCWLimit, int calibrationCWLimit) {
		super(name, moduleID);
		this.calibrationCCWLimit = calibrationCCWLimit;
		this.calibrationCWLimit = calibrationCWLimit;
		this.desiredPosition = 0;
	}

	// /* //Decide if necessary, or if only float version is useful.
	//  * Sets the desired position of the stepper motor, based on:
	//  * 		a) valueInRange, and the limits of the associated range
	//  * 		b) The calibration limits of the gauge, calibrationCCWLimit and calibrationCWLimit, which are pulled from OP.java during instantiation.
	//  * 	If valueInRange is outside of [rangeMin,rangeMax], it is set to the applicable valid limit.
	//  * 
	//  * For example, for G-Force, a possible valid example is:
	//  * 		valueInRange = -20 (-2.0 G's)
	//  * 		rangeMin = 0        (0.0 G's)
	//  *		rangeMax = 150     (15.0 G's)
	//  */
	// public void setDesiredPosition(int valueInRange, int rangeMin, int rangeMax) {

	// 	int requestedPosition = CommonUtilities.rescaleValue(valueInRange, rangeMin, rangeMax, this.calibrationCCWLimit, this.calibrationCWLimit);
	// 	validatePosition(requestedPosition);
	// 	this.desiredPosition = requestedPosition;
	// }

	public void setDesiredPosition(int requestedPosition) {
		validatePosition(requestedPosition);
		this.desiredPosition = requestedPosition;
	}

	/**
	 * Sets the desired position of the stepper motor, based on:
	 * 		a) valueInRange, and the limits of the associated range: [rangeMin,rangeMax]
	 * 		b) KKIMProp.kmegaSteppersNumberOfNeedlePositions
	 * 		c) The calibration limits of the gauge, calibrationCCWLimit and calibrationCWLimit, which are pulled from OP.java during instantiation.
	 * 	If valueInRange is outside of [rangeMin,rangeMax], it is set to the applicable valid limit.
	 * 
	 * For example, for G-Force, a possible valid example is:
	 * 		valueInRange = -20 (-2.0 G's)
	 * 		rangeMin = 0        (0.0 G's)
	 *		rangeMax = 150     (15.0 G's)
	 */
	public void setDesiredPositionUsingCalibrationLimits(float valueInRange, float rangeMin, float rangeMax) {

		if (this.calibrationCCWLimit < 0) {
			throw new RuntimeException("calibrationCCWLimit (" + this.calibrationCCWLimit + ") has not been defined for " + this.getName());
		}

		if (this.calibrationCWLimit < 0) {
			throw new RuntimeException("calibrationCWLimit (" + this.calibrationCWLimit + ") has not been defined for " + this.getName());
		}

		setDesiredPositionUsingCustomLimits(valueInRange, rangeMin, rangeMax, this.calibrationCCWLimit, this.calibrationCWLimit);
	}

	/**
	 * Sets the desired position of the stepper motor, based on:
	 * 		a) valueInRange, and the limits of the associated range: [rangeMin,rangeMax]
	 * 		b) rangeMinPosition: the stepper motor position associated with rangeMin
	 * 		c) rangeMaxPosition: the stepper motor position associated with rangeMax
	 * 
	 * If valueInRange is outside of [rangeMin,rangeMax], it is set to the applicable valid limit.
	 */
	public void setDesiredPositionUsingCustomLimits(float valueInRange, float rangeMin, float rangeMax, int rangeMinPosition, int rangeMaxPosition) {

		// (STEP 1) Force valueInRange into range [rangeMin,rangeMax]
		if (valueInRange < rangeMin) {
			valueInRange = rangeMin;
		} else if (valueInRange > rangeMax) {
			valueInRange = rangeMax;
		}

		// (STEP 2) Shift values so that range starts at zero
		if (rangeMin < (float) 0 || rangeMin > (float) 0) {
			valueInRange = valueInRange - rangeMin;
			rangeMax = rangeMax - rangeMin;
			rangeMin = rangeMin - rangeMin;
		}
		// System.out.println("rangeMin: " + rangeMin);
		// System.out.println("valueInRange: " + valueInRange);
		// System.out.println("rangeMax: " + rangeMax);
		
		// (STEP 3) Calculate percentage-based location of valueInRange in [rangeMin,rangeMax]
		float percent = valueInRange/rangeMax;
		// System.out.println("percent: " + percent);

		// (STEP 4) Scale up to range [0,KKIMProp.kmegaSteppersNumberOfNeedlePositions], lowering the "resolution" of the measurement:
		float floatValueInNeedlePositions = percent * ((float) KKIMProp.kmegaSteppersNumberOfNeedlePositions);
		int integerValueInNeedlePositions = Math.round(floatValueInNeedlePositions);
		// System.out.println("floatValueInNeedlePositions: " + floatValueInNeedlePositions);
		// System.out.println("integerValueInNeedlePositions: " + integerValueInNeedlePositions);

		// (STEP 5) Scale up from [KKIMProp.kmegaSteppersCCWLimit,KKIMProp.kmegaSteppersNumberOfNeedlePositions] range to [rangeMinPosition,rangeMaxPosition]:
		int requestedPosition = CommonUtilities.rescaleValue(
			integerValueInNeedlePositions,
			KKIMProp.kmegaSteppersCCWLimit, KKIMProp.kmegaSteppersNumberOfNeedlePositions,
			rangeMinPosition, rangeMaxPosition);
		// System.out.println("requestedPosition: " + requestedPosition);
		// System.out.println();
		this.setDesiredPosition(requestedPosition);
    }
	
	//TODO Ensure this triggers WARNING flag, not a hard crash
	private void validatePosition(int position) {
		if(position < KKIMProp.kmegaSteppersCCWLimit || position > KKIMProp.kmegaSteppersCWLimit) {
			String message = String.format("%s desiredPosition (%d) is outside of allowed range [%d-%d].", this.name, position, KKIMProp.kmegaSteppersCCWLimit, KKIMProp.kmegaSteppersCWLimit);
			throw new IllegalArgumentException(message);
		}
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}

	public int getCalibrationCCWLimit() {
		return this.calibrationCCWLimit;
	}
	
	public int getCalibrationCWLimit() {
		return this.calibrationCWLimit;
	}
}