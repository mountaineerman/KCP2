package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.model.LED_PWM;
import mountaineerman.kcp2.kkim.model.ModuleID;

class LED_PWM_Test {

	@Test
	void testSetDutyCycle() {
		
		LED_PWM brakePWMLED = new LED_PWM("brakePWMLED", ModuleID.A);
		
		// VALID
		brakePWMLED.setPWM(KKIMProp.getkmegaMinPWM());
		//System.out.println(brakePWMLED.getPWM());
		brakePWMLED.setPWM(KKIMProp.getkmegaMaxPWM()-1);
		//System.out.println(brakePWMLED.getPWM());
		brakePWMLED.setPWM(KKIMProp.getkmegaMaxPWM());
		//System.out.println(brakePWMLED.getPWM());
		
		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brakePWMLED.setPWM(KKIMProp.getkmegaMinPWM()-1); // Too low
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brakePWMLED.setPWM(KKIMProp.getkmegaMaxPWM()+1); // Too high
		});
	}

}
