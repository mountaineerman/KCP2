package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StepperMotorNEMA17Test {

	@Test
	void testSetDesiredPosition() {
		
		StepperMotorNEMA17 fakeNEMA17Motor = new StepperMotorNEMA17("fakeNEMA17Motor", ModuleID.G);
		
		// VALID
		fakeNEMA17Motor.setDesiredPosition(0); // Minimum boundary
		Assertions.assertTrue(fakeNEMA17Motor.getDesiredPosition() == 0);

		fakeNEMA17Motor.setDesiredPosition(1599); // Maximum boundary
		Assertions.assertTrue(fakeNEMA17Motor.getDesiredPosition() == 1599);

		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fakeNEMA17Motor.setDesiredPosition(-1); // Too low
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fakeNEMA17Motor.setDesiredPosition(1600); // Too high
		});
	}

}
