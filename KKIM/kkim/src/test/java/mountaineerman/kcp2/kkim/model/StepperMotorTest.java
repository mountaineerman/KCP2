package mountaineerman.kcp2.kkim.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StepperMotorTest {

	@Test
	void testSetDesiredPosition() {

		// (GROUP 1: fakeMotor1)  No calibration limits
		StepperMotor fakeMotor1 = new StepperMotor("fakeMotor", ModuleID.GT, 0, 3779);

		//Minimum
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) 0.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 0);

		//Maximum
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) 100.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 3779);

		//valueInRange less than rangeMin
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) -20.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 0);

		//valueInRange greater than rangeMax
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) 135.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 3779);


		// (GROUP 2: fakeMotor2) With calibration limits
		StepperMotor fakeMotor2 = new StepperMotor("fakeMotor", ModuleID.GT, 100, 3000);

		//Minimum
		fakeMotor2.setDesiredPosition(200);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 200);
		fakeMotor2.setDesiredPositionUsingCalibrationLimits((float) 0.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 100);

		//Maximum
		fakeMotor2.setDesiredPosition(200);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 200);
		fakeMotor2.setDesiredPositionUsingCalibrationLimits((float) 100.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 3000);

		//valueInRange less than rangeMin
		fakeMotor2.setDesiredPosition(200);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 200);
		fakeMotor2.setDesiredPositionUsingCalibrationLimits((float) -20.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 100);

		//valueInRange greater than rangeMax
		fakeMotor2.setDesiredPosition(200);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 200);
		fakeMotor2.setDesiredPositionUsingCalibrationLimits((float) 135.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 3000);
		
		//Somewhere in range
		fakeMotor2.setDesiredPosition(200);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 200);
		fakeMotor2.setDesiredPositionUsingCalibrationLimits((float) 52.7932, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor2.getDesiredPosition(), 1631);
	}
}