package mountaineerman.kcp2.kkim.app.model;

import org.junit.jupiter.api.Assertions;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StepperMotorTest {

	@Test
	void testSetDesiredPosition() {
		
		StepperMotor fakeMotor = new StepperMotor("fakeMotor", ModuleID.GT);
		
		// VALID
		fakeMotor.setDesiredPosition(0);
		//System.out.println(fakeMotor.getDesiredPosition());
		fakeMotor.setDesiredPosition(3779);
		//System.out.println(fakeMotor.getDesiredPosition());
		fakeMotor.setDesiredPosition(1890);
		//System.out.println(fakeMotor.getDesiredPosition());
		
		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fakeMotor.setDesiredPosition(-1); // Too low
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			fakeMotor.setDesiredPosition(3880); // Too high
		});

	}

}