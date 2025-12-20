package mountaineerman.kcp2.kkim.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.OP;
//import mountaineerman.kcp2.kkim.model.ModuleID; //FIXME (see below)

class LED_PWM_Test {

	@Test
	void testSetDutyCycle() {
		
		//LED_PWM brakePWMLED = new LED_PWM("brakePWMLED", ModuleID.A); //FIXME (see below)
		LED_PWM brakePWMLED = new LED_PWM(OP.ModuleABrakeLED);
		
		// VALID
		brakePWMLED.setPWM(KKIMProp.kmegaLEDMinPWM);
		//System.out.println(brakePWMLED.getPWM());
		//FIXME brakePWMLED.setPWM(KKIMProp.kmegaMaxPWM-1); //FIXME (broke when switched constructor call from using Name + Module ID to OP enum)
		//System.out.println(brakePWMLED.getPWM());
		brakePWMLED.setPWM(KKIMProp.kmegaLEDOnPWM);
		//System.out.println(brakePWMLED.getPWM());
		
		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brakePWMLED.setPWM(KKIMProp.kmegaLEDMinPWM-1); // Too low
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brakePWMLED.setPWM(KKIMProp.kmegaLEDOnPWM+1); // Too high
		});
	}

}
