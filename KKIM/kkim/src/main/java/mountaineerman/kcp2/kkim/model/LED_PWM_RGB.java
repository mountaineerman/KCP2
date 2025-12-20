package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;

public class LED_PWM_RGB extends Part {

	private LED_PWM redLED;
	private LED_PWM grnLED;
	private LED_PWM bluLED;
	private LED_RGB_Mode mode;
	
	public LED_PWM_RGB(OP op_rgb, OP op_red, OP op_green, OP op_blue) {

		super(op_rgb.partName, op_rgb.moduleID);
		
		assert (op_rgb.moduleID == op_red.moduleID);
		assert (op_rgb.moduleID == op_green.moduleID);
		assert (op_rgb.moduleID == op_blue.moduleID);
		
		this.redLED = new LED_PWM(op_red);
		this.grnLED = new LED_PWM(op_green);
		this.bluLED = new LED_PWM(op_blue);
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
				this.redLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMaxPWM);
				break;
			case VIOLET:
				this.redLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMinPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMaxPWM);
				break;
			case BLUE:
				this.redLED.setPWM(KKIMProp.kmegaMinPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMinPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMaxPWM);
				break;
			case CYAN:
				this.redLED.setPWM(KKIMProp.kmegaMinPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMaxPWM);
				break;	
			case GREEN:
				this.redLED.setPWM(KKIMProp.kmegaMinPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case YELLOW:
				this.redLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case ORANGE:
				this.redLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMaxPWM*1/4);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case RED:
				this.redLED.setPWM(KKIMProp.kmegaMaxPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMinPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case DIM_WHITE:
				this.redLED.setPWM(KKIMProp.kmegaDimPWM);
				this.grnLED.setPWM(KKIMProp.kmegaDimPWM);
				this.bluLED.setPWM(KKIMProp.kmegaDimPWM);
				break;
			case DIM_VIOLET:
				this.redLED.setPWM(KKIMProp.kmegaDimPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMinPWM);
				this.bluLED.setPWM(KKIMProp.kmegaDimPWM);
				break;
			case DIM_BLUE:
				this.redLED.setPWM(KKIMProp.kmegaMinPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMinPWM);
				this.bluLED.setPWM(KKIMProp.kmegaDimPWM);
				break;
			case DIM_CYAN:
				this.redLED.setPWM(KKIMProp.kmegaMinPWM);
				this.grnLED.setPWM(KKIMProp.kmegaDimPWM);
				this.bluLED.setPWM(KKIMProp.kmegaDimPWM);
				break;	
			case DIM_GREEN:
				this.redLED.setPWM(KKIMProp.kmegaMinPWM);
				this.grnLED.setPWM(KKIMProp.kmegaDimPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case DIM_YELLOW:
				this.redLED.setPWM(KKIMProp.kmegaDimPWM);
				this.grnLED.setPWM(KKIMProp.kmegaDimPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case DIM_ORANGE:
				this.redLED.setPWM(KKIMProp.kmegaDimPWM);
				this.grnLED.setPWM(KKIMProp.kmegaDimPWM*1/4);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case DIM_RED:
				this.redLED.setPWM(KKIMProp.kmegaDimPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMinPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
			case OFF:
				this.redLED.setPWM(KKIMProp.kmegaMinPWM);
				this.grnLED.setPWM(KKIMProp.kmegaMinPWM);
				this.bluLED.setPWM(KKIMProp.kmegaMinPWM);
				break;
		}
	}
	
	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ":\n" +
			   this.redLED.toString() +
			   this.grnLED.toString() +
			   this.bluLED.toString();
	}

}
