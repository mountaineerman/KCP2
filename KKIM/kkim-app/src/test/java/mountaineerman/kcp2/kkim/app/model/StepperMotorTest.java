package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mountaineerman.kcp2.kkim.model.ModuleID;
import mountaineerman.kcp2.kkim.model.StepperMotor;

class StepperMotorTest {

	@Test
	void testSetDesiredPosition() {

		StepperMotor fakeMotor = new StepperMotor("fakeMotor", ModuleID.GT);

		// VALID
		fakeMotor.setDesiredPosition(0); // Minimum boundary
		Assertions.assertTrue(fakeMotor.getDesiredPosition() == 0);

		fakeMotor.setDesiredPosition(3779); // Maximum boundary
		Assertions.assertTrue(fakeMotor.getDesiredPosition() == 3779);

		fakeMotor.setDesiredPosition(1890); // Midpoint
		Assertions.assertTrue(fakeMotor.getDesiredPosition() == 1890);

		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fakeMotor.setDesiredPosition(-1); // Too low
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fakeMotor.setDesiredPosition(3880); // Too high
		});

	}

}