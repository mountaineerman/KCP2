package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;

public class ModuleH implements LEDAggregator {

	public SwitchMom glassTL_Button = null;
	public SwitchMom glassCL_Button = null;
	public SwitchMom glassBL_Button = null;
	public SwitchMom glassTR_Button = null;
	public SwitchMom glassCR_Button = null;
	public SwitchMom glassBR_Button = null;

	public LED_PWM glassTL_LED = null;
	public LED_PWM glassCL_LED = null;
	public LED_PWM glassBL_LED = null;
	public LED_PWM glassTR_LED = null;
	public LED_PWM glassCR_LED = null;
	public LED_PWM glassBR_LED = null;
	
	public ModuleH() {
		
		this.glassTL_Button = new SwitchMom(IP.GlassTL_Button);
		this.glassCL_Button = new SwitchMom(IP.GlassCL_Button);
		this.glassBL_Button = new SwitchMom(IP.GlassBL_Button);
		this.glassTR_Button = new SwitchMom(IP.GlassTR_Button);
		this.glassCR_Button = new SwitchMom(IP.GlassCR_Button);
		this.glassBR_Button = new SwitchMom(IP.GlassBR_Button);

		this.glassTL_LED = new LED_PWM(OP.GlassCockpitLED_TL);
		this.glassCL_LED = new LED_PWM(OP.GlassCockpitLED_CL);
		this.glassBL_LED = new LED_PWM(OP.GlassCockpitLED_BL);
		this.glassTR_LED = new LED_PWM(OP.GlassCockpitLED_TR);
		this.glassCR_LED = new LED_PWM(OP.GlassCockpitLED_CR);
		this.glassBR_LED = new LED_PWM(OP.GlassCockpitLED_BR);
	}
	
	@Override
	public void setAllLEDsOff() {
		this.glassTL_LED.setPWM(KKIMProp.kmegaLEDMinPWM);
		this.glassCL_LED.setPWM(KKIMProp.kmegaLEDMinPWM);
		this.glassBL_LED.setPWM(KKIMProp.kmegaLEDMinPWM);
		this.glassTR_LED.setPWM(KKIMProp.kmegaLEDMinPWM);
		this.glassCR_LED.setPWM(KKIMProp.kmegaLEDMinPWM);
		this.glassBR_LED.setPWM(KKIMProp.kmegaLEDMinPWM);
	}

	@Override
	public void setAllLEDsOn() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return  this.glassTL_Button.toString() +
				this.glassCL_Button.toString() +
				this.glassBL_Button.toString() +
				this.glassTR_Button.toString() +
				this.glassCR_Button.toString() +
				this.glassBR_Button.toString() +
				this.glassTL_LED.toString() +
				this.glassCL_LED.toString() +
				this.glassBL_LED.toString() +
				this.glassTR_LED.toString() +
				this.glassCR_LED.toString() +
				this.glassBR_LED.toString();
	}
}
