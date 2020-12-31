package mountaineerman.kcp2.kkim.app.model;

public class LED_PWM_RGB extends Part {

	private LED_PWM redLED;
	private LED_PWM grnLED;
	private LED_PWM bluLED;
	private LED_RGB_Mode mode;
	
	public LED_PWM_RGB(String name, ModuleID moduleID, LED_PWM red, LED_PWM green, LED_PWM blue) {
		super(name, moduleID);
		this.redLED = red; //TODO confirm format
		this.grnLED = green; //TODO confirm format
		this.bluLED = blue; //TODO confirm format
		this.setMode(LED_RGB_Mode.OFF);
	}
	
	public LED_RGB_Mode getMode() {
		return this.mode;
	}

	public int getRedPWMValue() {
		return this.redLED.getPWM();
	}

	public int getGrnPWMValue() {
		return this.grnLED.getPWM();
	}

	public int getBluPWMValue() {
		return this.bluLED.getPWM();
	}

	public void setMode(LED_RGB_Mode mode) {
		this.mode = mode;
		
		switch(this.mode) {
			case WHITE:
				this.redLED.setDutyCycle(100);
				this.grnLED.setDutyCycle(100);
				this.bluLED.setDutyCycle(100);
				break;
			case VIOLET:
				this.redLED.setDutyCycle(50);
				this.grnLED.setDutyCycle(0);
				this.bluLED.setDutyCycle(100);
			case MAGENTA:
				this.redLED.setDutyCycle(100);
				this.grnLED.setDutyCycle(0);
				this.bluLED.setDutyCycle(100);
				break;
			case BLUE:
				this.redLED.setDutyCycle(0);
				this.grnLED.setDutyCycle(0);
				this.bluLED.setDutyCycle(100);
				break;
			case CYAN:
				this.redLED.setDutyCycle(0);
				this.grnLED.setDutyCycle(100);
				this.bluLED.setDutyCycle(100);
				break;	
			case GREEN:
				this.redLED.setDutyCycle(0);
				this.grnLED.setDutyCycle(100);
				this.bluLED.setDutyCycle(0);
				break;
			case YELLOW:
				this.redLED.setDutyCycle(100);
				this.grnLED.setDutyCycle(100);
				this.bluLED.setDutyCycle(0);
				break;
			case ORANGE:
				this.redLED.setDutyCycle(100);
				this.grnLED.setDutyCycle(67);
				this.bluLED.setDutyCycle(0);
				break;
			case RED:
				this.redLED.setDutyCycle(100);
				this.grnLED.setDutyCycle(0);
				this.bluLED.setDutyCycle(0);
				break;
			case OFF:
				this.redLED.setDutyCycle(0);
				this.grnLED.setDutyCycle(0);
				this.bluLED.setDutyCycle(0);
				break;
		}
	}

}
