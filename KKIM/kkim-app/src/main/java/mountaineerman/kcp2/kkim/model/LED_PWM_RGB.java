package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.KKIMProp;

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
				this.redLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMaxPWM());
				break;
			case VIOLET:
				this.redLED.setPWM(KKIMProp.getkmegaMaxPWM()/2);
				this.grnLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMaxPWM());
			case MAGENTA:
				this.redLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMaxPWM());
				break;
			case BLUE:
				this.redLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMaxPWM());
				break;
			case CYAN:
				this.redLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMaxPWM());
				break;	
			case GREEN:
				this.redLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMinPWM());
				break;
			case YELLOW:
				this.redLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMinPWM());
				break;
			case ORANGE:
				this.redLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMaxPWM()*2/3);
				this.bluLED.setPWM(KKIMProp.getkmegaMinPWM());
				break;
			case RED:
				this.redLED.setPWM(KKIMProp.getkmegaMaxPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMinPWM());
				break;
			case OFF:
				this.redLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.grnLED.setPWM(KKIMProp.getkmegaMinPWM());
				this.bluLED.setPWM(KKIMProp.getkmegaMinPWM());
				break;
		}
	}

}
