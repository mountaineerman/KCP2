package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PWM_LED_Test {

	@Test
	void testSetDutyCycle() {
		
		PWM_LED brakePWMLED = new PWM_LED("brakePWMLED", ModuleID.A);
		
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
