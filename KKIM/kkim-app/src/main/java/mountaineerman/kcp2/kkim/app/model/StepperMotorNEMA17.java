package mountaineerman.kcp2.kkim.app.model;

/** NEMA17 Stepper Motor controlled via the Sparkfun EasyDriver.
 * Maximum Rotation Angle = Unlimited.
 * Minimum Step Angle (with 1/8th Microstep Resolution) = 0.225 degrees.
 * Steps per full rotation = 1600.
 *  See:
 *     -Motor: https://www.sparkfun.com/products/9238
 *     -Driver: https://www.sparkfun.com/products/12779 */
public class StepperMotorNEMA17 extends Part {

	private static int STEPPER_CW_LIMIT = 0;
	private static int STEPPER_CCW_LIMIT = 1599;
	
	/** A number describing the desired stepper motor position, in steps.
	 * Range: [0-1599] [STEPPER_CW_LIMIT-STEPPER_CCW_LIMIT] 
	 * e.g., desiredPosition of 0 corresponds to North.
	 * e.g., desiredPosition of 1 corresponds to one step East of North.
	 * e.g., desiredPosition of 1599 corresponds to one step West of North. */
	private int desiredPosition;
	
	public StepperMotorNEMA17(String name, ModuleID moduleID) {
		super(name, moduleID);
		this.desiredPosition = 0;
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}
	
	public void setDesiredPosition(int desiredPosition) {
		validatePosition(desiredPosition);
		this.desiredPosition = desiredPosition;
	}
	
	//TODO Ensure this triggers WARNING flag, not a hard crash
	private void validatePosition(int position) {
		if(position < STEPPER_CW_LIMIT || position > STEPPER_CCW_LIMIT) {
			String message = String.format("%s desiredPosition (%d) is outside of allowed range [%d-%d].", this.name, position, STEPPER_CW_LIMIT, STEPPER_CCW_LIMIT);
			throw new IllegalArgumentException(message);
		}
	}
}
