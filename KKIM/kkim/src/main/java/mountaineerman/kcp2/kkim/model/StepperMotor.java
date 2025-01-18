package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.OP;
import mountaineerman.kcp2.kkim.CommonUtilities;

/** x27.168 Geared Stepper Motor controlled via VID6606 Driver Chip.
 * Maximum Rotation Angle = 315 degrees.
 * Steps per degree = 12.
 * Steps per full rotation = 3780.
 * See:
 *    -Stepper: https://www.adafruit.com/product/2424
 *    -Driver: https://www.tindie.com/products/propwashsim/vid6606-sti6606-4x-stepper-driver-board-kit/ */
public class StepperMotor extends Part {

	//TODO: Move to configs
	private static int STEPPER_CCW_LIMIT = 0;
	private static int STEPPER_CW_LIMIT = 3779;
	private static int NUMBER_OF_NEEDLE_POSITIONS = 1000;

	private int calibrationCCWLimit;
	private int calibrationCWLimit;

	/** A number describing the desired stepper motor position, in steps.
	 * Range: [0-3779] [STEPPER_CCW_LIMIT-STEPPER_CW_LIMIT] 
	 * e.g., desiredPosition of 0 is the farthest CCW position possible.
	 * e.g., desiredPosition of 3779 is the farthest CW position possible. */
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

	/**
	 * Sets the desired position of the stepper motor, based on:
	 * 		a) valueInRange, and the limits of the associated range
	 * 		b) NUMBER_OF_NEEDLE_POSITIONS
	 * 		c) The calibration limits of the gauge, calibrationCCWLimit and calibrationCWLimit, which are pulled from OP.java during instantiation.
	 * 	If valueInRange is outside of [rangeMin,rangeMax], it is set to the applicable valid limit.
	 * 
	 * For example, for G-Force, a possible valid example is:
	 * 		valueInRange = -20 (-2.0 G's)
	 * 		rangeMin = 0        (0.0 G's)
	 *		rangeMax = 150     (15.0 G's)
	 */
	public void setDesiredPosition(float valueInRange, float rangeMin, float rangeMax) {

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

		// (STEP 4) Scale up to range [0,NUMBER_OF_NEEDLE_POSITIONS], lowering the "resolution" of the measurement:
		float floatValueInNeedlePositions = percent * ((float) NUMBER_OF_NEEDLE_POSITIONS);
		int integerValueInNeedlePositions = Math.round(floatValueInNeedlePositions);
		// System.out.println("floatValueInNeedlePositions: " + floatValueInNeedlePositions);
		// System.out.println("integerValueInNeedlePositions: " + integerValueInNeedlePositions);

		// (STEP 5) Scale up from [0,NUMBER_OF_NEEDLE_POSITIONS] range to [this.calibrationCCWLimit,this.calibrationCWLimit]:
		int requestedPosition = CommonUtilities.rescaleValue(
			integerValueInNeedlePositions,
			0, NUMBER_OF_NEEDLE_POSITIONS,
			this.calibrationCCWLimit, this.calibrationCWLimit);
		// System.out.println("requestedPosition: " + requestedPosition);
		// System.out.println();
		this.setDesiredPosition(requestedPosition);
	}

	/**
	 * This method should only be used directly for unit testing
	 */
	public void setDesiredPosition(int requestedPosition) {
		validatePosition(requestedPosition);
		this.desiredPosition = requestedPosition;
	}
	
	//TODO Ensure this triggers WARNING flag, not a hard crash
	private void validatePosition(int position) {
		if(position < STEPPER_CCW_LIMIT || position > STEPPER_CW_LIMIT) {
			String message = String.format("%s desiredPosition (%d) is outside of allowed range [%d-%d].", this.name, position, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT);
			throw new IllegalArgumentException(message);
		}
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}
}