package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.KKIMProp;

/** Pulse-Width Modulation (dimmable) Light Emitting Diode */
public class LED_PWM extends Part {
	
	/** The PWM value sent to the TLC5947 LED Driver Chip. 
	 *  Range: [0-4095] [KKIMProp.getkmegaMinPWM()-KKIMProp.getkmegaMaxPWM()]
	 *  For more information, see:
	 *     -Adafruit_TLC5947.h
	 *     -http://www.adafruit.com/products/1429 */
	private int pwm;
	
	public LED_PWM(String name, ModuleID moduleID) {
		super(name, moduleID);
		this.setPWM(KKIMProp.getkmegaMinPWM());
	}

	public int getPWM() {
		return pwm;
	}

	public void setPWM(int pwm) {
		validateValueIsInRange(pwm, "PWM", KKIMProp.getkmegaMinPWM(), KKIMProp.getkmegaMaxPWM());
		this.pwm = pwm;
	}
	
	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": " + this.getPWM() + "\n"; 
	}
	
	//TODO Ensure this triggers a system fault flag, not a hard crash
	private void validateValueIsInRange(int value, String valueType, int min, int max) {
		if(value < min || value > max) {
			String message = String.format("%s '%s' value (%d) is outside of allowed range [%d-%d].", this.name, valueType, value, min, max);
			throw new IllegalArgumentException(message);
		}
	}
}