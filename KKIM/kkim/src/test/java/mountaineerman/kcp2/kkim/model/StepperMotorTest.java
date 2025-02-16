package mountaineerman.kcp2.kkim.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mountaineerman.kcp2.kkim.KKIMProp;

class StepperMotorTest {

	@Test
	void testSetDesiredPosition() {

		KKIMProp.initializeProperties();

		// (GROUP 1: fakeMotor1)  No calibration limits
		StepperMotor fakeMotor1 = new StepperMotor("fakeMotor", ModuleID.GT, KKIMProp.getkmegaSteppersCCWLimit(), KKIMProp.getkmegaGearedStepperCWLimit());

		//Minimum
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) 0.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), KKIMProp.getkmegaSteppersCCWLimit());

		//Maximum
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) 100.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), KKIMProp.getkmegaGearedStepperCWLimit());

		//valueInRange less than rangeMin
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) -20.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), KKIMProp.getkmegaSteppersCCWLimit());

		//valueInRange greater than rangeMax
		fakeMotor1.setDesiredPosition(5);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), 5);
		fakeMotor1.setDesiredPositionUsingCalibrationLimits((float) 135.0, (float) 0.0, (float) 100.0);
		Assertions.assertEquals(fakeMotor1.getDesiredPosition(), KKIMProp.getkmegaGearedStepperCWLimit());


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

    @Test
    void testSetDesiredPositionUsingCustomLimits() {
        
		KKIMProp.initializeProperties();

		// No calibration limits
		StepperMotor motor = new StepperMotor("fakeMotor", ModuleID.GT, KKIMProp.getkmegaSteppersCCWLimit(), KKIMProp.getkmegaGearedStepperCWLimit());

		// Vertical Speed > 200.0 m/s
		motor.setDesiredPositionUsingCustomLimits((float) 201.0, (float) 50.0, (float) 200.0, 2950, 3770);
		Assertions.assertEquals(3770, motor.getDesiredPosition());

		// 50.0 < Vertical Speed < 200.0 m/s
		motor.setDesiredPositionUsingCustomLimits((float) 99.0, (float) 50.0, (float) 200.0, 2950, 3770);
		Assertions.assertEquals(3218, motor.getDesiredPosition());
    }
}