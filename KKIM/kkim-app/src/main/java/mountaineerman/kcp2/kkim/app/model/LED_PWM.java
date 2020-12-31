package mountaineerman.kcp2.kkim.app.model;

/** Pulse-Width Modulation (dimmable) Light Emitting Diode */
public class LED_PWM extends Part {
	
	private static int DUTY_CYCLE_MIN = 0;
	private static int DUTY_CYCLE_MAX = 100;
	private static int PWM_MIN = 0;
	private static int PWM_MAX = 4095;
	
	/** The Duty Cycle of the LED, in percent. This is used to control its brightness.
	 *  Range: [0-100] [DUTY_CYCLE_MIN-DUTY_CYCLE_MAX]
	 *  e.g, a dutyCycle of 97 corresponds to an LED that is ON 97% of the time. */
	private int dutyCycle;
	
	/** The PWM value sent to the TLC5947 LED Driver Chip.
	 *  This variable is directly proportional to the dutyCycle. 
	 *  Range: [0-4095] [PWM_MIN-PWM_MAX]
	 *  For more information, see:
	 *     -Adafruit_TLC5947.h
	 *     -http://www.adafruit.com/products/1429 */
	private int pwm;
	
	public LED_PWM(String name, ModuleID moduleID) {
		super(name, moduleID);
		this.dutyCycle = 0;
		this.pwm = 0;
	}

	public int getDutyCycle() {
		return dutyCycle;
	}

	public void setDutyCycle(int dutyCycle) {
		validateValueIsInRange(dutyCycle, "Duty Cycle", DUTY_CYCLE_MIN, DUTY_CYCLE_MAX);
		this.dutyCycle = dutyCycle;
		this.setPWM(this.dutyCycle * PWM_MAX / DUTY_CYCLE_MAX);
	}

	public int getPWM() {
		return pwm;
	}

	private void setPWM(int pwm) {
		validateValueIsInRange(pwm, "PWM", PWM_MIN, PWM_MAX);
		this.pwm = pwm;
	}
	
	//TODO Ensure this triggers WARNING flag, not a hard crash
	private void validateValueIsInRange(int value, String valueType, int min, int max) {
		if(value < min || value > max) {
			String message = String.format("%s '%s' value (%d) is outside of allowed range [%d-%d].", this.name, valueType, value, min, max);
			throw new IllegalArgumentException(message);
		}
	}
}