package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

/** x27.168 Geared Stepper Motor controlled via VID6606 Driver Chip.
 * Maximum Rotation Angle = 315 degrees.
 * Steps per degree = 12.
 * Steps per full rotation = 3780.
 * See:
 *    -Stepper: https://www.adafruit.com/product/2424
 *    -Driver: https://www.tindie.com/products/propwashsim/vid6606-sti6606-4x-stepper-driver-board-kit/ */
public class StepperMotor extends Part {

	private static int STEPPER_CCW_LIMIT = 0;
	private static int STEPPER_CW_LIMIT = 3779;
	
	/** A number describing the desired stepper motor position, in steps.
	 * Range: [0-3779] [STEPPER_CCW_LIMIT-STEPPER_CW_LIMIT] 
	 * e.g., desiredPosition of 0 is the farthest CCW position possible.
	 * e.g., desiredPosition of 3779 is the farthest CW position possible. */
	private int desiredPosition;
	
	public StepperMotor(OP op) {
		super(op.partName, op.moduleID);
		this.desiredPosition = 0;
	}
	
	public StepperMotor(String name, ModuleID moduleID) {
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
		if(position < STEPPER_CCW_LIMIT || position > STEPPER_CW_LIMIT) {
			String message = String.format("%s desiredPosition (%d) is outside of allowed range [%d-%d].", this.name, position, STEPPER_CCW_LIMIT, STEPPER_CW_LIMIT);
			throw new IllegalArgumentException(message);
		}
	}
}