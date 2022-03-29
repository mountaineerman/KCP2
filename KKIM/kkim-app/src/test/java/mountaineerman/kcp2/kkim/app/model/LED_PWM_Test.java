package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mountaineerman.kcp2.kkim.model.LED_PWM;
import mountaineerman.kcp2.kkim.model.ModuleID;

class LED_PWM_Test {

	@Test
	void testSetDutyCycle() {
		
		LED_PWM brakePWMLED = new LED_PWM("brakePWMLED", ModuleID.A);
		
		// VALID
		brakePWMLED.setDutyCycle(0);
		//System.out.println(brakePWMLED.getDutyCycle());
		//System.out.println(brakePWMLED.getPWM());
		brakePWMLED.setDutyCycle(99);
		//System.out.println(brakePWMLED.getDutyCycle());
		//System.out.println(brakePWMLED.getPWM());
		brakePWMLED.setDutyCycle(100);
		//System.out.println(brakePWMLED.getDutyCycle());
		//System.out.println(brakePWMLED.getPWM());
		
		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brakePWMLED.setDutyCycle(-1); // Too low
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brakePWMLED.setDutyCycle(101); // Too high
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			brakePWMLED.setDutyCycle(4095); // Mixed up with PWM
		});
	}

}
