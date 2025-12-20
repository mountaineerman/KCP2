package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;

 /** NEMA17 Stepper Motor (Heading Gauge) controlled via the Sparkfun EasyDriver.
 *
 * Maximum Rotation Angle = Unlimited.
 * Minimum Step Angle (with 1/8th Microstep Resolution) = 0.225 degrees.
 * Steps per full rotation = 1600.
 *  See:
 *     -Motor: https://www.sparkfun.com/products/9238
 *     -Driver: https://www.sparkfun.com/products/12779
 */
public class NEMA17Stepper extends Part {

	/** A number describing the desired stepper motor position, in steps.
	 * Range: [0-1599] [KKIMProp.kmegaSteppersCCWLimit-KKIMProp.kmegaNEMA17SteppersCWLimit] 
	 * e.g., desiredPosition of 0 means the heading gauge is pointing North.
	 * e.g., desiredPosition of 399 means the heading gauge is pointing East.
	 * e.g., desiredPosition of 799 means the heading gauge is pointing South.
	 * e.g., desiredPosition of 1199 means the heading gauge is pointing West.
	 */
	private int desiredPosition;
	
	public NEMA17Stepper(OP op) {
		super(op.partName, op.moduleID);
		this.desiredPosition = 0;
	}


	/** Sets the desired position of the stepper motor, based on:
	 * 		a) valueInRange, and the limits of the associated range
	 * 		b) The limits of the stepper, KKIMProp.kmegaSteppersCCWLimit and KKIMProp.kmegaNEMA17SteppersCWLimit
	 * 
	 * 	If valueInRange is outside of [rangeMin,rangeMax], it is set to the applicable valid limit.
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
		
		// (STEP 3) Calculate percentage-based location of valueInRange in [rangeMin,rangeMax]
		float percent = valueInRange/rangeMax;

		// (STEP 4) Scale up to range [KKIMProp.kmegaSteppersCCWLimit,KKIMProp.kmegaNEMA17SteppersCWLimit] (0-1599)
		float floatDesiredPosition = percent * ((float) KKIMProp.kmegaNEMA17SteppersCWLimit);
		int integerDesiredPosition = Math.round(floatDesiredPosition);
		this.setDesiredPosition(integerDesiredPosition);
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
		if(position < KKIMProp.kmegaSteppersCCWLimit || position > KKIMProp.kmegaNEMA17SteppersCWLimit) {
			String message = String.format("%s desiredPosition (%d) is outside of allowed range [%d-%d].", this.name, position, KKIMProp.kmegaSteppersCCWLimit, KKIMProp.kmegaNEMA17SteppersCWLimit);
			throw new IllegalArgumentException(message);
		}
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}
}